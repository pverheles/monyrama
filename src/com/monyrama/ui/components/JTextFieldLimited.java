/*
 * JTextFieldLimited.java
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

import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * Text field that can limit number of symbols that a user can enter,
 * as well as allow to set property to enter only upper symbols
 * 
 * @author Petro_Verheles
 *
 */
public class JTextFieldLimited extends JTextField {
    private static final long serialVersionUID = 1L;
    
    private int maxlength;
    private boolean upper;
    
    /**
     * Constructor with maximum length parameter
     * 
     * @param maxlength - maximum number of symbols allowed to enter
     */
    public JTextFieldLimited(int maxlength) {
        this.maxlength = maxlength;
        ((AbstractDocument)getDocument()).setDocumentFilter(new LimitedLengthFilter());
    }
    
    /**
     * Constructor with maximum length parameter and CAPS boolean property
     * 
     * @param maxlength - maximum number of symbols allowed to enter
     * @param upper - will be entered only CAPS or different symbols
     */
    public JTextFieldLimited(int maxlength, boolean upper) {
        this.maxlength = maxlength;
        this.upper = upper;
        ((AbstractDocument)getDocument()).setDocumentFilter(new LimitedLengthFilter());
    }
    
    /**
     * A class that inherits DocumentFilter and overrides its methods
     * accordingly to our target to limit symbols allowed to enter
     * 
     * @author Petro_Verheles
     *
     */
    class LimitedLengthFilter extends DocumentFilter{

    	@Override
    	public void replace(FilterBypass fb, int offset, int length,
                String text, AttributeSet attrs) throws BadLocationException {

            // The current length minus the no.of characters deleted plus the
            // number of characters inserted is the final length in the
            // textfield
            // and should not be more than maxlength
            if ((fb.getDocument().getLength() + text.length() - length) <= maxlength) {
                if (upper) text = text.toUpperCase();
                super.replace(fb, offset, length, text, attrs);
            } else {
                // Let's beep if the user tries to enter more characters
                Toolkit.getDefaultToolkit().beep();
            }
        }

    	@Override
        public void insertString(FilterBypass fb, int offset, String string,
                AttributeSet attr) throws BadLocationException {

            // While inserting the current length plus the number of characters
            // inserted
            // is the final length in the textfield and should not be more than
            // maxlength
            if ((fb.getDocument().getLength() + string.length()) <= maxlength) {
                if (upper) string = string.toUpperCase();
                super.insertString(fb, offset, string, attr);
            } else {
                // Let's beep if the user tries to enter more characters
                Toolkit.getDefaultToolkit().beep();
            }
        }
    }

    /**
     * Gets maximum length
     * 
     * @return - maximum length
     */
    public int getMaxlength() {
        return maxlength;
    }

    /**
     * Sets maximum length
     * 
     * @param maxlength - maximum length
     */
    public void setMaxlength(int maxlength) {
        this.maxlength = maxlength;
    }

    /**
     * Says if upper property is set to true or false
     * If true, only CAPS will be entered into the field, otherwise different symbols
     * If you type not only CAPS, they are replaced by appropriate CAPS
     * 
     * @return
     */
    public boolean isUpper() {
        return upper;
    }

    /**
     * Sets upper property
     * If true, only CAPS will be entered into the field, otherwise different symbols
     * If you type not only CAPS, they are replaced by appropriate CAPS
     * 
     * @param upper
     */
    public void setUpper(boolean upper) {
        this.upper = upper;
    }
}
