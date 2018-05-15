package com.monyrama.ui.tables.columns;

import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.resources.Resources;

/**
 * Column enumeration for the table of income items
 * 
 * @author Petro_Verheles
 *
 */
public enum IncomeItemColumnEnum {
	ID(0, "ID", 0), //This column should be invisible
	DATE(1, Resources.getString("labels.date"), 0.1),
	SUM(2, Resources.getString("labels.sum"), 0.3),
	TO_ACCOUNT(3, Resources.getString("labels.toaccount"), 0.2),
	COMMENTS(4, Resources.getString("labels.comments"), 0.4);
		
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
	private IncomeItemColumnEnum(int index, String name, double width) {
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
		int num = IncomeItemColumnEnum.values().length;
		double[] percents = new double[num];
		for(int i = 0; i < num; i++) {
			percents[i] = IncomeItemColumnEnum.values()[i].getWidth();
		}
		return percents;
	}
}
