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
//import com.monyrama.entity.PExpense;
//import com.monyrama.ui.resources.Resources;
//import com.monyrama.validator.IEntityValidator;
//import com.monyrama.validator.NewExpenseValidator;
//
//
//public class NewExpenseValidatorTest {
//	private static PExpensePlan budget;
//	private static PExpensePlanItem itemOne;
//	private static PExpensePlanItem itemTwo;
//	private static PExpensePlanItem itemThree;
//	
//	private static String[] itemSums = {"200.50", "2000", "1500"}; //3700.50
//	private static String[] item1ExpensesSums = {"100", "150"};
//	private static String[] item2ExpensesSums = {"100", "150", "1000", "25.50"};
//	private static String[] item3ExpensesSums = {"100", "90", "5.35"};
//	//Total sum of expenses: 1720.85
//	//Real balance: 2279.15
//	private static String moreThanRealBalance = "2300";
//	private static String equalRealBalance = "2279.15";
//	private static String lessRealBalance = "200";
//	
//	private static PCategory[] categories;
//	private static String[] categoryNames = {"Food", "Car", "Education", "Entertainment", "One more"};
//	
//	@BeforeClass
//	public static void prepareData() {
//		Resources.initSupportedLocales();
//		Resources.setLocale(new Locale("en"));
//		
//		createOnlyBudget();
//		createCategories();
//		createItems();		
//		createExpenses();
//	}
//	
//	@Test
//	public void testEmptySum() {
//		IEntityValidator validator_1 = new NewExpenseValidator(createExpense(null, ""), budget);
//		assertFalse(validator_1.validate());
//		assertEquals(Resources.getString("dialogs.warnings.sumempty") + "!", validator_1.message());
//		
//		IEntityValidator validator_2 = new NewExpenseValidator(createExpense("", ""), budget);
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
//		PExpense expense_1 = createExpense(sum_1, "");
//		IEntityValidator validator_1 = new NewExpenseValidator(expense_1, budget);
//		assertFalse(validator_1.validate());
//		assertEquals(Resources.getString("dialogs.warnings.invalidsum") + "!", validator_1.message());
//		
//		PExpense expense_2 = createExpense("jfdk83", "");
//		IEntityValidator validator_2 = new NewExpenseValidator(expense_2, budget);
//		assertFalse(validator_2.validate());
//		assertEquals(Resources.getString("dialogs.warnings.invalidsum") + "!", validator_2.message());
//		
//		String sum_3 = Resources.isSumDotSeparated() ? "25.25" : "25,25";
//		PExpense expense_3 = createExpense(sum_3, "");
//		IEntityValidator validator_3 = new NewExpenseValidator(expense_3, budget);
//		assertTrue(validator_3.validate());
//		assertNull(validator_3.message());
//	}
//
//	private static void createExpenses() {
////		itemOne.getSpentUnits().add(createExpense(item1ExpensesSums[0], ""));
////		itemOne.getSpentUnits().add(createExpense(item1ExpensesSums[1], ""));
////		
////		itemTwo.getSpentUnits().add(createExpense(item2ExpensesSums[0], ""));
////		itemTwo.getSpentUnits().add(createExpense(item2ExpensesSums[1], ""));
////		itemTwo.getSpentUnits().add(createExpense(item2ExpensesSums[2], ""));
////		itemTwo.getSpentUnits().add(createExpense(item2ExpensesSums[3], ""));
////		
////		itemThree.getSpentUnits().add(createExpense(item3ExpensesSums[0], ""));
////		itemThree.getSpentUnits().add(createExpense(item3ExpensesSums[1], ""));
////		itemThree.getSpentUnits().add(createExpense(item3ExpensesSums[2], ""));
//	}
//
//	private static void createItems() {
//		itemOne = createItem("", categories[1], EntityStates.ACTIVE, itemSums[0], "Nothing");
//		itemTwo = createItem("Mla-mla", categories[0], EntityStates.ACTIVE, itemSums[1], "");
//		itemThree = createItem("", categories[3], EntityStates.ACTIVE, itemSums[2], null);
//		
////		budget.getBudgetUnits().add(itemOne);
////		budget.getBudgetUnits().add(itemTwo);
////		budget.getBudgetUnits().add(itemThree);
//	}
//
//	private static void createOnlyBudget() {
//		budget = new PExpensePlan();
//		budget.setId(UniqueID.get());
////		budget.setBudgetUnits(new HashSet<PBudgetUnit>());
//		budget.setName("Only name");
//		PCurrency currency = new PCurrency();
//		currency.setName("USD");
//		budget.setCurrency(currency);
//		budget.setState(EntityStates.ACTIVE.getCode());
//		budget.setComment("bla bla");
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
//	
//	private static PExpensePlanItem createItem(String name, PCategory category, BudgetUnitStates state, String summ, String comment) {
//		PExpensePlanItem item = new PExpensePlanItem();
//		item.setId(UniqueID.get());
////		item.setSpentUnits(new HashSet<PSpentUnit>());
//		item.setName(name);
//		item.setCategory(category);
//		item.setState(state.getCode());
//		item.setSumm(summ);
//		item.setComment(comment);
//		
//		return item;
//	}
//	
//	private static PExpense createExpense(String sum, String comment) {
//		PExpense expense = new PExpense();
//		expense.setId(UniqueID.get());
//		expense.setDay(Calendar.getInstance().getTime());
//		expense.setPrice(sum);
//		expense.setComment(comment);
//		
//		return expense;
//	}
//}
