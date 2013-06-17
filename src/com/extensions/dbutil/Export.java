package com.extensions.dbutil;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Export {

	public abstract Map<String, String> generateCreateDrop();

	public abstract Map<String, List<String>> createInsertStatements();

	public abstract Map<String, List<Map<String, Object>>> readDB();

	public abstract int hashCode();

	public abstract boolean equals(Object obj);

}