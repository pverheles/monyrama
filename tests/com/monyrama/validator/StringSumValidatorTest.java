package com.monyrama.validator;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import java.util.Locale;

import org.junit.BeforeClass;
import org.junit.Test;

import com.monyrama.ui.resources.Resources;
import com.monyrama.validator.util.StringSumValidator;

public class StringSumValidatorTest {
	@BeforeClass
	public static void createStringSumValidator() {
		Resources.initSupportedLocales();
	}
	
	@Test
	public void testDoubleAccordingToLocale() {
		Locale ukLocale = new Locale("uk");
		Resources.setLocale(ukLocale);

		String ukSum_0 = null;
		boolean ukResult_0 = StringSumValidator.isValidPositiveFormat(ukSum_0);
		assertFalse(ukResult_0);
		
		String ukSum_1 = "25,678";
		boolean ukResult_1 = StringSumValidator.isValidPositiveFormat(ukSum_1);
		assertFalse(ukResult_1);
		
		String ukSum_2 = "25,67";
		boolean ukResult_2 = StringSumValidator.isValidPositiveFormat(ukSum_2);
		assertTrue(ukResult_2);
		
		String ukSum_3 = ",60";
		boolean ukResult_3 = StringSumValidator.isValidPositiveFormat(ukSum_3);
		assertTrue(ukResult_3);
		
		String ukSum_4 = "0,6";
		boolean ukResult_4 = StringSumValidator.isValidPositiveFormat(ukSum_4);
		assertTrue(ukResult_4);
		
		String ukSum_5 = "600,";
		boolean ukResult_5 = StringSumValidator.isValidPositiveFormat(ukSum_5);
		assertFalse(ukResult_5);
		
		String ukSum_6 = "dfsa";
		boolean ukResult_6 = StringSumValidator.isValidPositiveFormat(ukSum_6);
		assertFalse(ukResult_6);
		
		String ukSum_7 = "25.67";
		boolean ukResult_7 = StringSumValidator.isValidPositiveFormat(ukSum_7);
		assertFalse(ukResult_7);
		
		Locale ruLocale = new Locale("ru");
		Resources.setLocale(ruLocale);
		
		String ruSum_1 = "25,678";
		boolean ruResult_1 = StringSumValidator.isValidPositiveFormat(ruSum_1);
		assertFalse(ruResult_1);
		
		String ruSum_2 = "25,67";
		boolean ruResult_2 = StringSumValidator.isValidPositiveFormat(ruSum_2);
		assertTrue(ruResult_2);
		
		String ruSum_3 = ",60";
		boolean ruResult_3 = StringSumValidator.isValidPositiveFormat(ruSum_3);
		assertTrue(ruResult_3);
		
		String ruSum_4 = "0,6";
		boolean ruResult_4 = StringSumValidator.isValidPositiveFormat(ruSum_4);
		assertTrue(ruResult_4);
		
		String ruSum_5 = "600,";
		boolean ruResult_5 = StringSumValidator.isValidPositiveFormat(ruSum_5);
		assertFalse(ruResult_5);
		
		String ruSum_6 = "dfsa";
		boolean ruResult_6 = StringSumValidator.isValidPositiveFormat(ruSum_6);
		assertFalse(ruResult_6);
		
		String ruSum_7 = "25.67";
		boolean ruResult_7 = StringSumValidator.isValidPositiveFormat(ruSum_7);
		assertFalse(ruResult_7);
		
		Locale enLocale = new Locale("en");
		Resources.setLocale(enLocale);
		
		String enSum_1 = "25.678";
		boolean enResult_1 = StringSumValidator.isValidPositiveFormat(enSum_1);
		assertFalse(enResult_1);
		
		String enSum_2 = "25.67";
		boolean enResult_2 = StringSumValidator.isValidPositiveFormat(enSum_2);
		assertTrue(enResult_2);
		
		String enSum_3 = ".60";
		boolean enResult_3 = StringSumValidator.isValidPositiveFormat(enSum_3);
		assertTrue(enResult_3);
		
		String enSum_4 = "0.6";
		boolean enResult_4 = StringSumValidator.isValidPositiveFormat(enSum_4);
		assertTrue(enResult_4);
		
		String enSum_5 = "600.";
		boolean enResult_5 = StringSumValidator.isValidPositiveFormat(enSum_5);
		assertFalse(enResult_5);
		
		String enSum_6 = "dfsa";
		boolean enResult_6 = StringSumValidator.isValidPositiveFormat(enSum_6);
		assertFalse(enResult_6);
		
		String enSum_7 = "25,67";
		boolean enResult_7 = StringSumValidator.isValidPositiveFormat(enSum_7);
		assertFalse(enResult_7);
	}
	
	@Test
	public void testZero() {
		String sum_1 = "0";
		boolean result_1 = StringSumValidator.isZero(sum_1);
		assertTrue(result_1);
		
		String sum_2 = "00000";
		boolean result_2 = StringSumValidator.isZero(sum_2);
		assertTrue(result_2);
		
		String sum_3 = "0,0";
		boolean result_3 = StringSumValidator.isZero(sum_3);
		assertTrue(result_3);
		
		String sum_4 = "0.0";
		boolean result_4 = StringSumValidator.isZero(sum_4);
		assertTrue(result_4);
		
		String sum_5 = "0000,000";
		boolean result_5 = StringSumValidator.isZero(sum_5);
		assertTrue(result_5);
		
		String sum_6 = "0000.0000";
		boolean result_6 = StringSumValidator.isZero(sum_6);
		assertTrue(result_6);
		
		String sum_15 = "0,0,000,0";
		boolean result_15 = StringSumValidator.isZero(sum_15);
		assertFalse(result_15);
		
		String sum_16 = "0.0.0.0";
		boolean result_16 = StringSumValidator.isZero(sum_16);
		assertFalse(result_16);
		
		String sum_7 = ",00";
		boolean result_7 = StringSumValidator.isZero(sum_7);
		assertTrue(result_7);
		
		String sum_8 = ".00";
		boolean result_8 = StringSumValidator.isZero(sum_8);
		assertTrue(result_8);
		
		String sum_9 = "0,";
		boolean result_9 = StringSumValidator.isZero(sum_9);
		assertFalse(result_9);
		
		String sum_10 = "0.";
		boolean result_10 = StringSumValidator.isZero(sum_10);
		assertFalse(result_10);
		
		String sum_11 = "25";
		boolean result_11 = StringSumValidator.isZero(sum_11);
		assertFalse(result_11);
		
		String sum_12 = "I'm zero";
		boolean result_12 = StringSumValidator.isZero(sum_12);
		assertFalse(result_12);
	}
	
	@Test
	public void testGreaterThan() {
		String sum_1 = "256.78";
		String sum_2 = "14";
		String sum_3 = "256.78";
		
		int resultOne = StringSumValidator.greaterThan(sum_1, sum_2);
		assertTrue(resultOne > 0);
		
		int resultTwo = StringSumValidator.greaterThan(sum_2, sum_1);
		assertTrue(resultTwo < 0);
		
		int resultThree = StringSumValidator.greaterThan(sum_1, sum_3);
		assertEquals(0, resultThree);
	}
}
