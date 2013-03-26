package com.extensions.dbutil.dbcon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.owasp.esapi.Encoder;
import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.MySQLCodec;
import org.owasp.esapi.reference.DefaultEncoder;

/**
 * This class is only a wrapper around {@link DBConnection}, to avoid misuse and to be able to 
 * add logic somewhere if the need should ever arise.
 * @author XTFIWS
 *
 */
public class DB {
	private static DBConnection connection;
	public static final void setConnection(DBConnectionData connectionData) {
		connection = new DBConnection(connectionData);
	}
	private static Encoder encoder = new DefaultEncoder();
	private static Codec MYSQL_CODEC = new MySQLCodec(MySQLCodec.Mode.ANSI);
	public static String escapeSql(String str) {
		return encoder.encodeForSQL(MYSQL_CODEC, str);
	}
	public static String descapeSql(String str) {
		return MYSQL_CODEC.decode(str);
	}
	public static Statement getStatement() throws SQLException {
		return connection.getStatement();
	}
	
	public static PreparedStatement getStatement(String sql) throws SQLException {
		return connection.getStatement(sql);
	}
	public static Connection getConnection() {
		return connection.getSQLConnection();
	}
}
