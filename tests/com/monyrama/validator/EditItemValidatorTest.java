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
//import com.monyrama.controller.UniqueID;
//import com.monyrama.db.enumarations.BudgetStates;
//import com.monyrama.db.enumarations.BudgetUnitStates;
//import com.monyrama.entity.PExpensePlan;
//import com.monyrama.entity.PExpensePlanItem;
//import com.monyrama.entity.PCategory;
//import com.monyrama.entity.PCurrency;
//import com.monyrama.ui.resources.Resources;
//import com.monyrama.validator.EditExpensePlanItemValidator;
//import com.monyrama.validator.IEntityValidator;
//
//
//public class EditItemValidatorTest {
//	private static PExpensePlan budget;
//	
//	private static PCategory[] categories;
//	private static String[] categoryNames = {"Food", "Car", "Education", "Entertainment", "One more"};
//	
//	//DO NOT CHANGE SUMS
//	private static String[] itemsSumms = {"2000", "500.35", "200", "100", "1000", "500"}; //4300.35
//	//balance = 300.15
//
//	private static PExpensePlanItem itemWithName_1;
//
//	private static PExpensePlanItem itemWithName_2;
//
//	private static PExpensePlanItem itemWithName_3;
//
//	private static PExpensePlanItem itemNoName_1;
//
//	private static PExpensePlanItem itemNoName_2;
//
//	private static PExpensePlanItem itemNoName_3;
//	
//	@BeforeClass
//	public static void prepareData() {
//		Resources.initSupportedLocales();
//		Resources.setLocale(new Locale("en"));
//		
//		createTestBudget();
//		createCategories();
//		
//		itemWithName_1 = createItem(UniqueID.get(), "Item 1", categories[0], EntityStates.ACTIVE.getCode(), itemsSumms[0], "No comments");
//		itemWithName_2 = createItem(UniqueID.get(), "Item 2", categories[0], EntityStates.ACTIVE.getCode(), itemsSumms[1], "No comments");
//		itemWithName_3 = createItem(UniqueID.get(), "Existing Name", categories[1], EntityStates.ACTIVE.getCode(), itemsSumms[2], "No comments");
//
//		itemNoName_1 = createItem(UniqueID.get(), "", categories[2], EntityStates.ACTIVE.getCode(), itemsSumms[3], "No comments");
//		itemNoName_2 = createItem(UniqueID.get(), null, categories[3], EntityStates.ACTIVE.getCode(), itemsSumms[4], "No comments");
//		itemNoName_3 = createItem(UniqueID.get(), "", categories[0], EntityStates.ACTIVE.getCode(), itemsSumms[5], "No comments");
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
//		//Check we entered existing name of other item
//		PExpensePlanItem editItem_1 = createItem(itemWithName_2.getId(), itemWithName_3.getName(), itemWithName_2.getCategory(), itemWithName_2.getState(), itemWithName_2.getSumm(), itemWithName_2.getComment());
//		IEntityValidator validator_1 = new EditExpensePlanItemValidator(budget, itemWithName_2, editItem_1);
//		assertFalse(validator_1.validate());
//		assertEquals(Resources.getString("dialogs.warnings.itemnameexist") + "!", validator_1.message());
//		
//		//Check we entered empty name but with the same category as of other item with no name
//		PExpensePlanItem editItem_2 = createItem(itemNoName_1.getId(), "", itemNoName_2.getCategory(), itemNoName_1.getState(), itemNoName_1.getSumm(), itemNoName_1.getComment());
//		IEntityValidator validator_2 = new EditExpensePlanItemValidator(budget, itemNoName_1, editItem_2);
//		assertFalse(validator_2.validate());
//		assertEquals(Resources.getString("dialogs.warnings.itemcategoryexist") + "!", validator_2.message());
//		
//		//Check we entered non-existing name
//		PExpensePlanItem newItem_3 = createItem(itemNoName_3.getId(), "Non Existing Name", itemNoName_3.getCategory(), itemNoName_3.getState(), itemNoName_3.getSumm(), itemNoName_3.getComment());
//		IEntityValidator validator_3 = new EditExpensePlanItemValidator(budget, itemNoName_3, newItem_3);
//		assertTrue(validator_3.validate());
//		assertNull(validator_3.message());
//		
//		//Check we entered no name and category that non of items with no name has.
//		PExpensePlanItem newItem_4 = createItem(itemWithName_2.getId(), "", categories[4], itemWithName_2.getState(), itemWithName_2.getSumm(), itemWithName_2.getComment());
//		IEntityValidator validator_4 = new EditExpensePlanItemValidator(budget, itemWithName_2, newItem_4);
//		assertTrue(validator_4.validate());
//		assertNull(validator_4.message());
//		
//		//Check we entered existing name of the same item and it's ok
//		PExpensePlanItem editItem_5 = createItem(itemWithName_2.getId(), itemWithName_2.getName(), itemWithName_2.getCategory(), itemWithName_2.getState(), itemWithName_2.getSumm(), "Edited comment");
//		IEntityValidator validator_5 = new EditExpensePlanItemValidator(budget, itemWithName_2, editItem_5);
//		assertTrue(validator_5.validate());
//		assertNull(validator_5.message());
//		
//		//Check we entered empty name and the same category (as it was in old item)
//		PExpensePlanItem editItem_6 = createItem(itemNoName_1.getId(), itemNoName_1.getName(), itemNoName_1.getCategory(), itemNoName_1.getState(), itemNoName_1.getSumm(), "Edited comment");
//		IEntityValidator validator_6 = new EditExpensePlanItemValidator(budget, itemNoName_1, editItem_6);
//		assertTrue(validator_6.validate());
//		assertNull(validator_6.message());
//	}
//	
//	@Test
//	public void testSumEmpty() {
//		PExpensePlanItem editItem_1 = createItem(itemNoName_1.getId(), "Sum Empty Item", itemNoName_1.getCategory(), itemNoName_1.getState(), "", "haha");
//		IEntityValidator validator_1 = new EditExpensePlanItemValidator(budget, itemNoName_1, editItem_1);
//		assertFalse(validator_1.validate());
//		assertEquals(Resources.getString("dialogs.warnings.sumempty") + "!", validator_1.message());
//		
//		PExpensePlanItem editItem_2 = createItem(itemNoName_1.getId(), "Sum Null Item", itemNoName_1.getCategory(), itemNoName_1.getState(), null, "haha");
//		IEntityValidator validator_2 = new EditExpensePlanItemValidator(budget, itemNoName_1, editItem_2);
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
//		PExpensePlanItem editItem_1 = createItem(itemNoName_1.getId(), itemNoName_1.getName(), itemNoName_1.getCategory(), itemNoName_1.getState(), sum, itemNoName_1.getComment());
//		IEntityValidator validator_1 = new EditExpensePlanItemValidator(budget, itemNoName_1, editItem_1);
//		assertFalse(validator_1.validate());
//		assertEquals(Resources.getString("dialogs.warnings.invalidsum") + "!", validator_1.message());
//		
//		PExpensePlanItem editItem_2 = createItem(itemNoName_1.getId(), itemNoName_1.getName(), itemNoName_1.getCategory(), itemNoName_1.getState(), "34HF43", "haha");
//		IEntityValidator validator_2 = new EditExpensePlanItemValidator(budget, itemNoName_1, editItem_2);
//		assertFalse(validator_2.validate());
//		assertEquals(Resources.getString("dialogs.warnings.invalidsum") + "!", validator_2.message());
//		
//		String sumOK = Resources.isSumDotSeparated() ? "25.25" : "25,25";
//		PExpensePlanItem editItem_3 = createItem(itemNoName_1.getId(), itemNoName_1.getName(), itemNoName_1.getCategory(), itemNoName_1.getState(), sumOK, "haha");
//		IEntityValidator validator_3 = new EditExpensePlanItemValidator(budget, itemNoName_1, editItem_3);
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
//		//budget.setBudgetUnits(new HashSet<PBudgetUnit>());
//	}
//
//	private static PExpensePlanItem createItem(Long id, String name, PCategory category, Character state, String summ, String comment) {
//		PExpensePlanItem item = new PExpensePlanItem();
//		item.setId(id);
//		item.setName(name);
//		item.setCategory(category);
//		item.setState(state);
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
