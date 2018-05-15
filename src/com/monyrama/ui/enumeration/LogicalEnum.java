/*
 * LogicalEnum.java
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
 * The enumeration of logical operations
 * 
 * @author Petro_Verheles
 *
 */
public enum LogicalEnum {
	LESS(Resources.getString("logic.less")),
	LESS_EQUAL(Resources.getString("logic.lessequal")),
	EQUAL(Resources.getString("logic.equal")),
	MORE_EQUAL(Resources.getString("logic.moreequal")),
	MORE(Resources.getString("logic.more"));
		
	private String visual;
	
	/**
	 * Constructor
	 * 
	 * Creates an item of the enumeration
	 * 
	 * @param visual - visible name of the item
	 */
	private LogicalEnum(String visual) {
		this.visual = visual;
	}

	public String toString() {
		return visual;
	}
}
