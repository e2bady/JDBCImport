package com.extensions.dbutil;

import java.util.LinkedList;
import java.util.List;

import com.extensions.dbutil.batchexecutor.DBAFactory;

public class SQLImport {
	public boolean importSQL(String sql) {
		String[] stmts = split(sql);
		for(String s : stmts) {
			DBAFactory.getBatch().addSql(s);
		}
		return DBAFactory.getBatch().forceSyncUpdate();
	}

	public String[] split(String sql) {
		List<String> lst =  new LinkedList<String>();
		int i=0;
		int lastsplit = 0;
		char[] ca = sql.toCharArray();
		boolean isvalue = false;
		char nextendCharacter = ' ';
		for(i=0;i<ca.length;i++) {
			if(isvalue == false && (ca[i] == '\'' || ca[i] == '"')) {
				nextendCharacter = ca[i];
				isvalue = true;
			} else if(isvalue == true && ca[i] == nextendCharacter) {
				isvalue = false;
			} else if(isvalue == false && ca[i] == ';') {
				//split here
				lst.add(sql.substring(lastsplit, i));
				lastsplit = i+1;
			}
		}
		return lst.toArray(new String[lst.size()]);
	}
}
