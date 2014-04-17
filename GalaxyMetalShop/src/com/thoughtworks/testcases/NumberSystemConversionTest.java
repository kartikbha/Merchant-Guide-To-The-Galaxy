package com.thoughtworks.testcases;

import org.junit.Assert;
import org.junit.Test;

import com.thoughtworks.metalshop.NumberSytemConversion;

/**
 * test cases for number system conversion.
 * 
 * @author Kartik
 * 
 */

public class NumberSystemConversionTest {

	@Test
	public void convertRomanToIntegerTest() {

		Assert.assertEquals(10,
				NumberSytemConversion.convertRomanToInteger("X"));
		Assert.assertEquals(1984,
				NumberSytemConversion.convertRomanToInteger("MCMLXXXIV"));
		Assert.assertEquals(1953,
				NumberSytemConversion.convertRomanToInteger("MCMLIII"));
		Assert.assertEquals(3303,
				NumberSytemConversion.convertRomanToInteger("MMMCCCIII"));
		Assert.assertEquals(1995,
				NumberSytemConversion.convertRomanToInteger("MCMXCV"));
		Assert.assertEquals(17,
				NumberSytemConversion.convertRomanToInteger("XVII"));

	}
}
