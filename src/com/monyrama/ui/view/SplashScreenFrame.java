/*
 * SplashScreenFrame.java
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

package com.monyrama.ui.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.monyrama.ui.constants.ColorConstants;
import com.monyrama.ui.constants.Version;
import com.monyrama.ui.resources.Resources;


/**
 * Splash screen window
 * 
 * @author Petro_Verheles
 *
 */
public class SplashScreenFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private final String path = String.format("images/monyrama.jpg", Resources.getLocale().getLanguage());
	private final ImageIcon splashImage = new ImageIcon(getClass().getResource(path));
	private final Image MAIN_ICON = getToolkit().getImage(getClass().getResource("images/main.png"));
	private final int WIDTH = 420;
	private final int HEIGHT = 320;
		
	private javax.swing.JProgressBar progressBar;
	
	/**
	 * Constructor
	 * 
	 * Paints the frame
	 */
	public SplashScreenFrame() {			
		setIconImage(MAIN_ICON);
		
		panel.setOpaque(false);

		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setContentPane(panel);
		BorderLayout borderLayout = new BorderLayout();
		borderLayout.setHgap(0);
		borderLayout.setVgap(0);
		getContentPane().setLayout(null);

        versionLabel = new JLabel();
        versionLabel.setForeground(ColorConstants.SPLASH_TITLE);
        versionLabel.setText(String.format("<html><font color='red' size='4'><b>%s&nbsp;</b></font></html>", Version.VERSION));
        versionLabel.setSize(versionLabel.getPreferredSize());
        versionLabel.setBounds(WIDTH - versionLabel.getSize().width - 3, 0, versionLabel.getSize().width, versionLabel.getSize().height);
        getContentPane().add(versionLabel);
		
        progressBar = new javax.swing.JProgressBar(0, 100);
        progressBar.setForeground(ColorConstants.SPLASH_TITLE);
        progressBar.setStringPainted(true);
        progressBar.setValue(0);
        progressBar.setString(Resources.getString("splash.loading"));
        progressBar.setBounds(0, HEIGHT - 17, WIDTH, 18);        
        getContentPane().add(progressBar);
        
		setSize(WIDTH, HEIGHT);
        setUndecorated(true);
        setLocationRelativeTo(null);
	}
	
	/**
	 * Panel with the image
	 */
	JPanel panel = new JPanel() {
		private static final long serialVersionUID = 1L;

		@Override
		public void paintComponent(Graphics g) { 
			g.drawImage(splashImage.getImage(), 0, 0, this);
			super.paintComponent(g);
		}		
	};
	private JLabel versionLabel;

	/**
	 * Sets the new value of progress bar
	 * @param value from 0 to 100
	 */
	public void setProgress(int value) throws IllegalArgumentException {
		if(value < 0 || value > 100) {
			throw new IllegalArgumentException("Value cannot be less than 0 and more than 100");
		}
		
		progressBar.setValue(value);
	}
}
