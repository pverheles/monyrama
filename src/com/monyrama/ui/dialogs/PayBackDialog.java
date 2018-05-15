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

import com.monyrama.controller.ControllerListener;
import com.monyrama.controller.DebtController;
import com.monyrama.controller.PayingBackController;
import com.monyrama.db.enumarations.EntityStates;
import com.monyrama.entity.DBConstants;
import com.monyrama.entity.PCurrency;
import com.monyrama.entity.PPayingBack;
import com.monyrama.ui.components.ComponentsHelper;
import com.monyrama.ui.components.JDateChooser;
import com.monyrama.ui.components.SumFieldWithCalc;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.utils.MyDialogs;
import com.monyrama.controller.AccountController;
import com.monyrama.entity.PAccount;
import com.monyrama.entity.PDebt;
import com.monyrama.ui.components.ComboBombo;
import com.monyrama.ui.components.ExplainPanel;
import com.monyrama.ui.components.JCalendar;
import com.monyrama.ui.components.TwoButtonsPanel;
import com.monyrama.ui.constants.DimensionConstants;
import com.monyrama.ui.utils.MyFormatter;
import com.monyrama.utils.Calc;
import com.monyrama.utils.Trimmer;
import com.monyrama.validator.EntityValidator;
import com.monyrama.validator.PayBackValidator;


public class PayBackDialog extends EscapeDialog {
    
    private javax.swing.JLabel nameValueLabel;
    private javax.swing.JLabel currencyValueLabel;
    private ComboBombo<PAccount> accountBox;
    private SumFieldWithCalc sumField;
    private JDateChooser dateChooser;
    private TwoButtonsPanel buttonPanel;

    private PDebt debt;
	private ControllerListener<PAccount> accountListener;

    /**
     * Private constructor. Our dialog will be opened by static public method
     */
    private PayBackDialog() {
        super();
        setTitle(Resources.getString("buttons.payback"));
        final GridBagLayout gridBagLayout = new GridBagLayout();
        getContentPane().setLayout(gridBagLayout);
        GridBagConstraints gridBagConstraints;
        int gridy = 0;

        final JLabel nameLabel = new JLabel();
        nameLabel.setText(Resources.getString("labels.lendername") + ":");
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

        final JLabel accountLabel = new JLabel(Resources.getString("labels.fromaccount") + ":");
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
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(0, 5, 10, 0);
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

        sumField = new SumFieldWithCalc(DBConstants.SUM_LENGTH, Resources.getString("tools.calculator"), Resources.getString("calculator.errorMsg"), Resources.getString("calculator.insertTooltip"), !Resources.isSumDotSeparated());;
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

        gridy++;
        final GridBagConstraints gridBagConstraints_16 = new java.awt.GridBagConstraints();
        gridBagConstraints_16.gridx = 0;
        gridBagConstraints_16.gridy = gridy;
        gridBagConstraints_16.gridwidth = 3;
        gridBagConstraints_16.anchor = GridBagConstraints.CENTER;
        gridBagConstraints_16.insets = new java.awt.Insets(0, 0, 10, 0);
        getContentPane().add(buttonPanel, gridBagConstraints_16);

		accountBox.addPlusButtonListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				AccountDialog.openNewDialog(debt.getCurrency());
			}
		});	

    	accountListener = new ControllerListener<PAccount>() {

			@Override
			public void createdOrUpdated(PAccount account) {
				updateAccountBox();
				accountBox.setSelectedItem(account);
			}

			@Override
			public void deleted(PAccount object) {}
		};
		AccountController.instance().addListener(accountListener); 	        
        
        pack();
        setModal(true);
        setResizable(false);
    }

    /**
     * Opens the dialog
     *
     * @param debt
     */
    public static void openDialog(PDebt debt) {
    	PayBackDialog dialog = new PayBackDialog();
        dialog.debt = debt;
        dialog.setGiverFields();
        dialog.getRootPane().setDefaultButton(dialog.buttonPanel.getApproveButton());
        dialog.showIt();
    }

    /**
     * Sets source fields (depositories, budgets) depending on the selected currency
     */
    public void setGiverFields() {
        nameValueLabel.setText(debt.getName());
        currencyValueLabel.setText(debt.getCurrency().toString());

        BigDecimal leftToPay = debt.getSumm().subtract(Calc.sum(debt.getPayingBacks()));
        sumField.setText(MyFormatter.formatNumberToLocal(leftToPay.toPlainString()));

        updateAccountBox();
    }

	private void updateAccountBox() {
		accountBox.removeAllItems();

        PCurrency currency = debt.getCurrency();
		List<PAccount> accounts = AccountController.instance().listActiveByCurrency(currency);
		
		for(PAccount account : accounts) {
			accountBox.addItem(account);
		}
	}
       
    private void handleMoneyTransfer() {
    	PPayingBack payingBack = new PPayingBack();
    	payingBack.setAccount(getSelectedAccount());
    	payingBack.setSumm(new BigDecimal(getEnteredStandardSum()));
    	payingBack.setDebt(debt);
    	PayingBackController.instance().create(payingBack);
        debt.getPayingBacks().add(payingBack);
    	
    	BigDecimal paidYet = Calc.sum(debt.getPayingBacks());
        if (paidYet.compareTo(debt.getSumm()) == 0) {
            DebtController.instance().updateState(debt, EntityStates.CLOSED);
        } else {
            //just to fire event
            DebtController.instance().updateState(debt, EntityStates.ACTIVE);
        }
    }

	private PAccount getSelectedAccount() {
		return (PAccount)accountBox.getSelectedItem();
	}

	private void handleOkPressed() {
		BigDecimal paidYet = Calc.sum(debt.getPayingBacks());
		EntityValidator validator = new PayBackValidator(debt, getNotValidatedSum(), paidYet, getSelectedAccount());
    	
        if (validator.validate()) {
        	handleMoneyTransfer();                   
            actionOnEscape();
        } else {
        	MyDialogs.showWarningDialog(this, validator.message());
        }
	}

	private String getNotValidatedSum() {
		return Trimmer.trim(sumField.getText());
	}
	
	private String getEnteredStandardSum() {
		return MyFormatter.formatNumberToStandard(getNotValidatedSum());
	}
	
	@Override
	protected void actionOnEscape() {
		AccountController.instance().removeListener(accountListener);
		super.actionOnEscape();
	}	
}
