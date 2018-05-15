/*
 * EmailLabel.java
 * 
 * Version: 1.0
 * 
 * Date: 06.05.2013
 * 
 * Copyright (c) 2009 Petro Verheles.
 * e-mail: vergeles.petiaaa@gmail.com
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

import java.awt.Cursor;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JLabel;


/**
 * Represents a hyper-link for email
 * 
 * @author Petro_Verheles
 *
 */
public class EmailLabel extends JLabel {
	private static final long serialVersionUID = 1L;
	
	private String email;
	
	//private String text;
	
	public EmailLabel(String email) {
		this.email = email;
        
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        addMouseListener(new java.awt.event.MouseAdapter() {
        	public void mouseClicked(java.awt.event.MouseEvent evt) {
				if (evt.getClickCount() > 0) {
					try {
						Desktop.getDesktop().mail(new URI("mailto:" + EmailLabel.this.email));
					} catch (IOException e) {
						e.printStackTrace();
					} catch (URISyntaxException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
	
//	public EmailLabel(MouseListener mouseListener) {
//		setCursor(new Cursor(Cursor.HAND_CURSOR));
//		
//		addMouseListener(mouseListener);
//	}
	
	@Override
	public void setText(String text) {
		super.setText("<html><a href=\"\">" + text + "</a></html>");		
	}
}
