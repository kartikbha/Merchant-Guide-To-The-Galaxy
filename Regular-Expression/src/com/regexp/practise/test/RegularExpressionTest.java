package com.regexp.practise.test;

import org.junit.Test;

import com.regexp.practise.RegularExpression;

public class RegularExpressionTest {

	@Test
	public void twoRegTest(){
		
		RegularExpression regExp = new RegularExpression();
		
		String extractedText  = regExp.twoQuestionRegExTogether("how many Credits is glob prok silver ?");
		System.out.println("  how many Credits is -->  "+extractedText);
		String extractedText1  = regExp.twoQuestionRegExTogether("how much is pish tegj glob glob ?");
	    System.out.println(" how much is --> "+extractedText1);
  }
	
	@Test
	public void extractBasedOnStart(){
		
		RegularExpression regExp = new RegularExpression();
		String extractedText  = regExp.regSartWithExample("Ahow many Credits is Alob prok silver ?");
	    //System.out.println("  start with A : "+extractedText);
	}
	
	
	@Test
	public void searchInFIle(){
		
		
		String file = new FileSystemHelper().readFile("C://Users//Kartik//workspace//Regular-Expression//src//inputFile.txt");
		
		//System.out.println(" file : "+file);
		
		RegularExpression regExp = new RegularExpression();
		//String extractedText  = regExp.regSartWithExample("test hello queen why so this that less always");
		
		String extractedText  = regExp.regSartWithExample(file);
	    System.out.println("  start with Ammmmmmm : "+extractedText);
	}
	
}
