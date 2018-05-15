package com.monyrama.ui.dialogs;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import com.monyrama.controller.CategoryController;
import com.monyrama.entity.DBConstants;
import com.monyrama.entity.PCategory;
import com.monyrama.ui.components.ExplainPanel;
import com.monyrama.ui.components.JTextFieldLimited;
import com.monyrama.ui.components.TwoButtonsPanel;
import com.monyrama.ui.resources.Resources;


/**
 * New / Edit Category dialog
 * 
 * @author Petro_Verheles
 *
 */
public abstract class CategoryDialog extends EscapeDialog {
	protected JTextFieldLimited commentsField;
	protected JTextFieldLimited nameField;	
		
	protected PCategory category;
	
	private static CategoryDialog newDialog;
	private static CategoryDialog editDialog;
	
	private TwoButtonsPanel buttonPanel;

    /**
     * Private constructor. Our dialog will be opened by static public method
     */
	protected CategoryDialog() {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		final GridBagLayout gridBagLayout = new GridBagLayout();		
		getContentPane().setLayout(gridBagLayout);

		JLabel astericsLabel = new JLabel("*");
		final GridBagConstraints gridBagConstraints_0 = new GridBagConstraints();
		gridBagConstraints_0.insets = new Insets(10, 10, 10, 0);
		//gridBagConstraints_0.anchor = GridBagConstraints.WEST;
		gridBagConstraints_0.gridy = 0;
		gridBagConstraints_0.gridx = 0;
		getContentPane().add(astericsLabel, gridBagConstraints_0);		
		
		final JLabel nameLabel = new JLabel();
		nameLabel.setText(Resources.getString("labels.name") + ":");
		final GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 0, 10, 0);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridx = 1;
		getContentPane().add(nameLabel, gridBagConstraints);

		nameField = new JTextFieldLimited(DBConstants.NAME_LENGTH);
		nameField.setPreferredSize(new Dimension(200, 20));
		final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
		gridBagConstraints_1.anchor = GridBagConstraints.WEST;
		gridBagConstraints_1.insets = new Insets(10, 5, 10, 10);
		gridBagConstraints_1.gridy = 0;
		gridBagConstraints_1.gridx = 2;
		getContentPane().add(nameField, gridBagConstraints_1);

		final JLabel commentsLabel = new JLabel();
		commentsLabel.setText(Resources.getString("labels.comments") + ":");
		final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
		gridBagConstraints_2.insets = new Insets(0, 0, 10, 0);
		gridBagConstraints_2.anchor = GridBagConstraints.WEST;
		gridBagConstraints_2.gridy = 1;
		gridBagConstraints_2.gridx = 1;
		getContentPane().add(commentsLabel, gridBagConstraints_2);

		commentsField = new JTextFieldLimited(DBConstants.COMMENTS_LENGTH);
		commentsField.setPreferredSize(new Dimension(200, 20));
		final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
		gridBagConstraints_3.insets = new Insets(0, 5, 10, 0);
		gridBagConstraints_3.anchor = GridBagConstraints.WEST;
		gridBagConstraints_3.gridy = 1;
		gridBagConstraints_3.gridx = 2;
		getContentPane().add(commentsField, gridBagConstraints_3);

		JPanel explainPanel = new ExplainPanel();
		final GridBagConstraints gridBagConstraints_4 = new GridBagConstraints();
		gridBagConstraints_4.insets = new Insets(0, 10, 0, 0);
		gridBagConstraints_4.anchor = GridBagConstraints.WEST;
		gridBagConstraints_4.gridwidth = 3;
		gridBagConstraints_4.gridy = 2;
		gridBagConstraints_4.gridx = 0;
		getContentPane().add(explainPanel, gridBagConstraints_4);		
		
		JSeparator sep = new JSeparator(JSeparator.HORIZONTAL);
		final GridBagConstraints gridBagConstraints_5 = new GridBagConstraints();
		gridBagConstraints_5.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_5.insets = new Insets(0, 0, 10, 0);
		gridBagConstraints_5.gridwidth = 3;
		gridBagConstraints_5.gridy = 3;
		gridBagConstraints_5.gridx = 0;
		getContentPane().add(sep, gridBagConstraints_5);
				
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
        
        final GridBagConstraints gridBagConstraints_6 = new java.awt.GridBagConstraints();
		gridBagConstraints_6.gridx = 0;
		gridBagConstraints_6.gridy = 4;
		gridBagConstraints_6.gridwidth = 3;
		gridBagConstraints_6.anchor = GridBagConstraints.CENTER;
		gridBagConstraints_6.insets = new java.awt.Insets(0, 0, 10, 0);
		getContentPane().add(buttonPanel, gridBagConstraints_6);
        		
		setModal(true);
		setResizable(false);
		
		pack();
	}

	/**
	 * Opens New Category dialog
	 */
	public static void openNewDialog() {
		if(newDialog == null) {
			newDialog = new NewCategoryDialog();
		}
		
		newDialog.cleanFields();
		newDialog.nameField.requestFocus();
		newDialog.getRootPane().setDefaultButton(newDialog.buttonPanel.getApproveButton());
		newDialog.showIt();
	}
	
	private void cleanFields() {
		nameField.setText("");
		commentsField.setText("");		
	}

	/**
	 * Opens Edit Category dialog
	 * 
	 * @param category edited category
	 */
	public static void openEditDialog(PCategory category) {
		if(editDialog == null) {
			editDialog = new EditCategoryDialog();
		}
		
		editDialog.category = category;
		editDialog.setFields();
		editDialog.nameField.requestFocus();
		editDialog.getRootPane().setDefaultButton(editDialog.buttonPanel.getApproveButton());
		editDialog.showIt();
	}

	/**
	 * Set fields with the values of edited category
	 */
	private void setFields() {
		nameField.setText(category.getName());
		commentsField.setText(category.getComment());
	}
	
	@Override
	protected void actionOnEscape() {
		setVisible(false);
	}
	
	protected abstract void handleOkPressed(); 

}
