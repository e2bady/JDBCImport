package com.extensions.dbutil;

public class SQLAnalyser implements ISQLAnalyser {
	private static final String REPLACEMENT_VARIABLE_TABLENAME = "\\$tableName";
	private static final String REPLACEMENT_VARIABLE_SCHEMANAME = "\\$schemaName";
	
	public SQLAnalyser() {
		super();
	}

	@Override
	public String doFormat(String toFormat, final String schemaName, final String tableName) {
		toFormat = toFormat.replaceAll(REPLACEMENT_VARIABLE_SCHEMANAME, schemaName)
				.replaceAll(REPLACEMENT_VARIABLE_TABLENAME, tableName);
		return toFormat;
	}

}