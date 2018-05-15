/*
 * ExplainPanel.java
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

package com.monyrama.ui.components;

import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.monyrama.ui.components.ComponentsHelper;
import com.monyrama.ui.resources.Resources;


/**
 * The panel that shows a string "*required fields"
 * 
 * @author Petro_Verheles
 *
 */
public class ExplainPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public final static Font REQUIRED_FONT = new Font("Arial", Font.ITALIC, 11);
	
	public ExplainPanel() {
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setHgap(0);
		flowLayout.setAlignment(FlowLayout.LEFT);
		setLayout(flowLayout);
		add(ComponentsHelper.createAstericsLabel());
		JLabel explainLabel = new JLabel(Resources.getString("labels.requiredfields"));
		explainLabel.setFont(REQUIRED_FONT);
		explainLabel.setForeground(new java.awt.Color(0, 153, 51));
		add(explainLabel);		
	}
}
