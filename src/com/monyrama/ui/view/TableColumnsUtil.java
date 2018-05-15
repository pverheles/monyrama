package com.monyrama.ui.view;

import javax.swing.JTable;
import javax.swing.table.TableColumn;

public class TableColumnsUtil {
	public static void setPercentColumnWidths(JTable table, int fullWidth, double[] percentages) {
		for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
			TableColumn column = table.getColumnModel().getColumn(i);
			column.setPreferredWidth((int) (fullWidth * percentages[i]));
		}
	}
}
