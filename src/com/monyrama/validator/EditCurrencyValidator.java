package com.monyrama.validator;

import java.util.Collection;

import com.monyrama.entity.PCurrency;
import com.monyrama.entity.PCurrency;
import com.monyrama.validator.AbstractCurrencyValidator;
import com.monyrama.validator.util.NameExistValidator;
import com.monyrama.validator.util.StringValidator;


public class EditCurrencyValidator extends AbstractCurrencyValidator {


	public EditCurrencyValidator(Collection<PCurrency> currencyList, PCurrency currency) {
		super(currencyList, currency);
	}

	@Override
	protected boolean isNameEqualToExisting() {
		return NameExistValidator.nameExistsForEdited(currencyList, currency);
	}

	@Override
	protected boolean isCodeEqualToExisting() {
		for (PCurrency currencyNext : currencyList) {
			if (!currencyNext.getId().equals(currency.getId()) && StringValidator.areEqualEgnoreCase(currencyNext.getCode(), currency.getCode())) {				
				return true;
			}
		}
		
		return false;
	}
}
