package com.extensions.dbutil.batchexecutor;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.extensions.dbutil.dbcon.IDB;

public class DBASqlBatchImplExecutor implements Runnable {
	private final Queue<String> queue;
	private IDB db;
	private static final Logger LOG = LoggerFactory.getLogger(DBASqlBatchImplExecutor.class);
	public DBASqlBatchImplExecutor(Queue<String> queue, IDB db) {
		this.queue = queue;
		this.db = db;
	}
	public boolean sqlExec() {
		String lastStatement = "";
		List<String> lst = new ArrayList<String>(queue.size());
		try {
			Statement stmt = db.getStatement();
			while(!queue.isEmpty()) {
				if(LOG.isInfoEnabled()) {
					LOG.error("Trying to execute: " + queue.peek());
				}
				
				lastStatement = queue.poll();
				stmt.addBatch(lastStatement);
				lst.add(lastStatement);
			}
			int[] result = stmt.executeBatch();
			boolean success = true;
			StringBuilder sb = new StringBuilder("{ ");
			for(int r : result) {
				if(r != 1) success = false;
				sb.append(r);
				sb.append(", ");
			}
			sb.append(" }");
			LOG.info("forceUpdate: " + sb.toString());
			return success;
		} catch (SQLException e) {
			LOG.error("SQLError!@forceUpdate " + e.getMessage() + ":" + e.getSQLState());
		}
		return false;
	}
	@Override
	public void run() {
		this.sqlExec();
	}
}
