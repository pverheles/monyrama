package com.monyrama.validator;

import com.monyrama.entity.PAccount;
import com.monyrama.ui.resources.Resources;
import com.monyrama.validator.util.StringSumValidator;
import com.monyrama.validator.util.StringValidator;

public class BorrowValidator extends EntityValidator {
	
	private String name;
	private String sum;
	private PAccount account;
	
	public BorrowValidator(String name, String sum, PAccount account) {
		this.name = name;
		this.sum = sum;
		this.account = account;
	}

	@Override
	public boolean validate() {
        if (StringValidator.isStringNullOrEmpty(name)) {
            setMessage(Resources.getString("dialogs.warnings.nameempty") + "!");
            return false;
        }
        
		if(account == null) {
			setMessage(Resources.getString("dialogs.warnings.toaccountempty") + "!");
			return false;			
		}        

        if (StringValidator.isStringNullOrEmpty(sum)) {
            setMessage(Resources.getString("dialogs.warnings.sumempty") + "!");
            return false;
        }

        if (!StringSumValidator.isValidPositiveFormat(sum)) {
            setMessage(Resources.getString("dialogs.warnings.invalidsum") + "!");
            return false;
        }

        if (StringSumValidator.isZero(sum)) {
            setMessage(Resources.getString("dialogs.warnings.zerosum") + "!");
            return false;
        }

        return true;
	}

}
