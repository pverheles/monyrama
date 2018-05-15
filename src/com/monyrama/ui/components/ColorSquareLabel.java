/*
 * ColorSquareLabel.java
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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Represents a color square or maybe rectangle 
 * 
 * @author Petro_Verheles
 *
 */
public class ColorSquareLabel extends JLabel {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @param color - the color of the label
	 * @param string - the text displayed on the right of the colored square (rectangle)
	 * @param width - the width of the colored rectangle
	 * @param height - the height of the colored rectangle
	 */
	public ColorSquareLabel(Color color, String string, int width, int height) {
		super(string);
		Image squareImage = new BufferedImage(width, width, BufferedImage.TYPE_3BYTE_BGR);
		Graphics g = squareImage.getGraphics();
		g.setColor(color);		
		g.fillRect(0, 0, width, width);		
		setIcon(new ImageIcon(squareImage));
	}
}
