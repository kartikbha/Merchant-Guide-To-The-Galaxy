package com.thoughtworks.metalshop;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
/**
 * 
 * Rate Chart Configuration.
 * 
 * @author Kartik
 *
 */

public class RateCalculationMatrix {

	public static final Map<String[], Float> slangMetalRateMap;
	public static final Map<String, String> slangRomanEquivMap;

	static {
		
		// metal rate chart
		Map<String[], Float> rateMap = new HashMap<String[], Float>();
		// slag..............,slag, metal
		rateMap.put(new String[] { "glob", "glob", "silver" }, 34f);
		rateMap.put(new String[] { "glob", "prok", "gold" }, 57800f);
		rateMap.put(new String[] { "pish", "pish", "iron" }, 3910f);
		rateMap.put(new String[] { "pish", "prok", "tegj", "platinum" }, 9980f);
		slangMetalRateMap = Collections.unmodifiableMap(rateMap);

		// setting roman equivalent currency
		Map<String, String> slangRomanMap = new HashMap<String, String>();
		slangRomanMap.put("glob", "I");
		slangRomanMap.put("prok", "V");
		slangRomanMap.put("pish", "X");
		slangRomanMap.put("tegj", "L");
		slangRomanMap.put("zoo", "M");
		slangRomanMap.put("cha", "D");
		
		slangRomanEquivMap = Collections.unmodifiableMap(slangRomanMap);
	}

}
