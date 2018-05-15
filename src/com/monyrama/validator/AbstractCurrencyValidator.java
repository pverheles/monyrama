package com.monyrama.validator;

import java.math.BigDecimal;
import java.util.Collection;

import com.monyrama.entity.PCurrency;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.utils.MyFormatter;
import com.monyrama.validator.util.StringValidator;

abstract class AbstractCurrencyValidator extends EntityValidator {

	protected PCurrency currency;
	protected Collection<PCurrency> currencyList;

	public AbstractCurrencyValidator(Collection<PCurrency> currencyList,
			PCurrency currency) {
		this.currencyList = currencyList;
		this.currency = currency;
	}

	@Override
	public boolean validate() {
		if(StringValidator.isStringNullOrEmpty(currency.getCode())) {			
			String message = Resources.getString("dialogs.warnings.codeempty") + "!";
			setMessage(message);
			return false;
		}		
		
		if(StringValidator.isStringNullOrEmpty(currency.getName())) {
			String message = Resources.getString("dialogs.warnings.nameempty") + "!";
			setMessage(message);
			return false;
		}
				
		if(isCodeEqualToExisting()) {
			String message = Resources.getString("dialogs.warnings.currencycodeexist")	+ "!";
			setMessage(message);
			return false;
		}	
		
		if(isNameEqualToExisting()) {
			String message = Resources.getString("dialogs.warnings.currencynameexist")	+ "!";
			setMessage(message);
			return false;
		}	
		
		if(StringValidator.isStringNullOrEmpty(currency.getExchangeRateStr())) {
			String message = Resources.getString("dialogs.warnings.exchangerateempty") + "!";
			setMessage(message);
			return false;
		}
		
		boolean isRateValid = true;
		try {
			String stringRate = MyFormatter.formatNumberToStandard(currency.getExchangeRateStr());
			new BigDecimal(stringRate);
		} catch(Exception e) {
			isRateValid = false;
		}
				
		if(!isRateValid) {
			String message = Resources.getString("dialogs.warnings.invalidexchangerate") + "!";
			setMessage(message);
			return false;
		}
				
		return true;
	}

	protected abstract boolean isNameEqualToExisting();

	protected abstract boolean isCodeEqualToExisting();
}
