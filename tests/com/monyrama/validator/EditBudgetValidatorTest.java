//package com.monyrama.validator;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertNull;
//import static org.junit.Assert.assertTrue;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
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
//import com.monyrama.utils.Calc;
//import com.monyrama.validator.EditExpensePlanValidator;
//import com.monyrama.validator.IEntityValidator;
//
//
//public class EditBudgetValidatorTest {
//	private static List<PExpensePlan> budgets = new ArrayList<PExpensePlan>();
//	private static PCurrency currency = new PCurrency();
//	
//	@BeforeClass
//	public static void prepareData() {
//		Resources.initSupportedLocales();
//		Resources.setLocale(new Locale("en"));
//		
//		currency.setId(UniqueID.get());
//		currency.setName("USD");
//		
//		PExpensePlan budget_1 = new PExpensePlan();
//		budget_1.setId(UniqueID.get());
//		budget_1.setName("Budget 1");
//		budget_1.setCurrency(currency);
//		budget_1.setComment("No comments");
//		budgets.add(budget_1);
//		
//		PExpensePlan budget_2 = new PExpensePlan();
//		budget_2.setId(UniqueID.get());
//		budget_2.setName("Existing");
//		budget_2.setCurrency(currency);
//		budget_2.setComment("No comments");
//		budgets.add(budget_2);
//		
//		PExpensePlan budget_3 = new PExpensePlan();
//		budget_3.setId(UniqueID.get());
//		budget_3.setName("Check me");
//		budget_3.setCurrency(currency);
//		budget_3.setComment("No comments");
//		budgets.add(budget_3);
//		
//		PExpensePlan budget_4 = new PExpensePlan();
//		budget_4.setId(UniqueID.get());
//		budget_4.setName("Budget 1");
//		budget_4.setCurrency(currency);
//		budget_4.setComment("No comments");
//		budgets.add(budget_4);
//		
//		PExpensePlan budget_5 = new PExpensePlan();
//		budget_5.setId(UniqueID.get());
//		budget_5.setName("Budget 5");
//		budget_5.setCurrency(currency);
//		budget_5.setComment("No comments");
//		budgets.add(budget_5);
//	}
//
//	@Test
//	public void testEmptyNameFor() {
//		PExpensePlan budget_1 = new PExpensePlan();
//		budget_1.setId(budgets.get(1).getId());
//		budget_1.setName(null);
//		budget_1.setCurrency(currency);
//		budget_1.setComment("No comments");
//		IEntityValidator validator_1 = new EditExpensePlanValidator(budgets, budget_1);
//		assertFalse(validator_1.validate());
//		assertEquals(Resources.getString("dialogs.warnings.nameempty") + "!", validator_1.message());
//
//		PExpensePlan budget_2 = new PExpensePlan();
//		budget_2.setId(budgets.get(1).getId());
//		budget_2.setName("");
//		budget_2.setCurrency(currency);
//		budget_2.setComment("No comments");
//		IEntityValidator validator_2 = new EditExpensePlanValidator(budgets, budget_2);
//		assertFalse(validator_2.validate());
//		assertEquals(Resources.getString("dialogs.warnings.nameempty") + "!", validator_2.message());
//	}
//
//	@Test
//	public void compareNameWithExisting() {
//		PExpensePlan budget_1 = new PExpensePlan();
//		budget_1.setId(budgets.get(1).getId());
//		budget_1.setName("Existing");
//		budget_1.setCurrency(currency);
//		budget_1.setComment("No comments");
//		IEntityValidator validator_1 = new EditExpensePlanValidator(budgets, budget_1);
//		assertTrue(validator_1.validate());
//		assertNull(validator_1.message());
//
//		PExpensePlan budget_2 = new PExpensePlan();
//		budget_2.setId(budgets.get(1).getId());
//		budget_2.setName("The newest budget");
//		budget_2.setCurrency(currency);
//		budget_2.setComment("No comments");
//		IEntityValidator validator_2 = new EditExpensePlanValidator(budgets, budget_2);
//		assertTrue(validator_2.validate());
//		assertNull(validator_2.message());
//		
//		PExpensePlan budget_3 = new PExpensePlan();
//		budget_3.setId(budgets.get(1).getId());
//		budget_3.setName("Check me");
//		budget_3.setCurrency(currency);
//		budget_3.setComment("No comments");
//		IEntityValidator validator_3 = new EditExpensePlanValidator(budgets, budget_3);
//		assertFalse(validator_3.validate());
//		assertEquals(Resources.getString("dialogs.warnings.budgetnameexist") + "!", validator_3.message());
//	}		
//}
