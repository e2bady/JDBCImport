package com.extensions.dbutil.batchexecutor;

public class DBAFactory {
	private static DBASqlBatch sqlBatch = new DBASqlBatchImpl();
	public static DBASqlBatch getBatch() {
		return sqlBatch;
	}
}
