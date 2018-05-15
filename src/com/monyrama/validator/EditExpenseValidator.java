package com.monyrama.validator;

import com.monyrama.entity.PExpensePlan;
import com.monyrama.entity.PExpense;

public class EditExpenseValidator extends AbstractExpenseValidator {	
	public EditExpenseValidator(PExpense editExpense, PExpensePlan expensePlan) {
		super(editExpense, expensePlan);
	}

}