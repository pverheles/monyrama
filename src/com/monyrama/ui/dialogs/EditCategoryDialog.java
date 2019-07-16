package com.monyrama.ui.dialogs;

import com.monyrama.controller.CategoryController;
import com.monyrama.entity.PCategory;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.utils.MyDialogs;
import com.monyrama.utils.Trimmer;
import com.monyrama.validator.EditCategoryValidator;
import com.monyrama.validator.EntityValidator;

class EditCategoryDialog extends CategoryDialog {
	
	public EditCategoryDialog() {
		super();
		setTitle(Resources.getString("dialogs.titles.editcategory"));
	}

	@Override
	protected void handleOkPressed() {
		category.setName(Trimmer.trim(nameField.getText()));
		category.setComment(Trimmer.trim(commentsField.getText()));
		category.setCalculateSumPerDay(calculateSumPerDayBox.isSelected());

		EntityValidator validator = new EditCategoryValidator(CategoryController.instance().getAll(), category);
		
		if(validator.validate()) {
			CategoryController.instance().createOrUpdate(category);
			setVisible(false);	
		} else {
			MyDialogs.showWarningDialog(this, validator.message());
		}
	}
}
