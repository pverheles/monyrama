/*
 * AuthenticationDialog.java
 * 
 * Version: 1.0
 * 
 * Date: 11.04.2010
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

package com.monyrama.ui.dialogs;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSeparator;

import com.monyrama.ui.components.TwoButtonsPanel;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.utils.MyDialogs;
import com.monyrama.utils.PasswordEncoder;


/**
 *
 * @author  Petro_Verheles
 */
public class AuthenticationDialog extends EscapeDialog {

    // Variables declaration - do not modify
    private javax.swing.JPasswordField passwordField;
    private TwoButtonsPanel buttonsPanel;
    // End of variables declaration
    
    private String realPasswordHash;
    
    private boolean authenticated = false;
    
    public boolean isAuthenticated() {
		return authenticated;
	}

	/** Creates new form AuthenticationDialog */
    public AuthenticationDialog(String realPasswordHash) {
    	setModal(true);
    	
    	this.realPasswordHash = realPasswordHash;
    	
        java.awt.GridBagConstraints gridBagConstraints;

        javax.swing.JLabel pbvpLabel = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        javax.swing.JLabel yourPasswordLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        pbvpLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        pbvpLabel.setForeground(new java.awt.Color(0, 0, 204));
        pbvpLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pbvpLabel.setText(Resources.getString("title"));
        pbvpLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 15, 10);
        getContentPane().add(pbvpLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 10);
        getContentPane().add(passwordField, gridBagConstraints);

        yourPasswordLabel.setText(Resources.getString("labels.yourpassword") + ":");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 5);
        getContentPane().add(yourPasswordLabel, gridBagConstraints);

		JSeparator sep = new JSeparator(JSeparator.HORIZONTAL);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.insets = new Insets(0, 0, 10, 0);
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridx = 0;
		getContentPane().add(sep, gridBagConstraints);
				
		buttonsPanel = new TwoButtonsPanel();
				        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        getContentPane().add(buttonsPanel, gridBagConstraints);	
        
        buttonsPanel.setApproveButtonText(Resources.getString("dialogs.ok"));
        buttonsPanel.setApproveButtonIcon(Resources.getIcon("ok.png"));
        
        getRootPane().setDefaultButton(buttonsPanel.getApproveButton());

        addListeners();
        pack();
    }
    
	void addListeners() {
		buttonsPanel.setApproveListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String passwordStr = passwordField.getText();
				String enteredPasswordHash = PasswordEncoder.encode(passwordStr);
				if(!realPasswordHash.equals(enteredPasswordHash)) {
					MyDialogs.showWarningDialog(AuthenticationDialog.this, Resources.getString("dialogs.warnings.wrongpassword") + "!");
					passwordField.requestFocus();
					return;
				} 
				authenticated = true;
				setVisible(false);
			}
		
		});
		
		buttonsPanel.setCancelListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionOnEscape();
			}
		});
    }
}
