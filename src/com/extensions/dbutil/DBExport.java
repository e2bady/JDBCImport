package com.extensions.dbutil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.extensions.dbutil.dbcon.DB;
import com.extensions.printingutils.PrettyPrinter;

public class DBExport {
	private static final Logger log = (Logger) LoggerFactory
			.getLogger(DBExport.class);
	private final TypeConverter converter;
	private final String schema;
	private final DBSchemeAnalyser analyser;
	private final Map<String, DBField[]> dbdef;
	public DBExport(String schema) {
		this.schema = schema;
		analyser = new DBSchemeAnalyser(schema);
		converter = new MySQLTypeConverter();//apply default
		dbdef = this.analyser.getDBScheme();
	}
	public DBExport(String schema, TypeConverter converter, DBSchemeAnalyser analyser) {
		this.schema = schema;
		this.converter = converter;//apply default
		this.analyser = analyser;
		dbdef = this.analyser.getDBScheme();
	}
	public Map<String, String> generateCreateDrop() {
		Map<String, String> map = new HashMap<String, String>(dbdef.keySet().size());
		for(String tablename : dbdef.keySet()) {
			map.put(tablename, this.analyser.getTableDrop(tablename) + ";\n" + this.analyser.getTableCreate(tablename) + ";\n");
		}
		return map;
	}
	public Map<String,List<String>> createInsertStatements() {
		return generateInserts(readDB());
	}
	private Map<String,List<String>> generateInserts(
			Map<String, List<Map<String, Object>>> db) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String,List<String>> insertStatements = new HashMap<String, List<String>>();
		for(String tablename : db.keySet()) {
			List<Map<String, Object>> table = db.get(tablename);
			log.error("preparing " + tablename + " with " + (table != null ? table.size() : 0) + " entries.");
			if(table != null) {
				for(Map<String, Object> tr : table) {
					StringBuilder insertStmt = new StringBuilder("INSERT INTO ");
					insertStmt.append(schema);
					insertStmt.append('.');
					insertStmt.append(tablename);
					insertStmt.append(" (");
					int i=0;
					for(String th : tr.keySet()) {
						insertStmt.append(th);
						if(++i < tr.size()) {
							insertStmt.append(',');
						}
					}
					insertStmt.append(") VALUES (");
					i=0;
					for(String th : tr.keySet()) {
						if(tr.get(th) instanceof String) {
							insertStmt.append('\'');
							insertStmt.append(DB.escapeSql((String)tr.get(th)));
							insertStmt.append('\'');
						} else if (tr.get(th) instanceof Date) {
							insertStmt.append('\'');
							insertStmt.append(df.format((Date)tr.get(th)));
							insertStmt.append('\'');
						}
						else 
							insertStmt.append(tr.get(th));
						if(++i < tr.size()) {
							insertStmt.append(',');
						}
					}
					insertStmt.append(");");
					if(!insertStatements.containsKey(tablename)) {
						insertStatements.put(tablename, new ArrayList<String>());
					}
					insertStatements.get(tablename).add(insertStmt.toString());
				}
			}
		}
		return insertStatements;
	}
	public Map<String, List<Map<String, Object>>> readDB() {
		Map<String, List<Map<String, Object>>> db = new HashMap<String, List<Map<String,Object>>>(dbdef.keySet().size());
		for(String tablename : dbdef.keySet()) {
			List<Map<String, Object>> tableEntries = getAllFor(tablename, dbdef.get(tablename));
			db.put(tablename, tableEntries);
		}
		return db;
	}
	
	public static final boolean dbequals(Map<String, List<Map<String, Object>>> d1, Map<String, List<Map<String, Object>>> d2) {
		if(!collectionEquals(d1.keySet(), d2.keySet())) return false;
		for(String key : d1.keySet()) {
			if(!collectionEquals(d1.get(key), d2.get(key))) return false;
		}
		return true;
	}
	
	private static <E> boolean collectionEquals(Collection<E> keySet, Collection<E> keySet2) {
		if(keySet.size() != keySet2.size()) {
			return false;
		}
		for(E s : keySet) {
			if(!keySet2.contains(s)) return false;
		}
		return true;
	}
	@SuppressWarnings("unused")
	private void printTable(List<Map<String, Object>> tableEntries,
			String tablename) {
		if(tableEntries.size() > 0) { 
			Set<String> rownames = tableEntries.get(0).keySet();
			String[][] table = new String[tableEntries.size() + 1][];
			int j = 0;
			int i = 0;
			String[] tr = new String[rownames.size()];
			for(String rowname : rownames) {
				tr[i++] = rowname;
			}
			table[j++] = tr;
			for(Map<String, Object> trM : tableEntries) {
				tr = new String[rownames.size()];
				i = 0;
				for(String rowname : rownames) {
					tr[i++] = (String) (trM.containsKey(rowname) ? (trM.get(rowname) == null ? "NULL" : trM.get(rowname)).toString() : "NULL");
				}
				table[j++] = tr;
			}
			log.error("Table: " + tablename + "\n" + PrettyPrinter.print(table));
		}
	}
	private List<Map<String, Object>> getAllFor(String tablename, DBField[] fields) {
		List<Map<String, Object>> lst = new ArrayList<Map<String,Object>>();
		try {
			Statement stmt = DB.getStatement("SELECT * from " + this.schema+"."+tablename);
			ResultSet set = stmt.executeQuery("SELECT * from " + this.schema+"."+tablename);
			while(set != null && set.next()) {
				Map<String, Object> rowMap = new HashMap<String, Object>();
				for(DBField f : fields) {
					rowMap.put(f.getName(),converter.convert(set, f));
				}
				lst.add(rowMap);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lst;
	}
}
