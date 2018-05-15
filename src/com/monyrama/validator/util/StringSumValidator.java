package com.monyrama.validator.util;

import java.math.BigDecimal;

import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.utils.MyFormatter;


public class StringSumValidator {
	private StringSumValidator() {}
	
	/**
	 * Validates sum format
	 * 
	 * @param sum
	 * @return
	 */
	public static boolean isValidPositiveFormat(String sum) {
		if(sum == null) {
			return false;
		}
		
		String sumCommaRegExp = "[0-9]{0,63}(,[0-9]{1,2}){0,1}";
		String sumDotRegExp = "[0-9]{0,63}(\\.[0-9]{1,2}){0,1}";
		
		if (Resources.isSumDotSeparated()) {
			return sum.matches(sumDotRegExp);
		} else {	
			return sum.matches(sumCommaRegExp);
		}
	}

	/**
	 * Validates sum format
	 *
	 * @param sum
	 * @return
	 */
	public static boolean isValidPositiveOrNegativeFormat(String sum) {
		if(sum == null) {
			return false;
		}

		String sumCommaRegExp = "-?[0-9]{0,63}(,[0-9]{1,2}){0,1}";
		String sumDotRegExp = "-?[0-9]{0,63}(\\.[0-9]{1,2}){0,1}";

		if (Resources.isSumDotSeparated()) {
			return sum.matches(sumDotRegExp);
		} else {
			return sum.matches(sumCommaRegExp);
		}
	}
	
	/**
	 * Validates if the sum is zero
	 * 
	 * @param sum
	 * @return
	 */
	public static boolean isZero(String sum) {
		String severalZeroesRegExp = "0{0,}";
		String zeroesSeparatedWithOneCommaRegExp = "0{0,},0+";
		String zeroesSeparatedWithOneDotRegExp = "0{0,}\\.0+";
		
		if(sum.matches(severalZeroesRegExp)
			|| sum.matches(zeroesSeparatedWithOneCommaRegExp)
			|| sum.matches(zeroesSeparatedWithOneDotRegExp)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Compares to numbers presented as valid strings
	 * 
	 * @param num1 first number
	 * @param num2 second number
	 * @return number > 0, if num1 > num2; number < 0, if num1 < num2; number == 0, if num1 == num2;
	 */
	public static int greaterThan(String num1, String num2) {
		BigDecimal bigDecimal1 = new BigDecimal(MyFormatter.formatNumberToStandard(num1));
		BigDecimal bigDecimal2 = new BigDecimal(MyFormatter.formatNumberToStandard(num2));
		return bigDecimal1.compareTo(bigDecimal2);
	}
}
