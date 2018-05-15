package com.monyrama.ui.dialogs;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import com.monyrama.preferences.MyPreferences;
import com.monyrama.preferences.PrefKeys;
import com.monyrama.ui.components.TwoButtonsPanel;
import com.monyrama.ui.dialogs.EscapeDialog;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.utils.MyDialogs;
import com.monyrama.utils.Trimmer;

public class DataFolderDialog extends EscapeDialog {
	
	private JTextField dataFolderField;
	private JButton fileChooserButton;
	private TwoButtonsPanel buttonPanel;

	/**
	 * Creates new form LanguageDialog
	 */
	public DataFolderDialog() {
		setTitle(Resources.getString("dialogs.titles.datafolder"));
		setModal(true);
		setLocationRelativeTo(null);
		initComponents();
	}

	@SuppressWarnings("unchecked")
	private void initComponents() {
		java.awt.GridBagConstraints gridBagConstraints;

		JLabel selectDatafolderLabel = new JLabel();
		dataFolderField = new JTextField();
		dataFolderField.setEditable(false);
		dataFolderField.setText(MyPreferences.getString(PrefKeys.DATAFOLDER_PATH, System.getProperty("user.home") + File.separator + "Monyrama"));

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new java.awt.GridBagLayout());

		selectDatafolderLabel.setText(Resources.getString("labels.datafolder"));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(52, 10, 0, 0);
		getContentPane().add(selectDatafolderLabel, gridBagConstraints);		
		
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.ipadx = 200;
		gridBagConstraints.ipady = 2;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(6, 10, 68, 10);
		getContentPane().add(dataFolderField, gridBagConstraints);

		fileChooserButton = new JButton(Resources.getString("buttons.change") +  "...");
		fileChooserButton.setPreferredSize(new Dimension(fileChooserButton.getPreferredSize().width, 23));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(6, 0, 68, 10);
		getContentPane().add(fileChooserButton, gridBagConstraints);		
		
		JSeparator sep = new JSeparator(JSeparator.HORIZONTAL);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.insets = new Insets(0, 0, 10, 0);
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridx = 0;
		getContentPane().add(sep, gridBagConstraints);
				
		buttonPanel = new TwoButtonsPanel();
		buttonPanel.setApproveButtonIcon(Resources.getIcon("ok.png"));
		buttonPanel.setApproveButtonText("OK");
        buttonPanel.setApproveListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				handleOkPressed();
			}       	
        });
        
        buttonPanel.setCancelListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				actionOnEscape();
			}
		});
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
		getContentPane().add(buttonPanel, gridBagConstraints);		

		fileChooserButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser dataFolderChooser = new JFileChooser();
				dataFolderChooser.setMultiSelectionEnabled(false);
				dataFolderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				dataFolderChooser.showDialog(DataFolderDialog.this, Resources.getString("dialogs.titles.select"));				
				File dataFolder = dataFolderChooser.getSelectedFile();
				if(dataFolder != null) {
					dataFolderField.setText(dataFolder.getAbsolutePath());	
				}							
			}
		});
		
		pack();
	}
	
	private void handleOkPressed() {
		String dataFolderPath = Trimmer.trim(dataFolderField.getText());
		try {
			File dataFolder = new File(dataFolderPath);
			if(!dataFolder.exists()) {
				if(!dataFolder.mkdirs()) {
					MyDialogs.showWarningDialog(this, Resources.getString("dialog.warnings.invaliddatafolder"));
					return;
				}
			}
		} catch (Exception e) {
			MyDialogs.showWarningDialog(this, Resources.getString("dialog.warnings.invaliddatafolder"));
			return;
		}
		
		MyPreferences.save(PrefKeys.DATAFOLDER_PATH, dataFolderPath);
		dispose();
	}
}