package com.monyrama.ui.dialogs;

import com.monyrama.controller.AccountController;
import com.monyrama.controller.ControllerListener;
import com.monyrama.entity.*;
import com.monyrama.importer.ExpensesImporter;
import com.monyrama.preferences.MyPreferences;
import com.monyrama.preferences.PrefKeys;
import com.monyrama.ui.components.*;
import com.monyrama.ui.constants.DimensionConstants;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.utils.MyDialogs;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;


/**
 * Import expenses dialog
 *
 * @author Petro_Verheles
 */
public class ImportExpensesDialog extends EscapeDialog {
    protected ComboBombo<PAccount> accountBox;
    private JLabel expensePlanValueLabel;
    private TwoButtonsPanel buttonPanel;

    protected PExpensePlan expensePlan;
    private ControllerListener<PAccount> accountListener;


    /**
     * Private constructor. Our dialog will be opened by static public method
     */
    protected ImportExpensesDialog() {
        setModal(true);
        setTitle(Resources.getString("buttons.import"));

        JPanel contentPanel = new JPanel();

        GridBagConstraints gridBagConstraints;
        int gridy = 0;

        JLabel expensePlanLabel = new JLabel();
        expensePlanValueLabel = new JLabel();

        contentPanel.setLayout(new java.awt.GridBagLayout());

        expensePlanLabel.setText(Resources.getString("labels.budget") + ":");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(10, 0, 10, 0);
        contentPanel.add(expensePlanLabel, gridBagConstraints);

        expensePlanValueLabel.setPreferredSize(DimensionConstants.STD_FIELD_DIMENSION);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(10, 10, 10, 10);
        contentPanel.add(expensePlanValueLabel, gridBagConstraints);

        gridy++;
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(0, 10, 10, 0);
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.gridx = 0;
        contentPanel.add(ComponentsHelper.createAstericsLabel(), gridBagConstraints);

        JLabel accountLabel = new JLabel(Resources.getString("labels.fromaccount") + ":");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(0, 0, 10, 0);
        contentPanel.add(accountLabel, gridBagConstraints);

        accountBox = new ComboBombo<PAccount>();
        accountBox.setPreferredSize(DimensionConstants.STD_FIELD_DIMENSION);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(0, 10, 10, 10);
        contentPanel.add(accountBox, gridBagConstraints);

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
        buttonPanel.setApproveButtonText(Resources.getString("buttons.selectfile"));
        buttonPanel.setApproveButtonIcon(Resources.getIcon("find.png"));

        gridy++;
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.insets = new Insets(0, 0, 10, 0);
        contentPanel.add(buttonPanel, gridBagConstraints);

        setContentPane(contentPanel);

        addListeners();

        pack();
    }

    @Override
    protected void actionOnEscape() {
        AccountController.instance().removeListener(accountListener);
        super.actionOnEscape();
    }

    /**
     * Adds listeners to components
     */
    private void addListeners() {

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
            public void deleted(PAccount object) {
            }
        };
        AccountController.instance().addListener(accountListener);

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
    }

    private void handleOk() {

        PAccount selectedAccount = accountBox.getSelectedItem();
        if (selectedAccount.getAccountBank() == null) {
            MyDialogs.showWarningDialog(ImportExpensesDialog.this, "Unknown account bank");
        }

        JFileChooser fileChooser = new JFileChooser();
            fileChooser.addChoosableFileFilter(new FileFilter() {

                @Override
                public boolean accept(File f) {
                    return f.isDirectory() || f.getName().endsWith(".xls");
                }

                @Override
                public String getDescription() {
                    return "PrivatBank statement";
                }
            });
            fileChooser.setAcceptAllFileFilterUsed(false);
            int option = fileChooser.showOpenDialog(ImportExpensesDialog.this);
            if (option == JFileChooser.APPROVE_OPTION) {
                ImportExpensesDialog.this.dispose();
                File file = fileChooser.getSelectedFile();

                ExpensesImporter expensesImporter = new ExpensesImporter();
                expensesImporter
                    .importExpenses(ImportExpensesDialog.this, expensePlan, selectedAccount, file);
            }
    }

    /**
     * Opens New Expense dialog
     *
     * @param expensePlan the budget in which we add the expense
     */
    public static void openDialog(PExpensePlan expensePlan) {
        ImportExpensesDialog dialog = new ImportExpensesDialog();
        dialog.expensePlan = expensePlan;
        dialog.expensePlanValueLabel.setText(expensePlan.getName());
        dialog.updateAccounts(expensePlan.getCurrency());
        dialog.getRootPane().setDefaultButton(dialog.buttonPanel.getApproveButton());
        dialog.showIt();
    }

    protected void updateAccounts(PCurrency currency) {
        accountBox.removeAllItems();
        List<PAccount> activeAccounts = AccountController.instance().listActiveByCurrency(currency);
        for (PAccount account : activeAccounts) {
            accountBox.addItem(account);
        }

        Long lastExpenseAccountId = MyPreferences.getLong(PrefKeys.LAST_EXPENSE_ACCOUNT_ID, -1L);
        for (PAccount account : activeAccounts) {
            if (account.getId().equals(lastExpenseAccountId)) {
                accountBox.setSelectedItem(account);
                break;
            }
        }
    }

    private PAccount getSelectedAccount() {
        return (PAccount) accountBox.getSelectedItem();
    }

}
