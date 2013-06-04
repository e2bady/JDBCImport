package com.extensions.printingutils.tests;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.extensions.printingutils.PrettyPrinter;
import com.extensions.printingutils.Printer;

public class PrettyPrinterTest {

	private static final Logger log = (Logger) LoggerFactory
			.getLogger(PrettyPrinterTest.class);

	private final Integer[][] integerTable = new Integer[5][5];
	private final PrimitivesCombination[][] primitivesTable = new PrimitivesCombination[5][5];
	private final Map<PrimitivesCombination, Collection<Integer>> map = new HashMap<PrimitivesCombination, Collection<Integer>>();
	private final IntegerPrinter integerPrinter = new IntegerPrinter();
	private final Printer<PrimitivesCombination> primitivesPrinter =  new PrimitivesCombinationPrinter();
	
	@Before
	public void setUp() throws Exception {
		int i = 0;
		for(Integer[] row : integerTable) {
			for(int index = 0; index<row.length;index++) {
				row[index] = new Integer(i++);
			}
		}
		i = 0;
		for(PrimitivesCombination[] row : primitivesTable) {
			for(int index = 0; index<row.length;index++) {
				PrimitivesCombination primitivesCombination = new PrimitivesCombination(i++, Integer.toString(index), new PrimitivesCombination(i++, "Secondary"));
				List<Integer> lst = new LinkedList<Integer>();
				for(Integer e : integerTable[index]) {
					lst.add(e);
				}
				map.put(primitivesCombination, lst);
				row[index] = primitivesCombination;
			}
		}
	}

	@Test
	public final void testPrintObjectArrayArray() {
		String printIntegers = PrettyPrinter.print(integerTable);
		assertTrue("Printed String does not contain lines with equal length", checkRowLength(printIntegers));
		assertTrue("Printed String does not match assumptions.", checkPrintOut(printIntegers, integerTable, null));
		log.error("\n" + printIntegers);
		
		String printPrimitives = PrettyPrinter.print(primitivesTable);
		assertTrue("Printed String does not contain lines with equal length", checkRowLength(printPrimitives));
		assertTrue("Printed String does not match assumptions.", checkPrintOut(printPrimitives, primitivesTable, null));
		log.error("\n" + printPrimitives);
	}

	private <T> boolean checkPrintOut(String printOut, T[][] table, Printer<T> printer) {
		//copy the String.
		printOut = new String(printOut);
		checkRowLength(printOut);
		
		//Check if every elements toString concatenated is equal to removing everything from the 
		//printout that the printer adds (e.g. row-seperators, Border-Knots...)
		StringBuilder sb = new StringBuilder();
		if(printer == null)
			printer = new Printer<T>() { @Override public String print(T obj) { return obj.toString(); } };
		
		for(T[] row : table)
			for(T e : row)
				if(e != null)
					sb.append(printer.print(e));
		
		printOut = trim(printOut).trim();
		String selfBuild = trim(sb.toString()).trim();
		log.error("Checking if: " + printOut + " == " + selfBuild);
		return printOut.equals(selfBuild);
	}

	private String trim(String printOut) {
		printOut = printOut.replace(PrettyPrinter.getDefaultAsNull(), "");
		printOut = printOut.replace(" ", "");
		printOut = printOut.replace("\n", "");
		for(char c : new char[]{PrettyPrinter.getBorderKnot(), 
								PrettyPrinter.getHorizontalBorder(), 
								PrettyPrinter.getVerticalBorder()})
			printOut = printOut.replaceAll("\\" + c, "");
		return printOut;
	}

	private boolean checkRowLength(String printOut) {
		//check if the printout is splittable into lines.
		String[] lines = printOut.split("\n");
		if(lines == null || lines.length == 0)
			return false;
		
		//Check if every line has the same length.
		int length = lines[0].length();
		for(String line : lines) {
			if(line.length() != length) return false;
		}
		return true;
	}

	@Test
	public final void testPrintMapOfKCollectionOfVPrinterOfKPrinterOfV() {
		String print = PrettyPrinter.print(map, primitivesPrinter, integerPrinter);
		assertTrue("Printed String does not contain lines with equal length", checkRowLength(print));
		
		log.error("\n" + print);
	}

	@Test
	public final void testPrintTArrayArrayPrinterOfT() {
		String print = PrettyPrinter.print(integerTable, integerPrinter);
		assertTrue("Printed String does not contain lines with equal length", checkRowLength(print));
		assertTrue("Printed String does not match assumptions.", checkPrintOut(print, integerTable, integerPrinter));
		log.error("\n" + print);
		
		print = PrettyPrinter.print(primitivesTable,primitivesPrinter);
		assertTrue("Printed String does not contain lines with equal length", checkRowLength(print));
		assertTrue("Printed String does not match assumptions.", checkPrintOut(print, primitivesTable, primitivesPrinter));
		log.error("\n" + print);
	}
}
