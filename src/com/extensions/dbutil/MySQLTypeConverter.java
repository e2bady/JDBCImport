package com.extensions.dbutil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLTypeConverter implements TypeConverter {
	/* (non-Javadoc)
	 * @see com.extensions.dbutil.TypeConverter#convert(java.sql.ResultSet, com.extensions.dbutil.DBField)
	 */
	@Override
	public Object convert(ResultSet set, DBField field) throws SQLException {
		String type = field.getType();
		if(type.matches("(?i)BIT\\(1\\)") || type.matches("(?i)BIT")) {
			return set.getBoolean(field.getName());
		} else if(type.matches("(?i)TINYINT") || 
				type.matches("(?i)SMALLINT") || 
				type.matches("(?i)SMALLINT\\([0-9]+\\)") ||
				type.matches("(?i)MEDIUMINT") || 
				type.matches("(?i)MEDIUMINT\\([0-9]+\\)") ||
				type.matches("(?i)INT") || 
				type.matches("(?i)INT\\([0-9]+\\)") ||
				type.matches("(?i)INTEGER") || 
				type.matches("(?i)INTEGER\\([0-9]+\\)") ||
				type.matches("(?i)BIGINT") || 
				type.matches("(?i)BIGINT\\([0-9]+\\)")
			) {
			return set.getInt(field.getName());
		} else if(type.matches("(?i)FLOAT\\([0-9]+\\)") ||
				type.matches("(?i)FLOAT\\([0-9]+,[0-9]\\)") ||
				type.matches("(?i)FLOAT")
				) {
			return set.getFloat(field.getName());
		} else if(type.matches("(?i)DOUBLE\\([0-9]+\\)") ||
				type.matches("(?i)DOUBLE\\([0-9]+,[0-9]\\)") ||
				type.matches("(?i)DOUBLE")
				) {
			return set.getDouble(field.getName());
		} else if(type.matches("(?i)DECIMAL\\([0-9]+\\)") ||
				type.matches("(?i)DECIMAL\\([0-9]+,[0-9]\\)") ||
				type.matches("(?i)DECIMAL")
				) {
			return set.getBigDecimal(field.getName());
		} else if(type.matches("(?i)DATE") || type.matches("(?i)TIMESTAMP") 
				|| type.matches("(?i)DATETIME") ||
				type.matches("(?i)YEAR") || type.matches("(?i)YEAR\\([24]+\\)")) {
			return set.getDate(field.getName());
		} else if(type.matches("(?i)TIME")) {
			return set.getTime(field.getName());
		} else if(type.matches("(?i)CHAR") || type.matches("(?i)CHAR\\([0-9]+\\)")
				|| type.matches("(?i)VARCHAR") || type.matches("(?i)VARCHAR\\([0-9]+\\)")
				|| type.matches("(?i)TINYTEXT")
				|| type.matches("(?i)TEXT")
				|| type.matches("(?i)MEDIUMTEXT")
				|| type.matches("(?i)LONGTEXT")
				|| type.matches("(?i)ENUM\\(.*\\)")
				|| type.matches("(?i)SET\\(.*\\)")) {
			return set.getString(field.getName());
		} else if(type.matches("(?i)BINARY") 
				|| type.matches("(?i)BINARY\\([0-9]+\\)")
				|| type.matches("(?i)LONGBLOB")
				|| type.matches("(?i)MEDIUMBLOB")
				|| type.matches("(?i)BLOB")
				|| type.matches("(?i)TINYBLOB")
				|| type.matches("(?i)VARBINARY") 
				|| type.matches("(?i)VARBINARY\\([0-9]+\\)")
				|| type.matches("(?i)BIT\\([0-9]+\\)")) {
			return set.getBytes(field.getName());
		}
		return null;
	}
}