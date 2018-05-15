package com.monyrama.validator;

import java.util.Collection;

import com.monyrama.ui.resources.Resources;
import com.monyrama.entity.PAccount;
import com.monyrama.ui.resources.Resources;
import com.monyrama.validator.EntityValidator;
import com.monyrama.validator.util.StringSumValidator;
import com.monyrama.validator.util.StringValidator;

abstract class AbstractAccountValidator extends EntityValidator {

	protected PAccount account;
	protected Collection<PAccount> existingAccounts;
	
	protected AbstractAccountValidator(Collection<PAccount> existingAccounts, PAccount account) {
		this.existingAccounts = existingAccounts;
		this.account = account;
	}

	@Override
	public boolean validate() {

		if(StringValidator.isStringNullOrEmpty(account.getName())) {
			String message = Resources.getString("dialogs.warnings.nameempty") + "!";
			setMessage(message);
			return false;
		}

		if(isNameEqualToExisting()) {
			String message = Resources.getString("dialogs.warnings.accountnameexist") + "!";
			setMessage(message);
			return false;
		}

		if (StringValidator.isStringNullOrEmpty(account.getSumStr())) {
			String message = Resources.getString("dialogs.warnings.sumempty") + "!";
			setMessage(message);
			return false;
		}

		if (!isValidSumFormat(account.getSumStr())) {
			String message = Resources.getString("dialogs.warnings.invalidsum") + "!";
			setMessage(message);
			return false;
		}

		return true;
	}

	protected abstract boolean isValidSumFormat(String sumStr);

	protected abstract boolean isNameEqualToExisting();
	
}
