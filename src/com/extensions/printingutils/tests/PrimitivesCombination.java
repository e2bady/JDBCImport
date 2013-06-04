package com.extensions.printingutils.tests;

public class PrimitivesCombination {
	public static PrimitivesCombination createPrimitivesCombination(int i,
			String s) {
		return new PrimitivesCombination(i, s);
	}
	public static PrimitivesCombination createPrimitivesCombination(int i,
			String s, PrimitivesCombination pc) {
		return new PrimitivesCombination(i, s, pc);
	}
	final private int i;
	final private String s;
	final private PrimitivesCombination pc;
	private PrimitivesCombination(int i, String s, PrimitivesCombination pc) {
		super();
		this.i = i;
		this.s = s;
		this.pc = pc;
	}
	private PrimitivesCombination(int i, String s) {
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + i;
		result = prime * result + ((pc == null) ? 0 : pc.hashCode());
		result = prime * result + ((s == null) ? 0 : s.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PrimitivesCombination other = (PrimitivesCombination) obj;
		if (i != other.i)
			return false;
		if (pc == null) {
			if (other.pc != null)
				return false;
		} else if (!pc.equals(other.pc))
			return false;
		if (s == null) {
			if (other.s != null)
				return false;
		} else if (!s.equals(other.s))
			return false;
		return true;
	}
}