package com.monyrama.ui.dialogs;

import com.monyrama.entity.PCurrency;
import com.monyrama.ui.resources.Resources;
import com.monyrama.validator.EntityValidator;
import com.monyrama.validator.NewCurrencyValidator;

class NewCurrencyDialog extends CurrencyDialog {

	public NewCurrencyDialog() {
		super();
		setTitle(Resources.getString("dialogs.titles.newcurrency"));
	}

	@Override
	protected EntityValidator getValidator(PCurrency currency) {
		return new NewCurrencyValidator(allCurrencies(), currency);
	}

}
