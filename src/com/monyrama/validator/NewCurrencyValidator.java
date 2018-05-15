package com.monyrama.validator;

import java.util.Collection;

import com.monyrama.entity.PCurrency;
import com.monyrama.validator.util.NameExistValidator;
import com.monyrama.validator.util.StringValidator;


public class NewCurrencyValidator extends AbstractCurrencyValidator {

	public NewCurrencyValidator(Collection<PCurrency> currencyList,
			PCurrency currency) {
		super(currencyList, currency);
	}

	@Override
	protected boolean isNameEqualToExisting() {
		return NameExistValidator.nameExistsForNew(currencyList, currency);
	}

	@Override
	protected boolean isCodeEqualToExisting() {
		for (PCurrency currencyNext : currencyList) {
			if (StringValidator.areEqualEgnoreCase(currencyNext.getCode(), currency.getCode())) {
				return true;
			}
		}
		
		return false;
	}

}
