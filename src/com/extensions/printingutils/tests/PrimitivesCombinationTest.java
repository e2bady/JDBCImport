package com.extensions.printingutils.tests;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PrimitivesCombinationTest {
	private final PrimitivesCombination pc1 = PrimitivesCombination.createPrimitivesCombination(0, "SomeString");
	private final PrimitivesCombination pc2 = PrimitivesCombination.createPrimitivesCombination(1, "SomeOtherString", pc1);
	private final PrimitivesCombination pc3 = PrimitivesCombination.createPrimitivesCombination(2, "SomeOtherString", pc2);
	
	@Before
	public void setUp() throws Exception {	
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testHashCodeSimple() {
		PrimitivesCombination cpypc1 = PrimitivesCombination.createPrimitivesCombination(pc1.getI(), pc1.getS());
		PrimitivesCombination cpypc2 = PrimitivesCombination.createPrimitivesCombination(pc2.getI(), pc2.getS(), pc2.getPc());
		PrimitivesCombination cpypc3 = PrimitivesCombination.createPrimitivesCombination(pc3.getI(), pc3.getS(), pc3.getPc());
		
		assertEquals("hashCodes should be equal but are not.", pc1.hashCode(), cpypc1.hashCode());
		assertEquals("hashCodes should be equal but are not.", pc2.hashCode(), cpypc2.hashCode());
		assertEquals("hashCodes should be equal but are not.", pc3.hashCode(), cpypc3.hashCode());
		
		assertFalse("hashCodes should not be equal but are.", pc1.hashCode() == pc2.hashCode());
		assertFalse("hashCodes should not be equal but are.", pc3.hashCode() == pc2.hashCode());
		assertFalse("hashCodes should not be equal but are.", pc1.hashCode() == pc3.hashCode());
	}
	
	private final static int sizeOfHashCodeTest = 200000;
	
	@Test
	public final void testHashCode() {
		int overlaps = 0;
		RandomPrimitivesCombination random = new RandomPrimitivesCombination(20);
		Map<Integer, Collection<PrimitivesCombination>> map = new HashMap<Integer, Collection<PrimitivesCombination>>();
		
		for(int i=0;i<sizeOfHashCodeTest;i++) {
			PrimitivesCombination nextPrimitive = random.nextPrimitivesCombination();
			int hash = nextPrimitive.hashCode();
			if(map.containsKey(hash)) {
				for(PrimitivesCombination pc : map.get(hash)) {
					if(!pc.equals(nextPrimitive)) {
						overlaps++;
					}
				}
				map.get(hash).add(nextPrimitive);
			}
			List<PrimitivesCombination> lst = new LinkedList<PrimitivesCombination>();
			lst.add(nextPrimitive);
			map.put(hash, lst);
		}
		if(overlaps > (double) (sizeOfHashCodeTest) / 10000.) {
			fail("HashCode produced to many overlaps for efficient hashing. Maximum is 0.001 %. Overlaps/Size: " + overlaps + " / " + sizeOfHashCodeTest + " = " + 100.*((double) overlaps / (double) map.size()) + " %");
		}
	}

	@Test
	public final void testCreatePrimitivesCombinationIntString() {
		PrimitivesCombination pc = PrimitivesCombination.createPrimitivesCombination(0, "SomeString");
		
		assertNotNull("createPrimitivesCombination returned null", pc);
		assertEquals("createPrimitivesCombination created pc where i was not correct", pc.getI(), 0);
		assertEquals("createPrimitivesCombination created pc where s was not correct", pc.getS(), "SomeString");
	}

	@Test
	public final void testCreatePrimitivesCombinationIntStringPrimitivesCombination() {
		PrimitivesCombination tpc = PrimitivesCombination.createPrimitivesCombination(1, "SomeOtherString");
		PrimitivesCombination pc = PrimitivesCombination.createPrimitivesCombination(0, "SomeString", tpc);
		
		assertNotNull("createPrimitivesCombination returned null", pc);
		assertEquals("createPrimitivesCombination created pc where i was not correct", pc.getI(), 0);
		assertEquals("createPrimitivesCombination created pc where s was not correct", pc.getS(), "SomeString");
		assertEquals("createPrimitivesCombination created pc where pc was not correct", pc.getPc(), tpc);
	}

	@Test
	public final void testEqualsObject() {
		PrimitivesCombination cpypc1 = PrimitivesCombination.createPrimitivesCombination(pc1.getI(), pc1.getS());
		PrimitivesCombination cpypc2 = PrimitivesCombination.createPrimitivesCombination(pc2.getI(), pc2.getS(), pc2.getPc());
		PrimitivesCombination cpypc3 = PrimitivesCombination.createPrimitivesCombination(pc3.getI(), pc3.getS(), pc3.getPc());
		
		assertEquals("hashCodes should be equal but are not.", pc1, cpypc1);
		assertEquals("hashCodes should be equal but are not.", pc2, cpypc2);
		assertEquals("hashCodes should be equal but are not.", pc3, cpypc3);
		
		assertFalse("hashCodes should not be equal but are.", pc1.equals(pc2));
		assertFalse("hashCodes should not be equal but are.", pc3.equals(pc2));
		assertFalse("hashCodes should not be equal but are.", pc1.equals(pc3));
	}
}
