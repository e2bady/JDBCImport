package com.extensions.printingutils.tests;

import com.extensions.printingutils.Printer;

final class IntegerPrinter implements Printer<Integer> {
	@Override
	public String print(Integer obj) {
		return Integer.toString(obj % 5);
	}
}