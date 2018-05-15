package com.monyrama.ui.dialogs;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import com.monyrama.controller.ControllerListener;
import com.monyrama.controller.SavedExpenseCommentController;
import com.monyrama.entity.DBConstants;
import com.monyrama.entity.PCurrency;
import com.monyrama.entity.PExpense;
import com.monyrama.entity.PExpensePlanItem;
import com.monyrama.preferences.MyPreferences;
import com.monyrama.preferences.PrefKeys;
import com.monyrama.ui.components.*;
import com.monyrama.ui.resources.Resources;
import com.monyrama.controller.AccountController;
import com.monyrama.controller.ExpensePlanItemController;
import com.monyrama.entity.PAccount;
import com.monyrama.entity.PExpensePlan;
import com.monyrama.ui.constants.DimensionConstants;
import com.monyrama.ui.utils.MyFormatter;


/**
 * New / Edit expense dialog
 *
 * @author Petro_Verheles
 */
public abstract class ExpenseDialog extends EscapeDialog {
    private ComboBombo<PExpensePlanItem> expensePlanItemBox;
    private JLabel categoryValueLabel;
    protected ComboBombo<PAccount> accountBox;
    protected AutosuggestTextFieldLimited commentField;
    protected SumFieldWithCalc priceField;
    private JLabel expensePlanValueLabel;
    protected JDateChooser dateChooser;

    private TwoButtonsPanel buttonPanel;

    protected PExpense expense;
    protected PExpensePlan expensePlan;
	private ControllerListener<PExpensePlanItem> expensePlanItemListener;
	private ControllerListener<PAccount> accountListener;


    /**
     * Private constructor. Our dialog will be opened by static public method
     */
    protected ExpenseDialog() {
        setModal(true);

        JPanel contentPanel = new JPanel();

        java.awt.GridBagConstraints gridBagConstraints;
        int gridy = 0;

        javax.swing.JLabel expensePlanLabel = new javax.swing.JLabel();
        javax.swing.JLabel categoryLabel = new javax.swing.JLabel();
        javax.swing.JLabel expensePlanItemLabel = new javax.swing.JLabel();
        javax.swing.JLabel priceLabel = new javax.swing.JLabel();
        javax.swing.JLabel dateLabel = new javax.swing.JLabel();
        javax.swing.JLabel commentLabel = new javax.swing.JLabel();
        expensePlanItemBox = new ComboBombo<PExpensePlanItem>();
        priceField = new SumFieldWithCalc(DBConstants.SUM_LENGTH, Resources.getString("tools.calculator"), Resources.getString("calculator.errorMsg"), Resources.getString("calculator.insertTooltip"), !Resources.isSumDotSeparated());
        commentField = new AutosuggestTextFieldLimited(DBConstants.COMMENTS_LENGTH);
        expensePlanValueLabel = new javax.swing.JLabel();
        categoryValueLabel = new JLabel();

        contentPanel.setLayout(new java.awt.GridBagLayout());

        expensePlanLabel.setText(Resources.getString("labels.budget") + ":");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        contentPanel.add(expensePlanLabel, gridBagConstraints);
        
        expensePlanValueLabel.setPreferredSize(DimensionConstants.STD_FIELD_DIMENSION);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        contentPanel.add(expensePlanValueLabel, gridBagConstraints);  
        
        gridy++;
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 10, 10, 0);
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridx = 0;
		contentPanel.add(ComponentsHelper.createAstericsLabel(), gridBagConstraints);
        
        JLabel accountLabel = new JLabel(Resources.getString("labels.fromaccount") + ":");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        contentPanel.add(accountLabel, gridBagConstraints);
        
        accountBox = new ComboBombo<PAccount>();
        accountBox.setPreferredSize(DimensionConstants.STD_FIELD_DIMENSION);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 10);
        contentPanel.add(accountBox, gridBagConstraints);        

        gridy++;
        categoryLabel.setText(Resources.getString("labels.category") + ":");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        contentPanel.add(categoryLabel, gridBagConstraints);

        categoryValueLabel.setPreferredSize(DimensionConstants.STD_FIELD_DIMENSION);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 10);
        contentPanel.add(categoryValueLabel, gridBagConstraints);
        
        gridy++;
        JLabel astericsLabel = ComponentsHelper.createAstericsLabel();
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(0, 10, 10, 0);
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.gridx = 0;
        contentPanel.add(astericsLabel, gridBagConstraints);

        expensePlanItemLabel.setText(Resources.getString("labels.item") + ":");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        contentPanel.add(expensePlanItemLabel, gridBagConstraints);
       
        expensePlanItemBox.setPreferredSize(DimensionConstants.STD_FIELD_DIMENSION);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 10);
        contentPanel.add(expensePlanItemBox, gridBagConstraints);
        
        gridy++;
        JLabel astericsLabel1 = ComponentsHelper.createAstericsLabel();
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(0, 10, 10, 0);
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.gridx = 0;
        contentPanel.add(astericsLabel1, gridBagConstraints);
        
        priceLabel.setText(Resources.getString("labels.price") + ":");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        contentPanel.add(priceLabel, gridBagConstraints);

        priceField.setPreferredSize(DimensionConstants.STD_FIELD_DIMENSION);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 10);
        contentPanel.add(priceField, gridBagConstraints);
        
        gridy++;
        JLabel astericsLabel2 = ComponentsHelper.createAstericsLabel();
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(0, 10, 10, 0);
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.gridx = 0;
        contentPanel.add(astericsLabel2, gridBagConstraints);

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
        commentLabel.setText(Resources.getString("labels.comments") + ":");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        contentPanel.add(commentLabel, gridBagConstraints);
        
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
    	ExpensePlanItemController.instance().removeListener(expensePlanItemListener);
    	AccountController.instance().removeListener(accountListener);
        super.actionOnEscape();
    }

    /**
     * Adds listeners to components
     */
    private void addListeners() {
    	expensePlanItemBox.addPlusButtonListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				ExpensePlanItemDialog.openNewItemDialog(expensePlan);
			}
		});
    	
    	expensePlanItemListener = new ControllerListener<PExpensePlanItem>() {			
			@Override
			public void deleted(PExpensePlanItem object) {}
			
			@Override
			public void createdOrUpdated(PExpensePlanItem item) {
				updateItems();
				expensePlanItemBox.setSelectedItem(item);
			}
		};
		ExpensePlanItemController.instance().addListener(expensePlanItemListener);
    	
    	accountBox.addPlusButtonListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				AccountDialog.openNewDialog(expensePlan.getCurrency());
			}
		});
    	
    	accountListener = new ControllerListener<PAccount>() {

			@Override
			public void createdOrUpdated(PAccount account) {
				updateAccounts(expensePlan.getCurrency());
				accountBox.setSelectedItem(account);
			}

			@Override
			public void deleted(PAccount object) {}
		};
		AccountController.instance().addListener(accountListener);
    	
        expensePlanItemBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent paramItemEvent) {
                if (paramItemEvent.getStateChange() == ItemEvent.SELECTED) {
                    PExpensePlanItem item = getSelectedItem();
                    expensePlanValueLabel.setText(item.getExpensePlan().getName());
                    categoryValueLabel.setText(item.getCategory().getName());
                }
            }
        });

        ActionListener approveListener = new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                handleOk();
            }
        };
        buttonPanel.setApproveListener(approveListener);

        buttonPanel.setCancelListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionOnEscape();
            }
        });

        commentField.setSuggestionProvider(createCommentSuggestionProvider());
        commentField.setEnterKeyListener(approveListener);
    }

    /**
     * Opens New Expense dialog
     *
     * @param expensePlan the budget in which we add the expense
     */
    public static void openNewDialog(PExpensePlan expensePlan) {
        ExpenseDialog newDialog = new NewExpenseDialog();
        newDialog.expensePlan = expensePlan;
        newDialog.updateItems();
        newDialog.updateAccounts(expensePlan.getCurrency());
        newDialog.expensePlanItemBox.requestFocus();
        newDialog.getRootPane().setDefaultButton(newDialog.buttonPanel.getApproveButton());
        newDialog.showIt();
    }

    /**
     * Opens the Edit Expense Dialog
     *
     * @param expensePlan  budget in which we add the expense
     * @param expense the expense to edit
     */
    public static void openEditDialog(PExpensePlan expensePlan, PExpense expense) {
        ExpenseDialog editDialog = new EditExpenseDialog();
        editDialog.expensePlan = expensePlan;
        editDialog.expense = expense;
        editDialog.updateItems();
        editDialog.updateAccounts(expensePlan.getCurrency());
        editDialog.setFields();
        editDialog.expensePlanItemBox.requestFocus();
        editDialog.getRootPane().setDefaultButton(editDialog.buttonPanel.getApproveButton());
        editDialog.showIt();
    }

    /**
     * Sets the fields for the edited expense
     */
    private void setFields() {
    	accountBox.setSelectedItem(expense.getAccount());
        expensePlanItemBox.setSelectedItem(expense.getExpensePlanItem());
        dateChooser.setDate(expense.getLastChangeDate());
        priceField.setText(MyFormatter.formatNumberToLocal(expense.getSumm().toPlainString()));
        commentField.setText(expense.getComment());
    }

    /**
     * Updates budget items box
     */
    private void updateItems() {
    	expensePlanItemBox.removeAllItems();
        List<PExpensePlanItem> openedEnvelopes = ExpensePlanItemController.instance().listOpenedByExpensePlan(expensePlan);
        for (PExpensePlanItem envelope : openedEnvelopes) {
            expensePlanItemBox.addItem(envelope);
        }

        Long lastExpenseEnvelopeId = MyPreferences.getLong(PrefKeys.LAST_EXPENSE_ENVELOPE_ID, -1L);
        for (PExpensePlanItem envelope : openedEnvelopes) {
            if(envelope.getId().equals(lastExpenseEnvelopeId)) {
                expensePlanItemBox.setSelectedItem(envelope);
            }
        }
    }

	protected void updateAccounts(PCurrency currency) {
		accountBox.removeAllItems();
        List<PAccount> activeAccounts = AccountController.instance().listActiveByCurrency(currency);
		for(PAccount account : activeAccounts) {
        	accountBox.addItem(account);
        }

        Long lastExpenseAccountId = MyPreferences.getLong(PrefKeys.LAST_EXPENSE_ACCOUNT_ID, -1L);
        for(PAccount account : activeAccounts) {
            if(account.getId().equals(lastExpenseAccountId)) {
                accountBox.setSelectedItem(account);
                break;
            }
        }
	}

    private AutosuggestTextFieldLimited.SuggestionProvider createCommentSuggestionProvider() {
        return new AutosuggestTextFieldLimited.SuggestionProvider() {
            @Override
            public List<String> getValues(String text) {
                ArrayList<String> values = new ArrayList<String>();

                if(text != null && text.trim().length() > 0) {
                    values.addAll(SavedExpenseCommentController.instance().findComments(text.trim()));
                }

                return values;
            }
        };
    }

    protected PExpensePlanItem getSelectedItem() {
        return expensePlanItemBox.getSelectedItem();
    }
    
    protected PAccount getSelectedAccount() {
        return (PAccount) accountBox.getSelectedItem();
    }    

    protected abstract void handleOk();
}
