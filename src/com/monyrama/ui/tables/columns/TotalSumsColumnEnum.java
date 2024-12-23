/*
 * TotalSumsColumnEnum.java
 * 
 * Version: 1.0
 * 
 * Date: 07.11.2009
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
 * Column enumeration for the table of total sums
 * 
 * @author Petro_Verheles
 *
 */
public enum TotalSumsColumnEnum {
	SUM(0, Resources.getString("labels.sum"), 0.3),
	CURRENCY(1, Resources.getString("labels.currency"), 0.7);
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
	private TotalSumsColumnEnum(int index, String name, double width) {
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
		int num = TotalSumsColumnEnum.values().length;
		double[] percents = new double[num];
		for(int i = 0; i < num; i++) {
			percents[i] = TotalSumsColumnEnum.values()[i].getWidth();
		}
		return percents;
	}
}
