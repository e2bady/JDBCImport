package com.extensions.dbutil;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SQLExport extends SQLAnalyser {
	private final String prefix;
	private final String preTable;
	private final String postTable;
	private final String postfix;
	private final Logger log = (Logger) LoggerFactory
				.getLogger(MySQLExport.class);

	public SQLExport(String prefix, String preTable, String postTable,
			String postfix) {
		super();
		this.prefix = prefix;
		this.preTable = preTable;
		this.postTable = postTable;
		this.postfix = postfix;
	}

	public final String mySQLExport(String schema, Export dbe) {
		StringBuilder sb = new StringBuilder();
		sb.append(super.doFormat(getPrefix(), schema, ""));
		Map<String,List<String>> insertStatements = dbe.createInsertStatements();
		Map<String, String> createStatements = dbe.generateCreateDrop();
		for(String tblName : insertStatements.keySet()) {
			log.error("exporting " + tblName + " ... ");
			sb.append(super.doFormat(getPretable(), schema, tblName));
			sb.append(createStatements.get(tblName));
			sb.append('\n');
			for(String stmt : insertStatements.get(tblName)) {
				sb.append(stmt);
				sb.append('\n');
			}
			sb.append(super.doFormat(getPosttable(), schema, tblName));
		}
		sb.append(super.doFormat(getPostfix(), schema, ""));
		return sb.toString();
	}

	private String getPrefix() {
		return prefix;
	}

	private String getPretable() {
		return preTable;
	}

	private String getPosttable() {
		return postTable;
	}

	private String getPostfix() {
		return postfix;
	}

}