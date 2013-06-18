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
	private static final String prefix = "CREATE DATABASE  IF NOT EXISTS `$schemaName`\n"+
			"/*!40100 DEFAULT CHARACTER SET utf8 */;\n"+
			"USE `$schemaName`;\n"+
			"/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;\n"+
			"/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;\n"+
			"/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;\n"+
			"/*!40101 SET NAMES utf8 */;\n"+
			"/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;\n"+
			"/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;\n"+
			"/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;\n"+
			"/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;\n";
	private static final String preTable = "/*!40000 ALTER TABLE `$tableName` DISABLE KEYS */;\n";
	private static final String postTable = "/*!40000 ALTER TABLE `$tableName` ENABLE KEYS */;\n" + 
												"UNLOCK TABLES;\n";
	private static final String postfix = 
			"/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;\n"+
			"/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;\n"+
			"/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;\n"+
			"/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;\n"+
			"/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;\n"+
			"/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;\n"+
			"/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;\n";
	public static ISqlExport MySQLExport() {
		return new SQLExport(prefix, preTable, postTable, postfix);
	}
}
