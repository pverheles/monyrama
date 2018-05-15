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
//import com.monyrama.entity.PExpensePlan;
//import com.monyrama.entity.PExpensePlanItem;
//import com.monyrama.entity.PCategory;
//import com.monyrama.entity.PDepository;
//import com.monyrama.ui.resources.Resources;
//import com.monyrama.validator.EntityType;
//import com.monyrama.validator.IEntityValidator;
//import com.monyrama.validator.LendValidator;
//
//
//public class LendValidatorTest {
//	private static PExpensePlan budget;
//	private static PExpensePlanItem itemOne;
//	private static PExpensePlanItem itemTwo;
//	private static PExpensePlanItem itemThree;
//	
//	private static PCategory[] categories;
//	private static String[] categoryNames = {"Food", "Car", "Education", "Entertainment", "One more"};
//	
//	private static PDepository depository = new PDepository();
//	private static String depositorySum = "1000";
//	private static String moreDepositorySum = "8000.50";
//	private static String lessDepositorySum = "998";
//	
//	@BeforeClass
//	public static void prepareData() {
//		Resources.initSupportedLocales();
//		Resources.setLocale(new Locale("en"));
//		
//		createDepository();
//	}
//
//	
//	@Test
//	public void testEmptyName() {
//		IEntityValidator validator_1 = new LendValidator(null, null, null, null);
//		assertFalse(validator_1.validate());
//		assertEquals(Resources.getString("dialogs.warnings.nameempty") + "!", validator_1.message());
//		
//		IEntityValidator validator_2 = new LendValidator("", null, null, null);
//		assertFalse(validator_2.validate());
//		assertEquals(Resources.getString("dialogs.warnings.nameempty") + "!", validator_2.message());
//	}
//	
//	@Test
//	public void testEmptySum() {
//		IEntityValidator validator_1 = new LendValidator("Vasia", null, null, null);
//		assertFalse(validator_1.validate());
//		assertEquals(Resources.getString("dialogs.warnings.sumempty") + "!", validator_1.message());
//		
//		IEntityValidator validator_2 = new LendValidator("Vasia", "", null, null);
//		assertFalse(validator_2.validate());
//		assertEquals(Resources.getString("dialogs.warnings.sumempty") + "!", validator_2.message());
//	}
//	
//	@Test
//	public void testSumZero() {
//		IEntityValidator validator_1 = new LendValidator("Vasia", "0", null, null);
//		assertFalse(validator_1.validate());
//		assertEquals(Resources.getString("dialogs.warnings.zerosum") + "!", validator_1.message());
//		
//		IEntityValidator validator_2 = new LendValidator("Vasia", "00", null, null);
//		assertFalse(validator_2.validate());
//		assertEquals(Resources.getString("dialogs.warnings.zerosum") + "!", validator_2.message());
//		
//		IEntityValidator validator_3 = new LendValidator("Vasia", "100", null, null);
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
//    @Test
//    public void testExceedDepositorySum() {
//		IEntityValidator validator_1 = new LendValidator("Gosha", moreDepositorySum, EntityType.DEPOSITORY, depository);
//		assertFalse(validator_1.validate());
//		assertEquals(Resources.getString("dialogs.warnings.lendhigherdepository") + "!", validator_1.message());	
//		
//		IEntityValidator validator_2 = new LendValidator("Sasha", depositorySum, EntityType.DEPOSITORY, depository);
//		assertTrue(validator_2.validate());
//		assertNull(validator_2.message());
//		
//		IEntityValidator validator_3 = new LendValidator("Lona", lessDepositorySum, EntityType.DEPOSITORY, depository);
//		assertTrue(validator_3.validate());
//		assertNull(validator_3.message());
//    }
//	
//	private void testValidSumForOneLocale() {
//		String sum_1 = Resources.isSumDotSeparated() ? "25,25" : "25.25";
//		IEntityValidator validator_1 = new LendValidator("Petia", sum_1, null, null);
//		assertFalse(validator_1.validate());
//		assertEquals(Resources.getString("dialogs.warnings.invalidsum") + "!", validator_1.message());
//		
//		IEntityValidator validator_2 = new LendValidator("Petia","jfdk83", null, null);
//		assertFalse(validator_2.validate());
//		assertEquals(Resources.getString("dialogs.warnings.invalidsum") + "!", validator_2.message());
//		
//		String sum_3 = Resources.isSumDotSeparated() ? "25.25" : "25,25";
//		IEntityValidator validator_3 = new LendValidator("Petia", sum_3, null, null);
//		assertTrue(validator_3.validate());
//		assertNull(validator_3.message());
//	}
//	
//	private static void createDepository() {
//		depository.setName("My depository");
//		depository.setSumm(depositorySum);
//	}
//}
