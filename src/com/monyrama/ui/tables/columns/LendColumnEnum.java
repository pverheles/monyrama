package com.monyrama.ui.tables.columns;

import com.monyrama.ui.resources.Resources;

public enum LendColumnEnum {
	ID(0, "ID", 0), //This column should be invisible
	DEBTOR_NAME(1, Resources.getString("labels.debtorname"), 0.15),
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
	private LendColumnEnum(int index, String name, double width) {
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
		int num = LendColumnEnum.values().length;
		double[] percents = new double[num];
		for(int i = 0; i < num; i++) {
			percents[i] = LendColumnEnum.values()[i].getWidth();
		}
		return percents;
	}
}