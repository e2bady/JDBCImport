package com.extensions.dbutil.batchexecutor.datastructure;

public abstract class SqlEntry {
	private boolean needsUpdate = false;
	public boolean isNeedsUpdate() {
		return needsUpdate;
	}
	public void setNeedsUpdate() {
		this.needsUpdate = true;
	}
	public void unsetNeedsUpdate() {
		this.needsUpdate = false;
	}
}