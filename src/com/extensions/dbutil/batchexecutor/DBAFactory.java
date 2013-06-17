package com.extensions.dbutil.batchexecutor;

import com.extensions.dbutil.dbcon.IDB;

public class DBAFactory {
	private static DBASqlBatch sqlBatch;
	public static void setBatch(IDB db) {
		sqlBatch = new DBASqlBatchImpl(db);
	}
	public static DBASqlBatch getBatch() {
		return sqlBatch;
	}
}
