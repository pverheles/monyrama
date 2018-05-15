package com.monyrama.ui.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JDialog;

import com.monyrama.preferences.MyPreferences;
import com.monyrama.preferences.PrefKeys;
import com.monyrama.ui.components.ImageAndLinkLabel;
import com.monyrama.ui.resources.Resources;

public class WelcomeDialog extends JDialog {
	public WelcomeDialog() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setModal(true);
		setResizable(false);
		//setIconImage((Image)Resources.getIcon("welcome.png"));
		setTitle(Resources.getString("labels.welcometopbvp"));
		setLayout(new GridBagLayout());		
		GridBagConstraints constraints;
		
		ImageAndLinkLabel startLabel = new ImageAndLinkLabel(Resources.getIcon("start-big.png"), Resources.getString("welcome.labels.start"),
				new MouseAdapter() {
			    	public void mouseClicked(java.awt.event.MouseEvent evt) {
						if (evt.getClickCount() > 0) {
							WelcomeDialog.this.dispose();
						}
					}
				});
		constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets = new Insets(0, 0, 0, 30);
		add(startLabel, constraints);
		
		ImageAndLinkLabel androidMBLabel = new ImageAndLinkLabel(Resources.getIcon("android-mb.png"), Resources.getString("help.androidmb"), Resources.getString("links.androidmb"));
		constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.gridx = 2;
		constraints.gridy = 0;
		constraints.insets = new Insets(0, 0, 0, 30);
		add(androidMBLabel, constraints);
		
		ImageAndLinkLabel facebookLabel = new ImageAndLinkLabel(Resources.getIcon("facebook-big.png"), Resources.getString("help.facebook"), Resources.getString("links.facebook"));
		constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.gridx = 5;
		constraints.gridy = 0;
		constraints.insets = new Insets(0, 0, 0, 0);
		add(facebookLabel, constraints);		
		
		final JCheckBox dontShowButton = new JCheckBox(Resources.getString("welcome.labels.dontshow"));
		constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 5;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(20, 0, 0, 0);
		add(dontShowButton, constraints);
		dontShowButton.setSelected(!MyPreferences.getBoolean(PrefKeys.SHOW_WELCOME_SCREEN, true));
		dontShowButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {				
				MyPreferences.save(PrefKeys.SHOW_WELCOME_SCREEN, !dontShowButton.isSelected());
			}
		});
	}
}
