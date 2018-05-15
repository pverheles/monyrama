package com.monyrama.validator;

import com.monyrama.ui.resources.Resources;
import com.monyrama.validator.util.StringValidator;

public class PortValidator extends EntityValidator {
	
	private String value; 
	
	public PortValidator(String value) {
		this.value = value;
	}
	
	@Override
	public boolean validate() {
		if(StringValidator.isStringNullOrEmpty(value)) {
			setMessage(Resources.getString("dialogs.warnings.serverPortEmpty"));
			return false;
		}
		
		if(!value.matches("[0-9]{1,}")) {
			setMessage(Resources.getString("dialogs.warnings.serverPortInvalid"));
			return false;
		}
		
		int valueInt = Integer.valueOf(value);
		if(valueInt < 1024 || valueInt > 99999) {
			setMessage(Resources.getString("dialogs.warnings.serverPortInvalid"));
			return false;
		}
		
		return true;
	}
}
