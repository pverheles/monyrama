package com.monyrama.validator;

import com.monyrama.entity.PExpensePlan;
import com.monyrama.entity.PExpensePlanItem;
import com.monyrama.ui.resources.Resources;
import com.monyrama.validator.util.StringSumValidator;
import com.monyrama.validator.util.StringValidator;

abstract class AbstractExpensePlanItemValidator extends EntityValidator {
	protected PExpensePlanItem expensePlanItem;
	protected PExpensePlan expensePlan;

	protected AbstractExpensePlanItemValidator(PExpensePlan expensePlan, PExpensePlanItem expensePlanItem) {
		this.expensePlanItem = expensePlanItem;
		this.expensePlan = expensePlan;
	}

	@Override
	public boolean validate() {			
		if (isNameEqualToExisting()) {
			return false;
		}
		
		if (StringValidator.isStringNullOrEmpty(expensePlanItem.getSumStr())) {
			String message = Resources.getString("dialogs.warnings.sumempty") + "!";
			setMessage(message);
			return false;
		}

		if (!StringSumValidator.isValidPositiveFormat(expensePlanItem.getSumStr())) {
			String message = Resources.getString("dialogs.warnings.invalidsum") + "!";
			setMessage(message);
			return false;
		}		

		return true;
	}

	protected abstract boolean isNameEqualToExisting();

}
