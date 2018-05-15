package com.monyrama.validator;

import java.util.Collection;

import com.monyrama.entity.PIncomeSource;
import com.monyrama.ui.resources.Resources;
import com.monyrama.validator.util.StringValidator;


public class NewIncomeSourceValidator extends AbstractIncomeSourceValidator {

	public NewIncomeSourceValidator(Collection<PIncomeSource> existingIncomeSources,
			PIncomeSource incomeSource) {
		super(existingIncomeSources, incomeSource);
	}

	@Override
	protected boolean isNameEqualToExisting() {
		for(PIncomeSource existingincomeSource : existingIncomeSources) {
			if(StringValidator.areEqualEgnoreCase(existingincomeSource.getName(), incomeSource.getName())) {
				setMessage(Resources.getString("dialogs.warnings.incomeSourceNameExist") + "!");
				return true;
			}
		}
		
		return false;
	}

}
