package com.extensions.printingutils.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.extensions.printingutils.tests.util.RandomString;

public class RandomStringTest {
	private RandomString random;
	
	@Before
	public void setUp() throws Exception {
		random = new RandomString(20);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testNextString() {
		String rndStr = random.nextString();
		String rndStr2 = random.nextString();
		
		assertNotNull(rndStr);
		assertEquals(rndStr.length(), 20);
		assertNotNull(rndStr2);
		assertEquals(rndStr2.length(), 20);
		assertNotSame(rndStr, rndStr2);
		assertFalse("RandomString where equal.", rndStr.equals(rndStr2));
	}
}
