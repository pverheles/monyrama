/*
 * EscapeDialog.java
 * 
 * Version: 1.0
 * 
 * Date: 23.03.2010
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

import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

/**
 * Dialog that hides when 'Escape' key is pressed
 * 
 * @author Petia
 *
 */
public class EscapeDialog extends JDialog {
	public EscapeDialog() {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				actionOnEscape();
			}			
		});
		setResizable(false);
		setModal(true);
	}
	
	@Override
	protected JRootPane createRootPane() {
	    JRootPane rootPane = new JRootPane();
	    KeyStroke stroke = KeyStroke.getKeyStroke("ESCAPE");
	    Action actionListener = new AbstractAction() {
			
			@Override
		      public void actionPerformed(ActionEvent actionEvent) {
				  actionOnEscape();
		      }
		};
	    InputMap inputMap = rootPane
	        .getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
	    inputMap.put(stroke, "ESCAPE");
	    rootPane.getActionMap().put("ESCAPE", actionListener);
	    
	    return rootPane;
	}

	/**
	 * Hides the dialog.
	 * Can be overridden in child classes
	 */
	protected void actionOnEscape() {
		dispose();
	}
	
	/**
	 * Makes the dialog visible
	 */
	protected void showIt() {
		PointerInfo info = MouseInfo.getPointerInfo();
        setLocation(info.getLocation().x, info.getLocation().y);
		setVisible(true);
	}
}
