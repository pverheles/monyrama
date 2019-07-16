/*
 * CategoryColumnEnum.java
 * 
 * Version: 1.0
 * 
 * Date: 06.05.2009
 * 
 * Copyright (c) 2009 Petro Verheles.
 * e-mail: vergeles.petiaaa@gmail.com
 * ICQ: 372-831-939
 * Skype: petro.vergeles
 * 
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Petro Verheles
 * ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Petro Verheles. 
 */

package com.monyrama.ui.tables.columns;

import com.monyrama.ui.resources.Resources;

/**
 * Column enumeration for the table of categories
 * 
 * @author Petro_Verheles
 *
 */
public enum CategoryColumnEnum {
	ID(0, "ID", 0), //This column should be invisible
	NAME(1, Resources.getString("labels.name"), 0.25),
	AVG_SUM_PER_DAY(2, Resources.getString("labels.avaragesumperday"), 0.15),
	COMMENTS(3, Resources.getString("labels.comments"), 0.5),
	BLOCKED(4, Resources.getString("labels.blocked"), 0.1);
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
	CategoryColumnEnum(int index, String name, double width) {
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
		int num = CategoryColumnEnum.values().length;
		double[] percents = new double[num];
		for(int i = 0; i < num; i++) {
			percents[i] = CategoryColumnEnum.values()[i].getWidth();
		}
		return percents;
	}

}
