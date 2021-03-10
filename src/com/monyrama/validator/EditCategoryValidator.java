package com.monyrama.validator;

import java.util.ArrayList;
import java.util.Collection;

import com.monyrama.entity.PCategory;
import com.monyrama.ui.resources.Resources;
import com.monyrama.validator.util.StringValidator;


public class EditCategoryValidator extends AbstractCategoryValidator {

	public EditCategoryValidator(Collection<PCategory> categoryList,	PCategory category) {
		super(categoryList, category);
	}

	@Override
	protected Collection<PCategory> filterCategoryList() {
		Collection<PCategory> filteredCategoryList = new ArrayList<>();
		for (PCategory cat : categoryList) {
			if (!cat.equals(category)) {
				filteredCategoryList.add(cat);
			}
		}

		return filteredCategoryList;
	}

}
