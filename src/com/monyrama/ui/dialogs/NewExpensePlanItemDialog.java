package com.monyrama.ui.dialogs;

import java.math.BigDecimal;

import com.monyrama.db.enumarations.EntityStates;
import com.monyrama.entity.PExpensePlanItem;
import com.monyrama.db.enumarations.EntityStates;
import com.monyrama.entity.PExpensePlanItem;
import com.monyrama.ui.dialogs.ExpensePlanItemDialog;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.utils.MyDialogs;
import com.monyrama.utils.Trimmer;
import com.monyrama.validator.EntityValidator;
import com.monyrama.validator.NewExpensePlanItemValidator;

class NewExpensePlanItemDialog extends ExpensePlanItemDialog {

	public NewExpensePlanItemDialog() {
		super();
		setTitle(Resources.getString("dialogs.titles.newitem"));
	}

	@Override
	protected void handleOk() {
		PExpensePlanItem newItem = new PExpensePlanItem();
		
		newItem.setState(EntityStates.ACTIVE.getCode());
		newItem.setExpensePlan(expensePlan);
		newItem.setName(Trimmer.trim(nameField.getText()));
		newItem.setCategory(getSelectedCategory());
		newItem.setSumStr(Trimmer.trim(sumField.getText()));
		newItem.setComment(Trimmer.trim(commentField.getText()));

		EntityValidator validator = new NewExpensePlanItemValidator(expensePlan, newItem);
		if(validator.validate()) {
			expensePlanItem = newItem;
			tryToCreateOrUpdateItem();
			setVisible(false);
		} else {
			MyDialogs.showWarningDialog(this, validator.message());
		}
	}

}
