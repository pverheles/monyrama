package com.monyrama.validator;

import java.util.Collection;

import com.monyrama.entity.PCategory;
import com.monyrama.ui.resources.Resources;
import com.monyrama.entity.PCategory;
import com.monyrama.ui.resources.Resources;
import com.monyrama.validator.EntityValidator;
import com.monyrama.validator.util.StringValidator;


abstract class AbstractCategoryValidator extends EntityValidator {

	protected PCategory category;
	protected Collection<PCategory> categoryList;

	public AbstractCategoryValidator(Collection<PCategory> categoryList, PCategory category) {
		this.categoryList = categoryList;
		this.category = category;
	}

	@Override
	public boolean validate() {
		if (StringValidator.isStringNullOrEmpty(category.getName())) {
			setMessage(Resources.getString("dialogs.warnings.nameempty") + "!");
			return false;
		}
		
		if(isNameEqualToExisting()) {
			return false;
		}
		
		return true;
	}
	
	protected abstract boolean isNameEqualToExisting();
}
