package com.monyrama.ui.tables.model;

import com.monyrama.controller.CategoryController;
import com.monyrama.db.enumarations.EntityStates;
import com.monyrama.entity.PCategory;
import com.monyrama.ui.tables.columns.CategoryColumnEnum;

/**
 * Model for the table of categories
 * 
 * @author Petro_Verheles
 *
 */
public class CategoryTableModel extends AbstractIdableTableModel<PCategory> {
		
	@Override
	public int getColumnCount() {
		return CategoryColumnEnum.values().length;
	}


	@Override
	public Object getValueAt(int row, int column) {
		PCategory category = data.values().toArray(new PCategory[data.size()])[row];

		if(column == CategoryColumnEnum.ID.getIndex()) {
			return category.getId();
		}
		
		if(column == CategoryColumnEnum.NAME.getIndex()) {
			return category.getName();
		}
		
		if(column == CategoryColumnEnum.COMMENTS.getIndex()) {
			return category.getComment();
		}
		
		if(column == CategoryColumnEnum.BLOCKED.getIndex()) {
			return category.getState() != null && category.getState().equals(EntityStates.CLOSED.getCode());
		}

		if (column == CategoryColumnEnum.AVG_SUM_PER_DAY.getIndex()) {
			if (category.getCalculateSumPerDay()) {
				return CategoryController.instance().calculateAvarageExpenseSumPerDay(category);
			}

			return "-";
		}
		
		System.out.println("Invalid column index: " + column);

		return null;
	}
	
	@Override
	public String getColumnName(int col) {
        return CategoryColumnEnum.values()[col].getName();
    }	
}
