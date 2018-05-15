/*
 * MyDialogs.java
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

package com.monyrama.ui.utils;

import java.awt.Component;
import java.awt.Toolkit;

import javax.swing.JOptionPane;

import com.monyrama.ui.resources.Resources;


/**
 * Simple dialogs "factory"
 * 
 * @author Petro_Verheles
 *
 */
public class MyDialogs {
    
	public static int YES = JOptionPane.YES_OPTION;
	//public static int NO = JOptionPane.NO_OPTION;
	//public static int CLOSED = JOptionPane.CLOSED_OPTION;
	
    /**
     * Shows warning dialog and beeps
     * 
     * @param message
     */
    public static void showWarningDialog(Component parent, String message) {
        Toolkit.getDefaultToolkit().beep();
        JOptionPane.showMessageDialog(parent, message, Resources.getString("dialogs.warning"), JOptionPane.WARNING_MESSAGE);                
    }
    
    /**
     * Shows error dialog and beeps
     * 
     * @param message
     */
    public static void showErrorDialog(Component parent, String message) {
        Toolkit.getDefaultToolkit().beep();
        JOptionPane.showMessageDialog(parent, message, Resources.getString("dialogs.error"), JOptionPane.ERROR_MESSAGE);                
    }

    /**
     * Shows yes / no dialog and beeps
     * 
     * @param message
     */    
    public static int showYesNoDialog(Component parent, String message) {
    	Toolkit.getDefaultToolkit().beep();
    	return JOptionPane.showConfirmDialog(parent, message, Resources.getString("dialogs.question"), JOptionPane.YES_NO_OPTION);
    }

    /**
     * Shows info dialog and beeps
     * 
     * @param message
     */    
    public static void showInfoDialog(Component parent, String message) {
    	Toolkit.getDefaultToolkit().beep();
    	JOptionPane.showMessageDialog(parent, message, Resources.getString("dialogs.info"), JOptionPane.INFORMATION_MESSAGE);
    }
}
