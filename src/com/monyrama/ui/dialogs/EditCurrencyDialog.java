package com.monyrama.ui.dialogs;

import com.monyrama.entity.PCurrency;
import com.monyrama.ui.resources.Resources;
import com.monyrama.validator.EditCurrencyValidator;
import com.monyrama.entity.PCurrency;
import com.monyrama.ui.dialogs.CurrencyDialog;
import com.monyrama.ui.resources.Resources;
import com.monyrama.validator.EditCurrencyValidator;
import com.monyrama.validator.EntityValidator;

class EditCurrencyDialog extends CurrencyDialog {

	public EditCurrencyDialog() {
		super();
		setTitle(Resources.getString("dialogs.titles.editcurrency"));
	}

	@Override
	protected EntityValidator getValidator(PCurrency currency) {
		return new EditCurrencyValidator(allCurrencies(), currency);
	}
}
