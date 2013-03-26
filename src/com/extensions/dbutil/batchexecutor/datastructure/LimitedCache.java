package com.extensions.dbutil.batchexecutor.datastructure;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.extensions.dbutil.batchexecutor.DBASqlBatchImpl;

public class LimitedCache<K, V extends SqlEntry> implements Map<K, SqlEntry>{
	private Map<K, SqlEntry> map;
	@SuppressWarnings("unchecked")
	public LimitedCache(int maxSize) {
		map = new LimitedMap<K>(maxSize);
		DBASqlBatchImpl.addCache((LimitedCache<Object, SqlEntry>) this);
	}
	@Override
	public void clear() {
		map.clear();
	}
	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}
	@Override
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}
	@Override
	public Set<java.util.Map.Entry<K, SqlEntry>> entrySet() {
		return map.entrySet();
	}
	@Override
	public SqlEntry get(Object key) {
		return map.get(key);
	}
	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}
	@Override
	public Set<K> keySet() {
		return map.keySet();
	}
	@Override
	public SqlEntry put(K key, SqlEntry value) {
		return map.put(key, value);
	}
	@Override
	public void putAll(Map<? extends K, ? extends SqlEntry> m) {
		map.putAll(m);
	}
	@Override
	public SqlEntry remove(Object key) {
		return map.remove(key);
	}
	@Override
	public int size() {
		return map.size();
	}
	@Override
	public Collection<SqlEntry> values() {
		return map.values();
	}
}