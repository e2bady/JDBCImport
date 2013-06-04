package com.extensions.printingutils.tests.util;

import com.extensions.printingutils.Printer;

public final class IntegerPrinter implements Printer<Integer> {
	@Override
	public String print(Integer obj) {
		return Integer.toString(obj % 5);
	}
}