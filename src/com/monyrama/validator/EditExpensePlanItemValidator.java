package com.monyrama.validator;

import java.util.List;

import com.monyrama.entity.PExpensePlanItem;
import com.monyrama.ui.resources.Resources;
import com.monyrama.controller.ExpensePlanItemController;
import com.monyrama.entity.PExpensePlan;
import com.monyrama.entity.PExpensePlanItem;
import com.monyrama.ui.resources.Resources;
import com.monyrama.validator.AbstractExpensePlanItemValidator;
import com.monyrama.validator.util.StringValidator;


public class EditExpensePlanItemValidator extends AbstractExpensePlanItemValidator {
	private PExpensePlanItem oldItem;
	
	public EditExpensePlanItemValidator(PExpensePlan budget, PExpensePlanItem oldItem, PExpensePlanItem newItem) {
		super(budget, newItem);
		
		this.oldItem = oldItem;
	}
	
	/*
	 * II. If we edit the item
	 *	   A. The edited item has no name:
	 *         1. Entered name is empty:
	 *              Check if the item with no name and the same category doesn't exist excluding the edited item
	 * 	       2. Entered name is not empty:
	 * 			    Check if the entered name is unique
	 * 
	 *     B. The edited item has a name:
	 *         1. Entered name is empty:
	 *              Check if the item with no name and the same category doesn't exist
	 * 	       2. Entered name is not empty:
	 * 			    Check if the entered name is unique excluding the name of the edited item
	 * 
	 */
	@Override
	protected boolean isNameEqualToExisting() {
		List<PExpensePlanItem> expensesPlanItems = ExpensePlanItemController.instance().listByExpensePlan(expensePlan);
		if(StringValidator.isStringNullOrEmpty(oldItem.getName())) {
			//II.A.1
			if(StringValidator.isStringNullOrEmpty(expensePlanItem.getName())) {
				for(PExpensePlanItem nextItem : expensesPlanItems) {
					if(!nextItem.equals(expensePlanItem)
							&& (nextItem.getCategory().equals(expensePlanItem.getCategory())
							&& (StringValidator.isStringNullOrEmpty(nextItem.getName())))) {
						
						setMessage(Resources.getString("dialogs.warnings.itemcategoryexist") + "!");
						return true;
					}
				}
		    //II.A.2	
			} else {				
				for(PExpensePlanItem nextItem : expensesPlanItems) {						
					if(StringValidator.areEqualEgnoreCase(expensePlanItem.getName(), nextItem.getName())) {
						setMessage(Resources.getString("dialogs.warnings.itemnameexist") + "!");
						return true;
					}
				}				
			}
		//II.B	
		} else {
			//II.B.1
			if(StringValidator.isStringNullOrEmpty(expensePlanItem.getName())) {
				for(PExpensePlanItem nextItem : expensesPlanItems) {
					if(nextItem.getCategory().equals(expensePlanItem.getCategory())
								&& (nextItem.getName() == null || nextItem.getName().equals(""))) {
						setMessage(Resources.getString("dialogs.warnings.itemcategoryexist") + "!");
						return true;
					}
				}
			//II.B.2
			} else {				
				for(PExpensePlanItem nextItem : expensesPlanItems) {
					if(!nextItem.equals(expensePlanItem)
							&& (StringValidator.areEqualEgnoreCase(nextItem.getName(), expensePlanItem.getName()))) {
						
						setMessage(Resources.getString("dialogs.warnings.itemnameexist") + "!");
						return true;
					}
				}				
			}				
		}
		
		return false;
	}
}
