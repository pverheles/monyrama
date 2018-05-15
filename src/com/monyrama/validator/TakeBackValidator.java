package com.monyrama.validator;

import java.math.BigDecimal;

import com.monyrama.entity.PAccount;
import com.monyrama.entity.PLend;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.utils.MyFormatter;
import com.monyrama.validator.util.StringSumValidator;
import com.monyrama.validator.util.StringValidator;

public class TakeBackValidator extends EntityValidator {
	
	private PLend lend;
	private String sum;
	private BigDecimal takenBackYet;
	private PAccount account;
	
	public TakeBackValidator(PLend lend, String sum, BigDecimal takenBackYet, PAccount account) {
		this.lend = lend;
		this.sum = sum;
		this.takenBackYet = takenBackYet;
		this.account = account;
	}
	
	@Override
	public boolean validate() {		
		if(account == null) {
			setMessage(Resources.getString("dialogs.warnings.toaccountempty") + "!");
			return false;			
		}
		
		if(StringValidator.isStringNullOrEmpty(sum)) {
			setMessage(Resources.getString("dialogs.warnings.sumempty") + "!");
			return false;
		}
		
		if(!StringSumValidator.isValidPositiveFormat(sum)) {
			setMessage(Resources.getString("dialogs.warnings.invalidsum") + "!");
			return false;
		}
		
		if(StringSumValidator.isZero(sum)) {
			setMessage(Resources.getString("dialogs.warnings.zerosum") + "!");
			return false;
		}
		
        //Validate unless the taking back sum is higher than the lend sum
        BigDecimal leftToTake = lend.getSumm().subtract(takenBackYet);
        if (new BigDecimal(MyFormatter.formatNumberToStandard(sum)).compareTo(leftToTake) > 0) {
        	setMessage(Resources.getString("dialogs.warnings.returnhigherlend") + "!");
            return false;
        }
		
		return true;
	}

}
