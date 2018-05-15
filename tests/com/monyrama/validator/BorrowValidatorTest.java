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
//import com.monyrama.ui.resources.Resources;
//import com.monyrama.validator.BorrowValidator;
//import com.monyrama.validator.IEntityValidator;
//
//
//public class BorrowValidatorTest {
//	@BeforeClass
//	public static void init() {
//		Resources.initSupportedLocales();
//		Resources.setLocale(new Locale("en"));
//	}
//	
//	@Test
//	public void testEmptyName() {
//		IEntityValidator validator_1 = new BorrowValidator("", "100");
//		assertFalse(validator_1.validate());
//		assertEquals(Resources.getString("dialogs.warnings.nameempty") + "!", validator_1.message());
//		
//		IEntityValidator validator_2 = new BorrowValidator(null, "100");
//		assertFalse(validator_2.validate());
//		assertEquals(Resources.getString("dialogs.warnings.nameempty") + "!", validator_2.message());
//	}
//	
//	@Test
//	public void testEmptySum() {
//		IEntityValidator validator_1 = new BorrowValidator("Gosha", null);
//		assertFalse(validator_1.validate());
//		assertEquals(Resources.getString("dialogs.warnings.sumempty") + "!", validator_1.message());
//		
//		IEntityValidator validator_2 = new BorrowValidator("Gosha", "");
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
//	private void testValidSumForOneLocale() {
//		String sum_1 = Resources.isSumDotSeparated() ? "25,25" : "25.25";
//		IEntityValidator validator_1 = new BorrowValidator("Dima", sum_1);
//		assertFalse(validator_1.validate());
//		assertEquals(Resources.getString("dialogs.warnings.invalidsum") + "!", validator_1.message());
//		
//		IEntityValidator validator_2 = new BorrowValidator("Zita", "35.45f");
//		assertFalse(validator_2.validate());
//		assertEquals(Resources.getString("dialogs.warnings.invalidsum") + "!", validator_2.message());
//		
//		String sum_3 = Resources.isSumDotSeparated() ? "25.25" : "25,25";
//		IEntityValidator validator_3 = new BorrowValidator("Misha", sum_3);
//		assertTrue(validator_3.validate());
//		assertNull(validator_3.message());
//	}
//}
