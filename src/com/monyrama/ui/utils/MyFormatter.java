package com.monyrama.ui.utils;

import java.math.BigDecimal;

import com.monyrama.entity.PExpensePlanItem;
import com.monyrama.ui.resources.Resources;

/**
 * Contains methods to format strings
 * 
 * @author Petro_Verheles
 *
 */
public class MyFormatter {
	
	/**
	 * private constructor prevents from instantiating this class
	 */
	private MyFormatter() {
		
	}
		
//	public static String formatNumberToLocal(String number) {
//		if(number == null) {
//			return null;
//		}
//		
//		//Remove leading zeros (for some stupid numbers which was saved in the previous versions)
////		BigDecimal bigDecimal = new BigDecimal(number);
////		number = bigDecimal.toPlainString();
//		
//		//Remove trailing zeros
////		if(number.endsWith(".00")) {
////			return number.substring(0, number.length() - 3);
////		}
////		
////		if(number.endsWith(".0")) {
////			return number.substring(0, number.length() - 2);
////		}
//		
//		//Format should be x.xx, for example 2.50 NOT 2.5
//		if(number.indexOf(".") > 0 && number.indexOf(".") == number.length() - 2) {
//			number += "0";
//		}
//		
//		if(!Resources.isSumDotSeparated()) {
//			return number.replace('.', ',');
//		} else {
//			return number;
//		}
//	}
	
	public static String formatNumberToLocal(String number) {
		if(number == null) {
			return null;
		}
		
		BigDecimal bd = new BigDecimal(number);
		bd = bd.stripTrailingZeros();		
		number = bd.toPlainString();
		
		if(number.indexOf(".") > 0 && number.indexOf(".") == number.length() - 2) {
			number += "0";
		}
				
		if(!Resources.isSumDotSeparated()) {
			return number.replace('.', ',');
		} else {
			return number;
		}		
	}
	
	public static String formatNumberToStandard(String number) {
		if(!Resources.isSumDotSeparated()) {
			number = number.replace(',', '.');
		}
		
		//To remove leading zeros
		BigDecimal bigDecimal = new BigDecimal(number);
		number = bigDecimal.stripTrailingZeros().toPlainString();
		return number;
	}
	
	/**
	 * Forms budget unit name. If budget unit has no name
	 * it is formed of the name of its category and comments
	 * 
	 * @param bu - budget unit
	 * @return - budget unit visual name
	 */
	public static String createBudgetUnitName(PExpensePlanItem bu) {		
		if(bu.getName() != null && !bu.getName().equals("")) {
			return bu.getName();
		} else {
			return bu.getCategory().getName();
		}
	}
}
