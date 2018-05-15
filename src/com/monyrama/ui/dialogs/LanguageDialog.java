package com.monyrama.ui.dialogs;

import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSeparator;

import com.monyrama.preferences.MyPreferences;
import com.monyrama.ui.resources.Resources;
import com.monyrama.preferences.MyPreferences;
import com.monyrama.preferences.PrefKeys;
import com.monyrama.ui.components.TwoButtonsPanel;
import com.monyrama.ui.dialogs.EscapeDialog;
import com.monyrama.ui.resources.Resources;

public class LanguageDialog extends EscapeDialog {
	
	private final Image MAIN_ICON = getToolkit().getImage(getClass().getResource("main.png"));

	private JComboBox languageBox;
	private JLabel languageLabel;
	private TwoButtonsPanel buttonPanel;

	/**
	 * Creates new form LanguageDialog
	 */
	public LanguageDialog() {
		setIconImage(MAIN_ICON);
		setTitle("Language");
		setModal(true);
		setLocationRelativeTo(null);
		initComponents();
	}

	@SuppressWarnings("unchecked")
	private void initComponents() {
		java.awt.GridBagConstraints gridBagConstraints;

		languageLabel = new JLabel();
		languageBox = new JComboBox();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new java.awt.GridBagLayout());

		languageLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
		languageLabel.setText("Select Your Language");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(52, 10, 0, 0);
		getContentPane().add(languageLabel, gridBagConstraints);

		languageBox.setModel(new javax.swing.DefaultComboBoxModel(new Language[] {
				new Language("English", "en"), new Language("Русский", "ru"), new Language("Українська", "uk")}));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.ipadx = 200;
		gridBagConstraints.ipady = 2;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(6, 10, 68, 10);
		getContentPane().add(languageBox, gridBagConstraints);
		
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

		pack();
	}
	
	private final class Language {
		private String name;
		private String code;
		
		Language(String name, String code) {
			this.name = name;
			this.code = code;
		}

		@Override
		public String toString() {
			return name;
		}
	}
	
	private void handleOkPressed() {
		Language language = (Language)languageBox.getSelectedItem();
		Resources.setLocale(new Locale(language.code));
		MyPreferences.save(PrefKeys.LANGUAGE_CODE, language.code);
		dispose();
	}
			
	@Override
	protected void actionOnEscape() {
		System.exit(0);
	}

//	public static final void main(String[] args) {
//		Resources.initSupportedLocales();
//		Resources.setLocale(new Locale("en"));
//		new LanguageDialog().setVisible(true);
//	}

}