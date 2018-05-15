//package com.monyrama.validator;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertNull;
//import static org.junit.Assert.assertTrue;
//
//import java.util.Calendar;
//import java.util.HashSet;
//import java.util.Locale;
//
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import com.monyrama.controller.UniqueID;
//import com.monyrama.db.enumarations.BudgetStates;
//import com.monyrama.db.enumarations.BudgetUnitStates;
//import com.monyrama.entity.PExpensePlan;
//import com.monyrama.entity.PExpensePlanItem;
//import com.monyrama.entity.PCategory;
//import com.monyrama.entity.PCurrency;
//import com.monyrama.entity.PDebt;
//import com.monyrama.entity.PDepository;
//import com.monyrama.entity.PExpense;
//import com.monyrama.ui.resources.Resources;
//import com.monyrama.validator.EntityType;
//import com.monyrama.validator.IEntityValidator;
//import com.monyrama.validator.PayBackValidator;
//
//
//public class PayBackValidatorTest {
//	private static PDebt debt;
//	private static String debtSum = "400.30";
//	private static String moreDebtSum = "500";
//	private static String lessDebtSum = "300.50";
//
//	private static PDebt bigDebt;
//	private static String bigDebtSum = "3000";
//	
//	private static PDepository depository = new PDepository();
//	private static String depositorySum = "1000";
//	private static String moreDepositorySum = "2000.50";
//	private static String lessDepositorySum = "998";
//
//	
//	@BeforeClass
//	public static void prepareData() {
//		Resources.initSupportedLocales();
//		Resources.setLocale(new Locale("en"));
//		
//		createDepository();
//		
//		createDebts();
//	}
//
//	
//	@Test
//	public void testEmptySum() {
//		IEntityValidator validator_1 = new PayBackValidator(debt, null, null, null);
//		assertFalse(validator_1.validate());
//		assertEquals(Resources.getString("dialogs.warnings.sumempty") + "!", validator_1.message());
//		
//		IEntityValidator validator_2 = new PayBackValidator(debt, "", null, null);
//		assertFalse(validator_2.validate());
//		assertEquals(Resources.getString("dialogs.warnings.sumempty") + "!", validator_2.message());
//	}
//	
//	@Test
//	public void testSumZero() {
//		IEntityValidator validator_1 = new PayBackValidator(debt, "0", null, null);
//		assertFalse(validator_1.validate());
//		assertEquals(Resources.getString("dialogs.warnings.zerosum") + "!", validator_1.message());
//		
//		IEntityValidator validator_2 = new PayBackValidator(debt, "00", null, null);
//		assertFalse(validator_2.validate());
//		assertEquals(Resources.getString("dialogs.warnings.zerosum") + "!", validator_2.message());
//		
//		IEntityValidator validator_3 = new PayBackValidator(debt, "100", null, null);
//		assertTrue(validator_3.validate());
//		assertNull(validator_3.message());
//	}
//	
//	@Test
//	public void testValidSum() {
//		for(Locale locale : Resources.getSupportedLocales()) {
//			Resources.setLocale(locale);
//			testValidSumForOneLocale();
//		}
//		Resources.setLocale(new Locale("en"));
//	}
//	
//	@Test
//    public void testExceedDebtSum() {
//		IEntityValidator validator_1 = new PayBackValidator(debt, moreDebtSum, EntityType.DEPOSITORY, depository);
//		assertFalse(validator_1.validate());
//		assertEquals(Resources.getString("dialogs.warnings.returnhigherdebt") + "!", validator_1.message());	
//		
//		IEntityValidator validator_2 = new PayBackValidator(debt, debtSum, EntityType.DEPOSITORY, depository);
//		assertTrue(validator_2.validate());
//		assertNull(validator_2.message());
//		
//		IEntityValidator validator_3 = new PayBackValidator(debt, lessDebtSum, EntityType.DEPOSITORY, depository);
//		assertTrue(validator_3.validate());
//		assertNull(validator_3.message());
//    }
//	
//	@Test
//    public void testExceedDepositorySum() {
//		IEntityValidator validator_1 = new PayBackValidator(bigDebt, moreDepositorySum, EntityType.DEPOSITORY, depository);
//		assertFalse(validator_1.validate());
//		assertEquals(Resources.getString("dialogs.warnings.returndebthigherdepository") + "!", validator_1.message());	
//		
//		IEntityValidator validator_2 = new PayBackValidator(bigDebt, depositorySum, EntityType.DEPOSITORY, depository);
//		assertTrue(validator_2.validate());
//		assertNull(validator_2.message());
//		
//		IEntityValidator validator_3 = new PayBackValidator(bigDebt, lessDepositorySum, EntityType.DEPOSITORY, depository);
//		assertTrue(validator_3.validate());
//		assertNull(validator_3.message());
//    }
//	
//	private void testValidSumForOneLocale() {
//		String sum_1 = Resources.isSumDotSeparated() ? "25,25" : "25.25";
//		IEntityValidator validator_1 = new PayBackValidator(debt, sum_1, null, null);
//		assertFalse(validator_1.validate());
//		assertEquals(Resources.getString("dialogs.warnings.invalidsum") + "!", validator_1.message());
//		
//		IEntityValidator validator_2 = new PayBackValidator(debt, "jfdk83", null, null);
//		assertFalse(validator_2.validate());
//		assertEquals(Resources.getString("dialogs.warnings.invalidsum") + "!", validator_2.message());
//		
//		String sum_3 = Resources.isSumDotSeparated() ? "25.25" : "25,25";
//		IEntityValidator validator_3 = new PayBackValidator(debt, sum_3, null, null);
//		assertTrue(validator_3.validate());
//		assertNull(validator_3.message());
//	}
//
//	private static void createDebts() {
//		debt = new PDebt();
//		debt.setName("Gosha");
//		debt.setSumm(debtSum);
//		
//		bigDebt = new PDebt();
//		bigDebt.setName("Bariga");
//		bigDebt.setSumm(bigDebtSum);
//	}
//	
//	private static void createDepository() {
//		depository.setName("My depository");
//		depository.setSumm(depositorySum);
//	}
//}
