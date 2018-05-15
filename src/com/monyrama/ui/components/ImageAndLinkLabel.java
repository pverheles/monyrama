package com.monyrama.ui.components;

import com.monyrama.ui.components.LinkLabel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.JPanel;

public class ImageAndLinkLabel extends JPanel {
	public ImageAndLinkLabel(Icon icon, String text, String link) {		
		setLayout(new GridBagLayout());
		GridBagConstraints constraints;
		
		LinkLabel iconLabel = new LinkLabel(link);
		iconLabel.setIcon(icon);
		constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(iconLabel, constraints);
		
		LinkLabel textLabel = new LinkLabel(link);
		textLabel.setText(text);
		constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.gridx = 0;
		constraints.gridy = 1;
		add(textLabel, constraints);		
	}
	
	public ImageAndLinkLabel(Icon icon, String text, MouseListener mouseListener) {
		setLayout(new GridBagLayout());
		GridBagConstraints constraints;
		
		LinkLabel iconLabel = new LinkLabel(mouseListener);
		iconLabel.setIcon(icon);
		constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(iconLabel, constraints);
		
		LinkLabel textLabel = new LinkLabel(mouseListener);
		textLabel.setText(text);
		constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.gridx = 0;
		constraints.gridy = 1;
		add(textLabel, constraints);		
	}	
}
