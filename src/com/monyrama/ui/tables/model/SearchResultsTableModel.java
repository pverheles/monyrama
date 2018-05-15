package com.monyrama.ui.tables.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.monyrama.entity.PCurrency;
import com.monyrama.entity.PExpense;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.tables.columns.ExpensesSearchColumnEnum;
import com.monyrama.ui.utils.MyFormatter;


/**
 * Model for the table of Search results
 * 
 * @author Petro_Verheles
 *
 */
public class SearchResultsTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 174929852255536994L;

	private List<PExpense> data = new ArrayList<PExpense>();
	
	@Override
	public int getColumnCount() {		
		return ExpensesSearchColumnEnum.values().length;
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		PExpense unit = data.get(row);
		
		if(column == ExpensesSearchColumnEnum.DATE.getIndex()) {
			SimpleDateFormat formatter;
		    formatter = new SimpleDateFormat(Resources.getDateFormat(), Resources.getLocale());			    
		    String result = formatter.format(unit.getLastChangeDate());
			return result;
		}
		
		if(column == ExpensesSearchColumnEnum.PRICE.getIndex()) {
			return MyFormatter.formatNumberToLocal(unit.getSumm().toPlainString());
		}
		
		if(column == ExpensesSearchColumnEnum.CATEGORY.getIndex()) {
			return unit.getExpensePlanItem().getCategory().getName();
		}
		
		if(column == ExpensesSearchColumnEnum.BUDGET.getIndex()) {
			return unit.getExpensePlanItem().getExpensePlan().getName();
		}
		
		if(column == ExpensesSearchColumnEnum.CURRENCY.getIndex()) {
			PCurrency currency = unit.getExpensePlanItem().getExpensePlan().getCurrency();
			return currency.toString();
		}
		
		if(column == ExpensesSearchColumnEnum.BUDGET_ITEM.getIndex()) {
			return MyFormatter.createBudgetUnitName(unit.getExpensePlanItem());
		}
		
		if(column == ExpensesSearchColumnEnum.COMMENTS.getIndex()) {
			return unit.getComment();
		}
		
		System.out.println("Invalid column index: " + column);
		
		return null;
	}

	@Override
	public String getColumnName(int i) {		
		return ExpensesSearchColumnEnum.values()[i].getName();
	}

	/**
	 * Clears the model and fills the model with new data
	 * 
	 * @param spentUnitList - list of expenses to add
	 */
	public void reFill(List<PExpense> spentUnitList) {
		data.clear();
		data.addAll(spentUnitList);
		fireTableDataChanged();
	}
	
	/**
	 * Clears the model
	 */
	public void clear() {
		data.clear();
		fireTableDataChanged();
	}
}
