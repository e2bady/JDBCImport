package com.extensions.dbutil.batchexecutor;

public interface DBASqlBatch {
	void addSql(String sql);
	void update();
	void forceUpdate();
	boolean forceSyncUpdate();
}
