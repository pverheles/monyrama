package com.monyrama.ui.tables.columns;

import com.monyrama.ui.resources.Resources;

public enum DebtColumnEnum {
	ID(0, "ID", 0), //This column should be invisible
	LENDER_NAME(1, Resources.getString("labels.lendername"), 0.15),
	SUM(2, Resources.getString("labels.sum"), 0.15),
	CURRENCY(3, Resources.getString("labels.currency"), 0.15),
	DATE(4, Resources.getString("labels.date"), 0.15),
	COMMENTS(5, Resources.getString("labels.comments"), 0.4);
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
	 * @param width - width of the column in percents
	 */	
	private DebtColumnEnum(int index, String name, double width) {
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
		int num = DebtColumnEnum.values().length;
		double[] percents = new double[num];
		for(int i = 0; i < num; i++) {
			percents[i] = DebtColumnEnum.values()[i].getWidth();
		}
		return percents;
	}
}