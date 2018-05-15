package com.monyrama.validator;

import com.monyrama.entity.PExpense;
import com.monyrama.entity.PExpense;
import com.monyrama.entity.PExpensePlan;
import com.monyrama.validator.AbstractExpenseValidator;

public class NewExpenseValidator extends AbstractExpenseValidator {
	public NewExpenseValidator(PExpense expense, PExpensePlan budget) {
		super(expense, budget);
	}
}
