
package com.monyrama.ui.tables.model;

import java.text.SimpleDateFormat;

import com.monyrama.entity.PIncome;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.tables.columns.IncomeItemColumnEnum;
import com.monyrama.ui.utils.MyFormatter;


/**
 * 
 * Model for the table of income items
 * 
 * @author Petro_Verheles
 *
 */
 public class IncomeItemTableModel extends AbstractIdableTableModel<PIncome> {
	@Override
	public int getColumnCount() {		
		return IncomeItemColumnEnum.values().length;
	}

	@Override	
	public Object getValueAt(int rowIndex, int columnIndex) {		

		if(data == null || data.isEmpty()) {
			return null;
		}				
		
		final PIncome item = data.values().toArray(new PIncome[data.size()])[rowIndex];
		
		if(columnIndex == IncomeItemColumnEnum.ID.getIndex()) {
			return item.getId();
		}
		
		if(columnIndex == IncomeItemColumnEnum.DATE.getIndex()) {
			SimpleDateFormat formatter;
		    formatter = new SimpleDateFormat(Resources.getDateFormat(), Resources.getLocale());			    
		    String result = formatter.format(item.getLastChangeDate());
			return result;
		}		
		
		if(columnIndex == IncomeItemColumnEnum.SUM.getIndex()) {
			return MyFormatter.formatNumberToLocal(item.getSumm().toPlainString());			
		}
		
		if(columnIndex == IncomeItemColumnEnum.TO_ACCOUNT.getIndex()) {
			return item.getAccount().getName();
		}
			
		if(columnIndex == IncomeItemColumnEnum.COMMENTS.getIndex()) {
			return item.getComment();
		}
		
		System.out.println("Invalid column index: " + columnIndex); //This shouldn't ever happen
		
		return null;
	}

	@Override
	public String getColumnName(int col) {
        return IncomeItemColumnEnum.values()[col].getName();
    }
}
