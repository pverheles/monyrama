package com.monyrama.ui.dialogs;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import com.monyrama.controller.ControllerListener;
import com.monyrama.controller.CurrencyController;
import com.monyrama.controller.IncomeSourceController;
import com.monyrama.entity.DBConstants;
import com.monyrama.entity.PCurrency;
import com.monyrama.entity.PIncomeSource;
import com.monyrama.sorter.NammableSorter;
import com.monyrama.ui.components.ComboBombo;
import com.monyrama.ui.components.ComponentsHelper;
import com.monyrama.ui.components.ExplainPanel;
import com.monyrama.ui.components.JTextFieldLimited;
import com.monyrama.ui.components.TwoButtonsPanel;
import com.monyrama.ui.constants.DimensionConstants;
import com.monyrama.ui.resources.Resources;


/**
 * New / Edit IncomeSource dialog
 * 
 * @author Petro_Verheles
 *
 */
public abstract class IncomeSourceDialog extends EscapeDialog {
	protected JTextFieldLimited nameField;
	protected ComboBombo<PCurrency> currencyBox;
	protected JTextFieldLimited commentsField;
	private TwoButtonsPanel buttonPanel;

	protected PIncomeSource incomeSource;
	private ControllerListener<PCurrency> currencyListener;

	
	/**
	 * Constructor
	 * 
	 * Paints the dialog
	 *  
	 */
	protected IncomeSourceDialog() {
		super();			
	
		final GridBagLayout gridBagLayout = new GridBagLayout();
		getContentPane().setLayout(gridBagLayout);

		JLabel astericsLabel = ComponentsHelper.createAstericsLabel();
		final GridBagConstraints gridBagConstraints_0 = new GridBagConstraints();
		gridBagConstraints_0.insets = new Insets(10, 10, 10, 0);
		gridBagConstraints_0.anchor = GridBagConstraints.WEST;
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
		nameField.setPreferredSize(DimensionConstants.STD_FIELD_DIMENSION);
		final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
		//gridBagConstraints_1.anchor = GridBagConstraints.NORTH;
		gridBagConstraints_1.insets = new Insets(10, 5, 10, 10);
		gridBagConstraints_1.gridy = 0;
		gridBagConstraints_1.gridx = 2;
		getContentPane().add(nameField, gridBagConstraints_1);

		JLabel astericsLabel1 = ComponentsHelper.createAstericsLabel();
		final GridBagConstraints gridBagConstraints_10 = new GridBagConstraints();
		gridBagConstraints_10.insets = new Insets(0, 10, 10, 0);
		//gridBagConstraints_10.anchor = GridBagConstraints.WEST;
		gridBagConstraints_10.gridy = 1;
		gridBagConstraints_10.gridx = 0;
		getContentPane().add(astericsLabel1, gridBagConstraints_10);		
		
		final JLabel currencyLabel = new JLabel();
		currencyLabel.setText(Resources.getString("labels.currency") + ":");
		final GridBagConstraints gridBagConstraints_6 = new GridBagConstraints();
		gridBagConstraints_6.insets = new Insets(0, 0, 10, 0);		
		gridBagConstraints_6.anchor = GridBagConstraints.WEST;
		gridBagConstraints_6.gridy = 1;
		gridBagConstraints_6.gridx = 1;
		getContentPane().add(currencyLabel, gridBagConstraints_6);
		
		currencyBox = new ComboBombo<PCurrency>();
		currencyBox.addPlusButtonListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				CurrencyDialog.openNewDialog();
			}
		});
		currencyBox.setPreferredSize(DimensionConstants.STD_FIELD_DIMENSION);
		final GridBagConstraints gridBagConstraints_5 = new GridBagConstraints();
		gridBagConstraints_5.insets = new Insets(0, 5, 10, 10);
		gridBagConstraints_5.anchor = GridBagConstraints.WEST;
		gridBagConstraints_5.gridy = 1;
		gridBagConstraints_5.gridx = 2;
		getContentPane().add(currencyBox, gridBagConstraints_5);

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
		
		currencyListener = new ControllerListener<PCurrency>() {
			
			@Override
			public void deleted(PCurrency currency) {}
			
			@Override
			public void createdOrUpdated(PCurrency currency) {
				updateCurrencyBox();
				currencyBox.setSelectedItem(currency);
			}
		};
		CurrencyController.instance().addListener(currencyListener);
		
		pack();
		setModal(true);
	}

	/**
	 * Opens New IncomeSource Dialog
	 */
	public static void openNewDialog() {
		IncomeSourceDialog newDialog = new NewIncomeSourceDialog();
		newDialog.updateCurrencyBox();
		newDialog.nameField.requestFocus();
		newDialog.getRootPane().setDefaultButton(newDialog.buttonPanel.getApproveButton());
		newDialog.showIt();
	}

	/**
	 * Opens Edit IncomeSource Dialog
	 * @param incomeSourceToEdit
	 */
	public static void openEditDialog(final PIncomeSource incomeSourceToEdit, boolean editCurrency) {
		IncomeSourceDialog editDialog = new EditIncomeSourceDialog();
		editDialog.incomeSource = incomeSourceToEdit;
		editDialog.currencyBox.setEnabled(editCurrency);
		editDialog.updateCurrencyBox();		
		editDialog.setFields();										
		editDialog.nameField.requestFocus();
		editDialog.getRootPane().setDefaultButton(editDialog.buttonPanel.getApproveButton());		
		editDialog.showIt();
	}
	
	/**
	 * Sets fields of the Edit Dialog
	 */
	private void setFields() {
		nameField.setText(incomeSource.getName());
		currencyBox.setSelectedItem(incomeSource.getCurrency());
		commentsField.setText(incomeSource.getComment());
	}

	/**
	 * Updates currency box
	 */
	private void updateCurrencyBox() {
		currencyBox.removeAllItems();
        List<PCurrency> sortedCurrencies = new ArrayList<PCurrency>(CurrencyController.instance().getAll());
        NammableSorter.sort(sortedCurrencies);
		for(PCurrency currency : sortedCurrencies) {
			currencyBox.addItem(currency);
		}	
	}
	
	protected abstract void handleOkPresses();
	
	protected void save() {
		IncomeSourceController.instance().createOrUpdate(incomeSource);
	}
	
	protected Collection<PIncomeSource> allIncomeSources() {
		return IncomeSourceController.instance().getAll();
	}

	@Override
	protected void actionOnEscape() {
		CurrencyController.instance().removeListener(currencyListener);
		super.actionOnEscape();
	}
}
