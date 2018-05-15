/*
 * ExpensesSearchColumnEnum.java
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
import com.monyrama.ui.resources.Resources;

/**
 * Column enumeration for the table of search of expenses results
 * 
 * @author Petro_Verheles
 *
 */
public enum ExpensesSearchColumnEnum {

	DATE(0, Resources.getString("labels.date"), 0.1),
	BUDGET(1, Resources.getString("labels.budget"), 0.15),
	CURRENCY(2, Resources.getString("labels.currency"), 0.15),
	CATEGORY(3, Resources.getString("labels.category"), 0.15),
	BUDGET_ITEM(4, Resources.getString("labels.item"), 0.15),
	PRICE(5, Resources.getString("labels.price"), 0.1),	
	COMMENTS(6, Resources.getString("labels.comments"), 0.2);
	
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
	private ExpensesSearchColumnEnum(int index, String name, double width) {
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
		int num = ExpensesSearchColumnEnum.values().length;
		double[] percents = new double[num];
		for(int i = 0; i < num; i++) {
			percents[i] = ExpensesSearchColumnEnum.values()[i].getWidth();
		}
		return percents;
	}	
}
