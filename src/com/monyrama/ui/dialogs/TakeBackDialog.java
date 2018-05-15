package com.monyrama.ui.dialogs;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import com.monyrama.controller.AccountController;
import com.monyrama.controller.ControllerListener;
import com.monyrama.controller.LendController;
import com.monyrama.controller.TakingBackController;
import com.monyrama.db.enumarations.EntityStates;
import com.monyrama.entity.DBConstants;
import com.monyrama.entity.PAccount;
import com.monyrama.entity.PCurrency;
import com.monyrama.entity.PLend;
import com.monyrama.entity.PTakingBack;
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
import com.monyrama.validator.EntityValidator;
import com.monyrama.validator.TakeBackValidator;


/**
 * 
 * @author Petro_Verheles
 *
 */
public class TakeBackDialog extends EscapeDialog {
	private javax.swing.JLabel nameValueLabel;
    private javax.swing.JLabel currencyValueLabel;
    private ComboBombo<PAccount> accountBox;
    private SumFieldWithCalc sumField;
	private JDateChooser dateChooser;
	//private javax.swing.JLabel commentsValueLabel;
	private TwoButtonsPanel buttonPanel;
	
	private PLend lend;
	private ControllerListener<PAccount> accountListener;
	
    /**
     * Private constructor. Our dialog will be opened by static public method
     */
	private TakeBackDialog() {
		super();		
		setTitle(Resources.getString("buttons.takeback"));	
		final GridBagLayout gridBagLayout = new GridBagLayout();
		getContentPane().setLayout(gridBagLayout);
		GridBagConstraints gridBagConstraints;
		int gridy = 0;
		
		final JLabel nameLabel = new JLabel();
		nameLabel.setText(Resources.getString("labels.debtorname") + ":");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 0, 10, 0);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 1;
		getContentPane().add(nameLabel, gridBagConstraints);

		nameValueLabel = new JLabel();
		nameValueLabel.setPreferredSize(DimensionConstants.STD_FIELD_DIMENSION);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 5, 10, 10);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 2;
		getContentPane().add(nameValueLabel, gridBagConstraints);	
		
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
		gridBagConstraints.insets = new Insets(0, 5, 10, 0);
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
		
		final JLabel accountLabel = new JLabel();
		accountLabel.setText(Resources.getString("labels.toaccount") + ":");
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
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        getContentPane().add(accountBox, gridBagConstraints);
		
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
		gridBagConstraints.insets = new Insets(5, 5, 10, 0);
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
		gridBagConstraints.insets = new java.awt.Insets(0, 5, 10, 0);
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        getContentPane().add(dateChooser, gridBagConstraints);
		
//        gridy++;
//		final JLabel commentsLabel = new JLabel();
//		commentsLabel.setText(Resources.getString("labels.comments") + ":");
//		gridBagConstraints = new GridBagConstraints();
//		gridBagConstraints.insets = new Insets(0, 0, 10, 0);
//		gridBagConstraints.anchor = GridBagConstraints.WEST;
//		gridBagConstraints.gridy = gridy;
//		gridBagConstraints.gridx = 1;
//		getContentPane().add(commentsLabel, gridBagConstraints);
//
//		commentsValueLabel = new JLabel();
//		commentsValueLabel.setPreferredSize(DimensionConstants.GENERAL_DIALOG_FIELD_DIMENSION);
//		gridBagConstraints = new GridBagConstraints();
//		gridBagConstraints.insets = new Insets(0, 5, 10, 10);
//		gridBagConstraints.anchor = GridBagConstraints.WEST;
//		gridBagConstraints.gridy = gridy;
//		gridBagConstraints.gridx = 2;
//		getContentPane().add(commentsValueLabel, gridBagConstraints);
							
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
		buttonPanel.setApproveButtonText(Resources.getString("dialogs.ok"));
		buttonPanel.setApproveButtonIcon(Resources.getIcon("ok.png"));
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
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
		getContentPane().add(buttonPanel, gridBagConstraints);
		
		accountBox.addPlusButtonListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				AccountDialog.openNewDialog(lend.getCurrency());
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

	private String getNotValidatedSum() {
		return Trimmer.trim(sumField.getText());
	}
	
	private String getEnteredStandardSum() {
		return MyFormatter.formatNumberToStandard(getNotValidatedSum());
	}

	public static void openDialog(PLend lend) {
		TakeBackDialog dialog = new TakeBackDialog();	
		dialog.lend = lend;
		dialog.setTakerFields();
		dialog.getRootPane().setDefaultButton(dialog.buttonPanel.getApproveButton());
		dialog.showIt();
	}

	/**
	 * Sets source fields (depositories, budgets) depending on the selected currency
	 */
	public void setTakerFields() {
		nameValueLabel.setText(lend.getName());
		currencyValueLabel.setText(lend.getCurrency().toString());
		//commentsValueLabel.setText(lend.getComment());
		
		BigDecimal takenBackYet = Calc.sum(lend.getTakingBacks());
		BigDecimal leftToTakeBack = lend.getSumm().subtract(takenBackYet);
		sumField.setText(MyFormatter.formatNumberToLocal(leftToTakeBack.toString()));
		
		updateAccounts();

	}

	private void updateAccounts() {
		accountBox.removeAllItems();
		
		PCurrency currency = lend.getCurrency();
		List<PAccount> accounts = AccountController.instance().listActiveByCurrency(currency);
		for(PAccount account : accounts) {
			accountBox.addItem(account);
		}
	}
		
	private void handleMoneyTransfer() {
        PTakingBack takingBack = new PTakingBack();
        takingBack.setAccount(getSelectedAccount());
        takingBack.setSumm(new BigDecimal(getEnteredStandardSum()));
        takingBack.setLend(lend);
        TakingBackController.instance().create(takingBack);
		lend.getTakingBacks().add(takingBack);
        
        BigDecimal takenBackYet = Calc.sum(lend.getTakingBacks());
        if(takenBackYet.compareTo(lend.getSumm()) == 0) {
        	LendController.instance().updateState(lend, EntityStates.CLOSED);
        } else {
			//just to fire update event
			LendController.instance().updateState(lend, EntityStates.ACTIVE);
		}

	}
	
	private void handleOkPressed() {
		BigDecimal takenBackYet = Calc.sum(lend.getTakingBacks());
		EntityValidator validator = new TakeBackValidator(lend, getNotValidatedSum(), takenBackYet, getSelectedAccount());
		
		if(validator.validate()) {
			handleMoneyTransfer();					
			actionOnEscape();
		} else {
			MyDialogs.showWarningDialog(this, validator.message());
		}
	} 
	
	private PAccount getSelectedAccount() {
		return accountBox.getSelectedItem();
	}

	@Override
	protected void actionOnEscape() {
		AccountController.instance().removeListener(accountListener);
		super.actionOnEscape();
	}
	
	
}