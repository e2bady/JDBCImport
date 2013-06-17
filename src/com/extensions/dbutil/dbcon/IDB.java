package com.extensions.dbutil.dbcon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public interface IDB {
	String escapeSql(String str);
	String descapeSql(String str);
	Statement getStatement() throws SQLException;
	PreparedStatement getStatement(String sql) throws SQLException;
	Connection getConnection();
}