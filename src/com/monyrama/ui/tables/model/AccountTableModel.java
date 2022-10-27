package com.monyrama.ui.tables.model;

import com.monyrama.entity.PAccount;
import com.monyrama.ui.tables.columns.AccountColumnEnum;
import com.monyrama.ui.tables.model.AbstractIdableTableModel;
import com.monyrama.ui.utils.MyFormatter;


/**
 * Model for the table of depositories
 * 
 * @author Petro_Verheles
 *
 */
public class AccountTableModel extends AbstractIdableTableModel<PAccount> {
	@Override
	public int getColumnCount() {
		return AccountColumnEnum.values().length;
	}

	@Override
	public Object getValueAt(int row, int column) {
		PAccount account = data.values().toArray(new PAccount[data.size()])[row];

		if(column == AccountColumnEnum.ID.getIndex()) {
			return account.getId();
		}

		if(column == AccountColumnEnum.BANK.getIndex()) {
			return account.getAccountBank() == null ? "" : account.getAccountBank().toString();
		}

		if(column == AccountColumnEnum.NAME.getIndex()) {
			return account.getName();
		}
		
		if(column == AccountColumnEnum.CURRENCY.getIndex()) {
			return account.getCurrency();
		}
		
		if(column == AccountColumnEnum.SUM.getIndex()) {
			return MyFormatter.formatNumberToLocal(account.getSumm().toPlainString());
		}		
		
		if(column == AccountColumnEnum.COMMENTS.getIndex()) {
			return account.getComment();
		}
		
		System.out.println("Invalid column index: " + column);

		return null;
	}
	
	@Override
	public String getColumnName(int col) {
        return AccountColumnEnum.values()[col].getName();
    }	
}
