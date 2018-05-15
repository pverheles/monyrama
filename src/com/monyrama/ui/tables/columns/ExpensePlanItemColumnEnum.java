package com.monyrama.ui.tables.columns;

import com.monyrama.ui.resources.Resources;

/**
 * Column enumeration for the table of items
 * 
 * @author Petro_Verheles
 *
 */
public enum ExpensePlanItemColumnEnum {
	ID(0, "ID", 0), //This column should be invisible
	NAME(1, Resources.getString("labels.name"), 0.15),
	CATEGORY(2, Resources.getString("labels.category"), 0.15),	
	SUM(3, Resources.getString("labels.sum"), 0.1),
	SPENT(4, Resources.getString("labels.spent"), 0.1),
	REMAINDER(5, Resources.getString("labels.remainder"), 0.1),
	COMMENTS(6, Resources.getString("labels.comments"), 0.3),
	BLOCKED(7, Resources.getString("labels.blocked"), 0.1);
		
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
	private ExpensePlanItemColumnEnum(int index, String name, double width) {
		this.index = index;
		this.name = name;
		this.width = width;
	}
	
	public int getIndex() {
		return index;
	}
	
	public String getName() {
		return name;
	}

	public double getWidth() {
		return width;
	}

	public static double[] getColumnPercents() {
		int num = ExpensePlanItemColumnEnum.values().length;
		double[] percents = new double[num];
		for(int i = 0; i < num; i++) {
			percents[i] = ExpensePlanItemColumnEnum.values()[i].getWidth();
		}
		return percents;
	}
}
