package com.extensions.printingutils.tests.util;

import java.util.Random;

import com.extensions.printingutils.tests.util.IAllowedCharacters;

public class RandomString {
	private final Random random;
	private IAllowedCharacters characters;
	private final char[] buf;

	public RandomString(Random random, IAllowedCharacters characters, int length) {
		if (length < 1)
			throw new IllegalArgumentException("length < 1: " + length);
		if (random == null)
			throw new IllegalArgumentException("Random may not be null.");
		if (characters == null)
			throw new IllegalArgumentException("Characters may not be null.");
		this.random = random;
		this.characters = characters;
		buf = new char[length];
	}
	public String nextString() {
		for (int idx = 0; idx < buf.length; ++idx)
			buf[idx] = this.characters.get(
							this.random.nextInt(this.characters.size())
					   );
		return new String(buf);
	}
}

