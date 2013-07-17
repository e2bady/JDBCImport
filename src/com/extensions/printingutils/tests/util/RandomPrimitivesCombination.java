package com.extensions.printingutils.tests.util;

import java.util.Random;

public class RandomPrimitivesCombination {
	private final Random random = new Random();
	private final RandomString randomString;

	public RandomPrimitivesCombination(int stringLength) {
		randomString = new RandomString(new Random(), new AllowedCharacters(), 20);
	}
	
	public final PrimitivesCombination nextPrimitivesCombination() {
		if(random.nextInt(5) == 1) { // one in 4 chance.
			return PrimitivesCombination.createPrimitivesCombination(
					random.nextInt(), randomString.nextString());
		} else {
			return PrimitivesCombination.createPrimitivesCombination(
					random.nextInt(), randomString.nextString(), nextPrimitivesCombination());
		}
	}
}
