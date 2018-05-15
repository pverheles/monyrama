/*
 * TwoButtonsPanel.java
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

import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JPanel;

import com.monyrama.ui.resources.Resources;


/**
 * Two buttons panel for dialogs (e.g. OK, CANCEL buttons, SAVE, CANCEL buttons etc.)
 * By default SAVE, CANCEL buttons 
 * 
 * @author Petro_Verheles
 *
 */
public class TwoButtonsPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
    private final javax.swing.JButton cancelButton;
    private final javax.swing.JButton approveButton;
	
    public javax.swing.JButton getApproveButton() {
		return approveButton;
	}

	/**
     * Constructor
     * Creates the panel
     **/
	public TwoButtonsPanel() {
		
		java.awt.GridBagConstraints gridBagConstraints;

        approveButton = new javax.swing.JButton(Resources.getIcon("save.png"));
        cancelButton = new javax.swing.JButton(Resources.getIcon("cancel1.png"));

        setLayout(new java.awt.GridBagLayout());        
        
        approveButton.setText(Resources.getString("dialogs.save"));
        //saveButton.setPreferredSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 2);
        add(approveButton, gridBagConstraints);

        cancelButton.setText(Resources.getString("dialogs.cancel"));
        //cancelButton.setPreferredSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 0);
        add(cancelButton, gridBagConstraints);

	}
	
	/**
	 * Sets action listener to the approve button
	 * 
	 * @param listener - action listener
	 */
	public void setApproveListener(ActionListener listener) {
		approveButton.addActionListener(listener);
	}
	
	/**
	 * Sets action listener to the Cancel button
	 * 
	 * @param listener
	 */
	public void setCancelListener(ActionListener listener) {
		cancelButton.addActionListener(listener);
	}
	
	/**
	 * Sets approve button text
	 * @param text
	 */
	public void setApproveButtonText(String text) {
		approveButton.setText(text);
	}
	
	/**
	 * Sets approve button icon
	 * @param icon
	 */
	public void setApproveButtonIcon(Icon icon) {
		approveButton.setIcon(icon);
	}

	public void hideCancelButton() {
		cancelButton.setVisible(false);
	}

	public void setCancelButtonText(String string) {
		cancelButton.setText(string);
	}
}
