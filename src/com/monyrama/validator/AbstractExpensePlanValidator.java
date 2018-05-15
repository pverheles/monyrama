package com.monyrama.validator;

import java.util.Collection;

import com.monyrama.ui.resources.Resources;
import com.monyrama.entity.PExpensePlan;
import com.monyrama.ui.resources.Resources;
import com.monyrama.validator.EntityValidator;
import com.monyrama.validator.util.StringValidator;


abstract class AbstractExpensePlanValidator extends EntityValidator {
	protected Collection<PExpensePlan> existingBudgets;
	protected PExpensePlan budget;
	
	protected AbstractExpensePlanValidator(Collection<PExpensePlan> existingBudgets, PExpensePlan budget) {
		this.existingBudgets = existingBudgets;
		this.budget = budget;
	}

	@Override
	public boolean validate() {
		if(StringValidator.isStringNullOrEmpty(budget.getName())) {
			setMessage(Resources.getString("dialogs.warnings.nameempty") + "!");
			return false;
		}
		
		if(isNameEqualToExisting()) {
			return false;
		}
				
		return true;
	}
	
	protected abstract boolean isNameEqualToExisting();

}
