package com.monyrama.ui.dialogs;

import com.monyrama.entity.PAccount;
import com.monyrama.ui.resources.Resources;
import com.monyrama.validator.EntityValidator;
import com.monyrama.validator.NewAccountValidator;

class NewAccountDialog extends AccountDialog {

	public NewAccountDialog() {
		super();
		setTitle(Resources.getString("dialogs.titles.newaccount"));
	}

	@Override
	protected void handleOkPressed() {
		PAccount newAccount = createAccountFromFields();
		
		EntityValidator validator = new NewAccountValidator(allAccounts(), newAccount);
		
		validateAndSave(newAccount, validator);
	}
}
