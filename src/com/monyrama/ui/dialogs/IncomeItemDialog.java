package com.monyrama.ui.dialogs;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import com.monyrama.controller.AccountController;
import com.monyrama.controller.ControllerListener;
import com.monyrama.entity.DBConstants;
import com.monyrama.entity.PAccount;
import com.monyrama.entity.PIncome;
import com.monyrama.entity.PIncomeSource;
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


/**
 * New / Edit income item dialog
 *
 * @author Petro_Verheles
 */
public abstract class IncomeItemDialog extends EscapeDialog {
    protected JTextFieldLimited commentField;
    protected SumFieldWithCalc sumField;
    private JLabel incomeSourceValueLabel;
    protected JDateChooser dateChooser;
    protected ComboBombo<PAccount> accountBox;

    private TwoButtonsPanel buttonPanel;

    protected PIncome incomeItem;
    protected PIncomeSource incomeSource;


    /**
     * Private constructor. Our dialog will be opened by static public method
     */
    protected IncomeItemDialog() {
        setModal(true);

        JPanel contentPanel = new JPanel();

        java.awt.GridBagConstraints gridBagConstraints;
        int gridy = 0;

        contentPanel.setLayout(new java.awt.GridBagLayout());

        JLabel incomeSourceLabel = new javax.swing.JLabel();
        incomeSourceLabel.setText(Resources.getString("labels.incomes.source") + ":");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        contentPanel.add(incomeSourceLabel, gridBagConstraints);
        
        incomeSourceValueLabel = new javax.swing.JLabel();
        incomeSourceValueLabel.setPreferredSize(DimensionConstants.STD_FIELD_DIMENSION);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        contentPanel.add(incomeSourceValueLabel, gridBagConstraints);        

        gridy++;
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 10, 10, 0);
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 0;
		contentPanel.add(ComponentsHelper.createAstericsLabel(), gridBagConstraints);        
        
        JLabel accountLabel = new JLabel(Resources.getString("labels.toaccount") + ":");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        contentPanel.add(accountLabel, gridBagConstraints);
        
        accountBox = new ComboBombo<PAccount>();
        accountBox.addPlusButtonListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				AccountDialog.openNewDialog(incomeSource.getCurrency());
			}
		});
        accountBox.setPreferredSize(DimensionConstants.STD_FIELD_DIMENSION);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 10);
        contentPanel.add(accountBox, gridBagConstraints);        
                
        gridy++;
		JLabel astericsLabel = ComponentsHelper.createAstericsLabel();
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 10, 10, 0);
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 0;
		contentPanel.add(astericsLabel, gridBagConstraints);
        
        JLabel sumLabel = new javax.swing.JLabel();
        sumLabel.setText(Resources.getString("labels.sum") + ":");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        contentPanel.add(sumLabel, gridBagConstraints);
        
        sumField = new SumFieldWithCalc(DBConstants.SUM_LENGTH, Resources.getString("tools.calculator"), Resources.getString("calculator.errorMsg"), Resources.getString("calculator.insertTooltip"), !Resources.isSumDotSeparated());
        sumField.setPreferredSize(DimensionConstants.STD_FIELD_DIMENSION);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 10);
        contentPanel.add(sumField, gridBagConstraints);        

        gridy++;
        JLabel dateLabel = new javax.swing.JLabel();
        dateLabel.setText(Resources.getString("labels.date") + ":");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        contentPanel.add(dateLabel, gridBagConstraints);
        
        JCalendar jcalUntil = new JCalendar();
        jcalUntil.setWeekOfYearVisible(false);

        dateChooser = new JDateChooser(jcalUntil);
        dateChooser.setPreferredSize(DimensionConstants.STD_FIELD_DIMENSION);
        dateChooser.setLocale(Resources.getLocale());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 10);
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        contentPanel.add(dateChooser, gridBagConstraints);        

        gridy++;
        JLabel commentLabel = new javax.swing.JLabel();
        commentLabel.setText(Resources.getString("labels.comments") + ":");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        contentPanel.add(commentLabel, gridBagConstraints);

        commentField = new JTextFieldLimited(DBConstants.COMMENTS_LENGTH);
        commentField.setPreferredSize(DimensionConstants.STD_FIELD_DIMENSION);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 10);
        contentPanel.add(commentField, gridBagConstraints);

        gridy++;
        JPanel explainPanel = new ExplainPanel();
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(0, 10, 0, 0);
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.gridx = 0;
        contentPanel.add(explainPanel, gridBagConstraints);

        gridy++;
        JSeparator sep = new JSeparator(JSeparator.HORIZONTAL);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(0, 0, 10, 0);
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.gridx = 0;
        contentPanel.add(sep, gridBagConstraints);

        buttonPanel = new TwoButtonsPanel();

        gridy++;
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        contentPanel.add(buttonPanel, gridBagConstraints);

        setContentPane(contentPanel);

        addListeners();

        pack();
    }

    @Override
    protected void actionOnEscape() {
        incomeItem = null;
        setVisible(false);
    }

    /**
     * Adds listeners to components
     */
    private void addListeners() {
		AccountController.instance().addListener(new ControllerListener<PAccount>() {
			
			@Override
			public void deleted(PAccount object) {}
			
			@Override
			public void createdOrUpdated(PAccount newAccount) {
				updateAccountsBox();
				accountBox.setSelectedItem(newAccount);
			}
		});
    	
        buttonPanel.setApproveListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                handleOk();
            }
        });

        buttonPanel.setCancelListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionOnEscape();
            }
        });
    }

    public static void openNewDialog(PIncomeSource incomeSource) {
    	IncomeItemDialog newDialog = new NewIncomeItemDialog();
        newDialog.incomeSource = incomeSource;
        newDialog.incomeSourceValueLabel.setText(incomeSource.getName());
        newDialog.updateAccountsBox();
        newDialog.getRootPane().setDefaultButton(newDialog.buttonPanel.getApproveButton());
        newDialog.showIt();
    }

	private void updateAccountsBox() {
		accountBox.removeAllItems();
		List<PAccount> accounts = AccountController.instance().listActiveByCurrency(incomeSource.getCurrency());
		for(PAccount account : accounts) {
        	accountBox.addItem(account);
        }
	}

    public static void openEditDialog(PIncome incomeItem, PIncomeSource incomeSource) {
        IncomeItemDialog editDialog = new EditIncomeItemDialog();    
        editDialog.incomeItem = incomeItem;
        editDialog.incomeSource = incomeSource;
        editDialog.updateAccountsBox();        
        editDialog.setFieldsForEdit();
        editDialog.sumField.requestFocus();
        editDialog.getRootPane().setDefaultButton(editDialog.buttonPanel.getApproveButton());
        editDialog.showIt();
    }

    /**
     * Sets the fields for the edited income item
     */
    private void setFieldsForEdit() {
    	incomeSourceValueLabel.setText(incomeItem.getIncomeSource().getName());
    	accountBox.setSelectedItem(incomeItem.getAccount());
        dateChooser.setDate(incomeItem.getLastChangeDate());
        sumField.setText(MyFormatter.formatNumberToLocal(incomeItem.getSumm().toPlainString()));        
        commentField.setText(incomeItem.getComment());
    }

    /**
     * Clean the fields
     */
    private void cleanFields() {
        sumField.setText("");
        commentField.setText("");
        accountBox.removeAllItems();
    }

	protected PAccount getSelectedAccount() {
		return (PAccount)accountBox.getSelectedItem();
	}    
    
    protected abstract void handleOk();
}
