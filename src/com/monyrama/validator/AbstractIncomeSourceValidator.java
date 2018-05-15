package com.monyrama.validator;

import java.util.Collection;

import com.monyrama.entity.PIncomeSource;
import com.monyrama.ui.resources.Resources;
import com.monyrama.validator.util.StringValidator;


abstract class AbstractIncomeSourceValidator extends EntityValidator {

	protected Collection<PIncomeSource> existingIncomeSources;
	protected PIncomeSource incomeSource;
	
	protected AbstractIncomeSourceValidator(Collection<PIncomeSource> existingIncomeSources, PIncomeSource incomeSource) {
		this.existingIncomeSources = existingIncomeSources;
		this.incomeSource = incomeSource;
	}
	
	@Override
	public boolean validate() {
		if(StringValidator.isStringNullOrEmpty(incomeSource.getName())) {
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
