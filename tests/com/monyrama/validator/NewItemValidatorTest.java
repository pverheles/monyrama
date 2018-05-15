//package com.monyrama.validator;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertNull;
//import static org.junit.Assert.assertTrue;
//
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
//import com.monyrama.ui.resources.Resources;
//import com.monyrama.validator.IEntityValidator;
//import com.monyrama.validator.NewExpensePlanItemValidator;
//
//
//public class NewItemValidatorTest {
//	private static PExpensePlan budget;
//	
//	private static PCategory[] categories;
//	private static String[] categoryNames = {"Food", "Car", "Education", "Entertainment", "One more"};
//	
//	private static String[] itemsSumms = {"2000", "500.35", "200", "100", "1000", "500"}; //4300.35
//	//balance = 300.15
//	
//	@BeforeClass
//	public static void prepareData() {
//		Resources.initSupportedLocales();
//		Resources.setLocale(new Locale("en"));
//		
//		createTestBudget();
//		createCategories();
//		
//		PExpensePlanItem itemWithName_1 = createItem("Item 1", categories[0], EntityStates.ACTIVE, itemsSumms[0], "No comments");
//		PExpensePlanItem itemWithName_2 = createItem("Item 2", categories[0], BudgetUnitStates.BLOCKED, itemsSumms[1], "No comments");
//		PExpensePlanItem itemWithName_3 = createItem("Existing Name", categories[1], EntityStates.ACTIVE, itemsSumms[2], "No comments");
//
//		PExpensePlanItem itemNoName_1 = createItem("", categories[2], EntityStates.ACTIVE, itemsSumms[3], "No comments");
//		PExpensePlanItem itemNoName_2 = createItem(null, categories[3], EntityStates.ACTIVE, itemsSumms[4], "No comments");
//		PExpensePlanItem itemNoName_3 = createItem("", categories[0], EntityStates.ACTIVE, itemsSumms[5], "No comments");
////		
////		budget.getBudgetUnits().add(itemWithName_1);
////		budget.getBudgetUnits().add(itemWithName_2);
////		budget.getBudgetUnits().add(itemWithName_3);
////		
////		budget.getBudgetUnits().add(itemNoName_1);
////		budget.getBudgetUnits().add(itemNoName_2);
////		budget.getBudgetUnits().add(itemNoName_3);
//		
//	}
//	
//	@Test
//	public void testName() {
//		PExpensePlanItem newItem_1 = createItem("Existing Name", categories[3], EntityStates.ACTIVE, "25.25", "haha");
//		IEntityValidator validator_1 = new NewExpensePlanItemValidator(budget, newItem_1);
//		assertFalse(validator_1.validate());
//		assertEquals(Resources.getString("dialogs.warnings.itemnameexist") + "!", validator_1.message());
//		
//		PExpensePlanItem newItem_2 = createItem("", categories[3], EntityStates.ACTIVE, "25.25", "haha");
//		IEntityValidator validator_2 = new NewExpensePlanItemValidator(budget, newItem_2);
//		assertFalse(validator_2.validate());
//		assertEquals(Resources.getString("dialogs.warnings.itemcategoryexist") + "!", validator_2.message());
//		
//		PExpensePlanItem newItem_3 = createItem("Non Existing Name", categories[3], EntityStates.ACTIVE, "25.25", "haha");
//		IEntityValidator validator_3 = new NewExpensePlanItemValidator(budget, newItem_3);
//		assertTrue(validator_3.validate());
//		assertNull(validator_3.message());
//		
//		PExpensePlanItem newItem_4 = createItem(null, categories[4], EntityStates.ACTIVE, "25.25", "haha");
//		IEntityValidator validator_4 = new NewExpensePlanItemValidator(budget, newItem_4);
//		assertTrue(validator_4.validate());
//		assertNull(validator_4.message());
//	}
//	
//	@Test
//	public void testSumEmpty() {
//		PExpensePlanItem newItem_1 = createItem("Sum Empty Item", categories[3], EntityStates.ACTIVE, "", "haha");
//		IEntityValidator validator_1 = new NewExpensePlanItemValidator(budget, newItem_1);
//		assertFalse(validator_1.validate());
//		assertEquals(Resources.getString("dialogs.warnings.sumempty") + "!", validator_1.message());
//		
//		PExpensePlanItem newItem_2 = createItem("Sum Null Item", categories[3], EntityStates.ACTIVE, "", "haha");
//		IEntityValidator validator_2 = new NewExpensePlanItemValidator(budget, newItem_2);
//		assertFalse(validator_2.validate());
//		assertEquals(Resources.getString("dialogs.warnings.sumempty") + "!", validator_2.message());
//	}
//	
//	@Test
//	public void testSumValid() {
//		for(Locale locale : Resources.getSupportedLocales()) {
//			Resources.setLocale(locale);
//			testSumValidForOneLocale();
//		}
//	}
//	
//	private void testSumValidForOneLocale() {
//		String sum = Resources.isSumDotSeparated() ? "25,25" : "25.25";
//		PExpensePlanItem newItem_1 = createItem("Sum Empty Item", categories[3], EntityStates.ACTIVE, sum, "haha");
//		IEntityValidator validator_1 = new NewExpensePlanItemValidator(budget, newItem_1);
//		assertFalse(validator_1.validate());
//		assertEquals(Resources.getString("dialogs.warnings.invalidsum") + "!", validator_1.message());
//		
//		PExpensePlanItem newItem_2 = createItem("Sum Empty Item", categories[3], EntityStates.ACTIVE, "34HF43", "haha");
//		IEntityValidator validator_2 = new NewExpensePlanItemValidator(budget, newItem_2);
//		assertFalse(validator_2.validate());
//		assertEquals(Resources.getString("dialogs.warnings.invalidsum") + "!", validator_2.message());
//		
//		String sumOK = Resources.isSumDotSeparated() ? "25.25" : "25,25";
//		PExpensePlanItem newItem_3 = createItem("Sum Empty Item", categories[3], EntityStates.ACTIVE, sumOK, "haha");
//		IEntityValidator validator_3 = new NewExpensePlanItemValidator(budget, newItem_3);
//		assertTrue(validator_3.validate());
//		assertNull(validator_3.message());
//	}
//	
//	private static void createTestBudget() {
//		//Create budget
//		budget = new PExpensePlan();
//		budget.setId(UniqueID.get());
//		budget.setName("Test budget");
//		budget.setCurrency(new PCurrency()); //dummy
//		budget.setState(EntityStates.ACTIVE.getCode());
//		budget.setComment("");
////		budget.setBudgetUnits(new HashSet<PBudgetUnit>());
//	}
//
//	private static PExpensePlanItem createItem(String name, PCategory category, BudgetUnitStates state, String summ, String comment) {
//		PExpensePlanItem item = new PExpensePlanItem();
//		item.setId(UniqueID.get());
//		item.setName(name);
//		item.setCategory(category);
//		item.setState(state.getCode());
//		item.setSumm(summ);
//		item.setComment(comment);
//		
//		return item;
//	}
//	
//	private static void createCategories() {
//		categories = new PCategory[categoryNames.length];
//		for(int i = 0; i < categoryNames.length; i++) {
//			categories[i] = createCategory(categoryNames[i], "No comments");	
//		}		
//	}
//	
//	private static PCategory createCategory(String name, String comment) {
//		PCategory category = new PCategory();
//		category.setId(UniqueID.get());
//		category.setName(name);
//		category.setComment(comment);
//		
//		return category;
//	}
//}
