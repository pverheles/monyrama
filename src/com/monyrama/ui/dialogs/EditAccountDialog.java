package com.monyrama.ui.dialogs;

import com.monyrama.entity.PAccount;
import com.monyrama.ui.resources.Resources;
import com.monyrama.validator.EditAccountValidator;
import com.monyrama.validator.EntityValidator;

class EditAccountDialog extends AccountDialog {
	public EditAccountDialog() {
		super();
		setTitle(Resources.getString("dialogs.titles.editaccount"));
	}
	
	@Override
	protected void handleOkPressed() {
		PAccount editAccount = createAccountFromFields();	
		editAccount.setId(account.getId());
		
		EntityValidator validator = new EditAccountValidator(allAccounts(), editAccount);
		
		validateAndSave(editAccount, validator);
	}
}
