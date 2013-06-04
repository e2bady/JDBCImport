package com.extensions.printingutils.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	PrettyPrinterTest.class, 
	PrimitivesCombinationTest.class,
	RandomStringTest.class 
	})
public class AllTests {
}
