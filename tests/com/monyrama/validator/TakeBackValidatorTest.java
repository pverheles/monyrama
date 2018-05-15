//package com.monyrama.validator;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertNull;
//import static org.junit.Assert.assertTrue;
//
//import java.util.Locale;
//
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import com.monyrama.entity.PLend;
//import com.monyrama.ui.resources.Resources;
//import com.monyrama.validator.IEntityValidator;
//import com.monyrama.validator.TakeBackValidator;
//
//
//public class TakeBackValidatorTest {
//	private static PLend lend = new PLend();
//	private static String lendSum = "500.50";
//	private static String moreSum = "600";
//	private static String lessSum = "400.35";
//	
//	@BeforeClass
//	public static void init() {
//		Resources.initSupportedLocales();
//		Resources.setLocale(new Locale("en"));
//		lend.setSumm("500.50");
//	}
//	
//	
//	
//	@Test
//	public void testEmptySum() {
//		IEntityValidator validator_1 = new TakeBackValidator(null, lend);
//		assertFalse(validator_1.validate());
//		assertEquals(Resources.getString("dialogs.warnings.sumempty") + "!", validator_1.message());
//		
//		IEntityValidator validator_2 = new TakeBackValidator("", lend);
//		assertFalse(validator_2.validate());
//		assertEquals(Resources.getString("dialogs.warnings.sumempty") + "!", validator_2.message());
//	}
//	
//	@Test
//	public void testValidSum() {
//		for(Locale locale : Resources.getSupportedLocales()) {
//			Resources.setLocale(locale);
//			testValidSumForOneLocale();
//		}
//	}
//	
//	@Test
//	public void testExceedLendSum() {	
//		Resources.setLocale(new Locale("en"));
//		
//		IEntityValidator validator_1 = new TakeBackValidator(moreSum, lend);
//		assertFalse(validator_1.validate());
//		assertEquals(Resources.getString("dialogs.warnings.returnhigherlend") + "!", validator_1.message());
//		
//		IEntityValidator validator_2 = new TakeBackValidator(lendSum, lend);
//		assertTrue(validator_2.validate());
//		assertNull(validator_2.message());
//		
//		IEntityValidator validator_3 = new TakeBackValidator(lessSum, lend);
//		assertTrue(validator_3.validate());
//		assertNull(validator_3.message());
//	}
//	
//	private void testValidSumForOneLocale() {
//		String sum_1 = Resources.isSumDotSeparated() ? "25,25" : "25.25";
//		IEntityValidator validator_1 = new TakeBackValidator(sum_1, lend);
//		assertFalse(validator_1.validate());
//		assertEquals(Resources.getString("dialogs.warnings.invalidsum") + "!", validator_1.message());
//		
//		IEntityValidator validator_2 = new TakeBackValidator("35.45f", lend);
//		assertFalse(validator_2.validate());
//		assertEquals(Resources.getString("dialogs.warnings.invalidsum") + "!", validator_2.message());
//		
//		String sum_3 = Resources.isSumDotSeparated() ? "25.25" : "25,25";
//		IEntityValidator validator_3 = new TakeBackValidator(sum_3, lend);
//		assertTrue(validator_3.validate());
//		assertNull(validator_3.message());
//	}
//}
