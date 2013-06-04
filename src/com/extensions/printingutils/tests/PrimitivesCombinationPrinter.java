package com.extensions.printingutils.tests;

import com.extensions.printingutils.Printer;

final class PrimitivesCombinationPrinter implements
		Printer<PrimitivesCombination> {
	@Override 
	  public String print(PrimitivesCombination obj) {
		return "(" + obj.getI() + "," + obj.getS() + ")";
	  }
}