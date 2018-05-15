package com.monyrama.ui.dialogs;

import com.monyrama.entity.PCurrency;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.utils.MyDialogs;
import com.monyrama.entity.PCurrency;
import com.monyrama.ui.dialogs.IncomeSourceDialog;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.utils.MyDialogs;
import com.monyrama.utils.Trimmer;
import com.monyrama.validator.EditIncomeSourceValidator;
import com.monyrama.validator.EntityValidator;

class EditIncomeSourceDialog extends IncomeSourceDialog {

	public EditIncomeSourceDialog() {
		super();
		setTitle(Resources.getString("dialogs.titles.editincomesource"));
	}

	@Override
	protected void handleOkPresses() {
		incomeSource.setName(Trimmer.trim(nameField.getText()));
		incomeSource.setCurrency((PCurrency)currencyBox.getSelectedItem());
		incomeSource.setComment(Trimmer.trim(commentsField.getText()));
		
		EntityValidator validator = new EditIncomeSourceValidator(allIncomeSources(), incomeSource);
		
		if(validator.validate()) {
			save();
			setVisible(false);
		} else {
			MyDialogs.showWarningDialog(this, validator.message());
		}
	}
}
