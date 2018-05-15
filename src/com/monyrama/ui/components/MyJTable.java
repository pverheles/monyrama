package com.monyrama.ui.components;

import javax.swing.JTable;

public class MyJTable extends JTable {

	@Override
	public int getRowHeight() {
		return 20;
	}
	
	@Override
	public boolean isCellEditable(int arg0, int arg1) {				
		return false;
	}
}
