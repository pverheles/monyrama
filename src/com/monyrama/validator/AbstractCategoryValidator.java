package com.monyrama.validator;

import com.monyrama.entity.PCategory;
import com.monyrama.ui.resources.Resources;
import com.monyrama.validator.util.StringValidator;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Set;


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

    if (isNameEqualToExisting()) {
      return false;
    }

		if (isKeywordEqualToExisting()) {
			return false;
		}

    return true;
  }

  private boolean isNameEqualToExisting() {
    for (PCategory cat : filterCategoryList()) {
      if (StringValidator.areEqualEgnoreCase(cat.getName(), category.getName())) {
        setMessage(Resources.getString("dialogs.warnings.categorynameexist") + "!");
        return true;
      }
    }

    return false;
  }

  private boolean isKeywordEqualToExisting() {
    Set<String> keywordsSet = category.keywordsSetLower();
    if (keywordsSet.isEmpty()) {
      return false;
    }

    for (PCategory cat : filterCategoryList()) {
      Set<String> existingKeywordsSet = cat.keywordsSetLower();
      for (String existingKeyword : existingKeywordsSet) {
        if (keywordsSet.contains(existingKeyword)) {
					String messageTemplate = Resources.getString("dialogs.warnings.keywordexist");
					setMessage(MessageFormat.format(messageTemplate, existingKeyword, cat.getName()) + "!");
					return true;
        }
      }
    }

    return false;
  }

  protected abstract Collection<PCategory> filterCategoryList();
}
