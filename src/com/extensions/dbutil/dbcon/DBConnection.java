package com.extensions.dbutil.dbcon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;

class DBConnection {
	private static final Logger LOG = LoggerFactory.getLogger(DBConnection.class);
	private DBConnectionData data;
	private Connection conn;
	
	public Connection getSQLConnection() {
		return conn;
	}
	
	public DBConnection(DBConnectionData data) {
		this.data = data;
		if(LOG.isDebugEnabled())
			LOG.debug(data.toString());
		try {
			getConnection();
		} catch (SQLException e) {
			LOG.error("SQLException caught during startup. Cannot connect to the Database.", e);
		}
	}
	public Statement getStatement() throws SQLException {
		if(this.conn.isClosed() || !this.conn.isValid(1))
			this.getConnection();
		return conn.createStatement();
	}
	private void getConnection() throws SQLException {
	    conn = null;
	    Properties connectionProps = new Properties();
	    connectionProps.put("user", this.data.userName); //$NON-NLS-1$
	    connectionProps.put("password", this.data.password); //$NON-NLS-1$

	    if (this.data.dbms.equals("mysql")) { //$NON-NLS-1$
	    	try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				LOG.error("MySql Driver seems to be missing.", e);
			}
	        conn = DriverManager.getConnection(
	                   "jdbc:" + this.data.dbms + "://" + //$NON-NLS-1$ //$NON-NLS-2$
	                   this.data.serverName +
	                   ":" + this.data.portNumber + "/", //$NON-NLS-1$ //$NON-NLS-2$
	                   connectionProps);
	    } else if (this.data.dbms.equals("derby")) { //$NON-NLS-1$
	        conn = DriverManager.getConnection(
	                   "jdbc:" + this.data.dbms + ":" + //$NON-NLS-1$ //$NON-NLS-2$
	                   this.data.dbName +
	                   ";create=true", //$NON-NLS-1$
	                   connectionProps);
	    }
	    if(LOG.isDebugEnabled())
	    	LOG.debug("Database connection is up and running"); //$NON-NLS-1$
	}
	public PreparedStatement getStatement(String sql) throws SQLException {
		if(this.conn.isClosed() || !this.conn.isValid(1))
			this.getConnection();
		return conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	}
}
