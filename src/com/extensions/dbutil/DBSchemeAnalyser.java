package com.extensions.dbutil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.extensions.dbutil.dbcon.IDB;

public class DBSchemeAnalyser extends SQLAnalyser implements SchemeAnalyser {
	private static final String EMPTYSTRING = "";
	private final String schemaName;
	private final String getTablesSQL;
	private final String ColNameTablesSQL;
	private final String getTableDefinitionColName;
	private final String getTableDefinitionColType;
	private final String showColumnsSQL;
	private final String tableCreateSQL;
	private final String tableCreateColName;
	private IDB db;
	public DBSchemeAnalyser(final IDB db, final String schema) {
		this.schemaName = schema;
		this.db = db;
		this.getTablesSQL = "SHOW TABLES IN $schemaName";
		this.ColNameTablesSQL = "Tables_in_$schemaName";
		this.getTableDefinitionColType = "Type";
		this.getTableDefinitionColName = "Field";
		this.showColumnsSQL = "SHOW COLUMNS IN $tableName IN $schemaName";
		this.tableCreateSQL = "SHOW CREATE TABLE $schemaName.$tableName;";
		this.tableCreateColName = "Create Table";
	}
	public DBSchemeAnalyser(IDB db, final String schema, final String getTablesSQL, 
			final String ColNameTablesSQL, final String getColumnsSQL, 
			final String showColumnsSQL,
			final String getTableDefinitionColName, 
			final String getTableDefinitionColType,
			final String tableCreateSQL,
			final String tableCreateColName) {
		this.schemaName = schema;
		this.getTablesSQL = getTablesSQL;
		this.showColumnsSQL = showColumnsSQL;
		this.ColNameTablesSQL = ColNameTablesSQL;
		this.getTableDefinitionColType = getTableDefinitionColType;
		this.getTableDefinitionColName = getTableDefinitionColName;
		this.tableCreateSQL = tableCreateSQL;
		this.tableCreateColName = tableCreateColName;
		this.db = db;
	}
	/* (non-Javadoc)
	 * @see com.extensions.dbutil.SchemeAnalyser#getDBScheme()
	 */
	@Override
	public final Map<String, DBField[]> getDBScheme() {
		Set<String> tableNames = getTableNames(this.schemaName);
		Map<String, DBField[]> tblDefs = new HashMap<String, DBField[]>(tableNames.size());
		for(String table : tableNames) {
			Set<DBField> tblFields = getTableFields(this.schemaName, table);
			tblDefs.put(table, tblFields.toArray(new DBField[tblFields.size()]));
		}
		return tblDefs;
	}
	
	/* (non-Javadoc)
	 * @see com.extensions.dbutil.SchemeAnalyser#getTableCreate(java.lang.String)
	 */
	@Override
	public final String getTableCreate(final String tableName) {
		String sstmt = doFormat(tableCreateSQL, this.schemaName, tableName);
		try {
			Statement stmt = db.getStatement(sstmt);
			ResultSet set = stmt.executeQuery(sstmt);
			if(set != null && set.next()) {
				return set.getString(this.tableCreateColName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}
	/* (non-Javadoc)
	 * @see com.extensions.dbutil.SchemeAnalyser#getTableDrop(java.lang.String)
	 */
	@Override
	public final String getTableDrop(final String tableName) {
		return doFormat("DROP TABLE IF EXISTS $schemaName.$tableName;", this.schemaName, tableName);
	}
	
	private Set<String> getEntries(final String statement , final String colName) {
		Set<String> tableNames = new HashSet<String>();
		try {
			Statement stmt = db.getStatement(statement);
			ResultSet set = stmt.executeQuery(statement);
			while(set != null && set.next()) {
				tableNames.add(set.getString(colName));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tableNames;
	}
	
	private Set<String> getTableNames(final String schemaName) {
		return getEntries(doFormat(getTablesSQL, schemaName, EMPTYSTRING), doFormat(ColNameTablesSQL, schemaName, EMPTYSTRING));
	}
	private Set<DBField> getTableFields(final String schemaName, final String tableName) {
		String statement = doFormat(showColumnsSQL,schemaName,tableName);
		Set<DBField> tableNames = new HashSet<DBField>();
		try {
			Statement stmt = db.getStatement(statement);
			ResultSet set = stmt.executeQuery(statement);
			while(set != null && set.next()) {
				DBField f = new DBField();
				f.setName(set.getString(getTableDefinitionColName));
				f.setType(set.getString(getTableDefinitionColType));
				tableNames.add(f);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tableNames;
	}
}
