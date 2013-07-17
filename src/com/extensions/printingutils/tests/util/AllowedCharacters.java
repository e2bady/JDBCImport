package com.extensions.printingutils.tests.util;

public class AllowedCharacters implements IAllowedCharacters {
	private final char[] symbols;

	public AllowedCharacters() {
		symbols = new char[36];
		for (int idx = 0; idx < 10; ++idx)
			symbols[idx] = (char) ('0' + idx);
		for (int idx = 10; idx < 36; ++idx)
			symbols[idx] = (char) ('a' + idx - 10);
	}
	/* (non-Javadoc)
	 * @see com.extensions.printingutils.tests.util.IAllowedCharacters#get(int)
	 */
	@Override
	public char get(int i) {
		if(i >= 0 && i < symbols.length)
			return symbols[i];
		throw new IllegalArgumentException("iterator not in range!");
	}
	/* (non-Javadoc)
	 * @see com.extensions.printingutils.tests.util.IAllowedCharacters#size()
	 */
	@Override
	public int size() {
		return symbols.length;
	}
}
