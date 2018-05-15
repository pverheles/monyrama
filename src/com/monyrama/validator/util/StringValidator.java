package com.monyrama.validator.util;

public class StringValidator {
	private StringValidator() {}
	
	public static boolean isStringNullOrEmpty(String string) {
		return (string == null) || (string.trim().equals(""));
	}
	
	public static boolean areEqualEgnoreCase(String firstString, String secondString) {
		if((firstString == null) && (secondString == null)) {
			return true;
		}
		
		if((firstString == null) && (secondString.trim().equals(""))) {
			return true;
		}

		if((secondString == null) && (firstString.trim().equals(""))) {
			return true;
		}
		
		if(firstString == null) {
			return false;
		}
		
		if(secondString == null) {
			return false;
		}
		
		if((firstString.trim().equalsIgnoreCase((secondString.trim())))) {
			return true;
		}
		
		return false;
	}
}
