package com.extensions.dbutil;

public interface ISQLAnalyser {
	String doFormat(String toFormat, String schemaName, String tableName);
}