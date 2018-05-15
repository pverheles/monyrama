package com.monyrama.ui.components.totalsumspanel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.table.AbstractTableModel;

import com.monyrama.entity.PCurrency;
import com.monyrama.ui.tables.columns.TotalSumsColumnEnum;
import com.monyrama.ui.utils.MyFormatter;


class TotalSumsTableModel extends AbstractTableModel {
	private static final long serialVersionUID = -5223570487818826348L;
	
	private List<Entry<PCurrency, BigDecimal>> list = new ArrayList<Entry<PCurrency, BigDecimal>>();
	
	@Override
	public int getColumnCount() {		
		return TotalSumsColumnEnum.values().length;
	}

	@Override
	public int getRowCount() {		
		return list.size();
	}
	
	@Override
	public String getColumnName(int col) {
        return TotalSumsColumnEnum.values()[col].getName();
    }

	@Override
	public Object getValueAt(int row, int column) {
		Entry<PCurrency, BigDecimal> element = list.get(row);
		
		if(column == TotalSumsColumnEnum.SUM.getIndex()) {
			return MyFormatter.formatNumberToLocal(element.getValue().toString());
		}

		if(column == TotalSumsColumnEnum.CURRENCY.getIndex()) {
			return element.getKey().toString();
		}
		
		return null;
	}
	
	public void setData(Map<PCurrency, BigDecimal> map) {
		list = new ArrayList<Entry<PCurrency,BigDecimal>>(map.entrySet());
		fireTableDataChanged();
	}
	
	public List<Entry<PCurrency,BigDecimal>> getData() {
		return Collections.unmodifiableList(list);
	}
	
	/**
	 * Clears the model
	 */
	public void clearData() {
		list.clear();
		fireTableDataChanged();
	}
}
