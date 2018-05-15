package com.monyrama.validator;

import java.math.BigDecimal;

import com.monyrama.entity.PAccount;
import com.monyrama.entity.PDebt;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.utils.MyFormatter;
import com.monyrama.validator.util.StringSumValidator;
import com.monyrama.validator.util.StringValidator;

public class PayBackValidator extends EntityValidator {	
		
	private PDebt debt;
	private String sum;
	private BigDecimal paidYet;
	private PAccount account;
		
	public PayBackValidator(PDebt debt, String sum, BigDecimal paidYet, PAccount account) {
		this.debt = debt;
		this.sum = sum;
		this.paidYet = paidYet;
		this.account = account;
	}

	@Override
	public boolean validate() {
		if(account == null) {
			setMessage(Resources.getString("dialogs.warnings.fromaccountempty") + "!");
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

        //Validate unless the return sum is higher than the debt sum
        BigDecimal leftToPay = debt.getSumm().subtract(paidYet);
        if (new BigDecimal(MyFormatter.formatNumberToStandard(sum)).compareTo(leftToPay) > 0) {
        	setMessage(Resources.getString("dialogs.warnings.returnhigherdebt") + "!");
            return false;
        }

		return true;
	}
}
