
package com.monyrama.ui.tables.model;

import java.math.BigDecimal;
import java.util.Set;

import com.monyrama.db.enumarations.EntityStates;
import com.monyrama.entity.PExpense;
import com.monyrama.entity.PExpensePlanItem;
import com.monyrama.ui.tables.columns.ExpensePlanItemColumnEnum;
import com.monyrama.db.enumarations.EntityStates;
import com.monyrama.entity.PExpense;
import com.monyrama.entity.PExpensePlanItem;
import com.monyrama.ui.tables.columns.ExpensePlanItemColumnEnum;
import com.monyrama.ui.tables.model.AbstractIdableTableModel;
import com.monyrama.ui.tables.model.ExpenseTableModel;
import com.monyrama.ui.utils.MyFormatter;
import com.monyrama.utils.Calc;


/**
 * 
 * Model for the table of items
 * 
 * @author Petro_Verheles
 *
 */
 public class ExpenseItemTableModel extends AbstractIdableTableModel<PExpensePlanItem> {
	private ExpenseTableModel expenseTableModel;
		 
	public ExpenseItemTableModel(ExpenseTableModel expenseTableModel) {
		super();
		this.expenseTableModel = expenseTableModel;
	}

	@Override
	public int getColumnCount() {		
		return ExpensePlanItemColumnEnum.values().length;
	}

	@Override	
	public Object getValueAt(int rowIndex, int columnIndex) {		

		if(data == null || data.isEmpty()) {
			return null;
		}				
		
		final PExpensePlanItem nextExpensePlanItem = data.values().toArray(new PExpensePlanItem[data.size()])[rowIndex];
		
		if(columnIndex == ExpensePlanItemColumnEnum.ID.getIndex()) {
			return nextExpensePlanItem.getId();
		}
		
		if(columnIndex == ExpensePlanItemColumnEnum.NAME.getIndex()) {
			return nextExpensePlanItem.getName();
		}
		
		if(columnIndex == ExpensePlanItemColumnEnum.CATEGORY.getIndex()) {
			return nextExpensePlanItem.getCategory().getName();				
		}
		
		if(columnIndex == ExpensePlanItemColumnEnum.SUM.getIndex()) {
			if(nextExpensePlanItem.getSumm() == null) {
				return "";
			}						
			return MyFormatter.formatNumberToLocal(nextExpensePlanItem.getSumm().toPlainString());			
		}
		
		Set<PExpense> spentUnitsByBudgetUnit = expenseTableModel.getSpentUnitsByBudgetUnit(nextExpensePlanItem);
		BigDecimal spent = Calc.sum(spentUnitsByBudgetUnit);
		if(columnIndex == ExpensePlanItemColumnEnum.SPENT.getIndex()) {
			return MyFormatter.formatNumberToLocal(spent.toPlainString());			
		}
		
		if(columnIndex == ExpensePlanItemColumnEnum.REMAINDER.getIndex()) {
			BigDecimal remainder = nextExpensePlanItem.getSumm().subtract(spent);
			return MyFormatter.formatNumberToLocal(remainder.toPlainString());
		}
		
		if(columnIndex == ExpensePlanItemColumnEnum.COMMENTS.getIndex()) {
			return nextExpensePlanItem.getComment();
		}
		
		if(columnIndex == ExpensePlanItemColumnEnum.BLOCKED.getIndex()) {
			return nextExpensePlanItem.getState().equals(EntityStates.CLOSED.getCode());
		}		
		
		System.out.println("Invalid column index: " + columnIndex); //This shouldn't ever happen
		
		return null;
	}
	
	@Override
	public Class<?> getColumnClass(int column) {
		if(column == ExpensePlanItemColumnEnum.BLOCKED.getIndex()) {
			return Boolean.class;
		}
		return super.getColumnClass(column);
	}

	@Override
	public String getColumnName(int col) {
        return ExpensePlanItemColumnEnum.values()[col].getName();
    }

	@Override
	public void putItem(PExpensePlanItem item) {
		super.putItem(item);
		expenseTableModel.updateDependentExpenses(item);
	}
	
	
}
