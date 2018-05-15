/*
 * BudgetTabEnum.java
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

package com.monyrama.ui.enumeration;

import com.monyrama.ui.resources.Resources;

/**
 * Enumeration of tabs of budget panels 
 * 
 * @author Petro_Verheles
 *
 */
public enum ExpensesTabEnum {
	ITEMS(0, Resources.getString("labels.items")),
	EXPENSES(1, Resources.getString("labels.expenses"));
		
	private String name;
	private int index;
	
	/**
	 * Constructor
	 * 
	 * Creates an item of the enumeration
	 * 
	 * @param index - index of the tab
	 * @param name - name of the tab
	 */
	private ExpensesTabEnum(int index, String name) {
		this.index = index;
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public int getIndex() {
		return index;
	}
}
