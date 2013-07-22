package com.extensions.dbutil.batchexecutor.datastructure;

import java.util.LinkedHashMap;
import java.util.Map;

import com.extensions.dbutil.batchexecutor.DBASqlBatch;

public class LimitedMap<K> extends LinkedHashMap<K, SqlEntry> {
	private static final long serialVersionUID = 5836778008607172932L;
	private int max;
	private DBASqlBatch batch;
	public LimitedMap(DBASqlBatch batch, int max) {
		super(max);
		this.max = max;
		this.batch = batch;
	}
	@Override
	protected boolean removeEldestEntry(Map.Entry<K,SqlEntry> eldest) {
		boolean dec = size() > max;
		if( dec && eldest.getValue().isNeedsUpdate()) {
			this.batch.update();
		}
		return dec;
	}
}