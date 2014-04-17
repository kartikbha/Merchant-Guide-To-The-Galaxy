package com.thoughtworks.testcases;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.thoughtworks.exception.GalaxyShopException;
import com.thoughtworks.metalshop.GalaxyMetalShop;

/**
 * test cases for galaxy shop class
 * 
 * @author Kartik
 * 
 */
public class GalaxyShopTest {

	@Rule
	public ExpectedException expException = ExpectedException.none();

	@Test
	public void testSalesRequestQuestionFormat1WithOutMetal() {

		GalaxyMetalShop galaxyMetal = new GalaxyMetalShop();
		long output = galaxyMetal
				.saleInquiry("how much is pish tegj glob glob ?");
		Assert.assertEquals(42, output);
	}

	@Test
	public void testSalesRequestQuestionFormat2WithMetal() {

		GalaxyMetalShop galaxyMetal = new GalaxyMetalShop();
		long output1 = galaxyMetal
				.saleInquiry("how many Credits is glob prok silver ?");
		Assert.assertEquals(68, output1);

		long output2 = galaxyMetal
				.saleInquiry("how many Credits is glob prok iron ?");
		Assert.assertEquals(782, output2);

		long output3 = galaxyMetal
				.saleInquiry("how many Credits is glob prok gold ?");
		Assert.assertEquals(57800, output3);

	}
	
	@Test
	public void testSalesRequestWrongQuestion() {

		expException.expect(GalaxyShopException.class);
		expException.expectMessage("I have no idea what you are talking about");

		GalaxyMetalShop galaxyMetal = new GalaxyMetalShop();
		galaxyMetal
				.saleInquiry("how much wood could a woodchuck chuck if a woodchuck could chuck wood ?");

	}

	@Test
	public void testRegularExpression() {

		final Pattern pattern = Pattern.compile("how much is(.+?)\\?");
		final Matcher matcher = pattern
				.matcher("how much is pish tegj glob glob ?");
		matcher.find();
		//System.out.println(" testRegularExpression extract text " + matcher.group(1));

	}

}
