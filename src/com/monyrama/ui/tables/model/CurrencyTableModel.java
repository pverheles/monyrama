package com.monyrama.ui.tables.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import com.monyrama.entity.PCurrency;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.tables.columns.CurrencyColumnEnum;


/**
 * Model for the table of currencies
 * 
 * @author Petro_Verheles
 *
 */
public class CurrencyTableModel extends AbstractIdableTableModel<PCurrency> {

	@Override
	public int getColumnCount() {
		return CurrencyColumnEnum.values().length;
	}

	@Override
	public Object getValueAt(int row, int column) {
		PCurrency currency = data.values().toArray(new PCurrency[data.size()])[row];

		if(column == CurrencyColumnEnum.ID.getIndex()) {
			return currency.getId();
		}
		
		if(column == CurrencyColumnEnum.NAME.getIndex()) {
			if(currency.getStandard()) {
				return Resources.getCurrencyName(currency.getCode());	
			} else {
				return currency.getName();
			}			
		}
		
		if(column == CurrencyColumnEnum.COMMENTS.getIndex()) {
			return currency.getComment();
		}
		
		if(column == CurrencyColumnEnum.CODE.getIndex()) {
			return currency.getCode();
		}
		
		if(column == CurrencyColumnEnum.STANDARD.getIndex()) {
			return currency.getStandard();
		}
				
		if(column == CurrencyColumnEnum.EXCHANGE_RATE.getIndex()) {
			return currency.getExchangeRate().stripTrailingZeros();
		}
		
		if(column == CurrencyColumnEnum.UPDATE_ONLINE.getIndex()) {
			return currency.getUpdateOnline();
		}		
		
		System.out.println("Invalid column index: " + column);

		return null;
	}
	
	@Override
	public String getColumnName(int col) {
        return CurrencyColumnEnum.values()[col].getName();
    }
}
