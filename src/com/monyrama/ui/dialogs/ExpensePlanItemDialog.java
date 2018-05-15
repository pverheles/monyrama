package com.monyrama.ui.dialogs;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import com.monyrama.controller.CategoryController;
import com.monyrama.controller.ControllerListener;
import com.monyrama.controller.ExpensePlanItemController;
import com.monyrama.entity.DBConstants;
import com.monyrama.entity.PCategory;
import com.monyrama.entity.PExpensePlan;
import com.monyrama.entity.PExpensePlanItem;
import com.monyrama.ui.components.ComboBombo;
import com.monyrama.ui.components.ComponentsHelper;
import com.monyrama.ui.components.ExplainPanel;
import com.monyrama.ui.components.JTextFieldLimited;
import com.monyrama.ui.components.SumFieldWithCalc;
import com.monyrama.ui.components.TwoButtonsPanel;
import com.monyrama.ui.constants.DimensionConstants;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.utils.MyFormatter;


/**
 * New / Edit Item dialog
 * 
 * @author Petro_Verheles
 *
 */
public abstract class ExpensePlanItemDialog extends EscapeDialog {
	private javax.swing.JLabel budgetValueLabel;
    protected ComboBombo<PCategory> categoryBox;
    protected SumFieldWithCalc sumField;
    protected javax.swing.JTextField commentField;
    protected javax.swing.JTextField nameField;
    private TwoButtonsPanel buttonPanel;
	
	protected PExpensePlanItem expensePlanItem;
	protected PExpensePlan expensePlan;
	private ControllerListener<PCategory> categoryListener;

    /**
     * Private constructor. Our dialog will be opened by static public method
     */
	protected ExpensePlanItemDialog() {
		setModal(true);
		
		java.awt.GridBagConstraints gridBagConstraints;

        javax.swing.JLabel budgetLabel = new javax.swing.JLabel();
        budgetValueLabel = new javax.swing.JLabel();
        javax.swing.JLabel categoryLabel = new javax.swing.JLabel();
        categoryBox = new ComboBombo<PCategory>();
        javax.swing.JLabel nameLabel = new javax.swing.JLabel();
        nameField = new JTextFieldLimited(DBConstants.NAME_LENGTH);
        javax.swing.JLabel sumLable = new javax.swing.JLabel();
        sumField = new SumFieldWithCalc(DBConstants.SUM_LENGTH, Resources.getString("tools.calculator"), Resources.getString("calculator.errorMsg"), Resources.getString("calculator.insertTooltip"), !Resources.isSumDotSeparated());
        javax.swing.JLabel comentLabel = new javax.swing.JLabel();
        commentField = new JTextFieldLimited(DBConstants.COMMENTS_LENGTH);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        budgetLabel.setText(Resources.getString("labels.budget") + ":");        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        getContentPane().add(budgetLabel, gridBagConstraints);
        
        budgetValueLabel.setPreferredSize(DimensionConstants.STD_FIELD_DIMENSION);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 10);
        getContentPane().add(budgetValueLabel, gridBagConstraints);

		JLabel astericsLabel = ComponentsHelper.createAstericsLabel();
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 10, 10, 0);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridx = 0;
		getContentPane().add(astericsLabel, gridBagConstraints);
        
        categoryLabel.setText(Resources.getString("labels.category") + ":");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        getContentPane().add(categoryLabel, gridBagConstraints);

        categoryBox.setPreferredSize(DimensionConstants.STD_FIELD_DIMENSION);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 10, 10);
        getContentPane().add(categoryBox, gridBagConstraints);

        nameLabel.setText(Resources.getString("labels.name") + ":");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        getContentPane().add(nameLabel, gridBagConstraints);

        nameField.setPreferredSize(DimensionConstants.STD_FIELD_DIMENSION);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 10, 10);
        getContentPane().add(nameField, gridBagConstraints);
        
		JLabel astericsLabel1 = ComponentsHelper.createAstericsLabel();
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 10, 10, 0);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridx = 0;
		getContentPane().add(astericsLabel1, gridBagConstraints);

        sumLable.setText(Resources.getString("labels.sum") + ":");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        getContentPane().add(sumLable, gridBagConstraints);

        sumField.setPreferredSize(DimensionConstants.STD_FIELD_DIMENSION);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 10, 10);
        getContentPane().add(sumField, gridBagConstraints);

        comentLabel.setText(Resources.getString("labels.comments") + ":");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        getContentPane().add(comentLabel, gridBagConstraints);

        commentField.setPreferredSize(DimensionConstants.STD_FIELD_DIMENSION);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 10, 10);
        getContentPane().add(commentField, gridBagConstraints);
        
		JPanel explainPanel = new ExplainPanel();
		final GridBagConstraints gridBagConstraints_14 = new GridBagConstraints();
		gridBagConstraints_14.insets = new Insets(0, 10, 0, 0);
		gridBagConstraints_14.anchor = GridBagConstraints.WEST;
		gridBagConstraints_14.gridwidth = 3;
		gridBagConstraints_14.gridy = 5;
		gridBagConstraints_14.gridx = 0;
		getContentPane().add(explainPanel, gridBagConstraints_14);		
		
		JSeparator sep = new JSeparator(JSeparator.HORIZONTAL);
		final GridBagConstraints gridBagConstraints_15 = new GridBagConstraints();
		gridBagConstraints_15.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_15.insets = new Insets(0, 0, 10, 0);
		gridBagConstraints_15.gridwidth = 3;
		gridBagConstraints_15.gridy = 6;
		gridBagConstraints_15.gridx = 0;
		getContentPane().add(sep, gridBagConstraints_15);
				
		buttonPanel = new TwoButtonsPanel();
        buttonPanel.setApproveListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				handleOk();
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
        gridBagConstraints_16.gridy = 7;
        gridBagConstraints_16.gridwidth = 3;
        gridBagConstraints_16.anchor = GridBagConstraints.CENTER;
        gridBagConstraints_16.insets = new java.awt.Insets(0, 0, 10, 0);
		getContentPane().add(buttonPanel, gridBagConstraints_16);        
        
		categoryBox.addPlusButtonListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				CategoryDialog.openNewDialog();
			}
		});
		
		categoryListener = new ControllerListener<PCategory>() {
			
			@Override
			public void deleted(PCategory object) {}
			
			@Override
			public void createdOrUpdated(PCategory category) {
				updateCategoriesBox();
				categoryBox.setSelectedItem(category);
			}
		};
		CategoryController.instance().addListener(categoryListener);
		
        pack();
	}
	
	/**
	 * Updates categories box
	 */
	private void updateCategoriesBox() {
		categoryBox.removeAllItems();
        List<PCategory> sortedCategories = CategoryController.instance().listActive();
		for(PCategory cat : sortedCategories) {
			categoryBox.addItem(cat);
		}
	}
	
	/**
	 * Sets fields of Edit Dialog
	 */
	private void setFields() {
		budgetValueLabel.setText(expensePlan.getName());
		nameField.setText(expensePlanItem.getName());
		categoryBox.setSelectedItem(expensePlanItem.getCategory());
		sumField.setText(MyFormatter.formatNumberToLocal(expensePlanItem.getSumm().toPlainString()));
		commentField.setText(expensePlanItem.getComment());
	}
	
	protected PCategory getSelectedCategory() {
		return (PCategory)categoryBox.getSelectedItem();
	}

	public static void openNewItemDialog(PExpensePlan expensePlan) {
		ExpensePlanItemDialog newDialog = new NewExpensePlanItemDialog();
		
		newDialog.expensePlan = expensePlan;
		
		newDialog.budgetValueLabel.setText(expensePlan.getName());
		newDialog.updateCategoriesBox();
		newDialog.nameField.requestFocus();
		newDialog.getRootPane().setDefaultButton(newDialog.buttonPanel.getApproveButton());
		newDialog.showIt();
	}

	/**
	 * Opens Edit Item Dialog
	 * @param expensePlan selected budget
	 * @param editiedItem editiedItem
	 */
	public static void openEditItemDialog (PExpensePlan expensePlan, PExpensePlanItem editiedItem) {
		ExpensePlanItemDialog editDialog = new EditExpensePlanItemDialog();
		editDialog.expensePlanItem = editiedItem;
		editDialog.expensePlan = expensePlan;
		editDialog.updateCategoriesBox();
		editDialog.setFields();
		
		editDialog.nameField.requestFocus();
		editDialog.getRootPane().setDefaultButton(editDialog.buttonPanel.getApproveButton());
		editDialog.showIt();
	}
	
	protected abstract void handleOk();
	
	protected void tryToCreateOrUpdateItem() {
		ExpensePlanItemController.instance().createOrUpdate(expensePlanItem);
	}

	@Override
	protected void actionOnEscape() {
		CategoryController.instance().removeListener(categoryListener);
		super.actionOnEscape();
	}
	
	
}
