package com.monyrama.ui.tables.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.monyrama.entity.PExpensePlanItem;
import com.monyrama.entity.PExpense;
import com.monyrama.ui.tables.columns.ExpenseColumnEnum;
import com.monyrama.ui.utils.MyFormatter;


/**
 * Model for the table of expenses
 * 
 * @author Petro_Verheles
 *
 */
public class ExpenseTableModel extends AbstractIdableTableModel<PExpense> {

	private Map<PExpensePlanItem, Set<PExpense>> expensePlanItemToExpenseMap = new HashMap<PExpensePlanItem, Set<PExpense>>();
	
	@Override
	public int getColumnCount() {		
		return ExpenseColumnEnum.values().length;
	}

	@Override
	public Object getValueAt(int row, int column) {
		PExpense unit = data.values().toArray(new PExpense[data.size()])[row];
		
		if(column == ExpenseColumnEnum.ID.getIndex()) {
			return unit.getId();
		}
		
		if(column == ExpenseColumnEnum.DATE.getIndex()) {
			return unit.getLastChangeDate();
		}
		
		if(column == ExpenseColumnEnum.PRICE.getIndex()) {
			return MyFormatter.formatNumberToLocal(unit.getSumm().toPlainString());
		}
		
		if(column == ExpenseColumnEnum.CATEGORY.getIndex()) {
			return unit.getExpensePlanItem().getCategory().getName();
		}
		
		if(column == ExpenseColumnEnum.BUDGET_ITEM.getIndex()) {
			return MyFormatter.createBudgetUnitName(unit.getExpensePlanItem());
		}
		
		if(column == ExpenseColumnEnum.FROM_ACCOUNT.getIndex()) {
			return unit.getAccount().getName();
		}
		
		if(column == ExpenseColumnEnum.COMMENTS.getIndex()) {
			return unit.getComment();
		}
		
		System.out.println("Invalid column index: " + column);
		
		return null;
	}
	
	@Override
	public String getColumnName(int col) {
        return ExpenseColumnEnum.values()[col].getName();
    }

	@Override
	public void putItem(PExpense spentUnit) {
		addSpentUnitToMap(spentUnit);
		super.putItem(spentUnit);
	}
	
	@Override
	public void removeItem(PExpense spentUnit) {
		Set<PExpense> spentUnits = getNotNullSpentUnits(spentUnit.getExpensePlanItem());
		spentUnits.remove(spentUnit);
		super.removeItem(spentUnit);
	}

	@Override
	public void reFill(Collection<PExpense> spentUnits) {
		expensePlanItemToExpenseMap.clear();
		super.reFill(spentUnits);
	}

	@Override
	public void putAllItems(Collection<PExpense> spentUnits) {
		for(PExpense spentUnit : spentUnits) {
			addSpentUnitToMap(spentUnit);
		}
		super.putAllItems(spentUnits);
	}

	@Override
	public void clear() {
		expensePlanItemToExpenseMap.clear();
		super.clear();
	}
	
	public void updateDependentExpenses(PExpensePlanItem expensePlanItem) {
		for(PExpense spentUnit : getSpentUnitsByBudgetUnit(expensePlanItem)) {
			spentUnit.setExpensePlanItem(expensePlanItem);
		}
	}

	public Set<PExpense> getSpentUnitsByBudgetUnit(PExpensePlanItem budgetUnit) {
		return getNotNullSpentUnits(budgetUnit);
	}

	private Set<PExpense> getNotNullSpentUnits(PExpensePlanItem budgetUnit) {
		Set<PExpense> spentUnitsByBudgetUnit = expensePlanItemToExpenseMap.get(budgetUnit);
		if(spentUnitsByBudgetUnit == null) {
			spentUnitsByBudgetUnit = new HashSet<PExpense>();
			expensePlanItemToExpenseMap.put(budgetUnit, spentUnitsByBudgetUnit);
		}
		return spentUnitsByBudgetUnit;
	}
	
	private void addSpentUnitToMap(PExpense spentUnit) {
		Set<PExpense> spentUnitsByBudgetUnit = getNotNullSpentUnits(spentUnit.getExpensePlanItem());
		//equals by id see PSpentUnit code
		if(spentUnitsByBudgetUnit.contains(spentUnit)) {
			spentUnitsByBudgetUnit.remove(spentUnit);
		}
		spentUnitsByBudgetUnit.add(spentUnit);
	}	
}
