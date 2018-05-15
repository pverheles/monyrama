package com.monyrama.ui.dialogs;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import com.monyrama.ui.components.EmailLabel;
import com.monyrama.ui.components.LinkLabel;
import com.monyrama.ui.constants.Version;
import com.monyrama.ui.resources.Resources;


public class AboutDialog extends JDialog {
	private JDialog thisDialog = this;
	
	public AboutDialog() {
		super();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle(Resources.getString("help.about"));
		setModal(true);

		javax.swing.JPanel contentPanel = new javax.swing.JPanel();
		contentPanel.setLayout(new GridBagLayout());
		
        java.awt.GridBagConstraints gridBagConstraints;

        javax.swing.JPanel aboutPanel = new javax.swing.JPanel();
        javax.swing.JLabel personalBudgetLabel = new javax.swing.JLabel();
        javax.swing.JLabel versionLabel = new javax.swing.JLabel();
        javax.swing.JLabel developmentLabel = new javax.swing.JLabel();
        javax.swing.JLabel testingLabel = new javax.swing.JLabel();
        javax.swing.JPanel emailPanel = new javax.swing.JPanel();
        javax.swing.JLabel emailLabel = new javax.swing.JLabel();
        javax.swing.JLabel emailValueLabel = new EmailLabel(Resources.getString("labels.contacts.emailvalue"));
        javax.swing.JPanel sitePanel = new javax.swing.JPanel();
        javax.swing.JLabel siteLabel = new javax.swing.JLabel();
        javax.swing.JLabel siteValueLabel = new LinkLabel(Resources.getString("labels.sitevalue"));        

        setLayout(new java.awt.GridBagLayout());

        aboutPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        aboutPanel.setLayout(new java.awt.GridBagLayout());

        personalBudgetLabel.setText(Resources.getString("title"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        aboutPanel.add(personalBudgetLabel, gridBagConstraints);

        versionLabel.setText(Resources.getString("labels.version") + ": " + Version.VERSION);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        aboutPanel.add(versionLabel, gridBagConstraints);

        developmentLabel.setText(Resources.getString("labels.development") + ": " + Resources.getString("labels.petroverheles"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        aboutPanel.add(developmentLabel, gridBagConstraints);
        
        testingLabel.setText(Resources.getString("labels.testing") + ": " + Resources.getString("labels.kseniiaverheles"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        aboutPanel.add(testingLabel, gridBagConstraints);
        
        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        flowLayout.setHgap(0);
        flowLayout.setVgap(0);
        emailPanel.setLayout(flowLayout);
        emailLabel.setText(Resources.getString("labels.contacts.email") + ": ");
        emailPanel.add(emailLabel);
        emailValueLabel.setText(Resources.getString("labels.contacts.emailvalue"));
        emailPanel.add(emailValueLabel);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        aboutPanel.add(emailPanel, gridBagConstraints);        
        
        sitePanel.setLayout(flowLayout);
        siteLabel.setText(Resources.getString("labels.site") + ": ");
        sitePanel.add(siteLabel);
        siteValueLabel.setText(Resources.getString("labels.sitevalue"));
        sitePanel.add(siteValueLabel);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        aboutPanel.add(sitePanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        contentPanel.add(aboutPanel, gridBagConstraints);
        
        JPanel okPanel = new JPanel();
        okPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        okPanel.setBorder(BorderFactory.createEmptyBorder());
        JButton okButton = new JButton(Resources.getString("dialogs.ok"));
        okButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				thisDialog.dispose();				
			}
		});
        okPanel.add(okButton);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;       
        gridBagConstraints.weightx = 1.0;    
        gridBagConstraints.gridwidth = 2;
        contentPanel.add(new JSeparator(), gridBagConstraints);                
        
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;       
        gridBagConstraints.weightx = 1.0;    
        gridBagConstraints.gridwidth = 2;
        contentPanel.add(okPanel, gridBagConstraints);        
        
        setContentPane(contentPanel);
        
        setResizable(false);
        
        pack();
	}
}
