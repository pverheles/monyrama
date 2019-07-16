package com.monyrama.ui.dialogs;


import com.monyrama.controller.CategoryController;
import com.monyrama.db.enumarations.EntityStates;
import com.monyrama.entity.PCategory;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.utils.MyDialogs;
import com.monyrama.utils.Trimmer;
import com.monyrama.validator.EntityValidator;
import com.monyrama.validator.NewCategoryValidator;

class NewCategoryDialog extends CategoryDialog {
	
	public NewCategoryDialog() {
		super();
		setTitle(Resources.getString("dialogs.titles.newcategory"));
	}

	@Override
	protected void handleOkPressed() {
		PCategory newCategory = new PCategory();		
		newCategory.setName(Trimmer.trim(nameField.getText()));
		newCategory.setComment(Trimmer.trim(commentsField.getText()));
		newCategory.setCalculateSumPerDay(calculateSumPerDayBox.isSelected());
		
		EntityValidator validator = new NewCategoryValidator(CategoryController.instance().getAll(), newCategory);
		
		if(validator.validate()) {			
			newCategory.setState(EntityStates.ACTIVE.getCode());
			CategoryController.instance().createOrUpdate(newCategory);
			setVisible(false);	
		} else {
			MyDialogs.showWarningDialog(this, validator.message());
		}
	}

}
