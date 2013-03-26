package com.extensions.dbutil.dbcon;

public class DBConnectionData {
	public String userName;
	public String password;
	public String dbms;
	public String serverName;
	public int portNumber;
	public String dbName;

	public DBConnectionData(String userName, String password, String dbms,
			String serverName, int portNumber, String dbName) {
		super();
		this.userName = userName;
		this.password = password;
		this.dbms = dbms;
		this.serverName = serverName;
		this.portNumber = portNumber;
		this.dbName = dbName;
	}
	
	public DBConnectionData() {
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDbms() {
		return dbms;
	}

	public void setDbms(String dbms) {
		this.dbms = dbms;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public int getPortNumber() {
		return portNumber;
	}

	public void setPortNumber(int portNumber) {
		this.portNumber = portNumber;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	@Override
	public String toString() {
		return String
				.format("DBConnectionData [getUserName()=%s, getPassword()=%s, getDbms()=%s, getServerName()=%s, getPortNumber()=%s, getDbName()=%s]",
						getUserName(), getPassword(), getDbms(),
						getServerName(), getPortNumber(), getDbName());
	}

	
}