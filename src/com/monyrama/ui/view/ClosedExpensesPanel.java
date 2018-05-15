package com.monyrama.ui.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import com.monyrama.controller.ControllerListener;
import com.monyrama.controller.ExpensePlanController;
import com.monyrama.db.enumarations.EntityStates;
import com.monyrama.entity.PExpense;
import com.monyrama.entity.PExpensePlan;
import com.monyrama.preferences.MyPreferences;
import com.monyrama.preferences.PrefKeys;
import com.monyrama.sorter.NammableSorter;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.tables.columns.ExpenseColumnEnum;
import com.monyrama.ui.utils.MyDialogs;


/**
 * Represents Closed Expenses panel
 *
 * @author Petro_Verheles
 */
public class ClosedExpensesPanel extends AbstractExpensePanel {

    private Action reopenExpensePlanAction;

    public ClosedExpensesPanel() {
        super();
        
        addExpensePlanListener();
    }

    @Override
	public void initOnVisible() {
    	super.initOnVisible();
        if(getSelectedExpensePlan() != null) {
        	enableActions();
        }

        ExpensesRowCellRenderer cellRenderer = new ExpensesRowCellRenderer();
        for (int i = 0; i < ExpenseColumnEnum.values().length; i++) {
            expensesTable.getColumn(ExpenseColumnEnum.values()[i].getName()).setCellRenderer(cellRenderer);
        }
	}
    
	@Override
	protected JPanel getButtonsPanel() {
        JPanel buttonPanel = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        buttonPanel.setLayout(gridBagLayout);

        JButton reopenButton = new JButton(reopenExpensePlanAction);
        reopenButton.setPreferredSize(BUTTON_DIMENSION);
        reopenButton.setMinimumSize(BUTTON_DIMENSION);
        reopenButton.setHorizontalAlignment(JButton.LEFT);
        final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
        gridBagConstraints_2.gridy = 0;
        gridBagConstraints_2.gridx = 0;
        buttonPanel.add(reopenButton, gridBagConstraints_2);
        
        final GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        gridBagConstraints.insets = new Insets(6, 5, 0, 5);
        return buttonPanel;
	}
    //

    
    private class ReopenBudgetAction extends AbstractAction {
        public ReopenBudgetAction() {
            super(Resources.getString("buttons.reopen") + "...", Resources.getIcon("reopen.png"));
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            int result = MyDialogs.showYesNoDialog(ClosedExpensesPanel.this, Resources.getString("dialogs.questions.reopenbudget") + "?");
            if (result == MyDialogs.YES) {
                PExpensePlan budget = getSelectedExpensePlan();
                budget.setState(EntityStates.ACTIVE.getCode());
                ExpensePlanController.instance().createOrUpdate(budget);
            }
        }
    }    

    public void createActions() {        
        reopenExpensePlanAction = new ReopenBudgetAction();
        disableActions();
    }

    private void addExpensePlanListener() {
    	ExpensePlanController.instance().addListener(new ControllerListener<PExpensePlan>() {
			
			@Override
			public void deleted(PExpensePlan budget) {
	               nameBox.removeItem(budget);
	                if (nameBox.getItemCount() == 0) {
	                    cleanBudgetFields();
	                    disableActions();
	                }
	                enableOrDisableChartsButton();
			}
			
			@Override
			public void createdOrUpdated(PExpensePlan budget) {
                if(budget.getState().equals(EntityStates.CLOSED.getCode())) {
                    nameBox.addItem(budget);
                    enableActions();
                } else if(budget.getState().equals(EntityStates.ACTIVE.getCode())) {
                	if(nameBox.getItemCount() != 0 && getSelectedExpensePlan().equals(budget)) {
                		nameBox.removeItem(budget);
                		checkIfThoughOneBudgetExists();
                	}
                }
                                
                enableOrDisableChartsButton();
			}
		});
    }

	@Override
	protected void loadExpensesPlans() {
		nameBox.removeItemListener(nameBoxItemListener);
        List<PExpensePlan> activeExpensePlans = ExpensePlanController.instance().listClosed();        
        NammableSorter.sort(activeExpensePlans);
        for(PExpensePlan expensePlan : activeExpensePlans) {
        	nameBox.addItem(expensePlan);
        }		
        nameBox.addItemListener(nameBoxItemListener);
	}
	
	private void checkIfThoughOneBudgetExists() {
		if (nameBox.getItemCount() == 0) {
		    cleanBudgetFields();
		    disableActions();
		} else {
			enableActions();
		}
	}
	
	private void enableActions() {		
		reopenExpensePlanAction.setEnabled(true);
	}	
	
	private void disableActions() {
		reopenExpensePlanAction.setEnabled(false);
	}
	
	public void saveStateParams() {		
		MyPreferences.save(PrefKeys.CLOSED_EXPENSES_CHART_INDEX, chartsButton.getSelectedIndex());
	}
	
	@Override
	protected void restoreState() {
		int selectedChartIndex = MyPreferences.getInteger(PrefKeys.CLOSED_EXPENSES_CHART_INDEX, 0);
		chartsButton.setSelectedIndex(selectedChartIndex);		
	}

    private class ExpensesRowCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table,
                                                       Object value, boolean isSelected, boolean hasFocus, int row,
                                                       int column) {
            Long id = (Long) table.getValueAt(row, ExpenseColumnEnum.ID.getIndex());
            PExpense expense = expensesTableModel.getItemById(id);

            if(column == ExpenseColumnEnum.DATE.getIndex()) {
                value = formatDate(expense.getLastChangeDate());
            }

            Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            return comp;
        }
    }
}
	