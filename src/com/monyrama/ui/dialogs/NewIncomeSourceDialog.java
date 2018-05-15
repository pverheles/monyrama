package com.monyrama.ui.dialogs;

import com.monyrama.db.enumarations.EntityStates;
import com.monyrama.entity.PCurrency;
import com.monyrama.entity.PIncomeSource;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.utils.MyDialogs;
import com.monyrama.utils.Trimmer;
import com.monyrama.validator.EntityValidator;
import com.monyrama.validator.NewIncomeSourceValidator;

class NewIncomeSourceDialog extends IncomeSourceDialog {

	public NewIncomeSourceDialog() {
		super();
		setTitle(Resources.getString("dialogs.titles.newincomesource"));
	}

	@Override
	protected void handleOkPresses() {
		PIncomeSource newIncomeSource = new PIncomeSource();
		newIncomeSource.setName(Trimmer.trim(nameField.getText()));
		newIncomeSource.setState(EntityStates.ACTIVE.getCode());
		newIncomeSource.setCurrency((PCurrency)currencyBox.getSelectedItem());
		newIncomeSource.setComment(commentsField.getText());

		EntityValidator validator = new NewIncomeSourceValidator(allIncomeSources(), newIncomeSource);
		
		if(validator.validate()) {
			incomeSource = newIncomeSource;
			save();
			setVisible(false);
		} else {
			MyDialogs.showWarningDialog(this, validator.message());
		}
	}

}
