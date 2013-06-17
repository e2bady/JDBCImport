package com.extensions.dbutil;

import java.util.List;
import java.util.Map;

public interface Export {
	Map<String, String> generateCreateDrop();
	Map<String, List<String>> createInsertStatements();
	Map<String, List<Map<String, Object>>> readDB();
	int hashCode();
	boolean equals(Object obj);
}