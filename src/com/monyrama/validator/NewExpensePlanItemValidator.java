package com.monyrama.validator;

import java.util.List;

import com.monyrama.controller.ExpensePlanItemController;
import com.monyrama.entity.PExpensePlan;
import com.monyrama.entity.PExpensePlanItem;
import com.monyrama.ui.resources.Resources;
import com.monyrama.validator.util.StringValidator;


public class NewExpensePlanItemValidator extends AbstractExpensePlanItemValidator {

	public NewExpensePlanItemValidator(PExpensePlan budget, PExpensePlanItem item) {
		super(budget, item);
	}

	/**
	 * Validates the entered data
	 * As it is possible to create an item with no name this method makes validation so:
	 * I. If we create a new item (edit == false)
	 *     1. Entered name is empty:
	 *          Check if the item with no name and the same category doesn't exist
	 * 	   2. Entered name is not empty:
	 * 			Check if the entered name is unique 
	 */
	@Override
	protected boolean isNameEqualToExisting() {
		List<PExpensePlanItem> expensesPlanItems = ExpensePlanItemController.instance().listByExpensePlan(expensePlan);
		if(expensesPlanItems.size() == 0) {
			return false;
		}
		
		if(StringValidator.isStringNullOrEmpty(expensePlanItem.getName())) {
			for(PExpensePlanItem nextItem : expensesPlanItems) {
				boolean isCategoriesEqualWhenNamesEmpty = nextItem.getCategory().equals(expensePlanItem.getCategory())
						&& StringValidator.isStringNullOrEmpty(nextItem.getName());
				
				if(isCategoriesEqualWhenNamesEmpty) {
					setMessage(Resources.getString("dialogs.warnings.itemcategoryexist") + "!");
					return true;
				}
			}	
		} else {
			for(PExpensePlanItem nextItem : expensesPlanItems) {
				boolean isNamesEqualWhenNotEmpty = !StringValidator.isStringNullOrEmpty(nextItem.getName())
						&& StringValidator.areEqualEgnoreCase(expensePlanItem.getName(), nextItem.getName());
				if(isNamesEqualWhenNotEmpty) {
					setMessage(Resources.getString("dialogs.warnings.itemnameexist") + "!");
					return true;
				}
			}				
		}
		
		return false;
	}
}
