package com.monyrama.ui.dialogs;

import com.monyrama.entity.PCurrency;
import com.monyrama.ui.utils.MyDialogs;
import com.monyrama.validator.EditExpensePlanValidator;
import com.monyrama.entity.PCurrency;
import com.monyrama.ui.dialogs.ExpensePlanDialog;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.utils.MyDialogs;
import com.monyrama.utils.Trimmer;
import com.monyrama.validator.EditExpensePlanValidator;
import com.monyrama.validator.EntityValidator;

class EditBudgetDialog extends ExpensePlanDialog {

	protected EditBudgetDialog() {
		super();
		setTitle(Resources.getString("dialogs.titles.editbudget"));
	}

	@Override
	protected void handleOkPresses() {
		expensePlan.setName(Trimmer.trim(nameField.getText()));
		expensePlan.setCurrency((PCurrency)currencyBox.getSelectedItem());
		expensePlan.setComment(Trimmer.trim(commentsField.getText()));
		
		EntityValidator validator = new EditExpensePlanValidator(allBudgets(), expensePlan);
		
		if(validator.validate()) {
			save();
			setVisible(false);
		} else {
			MyDialogs.showWarningDialog(this, validator.message());
		}
	}
}
