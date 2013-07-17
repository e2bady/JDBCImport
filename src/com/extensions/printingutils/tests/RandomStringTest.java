package com.extensions.printingutils.tests;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.extensions.printingutils.tests.util.AllowedCharacters;
import com.extensions.printingutils.tests.util.RandomString;

public class RandomStringTest {
	private RandomString random;
	
	@Before
	public void setUp() throws Exception {
		random = new RandomString(new Random(), new AllowedCharacters(), 20);
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
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test(expected = IllegalArgumentException.class)
	public final void testIllegalArgumentZeroLength() {
		random = new RandomString(new Random(), new AllowedCharacters(), 0);
	}
	@Test
	public final void testIllegalArgumentAllowedCharactersNull() {
		exception.expect(IllegalArgumentException.class);
		random = new RandomString(new Random(), null, 20);
	}
	@Test
	public final void testIllegalArgumentBothNull() {
		exception.expect(IllegalArgumentException.class);
		random = new RandomString(null, null, 20);
	}
	@Test
	public final void testIllegalArgumentRandomNull() {
		exception.expect(IllegalArgumentException.class);
		random = new RandomString(null, new AllowedCharacters(), 0);
	}
}
