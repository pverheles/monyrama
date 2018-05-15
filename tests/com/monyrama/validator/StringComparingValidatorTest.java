//package com.monyrama.validator;
//
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import com.monyrama.validator.util.StringValidator;
//
//
//public class StringComparingValidatorTest {
//	private static StringValidator validator;
//
//	@BeforeClass
//	public static void createValidator() {
//		validator = new StringValidator();
//	}
//	
//	@Test
//	public void testIsStringNullOrEmpty() {
//		String nullString = null;
//		boolean nullStringResult = validator.isStringNullOrEmpty(nullString);
//		assertTrue(nullStringResult);
//		
//		String emptyString = "";
//		boolean emptyStringResult = validator.isStringNullOrEmpty(emptyString);
//		assertTrue(emptyStringResult);
//		
//		String spacesString = "   ";
//		boolean spacesStringResult = validator.isStringNullOrEmpty(spacesString);
//		assertTrue(spacesStringResult);
//		
//		String notEmptyString = "I'm not empty";
//		boolean notEmptyStringResult = validator.isStringNullOrEmpty(notEmptyString);
//		assertFalse(notEmptyStringResult);
//	}
//	
//	@Test
//	public void testAreEqualEgnoreCase() {
//		String string_0 = "Java the best";
//		String string_1 = "Java the best";
//		String string_2 = "JaVa tHe best";
//		String string_3 = "C++ better";
//		String nullString_1 = null;
//		String nullString_2 = null;
//		String emptyString = "";
//		
//		boolean result_1 = validator.areEqualEgnoreCase(string_0, string_2);
//		assertTrue(result_1);
//		
//		boolean result_2 = validator.areEqualEgnoreCase(string_1, string_2);
//		assertTrue(result_2);
//		
//		boolean result_3 = validator.areEqualEgnoreCase(string_0, string_3);
//		assertFalse(result_3);
//		
//		boolean result_4 = validator.areEqualEgnoreCase(nullString_1, nullString_2);
//		assertTrue(result_4);
//		
//		boolean result_5 = validator.areEqualEgnoreCase(nullString_1, emptyString);
//		assertTrue(result_5);
//		
//		boolean result_6 = validator.areEqualEgnoreCase(emptyString, nullString_1);
//		assertTrue(result_6);
//		
//		boolean result_7 = validator.areEqualEgnoreCase(string_1, nullString_1);
//		assertFalse(result_7);
//		
//		boolean result_8 = validator.areEqualEgnoreCase(nullString_1, string_1);
//		assertFalse(result_8);
//	}
//}
