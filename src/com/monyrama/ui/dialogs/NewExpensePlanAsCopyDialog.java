package com.monyrama.ui.dialogs;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Collection;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import com.monyrama.controller.ExpensePlanController;
import com.monyrama.db.enumarations.EntityStates;
import com.monyrama.entity.DBConstants;
import com.monyrama.entity.PExpensePlan;
import com.monyrama.ui.components.ExplainPanel;
import com.monyrama.ui.components.JTextFieldLimited;
import com.monyrama.ui.components.TwoButtonsPanel;
import com.monyrama.ui.components.UniqueSortedComboboxModel;
import com.monyrama.ui.constants.DimensionConstants;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.utils.MyDialogs;
import com.monyrama.utils.Trimmer;
import com.monyrama.validator.EntityValidator;
import com.monyrama.validator.NewExpensePlanValidator;

public class NewExpensePlanAsCopyDialog extends EscapeDialog {
	protected JTextFieldLimited nameField;
	protected JComboBox expensePlanBox;
	protected JTextFieldLimited commentsField;
	private TwoButtonsPanel buttonPanel;
	
	private static NewExpensePlanAsCopyDialog dialog;
	
	protected NewExpensePlanAsCopyDialog() {
		super();
		setTitle(Resources.getString("dialogs.titles.newexpenseplanascopy"));
		
		final GridBagLayout gridBagLayout = new GridBagLayout();
		getContentPane().setLayout(gridBagLayout);

		JLabel astericsLabel = new JLabel("*");
		final GridBagConstraints gridBagConstraints_0 = new GridBagConstraints();
		gridBagConstraints_0.insets = new Insets(10, 10, 10, 0);
		gridBagConstraints_0.anchor = GridBagConstraints.WEST;
		gridBagConstraints_0.gridy = 0;
		gridBagConstraints_0.gridx = 0;
		getContentPane().add(astericsLabel, gridBagConstraints_0);

		final JLabel budgetLabel = new JLabel();
		budgetLabel.setText(Resources.getString("labels.copyof") + ":");
		final GridBagConstraints gridBagConstraints_6 = new GridBagConstraints();
		gridBagConstraints_6.insets = new Insets(10, 0, 10, 0);		
		gridBagConstraints_6.anchor = GridBagConstraints.WEST;
		gridBagConstraints_6.gridy = 0;
		gridBagConstraints_6.gridx = 1;
		getContentPane().add(budgetLabel, gridBagConstraints_6);
		
		expensePlanBox = new JComboBox(new UniqueSortedComboboxModel<PExpensePlan>());
		expensePlanBox.setPreferredSize(DimensionConstants.STD_FIELD_DIMENSION);
		final GridBagConstraints gridBagConstraints_5 = new GridBagConstraints();
		gridBagConstraints_5.insets = new Insets(10, 5, 10, 10);
		gridBagConstraints_5.anchor = GridBagConstraints.WEST;
		gridBagConstraints_5.gridy = 0;
		gridBagConstraints_5.gridx = 2;
		getContentPane().add(expensePlanBox, gridBagConstraints_5);
		expensePlanBox.addItemListener(new ItemListener() {			
			@Override
			public void itemStateChanged(ItemEvent event) {
				if(event.getStateChange() == ItemEvent.SELECTED) {
					changeCommentValue();
				}
			}
		});		

		JLabel astericsLabel1 = new JLabel("*");
		final GridBagConstraints gridBagConstraints_10 = new GridBagConstraints();
		gridBagConstraints_10.insets = new Insets(0, 10, 10, 0);
		//gridBagConstraints_10.anchor = GridBagConstraints.WEST;
		gridBagConstraints_10.gridy = 1;
		gridBagConstraints_10.gridx = 0;
		getContentPane().add(astericsLabel1, gridBagConstraints_10);
				
		final JLabel nameLabel = new JLabel();
		nameLabel.setText(Resources.getString("labels.name") + ":");
		final GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 10, 0);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridx = 1;
		getContentPane().add(nameLabel, gridBagConstraints);

		nameField = new JTextFieldLimited(DBConstants.NAME_LENGTH);
		nameField.setPreferredSize(DimensionConstants.STD_FIELD_DIMENSION);
		final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
		//gridBagConstraints_1.anchor = GridBagConstraints.NORTH;
		gridBagConstraints_1.insets = new Insets(0, 5, 10, 10);
		gridBagConstraints_1.gridy = 1;
		gridBagConstraints_1.gridx = 2;
		getContentPane().add(nameField, gridBagConstraints_1);

		final JLabel commentsLabel = new JLabel();
		commentsLabel.setText(Resources.getString("labels.comments") + ":");
		final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
		gridBagConstraints_2.insets = new Insets(0, 0, 10, 0);
		gridBagConstraints_2.anchor = GridBagConstraints.WEST;
		gridBagConstraints_2.gridy = 3;
		gridBagConstraints_2.gridx = 1;
		getContentPane().add(commentsLabel, gridBagConstraints_2);

		commentsField = new JTextFieldLimited(DBConstants.COMMENTS_LENGTH);
		commentsField.setPreferredSize(DimensionConstants.STD_FIELD_DIMENSION);
		final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
		gridBagConstraints_3.insets = new Insets(0, 0, 10, 10);
		gridBagConstraints_3.anchor = GridBagConstraints.NORTHEAST;
		gridBagConstraints_3.gridy = 3;
		gridBagConstraints_3.gridx = 2;
		getContentPane().add(commentsField, gridBagConstraints_3);
							
		JPanel explainPanel = new ExplainPanel();
		final GridBagConstraints gridBagConstraints_14 = new GridBagConstraints();
		gridBagConstraints_14.insets = new Insets(0, 10, 0, 0);
		gridBagConstraints_14.anchor = GridBagConstraints.WEST;
		gridBagConstraints_14.gridwidth = 3;
		gridBagConstraints_14.gridy = 4;
		gridBagConstraints_14.gridx = 0;
		getContentPane().add(explainPanel, gridBagConstraints_14);		
		
		JSeparator sep = new JSeparator(JSeparator.HORIZONTAL);
		final GridBagConstraints gridBagConstraints_15 = new GridBagConstraints();
		gridBagConstraints_15.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_15.insets = new Insets(0, 0, 10, 0);
		gridBagConstraints_15.gridwidth = 3;
		gridBagConstraints_15.gridy = 5;
		gridBagConstraints_15.gridx = 0;
		getContentPane().add(sep, gridBagConstraints_15);		
						
		buttonPanel = new TwoButtonsPanel();
        buttonPanel.setApproveListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				handleOkPresses();
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
        gridBagConstraints_16.gridy = 6;
        gridBagConstraints_16.gridwidth = 3;
        gridBagConstraints_16.anchor = GridBagConstraints.CENTER;
        gridBagConstraints_16.insets = new java.awt.Insets(0, 0, 10, 0);
		getContentPane().add(buttonPanel, gridBagConstraints_16);
		
		pack();
		setModal(true);
	}

	public static void openDialog() {
		NewExpensePlanAsCopyDialog dialog = new NewExpensePlanAsCopyDialog();	
		dialog.fillExpensePlanBox();
		dialog.changeCommentValue();
		dialog.getRootPane().setDefaultButton(dialog.buttonPanel.getApproveButton());
		dialog.showIt();
	}
	
	protected void handleOkPresses() {
		PExpensePlan newBudget = new PExpensePlan();
		newBudget.setName(Trimmer.trim(nameField.getText()));
		newBudget.setState(EntityStates.ACTIVE.getCode());
		newBudget.setCurrency(getSelectedBudget().getCurrency());
		newBudget.setComment(commentsField.getText());

		EntityValidator validator = new NewExpensePlanValidator(allBudgets(), newBudget);
		
		if(validator.validate()) {
			ExpensePlanController.instance().createAsCopy(newBudget, getSelectedBudget());
			setVisible(false);
		} else {
			MyDialogs.showWarningDialog(this, validator.message());
		}
		
	}
	
	private void fillExpensePlanBox() {
		for(PExpensePlan budget : ExpensePlanController.instance().getAll()) {
			expensePlanBox.addItem(budget);
		}
		expensePlanBox.setSelectedIndex(0);
	}
	
	private PExpensePlan getSelectedBudget() {
		return (PExpensePlan)expensePlanBox.getSelectedItem();
	}

	private void changeCommentValue() {
		commentsField.setText(getSelectedBudget().getComment());
	}
	
	protected Collection<PExpensePlan> allBudgets() {
		return ExpensePlanController.instance().getAll();
	}	
}
