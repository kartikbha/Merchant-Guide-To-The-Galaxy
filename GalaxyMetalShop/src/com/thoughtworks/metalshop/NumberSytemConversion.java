package com.thoughtworks.metalshop;

public  class NumberSytemConversion {

	 /**
	  * Number conversion class
	  * 
	  * @param romanNum
	  * @return
	  */

	public static int convertRomanToInteger(String romanNum) {

		 int result = 0;
		 int index = 0;
         int length = romanNum.length(); // get number string length
         for (int i = 0; i < length - 1; i++) { // this loop will add each Roman
												// character value

			if (getRomanToIntEquivalent(romanNum.charAt(i)) > getRomanToIntEquivalent(romanNum.charAt(i + 1))) {
                result = result + getRomanToIntEquivalent(romanNum.charAt(i)) + index;
				index = 0;

			} else if (getRomanToIntEquivalent(romanNum.charAt(i)) == getRomanToIntEquivalent(romanNum
					.charAt(i + 1))) {
                index = index + getRomanToIntEquivalent(romanNum.charAt(i));

			} else if (getRomanToIntEquivalent(romanNum.charAt(i)) < getRomanToIntEquivalent(romanNum
					.charAt(i + 1))) {
                index = -index - getRomanToIntEquivalent(romanNum.charAt(i));

			}

		}
        result = result + index + getRomanToIntEquivalent(romanNum.charAt(length - 1));
		return result;

	}

	// Get integer value for corresponding Roman character
	private static int getRomanToIntEquivalent(char romanChar) {
		
		if (romanChar == 'I')
			return 1;
		else if (romanChar == 'V') {
			return 5;
		} else if (romanChar == 'X') {
			return 10;
		} else if (romanChar == 'L') {
			return 50;
		} else if (romanChar == 'C') {
			return 100;
		} else if (romanChar == 'D') {
			return 500;
		} else if (romanChar == 'M') {
			return 1000;
		}
		return (Integer) null;
	}

}
