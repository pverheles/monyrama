/*
 * LinkLabel.java
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

import java.awt.Cursor;
import java.awt.event.MouseListener;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JLabel;

import com.monyrama.ui.utils.WebHelper;


/**
 * Represents a hyper-link
 * 
 * @author Petro_Verheles
 *
 */
public class LinkLabel extends JLabel {
	private static final long serialVersionUID = 1L;
	
	private String link;
	
	//private String text;
	
	public LinkLabel(String lnk) {
		this.link = lnk;
        
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        addMouseListener(new java.awt.event.MouseAdapter() {
        	public void mouseClicked(java.awt.event.MouseEvent evt) {
				if (evt.getClickCount() > 0) {
			        try {
						final URI uri = new URI(link);
						WebHelper.openURI(uri);
					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}					
				}
			}
		});
	}
	
	public LinkLabel(MouseListener mouseListener) {
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		addMouseListener(mouseListener);
	}
	
	@Override
	public void setText(String text) {
		super.setText("<html><a href=\"\">" + text + "</a></html>");		
	}
}
