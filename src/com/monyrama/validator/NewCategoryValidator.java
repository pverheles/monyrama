package com.monyrama.validator;

import java.util.Collection;

import com.monyrama.entity.PCategory;
import com.monyrama.ui.resources.Resources;
import com.monyrama.validator.util.StringValidator;


public class NewCategoryValidator extends AbstractCategoryValidator {

	public NewCategoryValidator(Collection<PCategory> categoryList, PCategory category) {
		super(categoryList, category);
	}

	@Override
	protected Collection<PCategory> filterCategoryList() {
		return categoryList;
	}


}
