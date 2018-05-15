package com.monyrama.ui.tables.columns;

import com.monyrama.ui.resources.Resources;

public enum TransferColumnEnum {
	ID(0, "ID", 0), //This column should be invisible
	DATE(1, Resources.getString("labels.date"), 0.2),
	FROMACCOUNT(2, Resources.getString("labels.fromaccount"), 0.3),
	FROMSUM(3, Resources.getString("labels.sum"), 0.1),
	TOACCOUNT(4, Resources.getString("labels.toaccount"), 0.3),
	TOSUM(5, Resources.getString("labels.sum"), 0.1);
	
	private int index;	
	private String name;
	private double width;

	/**
	 * Constructor
	 * 
	 * Creates the item of the enumeration
	 * 
	 * @param index - index of the column 
	 * @param name - name of the column
	 * @param width - width of the column in percents.
	 */	
	private TransferColumnEnum(int index, String name, double width) {
		this.index = index;
		this.name = name;
		this.width = width;		
	}

	public String getName() {
		return name;
	}

	public double getWidth() {
		return width;
	}

	public int getIndex() {
		return index;
	}
	
	public static double[] getColumnPercents() {
		int num = TransferColumnEnum.values().length;
		double[] percents = new double[num];
		for(int i = 0; i < num; i++) {
			percents[i] = TransferColumnEnum.values()[i].getWidth();
		}
		return percents;
	}

}
