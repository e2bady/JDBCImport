package com.extensions.dbutil.batchexecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.extensions.dbutil.batchexecutor.datastructure.LimitedCache;
import com.extensions.dbutil.batchexecutor.datastructure.SqlEntry;
import com.extensions.dbutil.dbcon.IDB;

public class DBASqlBatchImpl implements DBASqlBatch {
	private static final Logger LOG = LoggerFactory.getLogger(DBASqlBatchImpl.class);
	private static final int MAX_QUEUE_SIZE = Integer.MAX_VALUE;
	private Queue<String> queue = new ConcurrentLinkedQueue<String>();
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	private Thread thread = null;
	private boolean nextExecute;
	private IDB db;
	private static List<LimitedCache<Object, SqlEntry>> caches = new ArrayList<LimitedCache<Object, SqlEntry>>();
	public static void addCache(LimitedCache<Object, SqlEntry> e) {
		caches.add(e);
	}
	public DBASqlBatchImpl(IDB db) {
		this.db = db;
	}
	@Override
	public void addSql(String sql) {
		lock.readLock().lock(); 
		try {
			queue.add(sql);
			if(LOG.isDebugEnabled()) {
				LOG.debug("added: " + sql);
			}
		} finally {
			lock.readLock().unlock();
		}
		this.softupdate();
	}
	private void softupdate() {
		if((queue.size() > MAX_QUEUE_SIZE || nextExecute) && (thread == null || !thread.isAlive())) {
			forceUpdate();
		}
	}
	@Override
	public void update() {
		if((queue.size() > MAX_QUEUE_SIZE || nextExecute) && (thread == null || !thread.isAlive())) {
			forceUpdate();
		} else {
			nextExecute = true;
		}
	}
	@Override
	public void forceUpdate() {
		lock.writeLock().lock();
		try {
			if(!queue.isEmpty()) {
				thread = new Thread(new DBASqlBatchImplExecutor(this.queue,db));
				thread.start();
				this.nextExecute = false;
				this.queue = new ConcurrentLinkedQueue<String>();
			}
			for(LimitedCache<Object, SqlEntry> e : caches) {
				for(Object key : e.keySet()) {
					e.get(key).unsetNeedsUpdate();
				}
			}
		} finally {
			lock.writeLock().unlock();
		}
	}
	@Override
	public boolean forceSyncUpdate() {
		boolean success = false;
		lock.writeLock().lock();
		try {
			if(!queue.isEmpty()) {
				DBASqlBatchImplExecutor exec = new DBASqlBatchImplExecutor(this.queue,db);
				success = exec.sqlExec();
				this.nextExecute = false;
				this.queue = new ConcurrentLinkedQueue<String>();
			}
			for(LimitedCache<Object, SqlEntry> e : caches) {
				for(Object key : e.keySet()) {
					e.get(key).unsetNeedsUpdate();
				}
			}
		} finally {
			lock.writeLock().unlock();
		}
		return !success;
	}
}
