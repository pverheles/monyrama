package com.monyrama.validator;

import java.util.Collection;

import com.monyrama.entity.PExpensePlan;
import com.monyrama.ui.resources.Resources;
import com.monyrama.validator.util.StringValidator;


public class NewExpensePlanValidator extends AbstractExpensePlanValidator {

	public NewExpensePlanValidator(Collection<PExpensePlan> existingBudgets,
			PExpensePlan budget) {
		super(existingBudgets, budget);
	}

	@Override
	protected boolean isNameEqualToExisting() {
		for(PExpensePlan existingBudget : existingBudgets) {
			if(StringValidator.areEqualEgnoreCase(existingBudget.getName(), budget.getName())) {
				setMessage(Resources.getString("dialogs.warnings.budgetnameexist") + "!");
				return true;
			}
		}
		
		return false;
	}

}
