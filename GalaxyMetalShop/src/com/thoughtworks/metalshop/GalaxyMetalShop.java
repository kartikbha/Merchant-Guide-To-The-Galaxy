package com.thoughtworks.metalshop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.thoughtworks.exception.GalaxyShopException;
/**
 * Implementation class for galaxy metal shop
 * @author Kartik
 *
 */
public class GalaxyMetalShop {

	final Map<String, String> quatityMap = RateCalculationMatrix.slangRomanEquivMap;
	final Map<String[], Float> metalRateMap = RateCalculationMatrix.slangMetalRateMap;
	final String METAL = "metal";
	final String SLANG = "slang";

	/**
	 * Method accept input quotation for metal and return rate as per rate
	 * calculation Matrix.
	 * 
	 * @param quotation
	 * @return
	 */
	public long saleInquiry(String quotation) {

		// extract question values {slag+metal} from English statement.
		final String quotationText = extractQuotationText(quotation);

		// separate out token as slang list and metal list with in map
		final Map<String, List<String>> grpToken = separateOutTokens(Arrays
				.asList(quotationText.trim().split(" "))); // create token out of
														// those question values
		
		// convert input slang into roman equivalent and later into numbers.
		final long qty = slangProcessor(grpToken);

		// calculate total metal value as metal rate x qty using rate
		// calculation matrix.
		if (grpToken.get(METAL).size() != 0) {
			final long metalValue = calculateMetalValue(grpToken, qty);
			System.out.println("quotation :"+quotation+" metalPrize "+metalValue);
			return metalValue;
		} else { // No metal quote, just slang into roman equivalent.
			System.out.println("quotation :"+quotation+" value : "+qty);
			return qty;
		}

	}

	/**
	 * Use Rate Calculation matrix and find metal value.
	 * 
	 * @param grpTokens
	 *            - slag list + metal
	 * @param qty
	 *            - input request
	 * @return
	 */
	private long calculateMetalValue(Map<String, List<String>> grpTokens,
			long qty) {

		// extract name of metal
		final List<String> metalTokens = grpTokens.get(METAL);
		 float rate = 0;
		// roman number out of those slangs
		String romanNumbers = null;
		// loop thru metal rate map.
		for (Map.Entry<String[], Float> entry : metalRateMap.entrySet()) {
			// metal is matched with rate matrix
			if (Arrays.asList(entry.getKey()).contains(metalTokens.get(0))) {

				// get slang token out of rate matrix
				List<String> ratestatements = Arrays.asList(entry.getKey());
				// rate statements into roman equivalent
				romanNumbers = romanNumerBuilder(ratestatements);
				// rate value of this metal
				rate = entry.getValue();
				break;
			}
		}
		// convert roman number into numeric form.
		
		final float numericValue = NumberSytemConversion
				.convertRomanToInteger(romanNumbers);
		final float metalPrize = (rate / numericValue) * qty;
	
		return (long)metalPrize;
	}

	/**
	 * 
	 * @param grpTokens
	 * @return
	 */
	private long slangProcessor(Map<String, List<String>> grpTokens) {

		// get list of input slang
		final List<String> slangList = grpTokens.get(SLANG);
		// convert slang into roman number
		final String romanNumber = romanNumerBuilder(slangList);
		// numeric value of slang list
		final long numericValue = NumberSytemConversion
				.convertRomanToInteger(romanNumber.toString());
		return numericValue;
	}

	/**
	 * get roman number equivalent of slangs
	 * 
	 * @param qtyTokens
	 * @return
	 */
	private String romanNumerBuilder(List<String> tokens) {
		final StringBuilder romanNumbers = new StringBuilder();
		for (String token : tokens) {
			if (quatityMap.get(token) != null) {
				romanNumbers.append(quatityMap.get(token));
			}
		}
		return romanNumbers.toString();
	}

	/**
	 * retunr map having two array list 1. metal --> name of metal 2. slang -->
	 * array list of slang
	 * 
	 * @param tokens
	 * @return
	 */
	private Map<String, List<String>> separateOutTokens(List<String> tokens) {
		final Map<String, List<String>> groupTokens = new HashMap<String, List<String>>();

		final List<String> quantity = new ArrayList<String>();
		final List<String> metal = new ArrayList<String>();
		// group into two array list as metal & slang list
		for (String token : tokens) {
			if (quatityMap.get(token) != null) {
				quantity.add(token);
			} else {
				metal.add(token);
			}
		}
		groupTokens.put(METAL, metal);
		groupTokens.put(SLANG, quantity);
		return groupTokens;
	}

	/**
	 * use regular expression to extract quotation text out of question.
	 * 
	 * @param quotation
	 * @return
	 */
	private String extractQuotationText(String quotation) {
		// question format 1 -> how much is pish tegj glob glob ?
		// question format 2 -> how many Credits is glob prok silver ?
		final Pattern questionFormat1 = Pattern.compile("(how much is|how many Credits is)(.+?)\\?");
		String questionValue = getQuestionValue(questionFormat1,quotation);
		if (getQuestionValue(questionFormat1,quotation) == null) {
				throw new GalaxyShopException(
						"I have no idea what you are talking about", "400");
		}
		return questionValue;
	}

	/**
	 * fetch and return matched expression
	 * 
	 * @param ptn
	 * @param str
	 * @return
	 */
	private String getQuestionValue(final Pattern ptn, final String str) {
		String questionValue = null;
		final Matcher matcher = ptn.matcher(str);
		if (matcher.find()) {
			questionValue = matcher.group(2);
		}
		return questionValue;

	}
}
