package com.extensions.printingutils.tests;

public class PrimitivesCombination {
	final private int i;
	final private String s;
	final private PrimitivesCombination pc;
	public PrimitivesCombination(int i, String s, PrimitivesCombination pc) {
		super();
		this.i = i;
		this.s = s;
		this.pc = pc;
	}
	public PrimitivesCombination(int i, String s) {
		super();
		this.i = i;
		this.s = s;
		this.pc = null;
	}
	@Override public String toString() {
		return "(" + i + ", " + s + (pc != null ? pc.toString() : "") + ")";
	}
	public int getI() {
		return i;
	}
	public String getS() {
		return s;
	}
	public PrimitivesCombination getPc() {
		return pc;
	}
}
