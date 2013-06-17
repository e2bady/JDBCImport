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
public class DB implements IDB {
	private final DBConnection connection;
	/* (non-Javadoc)
	 * @see com.extensions.dbutil.dbcon.IDB#setConnection(com.extensions.dbutil.dbcon.DBConnectionData)
	 */
	public DB(DBConnectionData condata) {
		this.connection = new DBConnection(condata);
	}
	private Encoder encoder = new DefaultEncoder();
	private Codec MYSQL_CODEC = new MySQLCodec(MySQLCodec.Mode.ANSI);
	/* (non-Javadoc)
	 * @see com.extensions.dbutil.dbcon.IDB#escapeSql(java.lang.String)
	 */
	@Override
	public String escapeSql(String str) {
		return encoder.encodeForSQL(MYSQL_CODEC, str);
	}
	/* (non-Javadoc)
	 * @see com.extensions.dbutil.dbcon.IDB#descapeSql(java.lang.String)
	 */
	@Override
	public String descapeSql(String str) {
		return MYSQL_CODEC.decode(str);
	}
	/* (non-Javadoc)
	 * @see com.extensions.dbutil.dbcon.IDB#getStatement()
	 */
	@Override
	public Statement getStatement() throws SQLException {
		return connection.getStatement();
	}
	
	/* (non-Javadoc)
	 * @see com.extensions.dbutil.dbcon.IDB#getStatement(java.lang.String)
	 */
	@Override
	public PreparedStatement getStatement(String sql) throws SQLException {
		return connection.getStatement(sql);
	}
	/* (non-Javadoc)
	 * @see com.extensions.dbutil.dbcon.IDB#getConnection()
	 */
	@Override
	public Connection getConnection() {
		return connection.getSQLConnection();
	}
}
