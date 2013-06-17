package com.extensions.dbutil;

import com.extensions.dbutil.dbcon.IDB;

public class DBFactory {
	public static Export createMySQLExport(IDB db, String schema) {
		return new DBExport(db, schema, new MySQLTypeConverter(), createDBSchemeAnalyserMySQL(db, schema));
	}
	public static SchemeAnalyser createDBSchemeAnalyserMySQL(IDB db, String schema) {
		return new DBSchemeAnalyser(db,schema,"SHOW TABLES IN $schemaName","Tables_in_$schemaName","SHOW COLUMNS IN $tableName IN $schemaName",
				"Field","Type","SHOW CREATE TABLE $schemaName.$tableName;","Create Table");
	}
}
