package com.extensions.dbutil;

import java.util.Map;

public interface SchemeAnalyser extends ISQLAnalyser {
	Map<String, DBField[]> getDBScheme();
	String getTableCreate(String tableName);
	String getTableDrop(String tableName);

}