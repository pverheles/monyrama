/*
 * PasswordDialog.java
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

import com.monyrama.preferences.MyPreferences;
import com.monyrama.preferences.PrefKeys;
import com.monyrama.ui.components.TwoButtonsPanel;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.utils.MyDialogs;
import com.monyrama.utils.PasswordEncoder;


/**
 *
 * @author  PetroVerheles
 * 
 */
public class PasswordDialog extends EscapeDialog {
    private javax.swing.JPasswordField confirmPasswordField;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JCheckBox turnOnPassword;
    private TwoButtonsPanel buttonsPanel;
    
    private static PasswordDialog dialog;

    /**
     * Private constructor. Our dialog will be opened by static public method
     */
    private PasswordDialog() {
    	setModal(true);
    	setTitle(Resources.getString("options.password"));
    	
        java.awt.GridBagConstraints gridBagConstraints;

        turnOnPassword = new javax.swing.JCheckBox();
        javax.swing.JLabel passwordLabel = new javax.swing.JLabel();
        javax.swing.JLabel confirmPasswordLabel = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        confirmPasswordField = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        turnOnPassword.setText(Resources.getString("labels.turnpasswordon"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 5, 0);
        getContentPane().add(turnOnPassword, gridBagConstraints);

        passwordLabel.setText(Resources.getString("labels.password") + ":");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 0);
        getContentPane().add(passwordLabel, gridBagConstraints);

        confirmPasswordLabel.setText(Resources.getString("labels.confirmpassword") + ":");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 0);
        getContentPane().add(confirmPasswordLabel, gridBagConstraints);

        passwordField.setText("jPasswordField1");
        passwordField.setPreferredSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 10, 10);
        getContentPane().add(passwordField, gridBagConstraints);

        confirmPasswordField.setPreferredSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 10, 10);
        getContentPane().add(confirmPasswordField, gridBagConstraints);
        
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
        
		passwordField.setText("");
		confirmPasswordField.setText("");
        
        addListeners();
        setLocationRelativeTo(null);
        pack();
    }
    
    @Override
	protected void actionOnEscape() {
		setVisible(false);
	}

	private void addListeners() {
		buttonsPanel.setApproveListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!validateData()) {
					return;
				}

				if(turnOnPassword.isSelected()) {
					String password = passwordField.getText();
					String passwordHash = PasswordEncoder.encode(password);
					MyPreferences.save(PrefKeys.PASSWORD_HASH, passwordHash);
				} else {
					MyPreferences.remove(PrefKeys.PASSWORD_HASH);
				}
				
				setVisible(false);
			}
		
		});
		
		buttonsPanel.setCancelListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionOnEscape();
			}
		});
		
		turnOnPassword.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(turnOnPassword.isSelected()) {
					passwordField.setEnabled(true);
					confirmPasswordField.setEnabled(true);
					passwordField.requestFocus();
				} else {
					passwordField.setText("");
					confirmPasswordField.setText("");
					passwordField.setEnabled(false);
					confirmPasswordField.setEnabled(false);
				}
			}
		});
	}

	private boolean validateData() {
		if(turnOnPassword.isSelected()) {
			if(passwordField.getText() == null || passwordField.getText().equals("")) {
				MyDialogs.showWarningDialog(dialog, Resources.getString("dialogs.warnings.emptypassword") + "!");
				passwordField.requestFocus();
				return false;
			}
			
			if(passwordField.getText().length() > 20) {
				MyDialogs.showWarningDialog(dialog, Resources.getString("dialogs.warnings.lengthcannotbemore20") + "!");
				passwordField.requestFocus();
				return false;
			}
			
			if(!passwordField.getText().equals(confirmPasswordField.getText())) {
				MyDialogs.showWarningDialog(dialog, Resources.getString("dialogs.warnings.passwordnotequalconfirmation") + "!");
				confirmPasswordField.requestFocus();
				return false;
			}
		}
		
		return true;
	}	
	
	public static void openDialog() {
    	if(dialog == null) {
    		dialog = new PasswordDialog();
    	}
    	
		dialog.passwordField.setText("");
		dialog.confirmPasswordField.setText("");
    	
		String passwordHash = MyPreferences.getString(PrefKeys.PASSWORD_HASH);
		
    	if(passwordHash != null) {
    		dialog.turnOnPassword.setSelected(true);
    		dialog.passwordField.setEnabled(true);
    		dialog.confirmPasswordField.setEnabled(true);
    		dialog.passwordField.requestFocus();
    	} else {
    		dialog.turnOnPassword.setSelected(false);
    		dialog.passwordField.setEnabled(false);
    		dialog.confirmPasswordField.setEnabled(false);
    	}
    	dialog.getRootPane().setDefaultButton(dialog.buttonsPanel.getApproveButton());
    	dialog.showIt();
    }
}
