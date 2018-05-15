package com.monyrama.ui.dialogs;

import com.monyrama.entity.PExpensePlanItem;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.utils.MyDialogs;
import com.monyrama.utils.Trimmer;
import com.monyrama.validator.EditExpensePlanItemValidator;
import com.monyrama.validator.EntityValidator;

public class EditExpensePlanItemDialog extends ExpensePlanItemDialog {

	public EditExpensePlanItemDialog() {
		super();
		setTitle(Resources.getString("dialogs.titles.edititem"));
	}

	@Override
	protected void handleOk() {
		PExpensePlanItem editItem = new PExpensePlanItem();
		editItem.setId(expensePlanItem.getId());
		editItem.setState(expensePlanItem.getState());
		editItem.setExpensePlan(expensePlanItem.getExpensePlan());
		editItem.setName(Trimmer.trim(nameField.getText()));
		editItem.setCategory(getSelectedCategory());
		editItem.setSumStr(Trimmer.trim(sumField.getText()));
		editItem.setComment(Trimmer.trim(commentField.getText()));

		EntityValidator validator = new EditExpensePlanItemValidator(expensePlan, expensePlanItem, editItem);
		if(validator.validate()) {
			expensePlanItem = editItem;
			tryToCreateOrUpdateItem();
			setVisible(false);
		} else {
			MyDialogs.showWarningDialog(this, validator.message());
		}
	}

}
