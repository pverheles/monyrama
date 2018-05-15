package com.monyrama.validator;

import com.monyrama.entity.PIncome;
import com.monyrama.ui.resources.Resources;
import com.monyrama.validator.EntityValidator;
import com.monyrama.validator.util.StringSumValidator;
import com.monyrama.validator.util.StringValidator;

public class IncomeItemValidator extends EntityValidator {
	
	private PIncome incomeItem;
	
	public IncomeItemValidator(PIncome incomeItem) {
		this.incomeItem = incomeItem;
	}
	

	@Override
	public boolean validate() {
		
		if(incomeItem.getAccount() == null) {
			setMessage(Resources.getString("dialogs.warnings.toaccountempty") + "!");
			return false;
		}
		
		if(StringValidator.isStringNullOrEmpty(incomeItem.getSumStr())) {
			setMessage(Resources.getString("dialogs.warnings.sumempty") + "!");
			return false;
		}
		
		if(!StringSumValidator.isValidPositiveFormat(incomeItem.getSumStr())) {
			setMessage(Resources.getString("dialogs.warnings.invalidsum") + "!");
			return false;
		}
				
		return true;
	}

}
