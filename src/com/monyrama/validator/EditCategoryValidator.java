package com.monyrama.validator;

import java.util.Collection;

import com.monyrama.entity.PCategory;
import com.monyrama.ui.resources.Resources;
import com.monyrama.validator.util.StringValidator;


public class EditCategoryValidator extends AbstractCategoryValidator {

	public EditCategoryValidator(Collection<PCategory> categoryList,	PCategory category) {
		super(categoryList, category);
	}

	@Override
	protected boolean isNameEqualToExisting() {
		for (PCategory cat : categoryList) {
			if (!cat.equals(category) && StringValidator.areEqualEgnoreCase(cat.getName(), category.getName())) {
				setMessage(Resources.getString("dialogs.warnings.categorynameexist")	+ "!");
				return true;
			}
		}
		
		return false;
	}
}
