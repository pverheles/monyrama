package com.monyrama.ui.dialogs;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import com.monyrama.controller.AccountController;
import com.monyrama.controller.ControllerListener;
import com.monyrama.controller.TransferController;
import com.monyrama.entity.DBConstants;
import com.monyrama.entity.PAccount;
import com.monyrama.ui.components.ComboBombo;
import com.monyrama.ui.components.ComponentsHelper;
import com.monyrama.ui.components.ExplainPanel;
import com.monyrama.ui.components.JCalendar;
import com.monyrama.ui.components.JDateChooser;
import com.monyrama.ui.components.SumFieldWithCalc;
import com.monyrama.ui.components.TwoButtonsPanel;
import com.monyrama.ui.constants.DimensionConstants;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.utils.MyDialogs;
import com.monyrama.ui.utils.MyFormatter;
import com.monyrama.utils.Calc;
import com.monyrama.utils.Trimmer;
import com.monyrama.validator.util.StringSumValidator;
import com.monyrama.validator.util.StringValidator;


public class TransferDialog extends EscapeDialog {	
	private JCheckBox convertAutomaticallyCheckbox;
	
	private ComboBombo<PAccount> fromAccountBox;
	private SumFieldWithCalc fromSumField;

	private ComboBombo<PAccount> toAccountBox;
	private SumFieldWithCalc toSumField;	
	
	protected JDateChooser dateChooser;
	
	private TwoButtonsPanel buttonPanel;	
	
	private boolean addingFromAccount = false;

	private ControllerListener<PAccount> accountListener;

	/**
	 * Constructor
	 * 
	 * Paints the dialog
	 *  
	 */
	private TransferDialog() {
		super();			
		
		setTitle(Resources.getString("dialogs.titles.transfer"));
		
		final GridBagLayout gridBagLayout = new GridBagLayout();
		getContentPane().setLayout(gridBagLayout);
		GridBagConstraints gridBagConstraints;
		JLabel astericsLabel;
		int gridy = 0;
		
		convertAutomaticallyCheckbox = new JCheckBox(Resources.getString("labels.convertautomatically"));
		convertAutomaticallyCheckbox.setSelected(true);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 0, 10, 0);		
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridwidth = 3;
		getContentPane().add(convertAutomaticallyCheckbox, gridBagConstraints);	
		
		gridy++;
		JSeparator topSep = new JSeparator(JSeparator.HORIZONTAL);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.insets = new Insets(0, 0, 10, 0);
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 0;
		getContentPane().add(topSep, gridBagConstraints);			
		
		//From account fields
		gridy++;
		final JLabel fromAccountLabel = new JLabel();
		fromAccountLabel.setText(Resources.getString("labels.fromaccount") + ":");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 0, 10, 0);		
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 1;
		getContentPane().add(fromAccountLabel, gridBagConstraints);
		
		fromAccountBox = new ComboBombo<PAccount>();
		fromAccountBox.setPreferredSize(DimensionConstants.STD_FIELD_DIMENSION);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 5, 10, 10);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 2;
		getContentPane().add(fromAccountBox, gridBagConstraints);
				
		gridy++;
		astericsLabel = ComponentsHelper.createAstericsLabel();
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 10, 10, 0);
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 0;
		getContentPane().add(astericsLabel, gridBagConstraints);
		
		final JLabel fromSumLabel = new JLabel();
		fromSumLabel.setText(Resources.getString("labels.sum") + ":");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 10, 0);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 1;
		getContentPane().add(fromSumLabel, gridBagConstraints);

		fromSumField = new SumFieldWithCalc(DBConstants.SUM_LENGTH, Resources.getString("tools.calculator"), Resources.getString("calculator.errorMsg"), Resources.getString("calculator.insertTooltip"), !Resources.isSumDotSeparated());
		fromSumField.setPreferredSize(DimensionConstants.STD_FIELD_DIMENSION);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 5, 10, 10);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 2;
		getContentPane().add(fromSumField, gridBagConstraints);
				
		
		//To account fields
		gridy++;
		final JLabel toAccountLabel = new JLabel();
		toAccountLabel.setText(Resources.getString("labels.toaccount") + ":");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(20, 0, 10, 0);		
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 1;
		getContentPane().add(toAccountLabel, gridBagConstraints);
		
		toAccountBox = new ComboBombo<PAccount>();
		toAccountBox.setPreferredSize(DimensionConstants.STD_FIELD_DIMENSION);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(20, 5, 10, 10);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 2;
		getContentPane().add(toAccountBox, gridBagConstraints);
				
		gridy++;
		astericsLabel = ComponentsHelper.createAstericsLabel();
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 10, 10, 0);
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 0;
		getContentPane().add(astericsLabel, gridBagConstraints);
		
		final JLabel toSumLabel = new JLabel();
		toSumLabel.setText(Resources.getString("labels.sum") + ":");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 10, 0);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 1;
		getContentPane().add(toSumLabel, gridBagConstraints);

		toSumField = new SumFieldWithCalc(DBConstants.SUM_LENGTH, Resources.getString("tools.calculator"), Resources.getString("calculator.errorMsg"), Resources.getString("calculator.insertTooltip"), !Resources.isSumDotSeparated());
		toSumField.setPreferredSize(DimensionConstants.STD_FIELD_DIMENSION);
		toSumField.setEnabled(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 5, 10, 10);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 2;
		getContentPane().add(toSumField, gridBagConstraints);
		
		gridy++;
        JLabel astericsLabel2 = ComponentsHelper.createAstericsLabel();
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(20, 10, 10, 0);
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.gridx = 0;
        getContentPane().add(astericsLabel2, gridBagConstraints);

        JLabel dateLabel = new javax.swing.JLabel();
        dateLabel.setText(Resources.getString("labels.date") + ":");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 10, 0);
        getContentPane().add(dateLabel, gridBagConstraints);		
		
        JCalendar jcalUntil = new JCalendar();
        jcalUntil.setWeekOfYearVisible(false);
		
        dateChooser = new JDateChooser(jcalUntil);
        dateChooser.setPreferredSize(DimensionConstants.STD_FIELD_DIMENSION);
        dateChooser.setLocale(Resources.getLocale());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.insets = new Insets(20, 0, 10, 10);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        getContentPane().add(dateChooser, gridBagConstraints);
		
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
        
        gridy++;
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
		getContentPane().add(buttonPanel, gridBagConstraints);

		updateAccountBoxes();
		
        addListeners();
		
		pack();
		setModal(true);
		setResizable(false);	
	}

	protected void addListeners() {		
		fromAccountBox.addPlusButtonListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				addingFromAccount = true;
				AccountDialog.openNewDialog();
			}
		});
		
		toAccountBox.addPlusButtonListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				addingFromAccount = false;
				AccountDialog.openNewDialog();
			}
		});
		
		accountListener = new ControllerListener<PAccount>() {
			
			@Override
			public void deleted(PAccount object) {}
			
			@Override
			public void createdOrUpdated(PAccount newAccount) {
				PAccount currentFromAccount = getFromAccount();
				PAccount currentToAccount = getToAccount();
				updateAccountBoxes();
				if(addingFromAccount) {
					fromAccountBox.setSelectedItem(newAccount);
					toAccountBox.setSelectedItem(currentToAccount);
				} else {
					fromAccountBox.setSelectedItem(currentFromAccount);
					toAccountBox.setSelectedItem(newAccount);
				}
			}
		};
		AccountController.instance().addListener(accountListener);
		
		fromAccountBox.addItemListener(new ItemListener() {			
			@Override
			public void itemStateChanged(ItemEvent ev) {
				if(ev.getStateChange() == ItemEvent.SELECTED) {					
					fromSumField.setText("");
					toSumField.setText("");
					fromSumField.requestFocus();
					updateToSumFieldEnability();
				}
			}
		});
		
		toAccountBox.addItemListener(new ItemListener() {			
			@Override
			public void itemStateChanged(ItemEvent ev) {
				if(ev.getStateChange() == ItemEvent.SELECTED) {
					calculateToSumIfNeeded();
					toSumField.requestFocus();
					updateToSumFieldEnability();
				}
			}
		});		
		
		fromSumField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(getFromAccount().getCurrency().equals(getToAccount().getCurrency())) {
					String fromSumValueStr = fromSumField.getText();
					if(!StringValidator.isStringNullOrEmpty(fromSumValueStr)) {
						fromSumValueStr = Trimmer.trim(fromSumValueStr);
						if(StringSumValidator.isValidPositiveFormat(fromSumValueStr)) {
							toSumField.setText(fromSumValueStr);
						}
					}
				} else {
					calculateToSumIfNeeded();	
				}				
			}			
		});
		
		toSumField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				calculateFromSumIfNeeded();
			}			
		});		
		
		buttonPanel.setApproveListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				if(validateData()) {
					TransferController.instance().makeTransfer(
							getFromAccount(),
							new BigDecimal(MyFormatter.formatNumberToStandard(Trimmer.trim(fromSumField.getText()))),
							getToAccount(),
							new BigDecimal(MyFormatter.formatNumberToStandard(Trimmer.trim(toSumField.getText()))),
							dateChooser.getDate());
					
					actionOnEscape();
				}
			}        	
        });
        buttonPanel.setCancelListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				actionOnEscape();
			}
		});
	}
	
	private void updateToSumFieldEnability() {
		PAccount fromAccount = (PAccount)fromAccountBox.getSelectedItem();
		PAccount toAccount = getToAccount();
		
		if(fromAccount == null || toAccount == null) {
			return;
		}
		
		if(toAccount.getCurrency().equals(fromAccount.getCurrency())) {
			toSumField.setEnabled(false);
		} else {
			toSumField.setEnabled(true);
		}
	}

	/**
	 * Validates the data entered by a user;
	 * If budget with entered name is empty or exists shows warning dialog
	 * If Sum is invalid shows warning dialog
	 * 
	 * @return - true if the data is valid or false if the data is invalid
	 */
	private boolean validateData() {
		if(getFromAccount() == null) {
			MyDialogs.showWarningDialog(this, Resources.getString("dialogs.warnings.fromaccountempty") + "!");
			return false;
		}
		
		if(getToAccount() == null) {
			MyDialogs.showWarningDialog(this, Resources.getString("dialogs.warnings.toaccountempty") + "!");
			return false;
		}		
		
		if(getFromAccount().equals(getToAccount())) {
			MyDialogs.showWarningDialog(this, Resources.getString("dialogs.warnings.sameaccount") + "!");
			return false;
		}
		
		String fromSum = Trimmer.trim(fromSumField.getText());		
		if(StringValidator.isStringNullOrEmpty(fromSum)) {
			MyDialogs.showWarningDialog(this, Resources.getString("dialogs.warnings.sumempty") + "!");
			return false;
		}
		
		if(!StringSumValidator.isValidPositiveFormat(fromSum)) {
			MyDialogs.showWarningDialog(this, Resources.getString("dialogs.warnings.invalidsum") + "!");
			return false;
		}
		
		String toSum = Trimmer.trim(toSumField.getText());		
		if(StringValidator.isStringNullOrEmpty(toSum)) {
			MyDialogs.showWarningDialog(this, Resources.getString("dialogs.warnings.sumempty") + "!");
			return false;
		}
		
		if(!StringSumValidator.isValidPositiveFormat(toSum)) {
			MyDialogs.showWarningDialog(this, Resources.getString("dialogs.warnings.invalidsum") + "!");
			return false;
		}		
		
		return true;
	}
	
	private void updateAccountBoxes() {
		List<PAccount> activeAccounts = AccountController.instance().listActive();
		
		fromAccountBox.removeAllItems();
		for(PAccount account : activeAccounts) {
			fromAccountBox.addItem(account);
		}		
		
		toAccountBox.removeAllItems();
		for(PAccount account : activeAccounts) {
			toAccountBox.addItem(account);
		}
	}
	
	public static void openDialog() {
		TransferDialog dialog = new TransferDialog();
		
		dialog.fromSumField.requestFocus();		
		dialog.getRootPane().setDefaultButton(dialog.buttonPanel.getApproveButton());		
		dialog.showIt();
	}

	@Override
	protected void actionOnEscape() {
		AccountController.instance().removeListener(accountListener);
		super.actionOnEscape();
	}

	private PAccount getToAccount() {
		return toAccountBox.getSelectedItem();
	}

	private PAccount getFromAccount() {
		return fromAccountBox.getSelectedItem();
	}

	private void calculateToSumIfNeeded() {
		if(convertAutomaticallyCheckbox.isSelected()) {
			calculateTransferSum(fromSumField, toSumField, getFromAccount(), getToAccount());	
		}		
	}
	
	private void calculateFromSumIfNeeded() {
		if(convertAutomaticallyCheckbox.isSelected()) {
			calculateTransferSum(toSumField, fromSumField, getToAccount(), getFromAccount());	
		}		
	}	

	private void calculateTransferSum(SumFieldWithCalc leftSumField, SumFieldWithCalc rightSumField, PAccount leftAccount, PAccount rightAccount) {
		String leftSumValueStr = leftSumField.getText();
		if(!StringValidator.isStringNullOrEmpty(leftSumValueStr)) {
			leftSumValueStr = Trimmer.trim(leftSumValueStr);
			if(StringSumValidator.isValidPositiveFormat(leftSumValueStr)) {
				leftSumValueStr = MyFormatter.formatNumberToStandard(leftSumValueStr);				
				BigDecimal rightSumValue = Calc.convertSum(leftAccount.getCurrency(),
						rightAccount.getCurrency(), new BigDecimal(leftSumValueStr));
				rightSumField.setText(MyFormatter.formatNumberToLocal(rightSumValue.toPlainString()));											
			}
		}
	}
}
