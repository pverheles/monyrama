package com.monyrama.ui.dialogs;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.util.Collection;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.monyrama.controller.CurrencyController;
import com.monyrama.controller.ExchangeRatesController;
import com.monyrama.controller.SettingController;
import com.monyrama.entity.DBConstants;
import com.monyrama.entity.PCurrency;
import com.monyrama.entity.PSetting;
import com.monyrama.ui.components.ComponentsHelper;
import com.monyrama.ui.components.ExplainPanel;
import com.monyrama.ui.components.JTextFieldLimited;
import com.monyrama.ui.components.TwoButtonsPanel;
import com.monyrama.ui.constants.DimensionConstants;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.utils.MyDialogs;
import com.monyrama.ui.utils.MyFormatter;
import com.monyrama.utils.Trimmer;
import com.monyrama.validator.EntityValidator;


/**
 * New / Edit Currency dialog
 * 
 * @author Petro_Verheles
 *
 */
public abstract class CurrencyDialog extends EscapeDialog {
	protected JTextFieldLimited commentsField;
	protected JTextFieldLimited codeField;
	protected JComboBox codeBoxField;
	protected JTextFieldLimited nameField;
	protected JComboBox nameBoxField;
	protected JTextFieldLimited exchangeRateField;
	protected JCheckBox standardCheckBox;
	protected JCheckBox updateOnlineCheckBox;
	private TwoButtonsPanel buttonPanel;
	
	private static CurrencyDialog newDialog;
	private static CurrencyDialog editDialog;
	
	private PCurrency currency;

    /**
     * Private constructor. Our dialog will be opened by static public method
     */
	protected CurrencyDialog() {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		final GridBagLayout gridBagLayout = new GridBagLayout();		
		getContentPane().setLayout(gridBagLayout);
		
		GridBagConstraints gridBagConstraints;
		int gridy = 0;

		//Standard Field
		final JLabel standardLabel = new JLabel();
		standardLabel.setText(Resources.getString("labels.standard") + ":");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 0, 5, 0);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 1;
		getContentPane().add(standardLabel, gridBagConstraints);
		
		standardCheckBox = new JCheckBox();
		standardCheckBox.setSelected(true);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(10, 5, 5, 10);
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 2;
		getContentPane().add(standardCheckBox, gridBagConstraints);		
		
		//Code field
		gridy++;
		JLabel astericsLabelForCode = ComponentsHelper.createAstericsLabel();
		final GridBagConstraints gridBagConstraints_0 = new GridBagConstraints();
		gridBagConstraints_0.insets = new Insets(5, 10, 5, 0);
		gridBagConstraints_0.gridy = gridy;
		gridBagConstraints_0.gridx = 0;
		getContentPane().add(astericsLabelForCode, gridBagConstraints_0);		
		
		final JLabel codeLabel = new JLabel();
		codeLabel.setText(Resources.getString("labels.code") + ":");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(5, 0, 5, 0);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 1;
		getContentPane().add(codeLabel, gridBagConstraints);

		codeField = new JTextFieldLimited(DBConstants.CURRENCY_CODE_LENGTH);
		codeField.setPreferredSize(DimensionConstants.STD_FIELD_DIMENSION);
		codeBoxField = new JComboBox(Resources.getAllCurrencyCodes().toArray());
		codeBoxField.setPreferredSize(DimensionConstants.STD_FIELD_DIMENSION);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 5, 5, 10);
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 2;
		getContentPane().add(codeField, gridBagConstraints);		
		getContentPane().add(codeBoxField, gridBagConstraints);
		codeField.setVisible(false);
		
		//Name field
		gridy++;
		JLabel astericsLabel = ComponentsHelper.createAstericsLabel();
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(5, 10, 5, 0);
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 0;
		getContentPane().add(astericsLabel, gridBagConstraints);		
		
		final JLabel nameLabel = new JLabel();
		nameLabel.setText(Resources.getString("labels.name") + ":");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(5, 0, 5, 0);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 1;
		getContentPane().add(nameLabel, gridBagConstraints);

		nameField = new JTextFieldLimited(DBConstants.NAME_LENGTH);
		nameField.setPreferredSize(DimensionConstants.STD_FIELD_DIMENSION);
		nameBoxField = new JComboBox(Resources.getAllCurrencyNames().toArray());
		nameBoxField.setPreferredSize(DimensionConstants.STD_FIELD_DIMENSION);
		final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
		gridBagConstraints_1.anchor = GridBagConstraints.WEST;
		gridBagConstraints_1.insets = new Insets(5, 5, 5, 10);
		gridBagConstraints_1.gridy = gridy;
		gridBagConstraints_1.gridx = 2;
		getContentPane().add(nameField, gridBagConstraints_1);
		getContentPane().add(nameBoxField, gridBagConstraints_1);

		//Update Online Field
		gridy++;
		final JLabel updateOnlineLabel = new JLabel();
		updateOnlineLabel.setText(Resources.getString("labels.updateonline") + ":");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(5, 0, 5, 0);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 1;
		getContentPane().add(updateOnlineLabel, gridBagConstraints);
		
		updateOnlineCheckBox = new JCheckBox();
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 5, 5, 10);
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 2;
		getContentPane().add(updateOnlineCheckBox, gridBagConstraints);	
		
		nameField.setVisible(false);

		//Exchange rate field
		gridy++;
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(5, 10, 5, 0);
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 0;
		getContentPane().add(ComponentsHelper.createAstericsLabel(), gridBagConstraints);		
		
		final JLabel exchangeRateLabel = new JLabel();
		exchangeRateLabel.setText(Resources.getString("labels.exchangerate") + ":");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(5, 0, 5, 0);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 1;
		getContentPane().add(exchangeRateLabel, gridBagConstraints);

		exchangeRateField = new JTextFieldLimited(DBConstants.SUM_LENGTH);
		exchangeRateField.setPreferredSize(DimensionConstants.STD_FIELD_DIMENSION);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 5, 5, 10);
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 2;
		getContentPane().add(exchangeRateField, gridBagConstraints);		
			
		//Comments field
		gridy++;
		final JLabel commentsLabel = new JLabel();
		commentsLabel.setText(Resources.getString("labels.comments") + ":");
		final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
		gridBagConstraints_2.insets = new Insets(5, 0, 5, 0);
		gridBagConstraints_2.anchor = GridBagConstraints.WEST;
		gridBagConstraints_2.gridy = gridy;
		gridBagConstraints_2.gridx = 1;
		getContentPane().add(commentsLabel, gridBagConstraints_2);

		commentsField = new JTextFieldLimited(DBConstants.COMMENTS_LENGTH);
		commentsField.setPreferredSize(DimensionConstants.STD_FIELD_DIMENSION);
		final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
		gridBagConstraints_3.insets = new Insets(5, 5, 5, 0);
		gridBagConstraints_3.anchor = GridBagConstraints.WEST;
		gridBagConstraints_3.gridy = gridy;
		gridBagConstraints_3.gridx = 2;
		getContentPane().add(commentsField, gridBagConstraints_3);

		//Explain panel
		gridy++;
		JPanel explainPanel = new ExplainPanel();
		final GridBagConstraints gridBagConstraints_4 = new GridBagConstraints();
		gridBagConstraints_4.insets = new Insets(0, 10, 0, 0);
		gridBagConstraints_4.anchor = GridBagConstraints.WEST;
		gridBagConstraints_4.gridwidth = 3;
		gridBagConstraints_4.gridy = gridy;
		gridBagConstraints_4.gridx = 0;
		getContentPane().add(explainPanel, gridBagConstraints_4);		
		
		//Separator
		gridy++;
		JSeparator sep = new JSeparator(JSeparator.HORIZONTAL);
		final GridBagConstraints gridBagConstraints_5 = new GridBagConstraints();
		gridBagConstraints_5.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_5.insets = new Insets(0, 0, 10, 0);
		gridBagConstraints_5.gridwidth = 3;
		gridBagConstraints_5.gridy = gridy;
		gridBagConstraints_5.gridx = 0;
		getContentPane().add(sep, gridBagConstraints_5);
		
        //Two buttons panel
		buttonPanel = new TwoButtonsPanel();        
        gridy++;
        final GridBagConstraints gridBagConstraints_6 = new java.awt.GridBagConstraints();
		gridBagConstraints_6.gridx = 0;
		gridBagConstraints_6.gridy = gridy;
		gridBagConstraints_6.gridwidth = 3;
		gridBagConstraints_6.anchor = GridBagConstraints.CENTER;
		gridBagConstraints_6.insets = new java.awt.Insets(0, 0, 10, 0);
		getContentPane().add(buttonPanel, gridBagConstraints_6);
       
		//Setting some initial values
		String selectedCode = codeBoxField.getSelectedItem().toString();
		String selectedName = Resources.getCurrencyName(selectedCode);
		nameBoxField.setSelectedItem(selectedName);
		
		//Listeners for elements
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
        
        standardCheckBox.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent e) {
				if(standardCheckBox.isSelected()) {
					codeField.setVisible(false);
					nameField.setVisible(false);
					codeBoxField.setVisible(true);
					codeBoxField.setVisible(true);
					updateOnlineCheckBox.setEnabled(true);
				} else {
					codeField.setVisible(true);
					nameField.setVisible(true);
					codeBoxField.setVisible(false);
					codeBoxField.setVisible(false);
					updateOnlineCheckBox.setSelected(false);
					updateOnlineCheckBox.setEnabled(false);
					exchangeRateField.setText("");
					exchangeRateField.setEnabled(true);
				}				
			}
		});
        
        codeBoxField.addItemListener(new ItemListener() {			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					String selectedCode = codeBoxField.getSelectedItem().toString();
					String selectedName = Resources.getCurrencyName(selectedCode);
					nameBoxField.setSelectedItem(selectedName);
					updateExchangeRateIfNeeded();
				}				
			}
		});
        
        nameBoxField.addItemListener(new ItemListener() {			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					String selectedName = nameBoxField.getSelectedItem().toString();
					String selectedCode = Resources.getCurrencyCode(selectedName);
					codeBoxField.setSelectedItem(selectedCode);
					updateExchangeRateIfNeeded();
				}
			}
		});
        
        updateOnlineCheckBox.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent ev) {
				updateExchangeRateIfNeeded();
			}
		});
		
		setModal(true);
		setResizable(false);
		
		pack();
	}
	
	private void updateExchangeRateIfNeeded() {
		if(updateOnlineCheckBox.isSelected()) {
			BigDecimal exchangeRate = ExchangeRatesController.instance().getRate(codeBoxField.getSelectedItem().toString());
			if(BigDecimal.ZERO.equals(exchangeRate)) {
				MyDialogs.showWarningDialog(CurrencyDialog.this, "Failed to get exchange rate");
				exchangeRateField.setEnabled(true);
				exchangeRateField.requestFocus();
			} else {
				exchangeRateField.setEnabled(false);
				exchangeRateField.setText(exchangeRate.toPlainString());
			}
		} else {
			exchangeRateField.setEnabled(true);
		}
	}

	/**
	 * Opens New Currency dialog
	 */
	public static void openNewDialog() {
		if(newDialog == null) {
			newDialog = new NewCurrencyDialog();
		}
		newDialog.currency = new PCurrency();
		newDialog.cleanFields();
		newDialog.codeBoxField.requestFocus();
		newDialog.getRootPane().setDefaultButton(newDialog.buttonPanel.getApproveButton());
		newDialog.showIt();
	}
	
	private void cleanFields() {
		codeBoxField.setSelectedIndex(0);
		updateOnlineCheckBox.setSelected(true);
		updateExchangeRateIfNeeded();
		codeField.setText("");
		nameField.setText("");
		commentsField.setText("");
	}

	/**
	 * Opens New Currency dialog
	 * 
	 * @param currency edited currency
	 * @return currency, edited by user if 'Save' button was pressed, otherwise null
	 */
	public static void openEditDialog(PCurrency currency) {
		if(editDialog == null) {
			editDialog = new EditCurrencyDialog();
		}
		
		editDialog.currency = currency;
		editDialog.standardCheckBox.setEnabled(false);
		editDialog.standardCheckBox.setSelected(editDialog.currency.getStandard());
		if(editDialog.currency.getStandard()) {
			editDialog.codeBoxField.setVisible(true);
			editDialog.nameBoxField.setVisible(true);
			editDialog.codeBoxField.setEnabled(false);
			editDialog.nameBoxField.setEnabled(false);
			editDialog.codeBoxField.setSelectedItem(editDialog.currency.getCode());
			editDialog.nameBoxField.setSelectedItem(editDialog.currency.getName());
			editDialog.codeField.setVisible(false);
			editDialog.nameField.setVisible(false);
			String mainCurrencyCode = SettingController.instance().getSettingValue(PSetting.MAIN_CURRENCY_KEY);
			editDialog.updateOnlineCheckBox.setSelected(editDialog.currency.getUpdateOnline());
			if(editDialog.currency.getCode().equals(mainCurrencyCode)) {
				editDialog.updateOnlineCheckBox.setEnabled(false);
				editDialog.exchangeRateField.setEnabled(false);
			} else {
				editDialog.updateOnlineCheckBox.setEnabled(true);
				if(editDialog.currency.getUpdateOnline()) {
					editDialog.exchangeRateField.setEnabled(false);
				} else {
					editDialog.exchangeRateField.setEnabled(true);
				}
			}
			
		} else {
			editDialog.codeBoxField.setVisible(false);
			editDialog.nameBoxField.setVisible(false);
			editDialog.codeField.setText(editDialog.currency.getCode());
			editDialog.nameField.setText(editDialog.currency.getName());
			editDialog.codeField.setVisible(true);
			editDialog.nameField.setVisible(true);
			editDialog.updateOnlineCheckBox.setEnabled(false);
			editDialog.codeField.requestFocus();			
		}
		editDialog.exchangeRateField.setText(MyFormatter.formatNumberToLocal(editDialog.currency.getExchangeRate().toString()));
		editDialog.commentsField.setText(editDialog.currency.getComment());
		
		editDialog.getRootPane().setDefaultButton(editDialog.buttonPanel.getApproveButton());
		editDialog.showIt();
	}
	
	private void handleOkPressed() {		
		currency.setStandard(standardCheckBox.isSelected());
		if(currency.getStandard()) {
			currency.setCode(codeBoxField.getSelectedItem().toString());
			currency.setName(nameBoxField.getSelectedItem().toString());			
		} else {
			currency.setCode(Trimmer.trim(codeField.getText()));
			currency.setName(Trimmer.trim(nameField.getText()));
		}
		
		currency.setExchangeRateStr(exchangeRateField.getText());
		currency.setUpdateOnline(updateOnlineCheckBox.isSelected());
		currency.setComment(commentsField.getText());
		
		EntityValidator validator = getValidator(currency);
		
		if(validator.validate()) {
			CurrencyController.instance().createOrUpdate(currency);
			setVisible(false);
		} else {
			MyDialogs.showWarningDialog(this, validator.message());
		}
	}
	
	protected abstract EntityValidator getValidator(PCurrency currency);
	
	protected Collection<PCurrency> allCurrencies() {
		return CurrencyController.instance().getAll();
	}
}
