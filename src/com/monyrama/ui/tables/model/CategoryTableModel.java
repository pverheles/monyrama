package com.monyrama.ui.tables.model;

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
		PCategory unit = data.values().toArray(new PCategory[data.size()])[row];

		if(column == CategoryColumnEnum.ID.getIndex()) {
			return unit.getId();
		}
		
		if(column == CategoryColumnEnum.NAME.getIndex()) {
			return unit.getName();
		}
		
		if(column == CategoryColumnEnum.COMMENTS.getIndex()) {
			return unit.getComment();
		}
		
		if(column == CategoryColumnEnum.BLOCKED.getIndex()) {
			return unit.getState() != null && unit.getState().equals(EntityStates.CLOSED.getCode());
		}
		
		System.out.println("Invalid column index: " + column);

		return null;
	}
	
	@Override
	public String getColumnName(int col) {
        return CategoryColumnEnum.values()[col].getName();
    }	
}
