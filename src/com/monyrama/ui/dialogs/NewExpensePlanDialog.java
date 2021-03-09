package com.monyrama.ui.dialogs;

import com.monyrama.controller.ExpensePlanItemController;
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
		PExpensePlan newExpensePlan = new PExpensePlan();
		newExpensePlan.setName(Trimmer.trim(nameField.getText()));
		newExpensePlan.setState(EntityStates.ACTIVE.getCode());
		newExpensePlan.setCurrency(currencyBox.getSelectedItem());
		newExpensePlan.setComment(commentsField.getText());

		EntityValidator validator = new NewExpensePlanValidator(allBudgets(), newExpensePlan);
		
		if(validator.validate()) {
			expensePlan = newExpensePlan;
			save();
			ExpensePlanItemController.instance().createDefault(expensePlan);
			setVisible(false);
		} else {
			MyDialogs.showWarningDialog(this, validator.message());
		}
	}

}
