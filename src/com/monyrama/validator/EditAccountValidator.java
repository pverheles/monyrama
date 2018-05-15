package com.monyrama.validator;

import java.util.Collection;

import com.monyrama.entity.PAccount;
import com.monyrama.validator.util.NameExistValidator;
import com.monyrama.validator.util.StringSumValidator;

public class EditAccountValidator extends AbstractAccountValidator {

	public EditAccountValidator(Collection<PAccount> existingAccounts, PAccount account) {
		super(existingAccounts, account);
	}

	@Override
	protected boolean isValidSumFormat(String sumStr) {
		return StringSumValidator.isValidPositiveOrNegativeFormat(sumStr);
	}

	@Override
	protected boolean isNameEqualToExisting() {
		return NameExistValidator.nameExistsForEdited(existingAccounts, account);
	}

}
