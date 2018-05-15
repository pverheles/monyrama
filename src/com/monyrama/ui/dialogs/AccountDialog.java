package com.monyrama.ui.dialogs;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import com.monyrama.controller.AccountController;
import com.monyrama.controller.ControllerListener;
import com.monyrama.controller.CurrencyController;
import com.monyrama.entity.DBConstants;
import com.monyrama.entity.PAccount;
import com.monyrama.entity.PCurrency;
import com.monyrama.sorter.NammableSorter;
import com.monyrama.ui.components.ComboBombo;
import com.monyrama.ui.components.ComponentsHelper;
import com.monyrama.ui.components.ExplainPanel;
import com.monyrama.ui.components.JTextFieldLimited;
import com.monyrama.ui.components.SumFieldWithCalc;
import com.monyrama.ui.components.TwoButtonsPanel;
import com.monyrama.ui.constants.DimensionConstants;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.utils.MyDialogs;
import com.monyrama.ui.utils.MyFormatter;
import com.monyrama.utils.Trimmer;
import com.monyrama.validator.EntityValidator;

public abstract class AccountDialog extends EscapeDialog {

	protected JTextFieldLimited nameField;
	protected SumFieldWithCalc sumField;
	protected ComboBombo<PCurrency> currencyBox;
	//protected JCheckBox savingCheckbox;
	protected JTextFieldLimited commentsField;
	private TwoButtonsPanel buttonPanel;

	protected PAccount account;
	private ControllerListener<PCurrency> currencyListener;

    /**
     * Private constructor. Our dialog will be opened by static public method
     */
	protected AccountDialog() {
		super();			

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		final GridBagLayout gridBagLayout = new GridBagLayout();
		getContentPane().setLayout(gridBagLayout);
		GridBagConstraints gridBagConstraints;
		int gridy = 0;

		JLabel astericsLabel = ComponentsHelper.createAstericsLabel();
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 10, 0);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 0;
		getContentPane().add(astericsLabel, gridBagConstraints);
		
		final JLabel nameLabel = new JLabel();
		nameLabel.setText(Resources.getString("labels.name") + ":");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 0, 10, 0);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 1;
		getContentPane().add(nameLabel, gridBagConstraints);

		nameField = new JTextFieldLimited(DBConstants.NAME_LENGTH);
		nameField.setPreferredSize(DimensionConstants.STD_FIELD_DIMENSION);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 5, 10, 10);
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 2;
		getContentPane().add(nameField, gridBagConstraints);

		gridy++;
		JLabel astericsLabel1 = ComponentsHelper.createAstericsLabel();
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 10, 10, 0);
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 0;
		getContentPane().add(astericsLabel1, gridBagConstraints);		
		
		final JLabel currencyLabel = new JLabel();
		currencyLabel.setText(Resources.getString("labels.currency") + ":");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 10, 0);		
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 1;
		getContentPane().add(currencyLabel, gridBagConstraints);
		
		currencyBox = new ComboBombo<PCurrency>();
		currencyBox.setPreferredSize(DimensionConstants.STD_FIELD_DIMENSION);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 5, 10, 10);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 2;
		getContentPane().add(currencyBox, gridBagConstraints);
		currencyBox.addPlusButtonListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				CurrencyDialog.openNewDialog();
			}
		});
		
		gridy++;
		JLabel astericsLabel3 = ComponentsHelper.createAstericsLabel();
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 10, 10, 0);
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 0;
		getContentPane().add(astericsLabel3, gridBagConstraints);
		
		final JLabel sumLabel = new JLabel();
		sumLabel.setText(Resources.getString("labels.sum") + ":");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 10, 0);		
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 1;
		getContentPane().add(sumLabel, gridBagConstraints);

		sumField = new SumFieldWithCalc(DBConstants.SUM_LENGTH, Resources.getString("tools.calculator"), Resources.getString("calculator.errorMsg"), Resources.getString("calculator.insertTooltip"), !Resources.isSumDotSeparated());
		sumField.setPreferredSize(DimensionConstants.STD_FIELD_DIMENSION);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 5, 10, 10);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 2;
		getContentPane().add(sumField, gridBagConstraints);
		
//		gridy++;
//		final JLabel savingLabel = new JLabel();
//		savingLabel.setText(Resources.getString("labels.saving") + ":");
//		gridBagConstraints = new GridBagConstraints();
//		gridBagConstraints.insets = new Insets(0, 0, 10, 0);
//		gridBagConstraints.anchor = GridBagConstraints.WEST;
//		gridBagConstraints.gridy = gridy;
//		gridBagConstraints.gridx = 1;
//		getContentPane().add(savingLabel, gridBagConstraints);
//		
//		savingCheckbox = new JCheckBox();
//		gridBagConstraints = new GridBagConstraints();
//		gridBagConstraints.anchor = GridBagConstraints.WEST;
//		gridBagConstraints.insets = new Insets(0, 2, 10, 0);
//		gridBagConstraints.gridy = gridy;
//		gridBagConstraints.gridx = 2;
//		getContentPane().add(savingCheckbox, gridBagConstraints);			

		gridy++;
		final JLabel commentsLabel = new JLabel();
		commentsLabel.setText(Resources.getString("labels.comments") + ":");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 10, 0);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 1;
		getContentPane().add(commentsLabel, gridBagConstraints);

		commentsField = new JTextFieldLimited(DBConstants.COMMENTS_LENGTH);
		commentsField.setPreferredSize(DimensionConstants.STD_FIELD_DIMENSION);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 10, 10);
		gridBagConstraints.anchor = GridBagConstraints.NORTHEAST;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 2;
		getContentPane().add(commentsField, gridBagConstraints);
								
		gridy++;
		JPanel explainPanel = new ExplainPanel();
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 10, 0, 0);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 0;
		getContentPane().add(explainPanel, gridBagConstraints);		
		
		gridy++;
		JSeparator sep = new JSeparator(JSeparator.HORIZONTAL);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.insets = new Insets(0, 0, 10, 0);
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 0;
		getContentPane().add(sep, gridBagConstraints);
				
		buttonPanel = new TwoButtonsPanel();
        buttonPanel.setApproveListener(new ActionListener() {
			@Override
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
        
        gridy++;
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
		getContentPane().add(buttonPanel, gridBagConstraints);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				actionOnEscape();
			}			
		});
		
		currencyListener = new ControllerListener<PCurrency>() {
			
			@Override
			public void deleted(PCurrency currency) {}
			
			@Override
			public void createdOrUpdated(PCurrency currency) {
				updateCurrencyBox();
				currencyBox.setSelectedItem(currency);
			}
		};
		CurrencyController.instance().addListener(currencyListener);
		
		pack();
		setModal(true);
		setResizable(false);	
	}
	
	private void updateCurrencyBox() {
		currencyBox.removeAllItems();
		List<PCurrency> sortedCurrencies = new ArrayList<PCurrency>(CurrencyController.instance().getAll());
	    NammableSorter.sort(sortedCurrencies);
		for(PCurrency currency : sortedCurrencies) {
			currencyBox.addItem(currency);
		}
	}
	
	public static void openNewDialog() {
		AccountDialog newDialog = new NewAccountDialog();		
		newDialog.updateCurrencyBox();
		newDialog.nameField.requestFocus();
		newDialog.getRootPane().setDefaultButton(newDialog.buttonPanel.getApproveButton());
		newDialog.showIt();
	}

	public static void openEditDialog(PAccount accountToEdit) {
		AccountDialog editDialog = new EditAccountDialog();
		
		editDialog.account = accountToEdit;
		editDialog.updateCurrencyBox();
		editDialog.setFields();
		editDialog.nameField.requestFocus();
		
		editDialog.currencyBox.setEnabled(false);
		editDialog.sumField.setEnabled(false);
		
		editDialog.getRootPane().setDefaultButton(editDialog.buttonPanel.getApproveButton());
		editDialog.showIt();		
	}

	private void setFields() {
		nameField.setText(account.getName());
		sumField.setText(MyFormatter.formatNumberToLocal(account.getSumm().toPlainString()));
		currencyBox.setSelectedItem(account.getCurrency());
		//savingCheckbox.setSelected(account.getSaving() != null && account.getSaving());
		commentsField.setText(account.getComment());
	}
	
	protected abstract void handleOkPressed();
	
	protected PAccount createAccountFromFields() {
		PAccount accountFromFields = new PAccount();		
		accountFromFields.setName(Trimmer.trim(nameField.getText()));
		accountFromFields.setSumStr(Trimmer.trim(sumField.getText()));
		accountFromFields.setCurrency((PCurrency)currencyBox.getSelectedItem());		
		accountFromFields.setComment(Trimmer.trim(commentsField.getText()));
		//accountFromFields.setSaving(savingCheckbox.isSelected());
		accountFromFields.setSaving(false);
		return accountFromFields;
	}
	
	protected Collection<PAccount> allAccounts() {
		return AccountController.instance().getAll();
	}
	
	private void tryToCreateOrUpdateAccount() {
		AccountController.instance().createOrUpdate(account);
	}
	
	protected void validateAndSave(PAccount editAccount, EntityValidator validator) {
		if(validator.validate()) {
			account = editAccount;
			tryToCreateOrUpdateAccount();
			setVisible(false);
		} else {
			MyDialogs.showWarningDialog(this, validator.message());
		}
	}

	public static void openNewDialog(PCurrency currency) {
		AccountDialog newDialog = new NewAccountDialog();		
		newDialog.updateCurrencyBox();
		newDialog.currencyBox.setSelectedItem(currency);
		newDialog.currencyBox.setEnabled(false);
		newDialog.nameField.requestFocus();
		newDialog.getRootPane().setDefaultButton(newDialog.buttonPanel.getApproveButton());
		newDialog.showIt();
	}

	@Override
	protected void actionOnEscape() {
		CurrencyController.instance().removeListener(currencyListener);
		super.actionOnEscape();
	}	
}
