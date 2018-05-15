package com.monyrama.ui.dialogs;

import com.monyrama.db.enumarations.EntityStates;
import com.monyrama.entity.PCurrency;
import com.monyrama.ui.utils.MyDialogs;
import com.monyrama.db.enumarations.EntityStates;
import com.monyrama.entity.PCurrency;
import com.monyrama.entity.PExpensePlan;
import com.monyrama.ui.dialogs.ExpensePlanDialog;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.utils.MyDialogs;
import com.monyrama.utils.Trimmer;
import com.monyrama.validator.EntityValidator;
import com.monyrama.validator.NewExpensePlanValidator;

class NewExpensePlanDialog extends ExpensePlanDialog {

	protected NewExpensePlanDialog() {
		super();
		setTitle(Resources.getString("dialogs.titles.newbudget"));
	}

	@Override
	protected void handleOkPresses() {
		PExpensePlan newBudget = new PExpensePlan();
		newBudget.setName(Trimmer.trim(nameField.getText()));
		newBudget.setState(EntityStates.ACTIVE.getCode());
		newBudget.setCurrency((PCurrency)currencyBox.getSelectedItem());
		newBudget.setComment(commentsField.getText());

		EntityValidator validator = new NewExpensePlanValidator(allBudgets(), newBudget);
		
		if(validator.validate()) {
			expensePlan = newBudget;
			save();
			setVisible(false);
		} else {
			MyDialogs.showWarningDialog(this, validator.message());
		}
	}

}
