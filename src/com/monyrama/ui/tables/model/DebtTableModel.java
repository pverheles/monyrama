package com.monyrama.ui.tables.model;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import com.monyrama.controller.PayingBackController;
import com.monyrama.entity.PDebt;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.tables.columns.DebtColumnEnum;
import com.monyrama.ui.tables.model.AbstractIdableTableModel;
import com.monyrama.ui.utils.MyFormatter;
import com.monyrama.utils.Calc;


/**
 * Model for the table of debts
 * 
 * @author Petro_Verheles
 *
 */
public class DebtTableModel extends AbstractIdableTableModel<PDebt> {
	
	@Override
	public int getColumnCount() {
		return DebtColumnEnum.values().length;
	}

	@Override
	public Object getValueAt(int row, int column) {
		PDebt debt = data.values().toArray(new PDebt[data.size()])[row];

		if(column == DebtColumnEnum.ID.getIndex()) {
			return debt.getId();
		}
		
		if(column == DebtColumnEnum.LENDER_NAME.getIndex()) {
			return debt.getName();
		}
		
		if(column == DebtColumnEnum.CURRENCY.getIndex()) {
			return debt.getCurrency();
		}
		
		if(column == DebtColumnEnum.SUM.getIndex()) {
			BigDecimal paidYet = Calc.sum(debt.getPayingBacks());
			BigDecimal leftToPay = debt.getSumm().subtract(paidYet);
			return MyFormatter.formatNumberToLocal(leftToPay.toPlainString());
		}
		
		if(column == DebtColumnEnum.DATE.getIndex()) {
			SimpleDateFormat formatter;
		    formatter = new SimpleDateFormat(Resources.getDateFormat(), Resources.getLocale());			    
		    String result = formatter.format(debt.getLastChangeDate());
			return result;
		}
		
		if(column == DebtColumnEnum.COMMENTS.getIndex()) {
			return debt.getComment();
		}
		
		System.out.println("Invalid column index: " + column);

		return null;
	}
	
	@Override
	public String getColumnName(int col) {
        return DebtColumnEnum.values()[col].getName();
    }	
}
