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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.extensions.dbutil.dbcon.IDB;

public class DBExport implements Export {
	private static final Logger log = (Logger) LoggerFactory.getLogger(DBExport.class);
	private final TypeConverter converter;
	private final String schema;
	private final SchemeAnalyser analyser;
	private final Map<String, DBField[]> dbdef;
	private IDB db;
	public DBExport(IDB db, String schema, TypeConverter converter, SchemeAnalyser analyser) {
		this.db = db;
		this.schema = schema;
		this.converter = converter;//apply default
		this.analyser = analyser;
		dbdef = this.analyser.getDBScheme();
	}
	/* (non-Javadoc)
	 * @see com.extensions.dbutil.Export#generateCreateDrop()
	 */
	@Override
	public Map<String, String> generateCreateDrop() {
		Map<String, String> map = new HashMap<String, String>(dbdef.keySet().size());
		for(String tablename : dbdef.keySet()) {
			map.put(tablename, this.analyser.getTableDrop(tablename) + ";\n" + this.analyser.getTableCreate(tablename) + ";\n");
		}
		return map;
	}
	/* (non-Javadoc)
	 * @see com.extensions.dbutil.Export#createInsertStatements()
	 */
	@Override
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
					String insertStmt = createInsert(df, tablename, tr);
					if(!insertStatements.containsKey(tablename)) {
						insertStatements.put(tablename, new ArrayList<String>());
					}
					insertStatements.get(tablename).add(insertStmt);
				}
			}
		}
		return insertStatements;
	}
	private String createInsert(DateFormat df, String tablename,
			Map<String, Object> col2Value) {
		StringBuilder insertStmt = new StringBuilder("INSERT INTO ");
		insertStmt.append(schema);
		insertStmt.append('.');
		insertStmt.append(tablename);
		insertStmt.append(" (");
		int i=0;
		for(String th : col2Value.keySet()) {
			insertStmt.append(th);
			if(++i < col2Value.size()) {
				insertStmt.append(',');
			}
		}
		insertStmt.append(") VALUES (");
		i=0;
		for(String th : col2Value.keySet()) {
			if(col2Value.get(th) instanceof String) {
				insertStmt.append('\'');
				insertStmt.append(this.db.escapeSql((String)col2Value.get(th)));
				insertStmt.append('\'');
			} else if (col2Value.get(th) instanceof Date) {
				insertStmt.append('\'');
				insertStmt.append(df.format((Date)col2Value.get(th)));
				insertStmt.append('\'');
			}
			else 
				insertStmt.append(col2Value.get(th));
			if(++i < col2Value.size()) {
				insertStmt.append(',');
			}
		}
		insertStmt.append(");");
		return insertStmt.toString();
	}
	/* (non-Javadoc)
	 * @see com.extensions.dbutil.Export#readDB()
	 */
	@Override
	public Map<String, List<Map<String, Object>>> readDB() {
		Map<String, List<Map<String, Object>>> db = new HashMap<String, List<Map<String,Object>>>(dbdef.keySet().size());
		for(String tablename : dbdef.keySet()) {
			List<Map<String, Object>> tableEntries = getAllFor(tablename, dbdef.get(tablename));
			db.put(tablename, tableEntries);
		}
		return db;
	}
	
	private <E> boolean collectionEquals(Collection<E> keySet, Collection<E> keySet2) {
		if(keySet.size() != keySet2.size()) {
			return false;
		}
		for(E s : keySet) {
			if(!keySet2.contains(s)) return false;
		}
		return true;
	}
	private List<Map<String, Object>> getAllFor(String tablename, DBField[] fields) {
		List<Map<String, Object>> lst = new ArrayList<Map<String,Object>>();
		try {
			Statement stmt = db.getStatement("SELECT * from " + this.schema+"."+tablename);
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
	/* (non-Javadoc)
	 * @see com.extensions.dbutil.Export#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((analyser == null) ? 0 : analyser.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see com.extensions.dbutil.Export#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Export) {
			Map<String, List<Map<String, Object>>> d1 = this.readDB();
			Map<String, List<Map<String, Object>>> d2 = ((Export)obj).readDB();
			if(!collectionEquals(d1.keySet(), d2.keySet())) return false;
			for(String key : d1.keySet()) {
				if(!collectionEquals(d1.get(key), d2.get(key))) return false;
			}
			return true;
		}
		return false;
	}
}
