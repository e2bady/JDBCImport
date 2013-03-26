package com.extensions.dbutil;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface TypeConverter {
	Object convert(ResultSet set, DBField field) throws SQLException;
}