package com.monyrama.validator;

import com.monyrama.entity.PExpense;
import com.monyrama.entity.PExpensePlan;
import com.monyrama.ui.resources.Resources;
import com.monyrama.validator.util.StringSumValidator;
import com.monyrama.validator.util.StringValidator;

abstract class AbstractExpenseValidator extends EntityValidator {	
	protected PExpense expense;
	protected PExpensePlan expensePlan;

	protected AbstractExpenseValidator(PExpense expense, PExpensePlan expensePlan) {
		this.expense = expense;
		this.expensePlan = expensePlan;
	}

	@Override
	public boolean validate() {		
		if(expense.getAccount() == null) {
			setMessage(Resources.getString("dialogs.warnings.fromaccountempty") + "!");
			return false;
		}
		
		if(StringValidator.isStringNullOrEmpty(expense.getSumStr())) {
			setMessage(Resources.getString("dialogs.warnings.sumempty") + "!");
			return false;
		}
		
		if(!StringSumValidator.isValidPositiveFormat(expense.getSumStr())) {
			setMessage(Resources.getString("dialogs.warnings.invalidsum") + "!");
			return false;
		}
				
		return true;
	}

}
