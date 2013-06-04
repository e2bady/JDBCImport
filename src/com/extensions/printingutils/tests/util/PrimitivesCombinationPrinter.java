package com.extensions.printingutils.tests.util;

import com.extensions.printingutils.Printer;

public final class PrimitivesCombinationPrinter implements
		Printer<PrimitivesCombination> {
	@Override 
	  public String print(PrimitivesCombination obj) {
		return "(" + obj.getI() + "," + obj.getS() + ")";
	  }
}