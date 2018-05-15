package com.monyrama.ui.dialogs;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import com.monyrama.controller.*;
import com.monyrama.ui.utils.MyDialogs;
import com.monyrama.controller.LendController;
import com.monyrama.entity.DBConstants;
import com.monyrama.entity.PAccount;
import com.monyrama.entity.PLend;
import com.monyrama.sorter.NammableSorter;
import com.monyrama.ui.components.ComboBombo;
import com.monyrama.ui.components.ComponentsHelper;
import com.monyrama.ui.components.ExplainPanel;
import com.monyrama.ui.components.JCalendar;
import com.monyrama.ui.components.JDateChooser;
import com.monyrama.ui.components.JTextFieldLimited;
import com.monyrama.ui.components.SumFieldWithCalc;
import com.monyrama.ui.components.TwoButtonsPanel;
import com.monyrama.ui.constants.DimensionConstants;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.utils.MyFormatter;
import com.monyrama.utils.Trimmer;
import com.monyrama.validator.EntityValidator;
import com.monyrama.validator.LendValidator;


/**
 * New Lend dialog
 * 
 * @author Petro_Verheles
 *
 */
public class LendDialog extends EscapeDialog {
	private JTextFieldLimited nameField;
    private JLabel currencyValueLabel;
    private ComboBombo<PAccount> accountBox;
    private SumFieldWithCalc sumField;
	private JDateChooser dateChooser;
	private JTextFieldLimited commentsField;
	private TwoButtonsPanel buttonPanel;
	private ControllerListener<PAccount> accountListener;
	
    /**
     * Private constructor. Our dialog will be opened by static public method
     */
	private LendDialog() {
		super();		
		setTitle(Resources.getString("dialogs.titles.newlend"));
		final GridBagLayout gridBagLayout = new GridBagLayout();
		getContentPane().setLayout(gridBagLayout);
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		int gridy = 0;
		
		gridBagConstraints.insets = new Insets(10, 10, 10, 0);		
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 0;
		getContentPane().add(ComponentsHelper.createAstericsLabel(), gridBagConstraints);
		
		final JLabel nameLabel = new JLabel();
		nameLabel.setText(Resources.getString("labels.debtorname") + ":");
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
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 2;
		getContentPane().add(nameField, gridBagConstraints);
			
		gridy++;		
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 10, 10, 0);		
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 0;
		getContentPane().add(ComponentsHelper.createAstericsLabel(), gridBagConstraints);
		
		final JLabel accountLabel = new JLabel();
		accountLabel.setText(Resources.getString("labels.fromaccount") + ":");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 10, 0);	
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 1;
		getContentPane().add(accountLabel, gridBagConstraints);		
    
		accountBox = new ComboBombo<PAccount>();
        accountBox.setPreferredSize(DimensionConstants.STD_FIELD_DIMENSION);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 10, 10);
		getContentPane().add(accountBox, gridBagConstraints);
		
		gridy++;		
		final JLabel currencyLabel = new JLabel();
		currencyLabel.setText(Resources.getString("labels.currency") + ":");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 10, 0);	
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 1;
		getContentPane().add(currencyLabel, gridBagConstraints);
		
		currencyValueLabel = new JLabel();
		currencyValueLabel.setPreferredSize(DimensionConstants.STD_FIELD_DIMENSION);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 5, 10, 10);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 2;
		getContentPane().add(currencyValueLabel, gridBagConstraints);		

		gridy++;
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 10, 10, 0);
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 0;
		getContentPane().add(ComponentsHelper.createAstericsLabel(), gridBagConstraints);
		
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
		
		gridy++;
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 10, 10, 0);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 0;
		getContentPane().add(ComponentsHelper.createAstericsLabel(), gridBagConstraints);
        
		javax.swing.JLabel dateLabel = new javax.swing.JLabel();
        dateLabel.setText(Resources.getString("labels.date") + ":");
    	gridBagConstraints = new GridBagConstraints();
    	gridBagConstraints.gridx = 1;
    	gridBagConstraints.gridy = gridy;
    	gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    	gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        getContentPane().add(dateLabel, gridBagConstraints);
		
		JCalendar jcalUntil = new JCalendar();
		jcalUntil.setWeekOfYearVisible(false);
		
		dateChooser = new JDateChooser(jcalUntil);
		dateChooser.setPreferredSize(DimensionConstants.STD_FIELD_DIMENSION);
		dateChooser.setLocale(Resources.getLocale());
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.insets = new java.awt.Insets(0, 5, 10, 10);
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        getContentPane().add(dateChooser, gridBagConstraints);
		
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
		gridBagConstraints.insets = new Insets(0, 5, 10, 10);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
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
				
		gridy++;
		buttonPanel = new TwoButtonsPanel();
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
        
        gridy++;
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
		getContentPane().add(buttonPanel, gridBagConstraints);

		accountBox.addPlusButtonListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				AccountDialog.openNewDialog();
			}
		});		
		
        accountBox.addItemListener(new ItemListener() {			
			@Override
			public void itemStateChanged(ItemEvent ev) {
				if(ev.getStateChange() == ItemEvent.SELECTED) {
					currencyValueLabel.setText(getSelectedAccount().getCurrency().toString());
				}
			}
		});
        
    	accountListener = new ControllerListener<PAccount>() {

			@Override
			public void createdOrUpdated(PAccount account) {
				updateAccounts();
				accountBox.setSelectedItem(account);
			}

			@Override
			public void deleted(PAccount object) {}
		};
		AccountController.instance().addListener(accountListener);        
		
		pack();
	}
	
	@Override
	protected void actionOnEscape() {
		AccountController.instance().removeListener(accountListener);
		super.actionOnEscape();
	}

	/**
	 * Makes the dialog visible
	 */
	public void showIt() {
		PointerInfo info = MouseInfo.getPointerInfo();
        setLocation(info.getLocation().x, info.getLocation().y);
		setVisible(true);
	}
	
	/**
	 * Opens Lend Dialog
	 */
	public static void openDialog() {
		LendDialog dialog = new LendDialog();
		dialog.nameField.requestFocus();
		dialog.updateAccounts();
		dialog.getRootPane().setDefaultButton(dialog.buttonPanel.getApproveButton());
		dialog.showIt();
	}


	private void updateAccounts() {
        accountBox.removeAllItems();
        
		List<PAccount> accounts = AccountController.instance().listActive();
		NammableSorter.sort(accounts);
        
		for (PAccount account : accounts) {
			accountBox.addItem(account);
		}
	}

	private void handleMoneyTransfer() {
		PLend lend = new PLend();
		lend.setName(getEnteredName());
		lend.setLastChangeDate(getSelectedDate());
		lend.setAccount(getSelectedAccount());
		lend.setSumm(new BigDecimal(getEnteredStandardSum()));
		lend.setComment(getEnteredComments());
		
		LendController.instance().create(lend);		
	}
	
	private void handleOkPressed() {
		String name = getEnteredName();
		String sum = getNotValidatedSum();
		
		EntityValidator validator;

		validator = new LendValidator(name, sum, getSelectedAccount());
		
		if(validator.validate()) {
			handleMoneyTransfer(); 		
			actionOnEscape();
		} else {
			MyDialogs.showWarningDialog(this, validator.message());
		}
	} 

	private PAccount getSelectedAccount() {
		return (PAccount)accountBox.getSelectedItem();
	}

	private String getEnteredComments() {
		return commentsField.getText();
	}

	private String getEnteredStandardSum() {
		return MyFormatter.formatNumberToStandard(getNotValidatedSum());
	}
	
	private String getNotValidatedSum() {
		return Trimmer.trim(sumField.getText());
	}

	private Date getSelectedDate() {
		return dateChooser.getDate();
	}

	private String getEnteredName() {
		return Trimmer.trim(nameField.getText());
	}  
}