package com.regexp.practise;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularExpression {
	
	
	public String  twoQuestionRegExTogether(String quote) {
		
	// question format 1 -> how much is pish tegj glob glob ?
	final Pattern questionFormat1 = Pattern.compile("(how much is|how many Credits is)(.+?)\\?");
	String questionValue = getQuestionValue(questionFormat1, quote,new Integer[]{0,1,2},2);
	return questionValue;
	
    }
	
	public String  regSartWithExample(String quote) {
		
		// start with A
		//final Pattern questionFormat1 = Pattern.compile(".*?\\bmiseries\\b.*?\\bfortunes\\b.*?");	
	    
		//final Pattern questionFormat1 = Pattern.compile(".*?\\bmiseries\\b.*?");		
        // ([A-Za-z])+ \1
		final Pattern questionFormat1 = Pattern.compile("([A-Za-z])+\\1");	
		//final Pattern questionFormat1 = Pattern.compile("<([A-Z][A-Z0-9]*)\b[^>]*>.*?</\1>");
		//<([A-Z][A-Z0-9]*)\b[^>]*>.*?</\1>		
		
		//final Pattern questionFormat1 = Pattern.compile("([A-Za-z])+\1");


		String questionValue = getQuestionValue(questionFormat1, quote,new Integer[]{0},0);
		return questionValue;
	}
	
	
	private String getQuestionValue(final Pattern ptn, final String str,final Integer[] strArray,final int returnIndex) {
		String questionValue = null;
		final Matcher matcher = ptn.matcher(str);
		if (matcher.find()) {
			for (int index : strArray) {
				  System.out.println(" group: "+index+" : "+matcher.group(index));
				  System.out.println(" match start: "+matcher.start());
				  System.out.println(" match end : "+matcher.end());
				  questionValue = matcher.group(returnIndex);
			}
		}
		return questionValue;
	}
	
	


}
