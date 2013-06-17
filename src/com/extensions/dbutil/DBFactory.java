package com.extensions.dbutil;

import com.extensions.dbutil.dbcon.IDB;

public class DBFactory {
	public static DBExport createMySQLExport(IDB db, String schema) {
		return new DBExport(db, schema, new MySQLTypeConverter(), new DBSchemeAnalyser(db, schema));
	}
}
