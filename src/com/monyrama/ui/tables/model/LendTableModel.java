package com.monyrama.ui.tables.model;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import com.monyrama.controller.TakingBackController;
import com.monyrama.entity.PLend;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.tables.columns.LendColumnEnum;
import com.monyrama.ui.tables.model.AbstractIdableTableModel;
import com.monyrama.ui.utils.MyFormatter;
import com.monyrama.utils.Calc;


/**
 * Model for the table of debts
 * 
 * @author Petro_Verheles
 *
 */
public class LendTableModel extends AbstractIdableTableModel<PLend> {

	@Override
	public int getColumnCount() {
		return LendColumnEnum.values().length;
	}

	@Override
	public Object getValueAt(int row, int column) {
		PLend lend = data.values().toArray(new PLend[data.size()])[row];

		if(column == LendColumnEnum.ID.getIndex()) {
			return lend.getId();
		}
		
		if(column == LendColumnEnum.DEBTOR_NAME.getIndex()) {
			return lend.getName();
		}
		
		if(column == LendColumnEnum.CURRENCY.getIndex()) {
			return lend.getCurrency();
		}
		
		BigDecimal leftToTakeBack = lend.getSumm().subtract(Calc.sum(lend.getTakingBacks()));
		if(column == LendColumnEnum.SUM.getIndex()) {
			return MyFormatter.formatNumberToLocal(leftToTakeBack.toPlainString());
		}
		
		if(column == LendColumnEnum.DATE.getIndex()) {
			SimpleDateFormat formatter;
		    formatter = new SimpleDateFormat(Resources.getDateFormat(), Resources.getLocale());			    
		    String result = formatter.format(lend.getLastChangeDate());
			return result;
		}
		
		if(column == LendColumnEnum.COMMENTS.getIndex()) {
			return lend.getComment();
		}
		
		System.out.println("Invalid column index: " + column);

		return null;
	}
	
	@Override
	public String getColumnName(int col) {
        return LendColumnEnum.values()[col].getName();
    }
}
