package com.monyrama.ui.dialogs;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import com.monyrama.controller.AccountController;
import com.monyrama.controller.ControllerListener;
import com.monyrama.controller.DebtController;
import com.monyrama.entity.DBConstants;
import com.monyrama.entity.PAccount;
import com.monyrama.entity.PDebt;
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
import com.monyrama.ui.utils.MyDialogs;
import com.monyrama.ui.utils.MyFormatter;
import com.monyrama.utils.Trimmer;
import com.monyrama.validator.BorrowValidator;
import com.monyrama.validator.EntityValidator;


/**
 * @author Petro_Verheles
 */
public class DebtDialog extends EscapeDialog {
    
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
    private DebtDialog() {
        super();
        setTitle(Resources.getString("dialogs.titles.newdebt"));
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        final GridBagLayout gridBagLayout = new GridBagLayout();
        getContentPane().setLayout(gridBagLayout);
        GridBagConstraints gridBagConstraints;
        int gridy = 0;

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(10, 10, 10, 0);
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.gridx = 0;
        getContentPane().add(ComponentsHelper.createAstericsLabel(), gridBagConstraints);

        final JLabel nameLabel = new JLabel(Resources.getString("labels.lendername") + ":");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(10, 0, 10, 0);
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.gridx = 1;
        getContentPane().add(nameLabel, gridBagConstraints);

        nameField = new JTextFieldLimited(DBConstants.NAME_LENGTH);
        nameField.setEditable(true);
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
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.gridx = 0;
        getContentPane().add(ComponentsHelper.createAstericsLabel(), gridBagConstraints);

        final JLabel accountLabel = new JLabel(Resources.getString("labels.toaccount") + ":");
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
        gridBagConstraints.insets = new Insets(0, 5, 10, 0);
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
        final GridBagConstraints gridBagConstraints222 = new GridBagConstraints();
        gridBagConstraints222.gridx = 1;
        gridBagConstraints222.gridy = gridy;
        gridBagConstraints222.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints222.insets = new java.awt.Insets(0, 0, 10, 0);
        getContentPane().add(dateLabel, gridBagConstraints222);

        JCalendar jcalUntil = new JCalendar();
        jcalUntil.setWeekOfYearVisible(false);

        dateChooser = new JDateChooser(jcalUntil);
        dateChooser.setPreferredSize(DimensionConstants.STD_FIELD_DIMENSION);
        dateChooser.setLocale(Resources.getLocale());
        final GridBagConstraints gridBagConstraints333 = new java.awt.GridBagConstraints();
        gridBagConstraints333.gridx = 2;
        gridBagConstraints333.gridy = gridy;
        gridBagConstraints333.insets = new java.awt.Insets(0, 5, 10, 0);
        gridBagConstraints333.anchor = java.awt.GridBagConstraints.WEST;
        getContentPane().add(dateChooser, gridBagConstraints333);

        gridy++;
        final JLabel commentsLabel = new JLabel();
        commentsLabel.setText(Resources.getString("labels.comments") + ":");
        final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
        gridBagConstraints_2.insets = new Insets(0, 0, 10, 0);
        gridBagConstraints_2.anchor = GridBagConstraints.WEST;
        gridBagConstraints_2.gridy = gridy;
        gridBagConstraints_2.gridx = 1;
        getContentPane().add(commentsLabel, gridBagConstraints_2);

        commentsField = new JTextFieldLimited(DBConstants.COMMENTS_LENGTH);
        commentsField.setPreferredSize(DimensionConstants.STD_FIELD_DIMENSION);
        final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
        gridBagConstraints_3.insets = new Insets(0, 5, 10, 10);
        gridBagConstraints_3.anchor = GridBagConstraints.WEST;
        gridBagConstraints_3.gridy = gridy;
        gridBagConstraints_3.gridx = 2;
        getContentPane().add(commentsField, gridBagConstraints_3);

        gridy++;
        JPanel explainPanel = new ExplainPanel();
        final GridBagConstraints gridBagConstraints_14 = new GridBagConstraints();
        gridBagConstraints_14.insets = new Insets(0, 10, 0, 0);
        gridBagConstraints_14.anchor = GridBagConstraints.WEST;
        gridBagConstraints_14.gridwidth = 3;
        gridBagConstraints_14.gridy = gridy;
        gridBagConstraints_14.gridx = 0;
        getContentPane().add(explainPanel, gridBagConstraints_14);

        gridy++;
        JSeparator sep = new JSeparator(JSeparator.HORIZONTAL);
        final GridBagConstraints gridBagConstraints_15 = new GridBagConstraints();
        gridBagConstraints_15.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints_15.insets = new Insets(0, 0, 10, 0);
        gridBagConstraints_15.gridwidth = 3;
        gridBagConstraints_15.gridy = gridy;
        gridBagConstraints_15.gridx = 0;
        getContentPane().add(sep, gridBagConstraints_15);

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
				AccountDialog.openNewDialog();
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
        
        accountBox.addItemListener(new ItemListener() {			
			@Override
			public void itemStateChanged(ItemEvent ev) {
				if(ev.getStateChange() == ItemEvent.SELECTED) {
					currencyValueLabel.setText(getSelectedAccount().getCurrency().toString());
				}
			}
		});
        
        pack();
    }

    /**
     * Opens New Debt Dialog
     *
     */
    public static void openDialog() {
    	DebtDialog dialog = new DebtDialog();
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
        PDebt debt = new PDebt();
        debt.setName(Trimmer.trim(nameField.getText()));
        debt.setAccount(getSelectedAccount());
        debt.setSumStr(getEnteredStandardSum());
        debt.setComment(getComments());
        debt.setLastChangeDate(getSelectedDate());
        
    	DebtController.instance().create(debt);
    }

	protected PAccount getSelectedAccount() {
		return (PAccount)accountBox.getSelectedItem();
	}
    
	private void handleOkPressed() {
		EntityValidator validator = new BorrowValidator(Trimmer.trim(nameField.getText()), getNotValidatedSum(), getSelectedAccount());
    	
        if (validator.validate()) {
        	handleMoneyTransfer();
            actionOnEscape();
        } else {
        	MyDialogs.showWarningDialog(this, validator.message());
        }
	}
	
	@Override
	protected void actionOnEscape() {
		AccountController.instance().removeListener(accountListener);
		super.actionOnEscape();
	}

	private String getComments() {
		return commentsField.getText();
	}

	private String getNotValidatedSum() {
		return Trimmer.trim(sumField.getText());
	}
	
	private String getEnteredStandardSum() {
		return MyFormatter.formatNumberToStandard(getNotValidatedSum());
	}

	private Date getSelectedDate() {
		return dateChooser.getDate();
	}
}