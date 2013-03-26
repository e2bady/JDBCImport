package com.extensions.dbutil.batchexecutor.datastructure;

import java.util.LinkedHashMap;
import java.util.Map;

import com.extensions.dbutil.batchexecutor.DBAFactory;

public class LimitedMap<K> extends LinkedHashMap<K, SqlEntry> {
	private static final long serialVersionUID = 5836778008607172932L;
	private int max;
	public LimitedMap(int max) {
		super(max);
		this.max = max;
	}
	@Override
	protected boolean removeEldestEntry(Map.Entry<K,SqlEntry> eldest) {
		boolean dec = size() > max;
		if( dec && eldest.getValue().isNeedsUpdate()) {
			DBAFactory.getBatch().update();
		}
		return dec;
	}
}