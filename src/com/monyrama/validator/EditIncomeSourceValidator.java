package com.monyrama.validator;

import java.util.Collection;

import com.monyrama.entity.PIncomeSource;
import com.monyrama.ui.resources.Resources;
import com.monyrama.validator.util.StringValidator;


public class EditIncomeSourceValidator extends AbstractIncomeSourceValidator {

	public EditIncomeSourceValidator(Collection<PIncomeSource> existingincomeSources,
			PIncomeSource incomeSource) {
		super(existingincomeSources, incomeSource);
	}

	@Override
	protected boolean isNameEqualToExisting() {
		for(PIncomeSource existingincomeSource : existingIncomeSources) {
			if(!existingincomeSource.equals(incomeSource)
					&& StringValidator.areEqualEgnoreCase(existingincomeSource.getName(), incomeSource.getName())) {
				setMessage(Resources.getString("dialogs.warnings.incomeSourceNameExist") + "!");
				return true;
			}
		}
		
		return false;
	}

}
