JDBCImport
==========

imports and exports data from MySQL ( adding other databases is really easy ) directly from java without the need for shell - access.



It's really easy:

USAGE:

private final DBConnectionData condata = new DBConnectionData(
				"myuser", // username
				"mypassword", //password
				"mysql", // driver 
				"localhost", // host
				3306, // port
				"mydb"); // database name
				
DB.setConnection(condata); // to setup the connection.

// after that you can do an export with one statement

String export = new MySQLExport().mySQLExport("mydb");

// and replace it entirely with

SQLImport.importSQL(export);


