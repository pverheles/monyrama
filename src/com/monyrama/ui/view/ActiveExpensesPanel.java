package com.monyrama.ui.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableCellRenderer;

import com.monyrama.controller.ControllerListener;
import com.monyrama.controller.DBConditions;
import com.monyrama.controller.ExpenseController;
import com.monyrama.controller.ExpensePlanController;
import com.monyrama.controller.ExpensePlanItemController;
import com.monyrama.db.enumarations.EntityStates;
import com.monyrama.entity.PExpense;
import com.monyrama.entity.PExpensePlan;
import com.monyrama.entity.PExpensePlanItem;
import com.monyrama.importer.PrivateBankTemporaryImporter;
import com.monyrama.preferences.MyPreferences;
import com.monyrama.preferences.PrefKeys;
import com.monyrama.server.MobileDataListener;
import com.monyrama.server.MobileDataManager;
import com.monyrama.sorter.NammableSorter;
import com.monyrama.ui.components.CheckboxCell;
import com.monyrama.ui.constants.ColorConstants;
import com.monyrama.ui.dialogs.ExpenseDialog;
import com.monyrama.ui.dialogs.ExpensePlanDialog;
import com.monyrama.ui.dialogs.ExpensePlanItemDialog;
import com.monyrama.ui.dialogs.NewExpensePlanAsCopyDialog;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.tables.columns.ExpenseColumnEnum;
import com.monyrama.ui.tables.columns.ExpensePlanItemColumnEnum;
import com.monyrama.ui.utils.MyDialogs;
import com.monyrama.utils.Calc;
import com.monyrama.validator.util.StringSumValidator;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * Represents Active Expenses panel
 *
 * @author Petro_Verheles
 */
public class ActiveExpensesPanel extends AbstractExpensePanel {

    //Declare actions
    private Action newExpensePlanAction;
    private Action removeExpensePlanAction;
    private Action editExpensePlanAction;
    private Action newExpensePlanAsCopyAction;    
    private Action prepareToCloseAction;
    private Action closeExpensePlanAction;

    private Action addItemAction;
    private Action editItemAction;
    private Action removeItemAction;
    private Action blockItemAction;
    private Action unblockItemAction;
    private Action equalItemAction;

    private Action addExpenseAction;
    private Action editExpenseAction;
    private Action removeExpenseAction;

    private Action importExpensesAction;

    private JPopupMenu popupMenuItems;
    private JPopupMenu popupMenuExpenses;

    public ActiveExpensesPanel() {
        super();        
        addExpensePlanListeners();
    }
    
	@Override
	protected JPanel getButtonsPanel() {
        JPanel buttonPanel = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        buttonPanel.setLayout(gridBagLayout);

        GridBagConstraints gridBagConstraints;
        
        JButton editButton = new JButton(editExpensePlanAction);
        editButton.setPreferredSize(BUTTON_DIMENSION);
        editButton.setMinimumSize(BUTTON_DIMENSION);
        editButton.setHorizontalAlignment(JButton.LEFT);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        buttonPanel.add(editButton, gridBagConstraints);        
        
        JButton prepareToCloseButton = new JButton(prepareToCloseAction);
        prepareToCloseButton.setPreferredSize(BUTTON_DIMENSION);
        prepareToCloseButton.setMinimumSize(BUTTON_DIMENSION);
        prepareToCloseButton.setHorizontalAlignment(JButton.LEFT);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        buttonPanel.add(prepareToCloseButton, gridBagConstraints); 
        
        JButton closeButton = new JButton(closeExpensePlanAction);
        closeButton.setPreferredSize(BUTTON_DIMENSION);
        closeButton.setMinimumSize(BUTTON_DIMENSION);
        closeButton.setHorizontalAlignment(JButton.LEFT);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        buttonPanel.add(closeButton, gridBagConstraints);   
        
        JButton removeButton = new JButton(removeExpensePlanAction);
        removeButton.setPreferredSize(BUTTON_DIMENSION);
        removeButton.setMinimumSize(BUTTON_DIMENSION);
        removeButton.setHorizontalAlignment(JButton.LEFT);
        final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
        gridBagConstraints_1.gridy = 3;
        gridBagConstraints_1.gridx = 0;
        buttonPanel.add(removeButton, gridBagConstraints_1);        
        
        JSeparator seJSeparator = new JSeparator(JSeparator.VERTICAL);
//        Dimension sepDimension = new Dimension(1, 50);
//        seJSeparator.setPreferredSize(sepDimension);
//        seJSeparator.setMinimumSize(sepDimension);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.insets = new Insets(0, 3, 0, 3);
        gridBagConstraints.fill = GridBagConstraints.VERTICAL;
        //gridBagConstraints.anchor = GridBagConstraints.NORTH;
        buttonPanel.add(seJSeparator, gridBagConstraints);
                
        JButton newButton = new JButton(newExpensePlanAction);
        newButton.setPreferredSize(BUTTON_DIMENSION);
        newButton.setMinimumSize(BUTTON_DIMENSION);
        newButton.setHorizontalAlignment(JButton.LEFT);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridx = 2;
        buttonPanel.add(newButton, gridBagConstraints);
        
        JButton newAsCopyButton = new JButton(newExpensePlanAsCopyAction);
        newAsCopyButton.setPreferredSize(BUTTON_DIMENSION);
        newAsCopyButton.setMinimumSize(BUTTON_DIMENSION);
        newAsCopyButton.setHorizontalAlignment(JButton.LEFT);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridx = 2;
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        buttonPanel.add(newAsCopyButton, gridBagConstraints);              
        
        return buttonPanel;
	}

	@Override
    protected JComponent getItemsPanel() {
        JPanel centralPanel = (JPanel) super.getItemsPanel();

        final JPanel buttonPanel = new JPanel();
        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        buttonPanel.setLayout(flowLayout);

        JButton addItemButton = new JButton(addItemAction);
        buttonPanel.add(addItemButton);

        JButton editItemButton = new JButton(editItemAction);
        buttonPanel.add(editItemButton);

        JButton removeItemButton = new JButton(removeItemAction);
        buttonPanel.add(removeItemButton);

        JButton blockItemButton = new JButton(blockItemAction);
        buttonPanel.add(blockItemButton);

        JButton unblockItemButton = new JButton(unblockItemAction);
        buttonPanel.add(unblockItemButton);

        JButton equalItemButton = new JButton(equalItemAction);
        buttonPanel.add(equalItemButton);

        centralPanel.add(buttonPanel, BorderLayout.NORTH);

        ItemsRowCellRenderer cellRenderer = new ItemsRowCellRenderer();
        for (int i = 0; i < ExpensePlanItemColumnEnum.values().length; i++) {
            itemsTable.getColumn(ExpensePlanItemColumnEnum.values()[i].getName()).setCellRenderer(cellRenderer);
        }

        return centralPanel;
    }

    @Override
    protected JComponent getExpensesPanel() {
        JPanel expensesPanel = (JPanel) super.getExpensesPanel();

        final JPanel buttonPanel = new JPanel();
        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        buttonPanel.setLayout(flowLayout);

        JButton addExpenseButton = new JButton(addExpenseAction);
        buttonPanel.add(addExpenseButton);

        JButton editExpenseButton = new JButton(editExpenseAction);
        buttonPanel.add(editExpenseButton);

        JButton removeExpenseButton = new JButton(removeExpenseAction);
        buttonPanel.add(removeExpenseButton);

        JSeparator separator = new JSeparator(JSeparator.VERTICAL);
        Dimension separatorDimension = new Dimension(1, removeExpenseButton.getPreferredSize().height);
        separator.setPreferredSize(separatorDimension);
        separator.setMinimumSize(separatorDimension);
        buttonPanel.add(separator);

        JButton importExpensesButton = new JButton(importExpensesAction);
        buttonPanel.add(importExpensesButton);

        expensesPanel.add(buttonPanel, BorderLayout.NORTH);

        ExpensesRowCellRenderer cellRenderer = new ExpensesRowCellRenderer();
        for (int i = 0; i < ExpenseColumnEnum.values().length; i++) {
            expensesTable.getColumn(ExpenseColumnEnum.values()[i].getName()).setCellRenderer(cellRenderer);
        }

        return expensesPanel;
    }

    @Override
    protected void addListeners() {
        super.addListeners();
        
		nameBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ev) {
				if(ev.getStateChange() == ItemEvent.SELECTED) {
					enableOrDisableAddExpenses();
					
					updateRemoveExpensePlanActionState(); 
				}				
			}
		});

        expensesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = expensesTable.getSelectedRow();
                if (selectedRow == -1) {
                    editExpenseAction.setEnabled(false);
                    removeExpenseAction.setEnabled(false);
                } else {
                    Long id = (Long) expensesTable.getValueAt(selectedRow, ExpenseColumnEnum.ID.getIndex());
                    PExpense su = expensesTableModel.getItemById(id);
                    if (su.getExpensePlanItem().getState().equals(EntityStates.CLOSED.getCode())) {
                        editExpenseAction.setEnabled(false);
                        removeExpenseAction.setEnabled(false);
                    } else {
                        editExpenseAction.setEnabled(true);
                        removeExpenseAction.setEnabled(true);
                    }

                }
            }
        });

        itemsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = itemsTable.getSelectedRow();
                if (row == -1) {
                    editItemAction.setEnabled(false);
                    removeItemAction.setEnabled(false);
                    blockItemAction.setEnabled(false);
                    unblockItemAction.setEnabled(false);
                    equalItemAction.setEnabled(false);
                } else {
                    Long id = (Long) itemsTable.getValueAt(row, ExpensePlanItemColumnEnum.ID.getIndex());

                    PExpensePlanItem bu = itemsTableModel.getItemById(id);

                    //We cannot delete budget units that have some spent units (expenses)
                    //List<PSpentUnit> spentUnits = SpentUnitController.instance().listByBudgetUnit(bu);
                    Set<PExpense> spentUnits = expensesTableModel.getSpentUnitsByBudgetUnit(bu);
                    if (spentUnits.size() == 0) {
                        removeItemAction.setEnabled(true);
                    } else {
                        removeItemAction.setEnabled(false);
                    }

                    if (bu.getState().equals(EntityStates.CLOSED.getCode())) {
                        blockItemAction.setEnabled(false);
                        editItemAction.setEnabled(false);
                        equalItemAction.setEnabled(false);
                        unblockItemAction.setEnabled(true);
                    } else {
                        blockItemAction.setEnabled(true);
                        editItemAction.setEnabled(true);
                        equalItemAction.setEnabled(true);
                        unblockItemAction.setEnabled(false);
                    }
                }
            }
        });
    }
    
    @Override
	public void initOnVisible() {
		super.initOnVisible();		
        checkIfThoughOneBudgetExists();		
		enableOrDisableAddExpenses();		
		updateNewAsCopyActionState();		
	}



	/**
     * Cell renderer for Items table
     *
     * @author Petro_Verheles
     */
    private class ItemsRowCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(final JTable table,
                                                       Object value, boolean isSelected, boolean hasFocus, int row,
                                                       int column) {

            Component comp;
            
            if(column == ExpensePlanItemColumnEnum.BLOCKED.getIndex()) {
            	CheckboxCell checkbox = new CheckboxCell();
            	checkbox.setSelected((Boolean)value);
            	checkbox.setEnabled(false);
                if(isSelected) {
                	checkbox.setBackground(ColorConstants.SELECTED_BACKGROUND);
                }
            	comp = checkbox;
            } else {
            	comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }

            if (isSelected == true) {
                return comp;
            }

            Long id = (Long) table.getValueAt(row, ExpensePlanItemColumnEnum.ID.getIndex());

            final PExpensePlanItem bu = itemsTableModel.getItemById(id);

            //List<PSpentUnit> spentUnitSet = SpentUnitController.instance().listByBudgetUnit(bu);
            Set<PExpense> spentUnits = expensesTableModel.getSpentUnitsByBudgetUnit(bu);
		            if (spentUnits.size() != 0) {
		                BigDecimal spentUnitsSum = new BigDecimal("0");
		                for (PExpense su : spentUnits) {
		                    spentUnitsSum = spentUnitsSum.add(su.getSumm());
		                }

		                BigDecimal buSumm = bu.getSumm();

		                if (buSumm.compareTo(spentUnitsSum) < 0) {
		                    comp.setBackground(ColorConstants.BE_ATTANTIVE_COLOR);
		                    return comp;
		                }
		            } else {
		                comp.setBackground(table.getBackground());
		            }

		            if (bu.getState().equals(EntityStates.CLOSED.getCode())) {
		                comp.setBackground(ColorConstants.BLOCKED_COLOR);
		                return comp;
		            } else {
		                comp.setBackground(table.getBackground());
		            }

		            if (spentUnits.size() != 0) {
		                comp.setBackground(ColorConstants.USED);
		            } else {
		                comp.setBackground(table.getBackground());
		            }

					return comp;           
        }
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

            if (isSelected) {
                return comp;
            }

            if (expense.getExpensePlanItem().getState().equals(EntityStates.CLOSED.getCode())) {
                comp.setBackground(ColorConstants.BLOCKED_COLOR);
            } else {
                comp.setBackground(table.getBackground());
            }

            return comp;
        }
    }

    /**
     * Creates Actions and Sets them to elements
     */
    @Override
    public void createActions() {
        //-------------- Create Actions ---------------
        newExpensePlanAction = new NewExpensePlanAction();
        editExpensePlanAction = new EditExpensePlanAction();
        newExpensePlanAsCopyAction = new NewExpensePlanAsCopyAction();
        prepareToCloseAction = new PrepareToCloseAction();
        closeExpensePlanAction = new CloseExpensePlanAction();
        removeExpensePlanAction = new RemoveExpensePlanAction();

        addItemAction = new AddItemAction();
        editItemAction = new EditItemAction();
        removeItemAction = new RemoveItemAction();
        blockItemAction = new BlockItemAction();
        unblockItemAction = new UnblockItemAction();
        equalItemAction = new EqualItemAction();

        addExpenseAction = new AddExpenseAction();
        editExpenseAction = new EditExpenseAction();
        removeExpenseAction = new RemoveExpenseAction();
        importExpensesAction = new ImportExpensesAction();

        //------- Disable some actions in the beginning ----------
        editItemAction.setEnabled(false);
        removeItemAction.setEnabled(false);
        blockItemAction.setEnabled(false);
        unblockItemAction.setEnabled(false);
        equalItemAction.setEnabled(false);
        editExpenseAction.setEnabled(false);
        removeExpenseAction.setEnabled(false);
    }


    /**
     * Creates the popup menu for the table and scrollpane
     */
    @Override
    public void createPopUp() {
        //Create items table popup
        popupMenuItems = new JPopupMenu();
        popupMenuItems.add(new JMenuItem(addItemAction));
        popupMenuItems.add(new JMenuItem(editItemAction));
        popupMenuItems.add(new JMenuItem(removeItemAction));
        popupMenuItems.add(new JMenuItem(blockItemAction));
        popupMenuItems.add(new JMenuItem(unblockItemAction));
        popupMenuItems.add(new JMenuItem(equalItemAction));

        MouseAdapter itemsMouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                maybeShowPopup(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                maybeShowPopup(e);
            }

            private void maybeShowPopup(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        Point p = e.getPoint();
                        int rowNumber = itemsTable.rowAtPoint(p);
                        ListSelectionModel model = itemsTable.getSelectionModel();
                        model.setSelectionInterval(rowNumber, rowNumber);
                    }
                    popupMenuItems.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        };

        itemsTable.addMouseListener(itemsMouseAdapter);
        itemsPane.addMouseListener(itemsMouseAdapter);

        //Create expenses table popup
        //Create items table popup
        popupMenuExpenses = new JPopupMenu();
        popupMenuExpenses.add(new JMenuItem(addExpenseAction));
        popupMenuExpenses.add(new JMenuItem(editExpenseAction));
        popupMenuExpenses.add(new JMenuItem(removeExpenseAction));
        popupMenuExpenses.addSeparator();
        popupMenuExpenses.add(new JMenuItem(importExpensesAction));

        MouseAdapter expensesMouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                maybeShowPopup(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                maybeShowPopup(e);
            }

            private void maybeShowPopup(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        Point p = e.getPoint();
                        int rowNumber = expensesTable.rowAtPoint(p);
                        ListSelectionModel model = expensesTable.getSelectionModel();
                        model.setSelectionInterval(rowNumber, rowNumber);
                    }
                    popupMenuExpenses.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        };

        expensesTable.addMouseListener(expensesMouseAdapter);
        expensesPane.addMouseListener(expensesMouseAdapter);
    }


    //------------------------- Actions ----------------------------------------
    //

    private class NewExpensePlanAction extends AbstractAction {
        public NewExpensePlanAction() {
            super(Resources.getString("buttons.new") + "...", Resources.getIcon("newbudget.png"));
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            ExpensePlanDialog.openNewDialog();
        }
    }

    //
    private class EditExpensePlanAction extends AbstractAction {
        public EditExpensePlanAction() {
            super(Resources.getString("buttons.edit") + "...", Resources.getIcon("edit_budget.png"));
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            ExpensePlanDialog.openEditDialog(getSelectedExpensePlan(), expensesTableModel.getRowCount() == 0);
        }
    }
    
    private class NewExpensePlanAsCopyAction extends AbstractAction {
        public NewExpensePlanAsCopyAction() {
            super(Resources.getString("buttons.newascopy") + "...", Resources.getIcon("copy-small.png"));
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            NewExpensePlanAsCopyDialog.openDialog();
        }
    }    

    //

    private class PrepareToCloseAction extends AbstractAction {
        public PrepareToCloseAction() {
            super(Resources.getString("buttons.preparetoclose"), Resources.getIcon("preparetoclose.png"));
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            int choose = MyDialogs.showYesNoDialog(ActiveExpensesPanel.this, Resources.getString("dialogs.info.preparetoclose") + "?");
            if (choose == MyDialogs.YES) {
            	List<PExpensePlanItem> budgetUnits = ExpensePlanItemController.instance().listByExpensePlan(getSelectedExpensePlan());
                for (PExpensePlanItem item : new ArrayList<PExpensePlanItem>(budgetUnits)) {
                    BigDecimal summ = Calc.sum(expensesTableModel.getSpentUnitsByBudgetUnit(item));
                    item.setSumm(summ);
                    item.setState(EntityStates.CLOSED.getCode());
                    ExpensePlanItemController.instance().createOrUpdate(item);
                }
            }
        }
    }

    //

    private class CloseExpensePlanAction extends AbstractAction {
        public CloseExpensePlanAction() {
            super(Resources.getString("buttons.close"), Resources.getIcon("close_budget.png"));
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            String balance = balanceValueLabel.getText();
            boolean isAllItemsBlocked = true;
            List<PExpensePlanItem> budgetUnits = ExpensePlanItemController.instance().listByExpensePlan(getSelectedExpensePlan());
			for (PExpensePlanItem item : budgetUnits) {
				if (item.getState().equals(EntityStates.ACTIVE.getCode())) {
					isAllItemsBlocked = false;
					break;
				}
			}

            if (!(StringSumValidator.isZero(balance) && isAllItemsBlocked)) {
                MyDialogs.showWarningDialog(ActiveExpensesPanel.this, Resources.getString("dialogs.warnings.notpreparedtoclose"));
                return;
            }

            int decision = MyDialogs.showYesNoDialog(ActiveExpensesPanel.this, Resources.getString("dialogs.questions.closebudget") + "?");
            if (decision == MyDialogs.YES) {
                PExpensePlan budget = getSelectedExpensePlan();
                budget.setState(EntityStates.CLOSED.getCode());
                ExpensePlanController.instance().createOrUpdate(budget);
            }
        }
    }
    
    private class RemoveExpensePlanAction extends AbstractAction {
        public RemoveExpensePlanAction() {
            super(Resources.getString("buttons.remove") + "...", Resources.getIcon("removebudget.png"));
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            int result = MyDialogs.showYesNoDialog(ActiveExpensesPanel.this, Resources.getString("dialogs.questions.removebudget") + "?");
            if (result == MyDialogs.YES) {
                ExpensePlanController.instance().delete(getSelectedExpensePlan());
            }
        }
    }    

    //

    private class AddItemAction extends AbstractAction {
        public AddItemAction() {
            super(Resources.getString("buttons.add") + "...",
                    Resources.getIcon("add.png"));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
        	ExpensePlanItemDialog.openNewItemDialog(getSelectedExpensePlan());
        }
    }

    //

    private class EditItemAction extends AbstractAction {
        public EditItemAction() {
            super(Resources.getString("buttons.edit") + "...",
                    Resources.getIcon("edit.png"));
        }

        @Override
        public void actionPerformed(ActionEvent paramActionEvent) {
            int row = itemsTable.getSelectedRow();
            Long id = (Long) itemsTable.getValueAt(row, ExpensePlanItemColumnEnum.ID.getIndex());
            PExpensePlanItem item = itemsTableModel.getItemById(id);
            ExpensePlanItemDialog.openEditItemDialog(getSelectedExpensePlan(), item);
        }
    }

    //

    private class RemoveItemAction extends AbstractAction {
        public RemoveItemAction() {
            super(Resources.getString("buttons.remove"),
                   Resources.getIcon("remove.png"));
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            int result = MyDialogs.showYesNoDialog(ActiveExpensesPanel.this, Resources.getString("dialogs.questions.removeitem") + "?");
            if (result == MyDialogs.YES) {
                int row = itemsTable.getSelectedRow();
                Long id = (Long) itemsTable.getValueAt(row, ExpensePlanItemColumnEnum.ID.getIndex());
                PExpensePlanItem item = itemsTableModel.getItemById(id);
                ExpensePlanItemController.instance().delete(item);
            }
        }
    }

    //

    private class BlockItemAction extends AbstractAction {
        public BlockItemAction() {
            super(Resources.getString("buttons.block"),
                    Resources.getIcon("block.png"));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int row = itemsTable.getSelectedRow();
            Long id = (Long) itemsTable.getValueAt(row, ExpensePlanItemColumnEnum.ID.getIndex());
            PExpensePlanItem item = itemsTableModel.getItemById(id);
            item.setState(EntityStates.CLOSED.getCode());
            ExpensePlanItemController.instance().createOrUpdate(item);
        }
    }

    //

    private class UnblockItemAction extends AbstractAction {
        public UnblockItemAction() {
            super(Resources.getString("buttons.unblock"),
                    Resources.getIcon("unblock.png"));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int row = itemsTable.getSelectedRow();
            Long id = (Long) itemsTable.getValueAt(row, ExpensePlanItemColumnEnum.ID.getIndex());
            PExpensePlanItem item = itemsTableModel.getItemById(id);
            item.setState(EntityStates.ACTIVE.getCode());
            ExpensePlanItemController.instance().createOrUpdate(item);
        }
    }

    //

    private class EqualItemAction extends AbstractAction {
        public EqualItemAction() {
            super(Resources.getString("buttons.equal"), Resources.getIcon("equal.png"));
        }

        @Override
        public void actionPerformed(ActionEvent paramActionEvent) {
            int row = itemsTable.getSelectedRow();
            Long id = (Long) itemsTable.getValueAt(row, ExpensePlanItemColumnEnum.ID.getIndex());
            PExpensePlanItem item = itemsTableModel.getItemById(id);
            BigDecimal summ = Calc.sum(expensesTableModel.getSpentUnitsByBudgetUnit(item));
            item.setSumm(summ);
            item.setState(EntityStates.CLOSED.getCode());
            ExpensePlanItemController.instance().createOrUpdate(item);
        }
    }

    //

    private class AddExpenseAction extends AbstractAction {
        public AddExpenseAction() {
            super(Resources.getString("buttons.add") + "...", Resources.getIcon("add.png"));
        }

        public void actionPerformed(ActionEvent arg0) {
            ExpenseDialog.openNewDialog(getSelectedExpensePlan());
        }
    }

    //

    @SuppressWarnings("serial")
	private class EditExpenseAction extends AbstractAction {
        public EditExpenseAction() {
            super(Resources.getString("buttons.edit") + "...", Resources.getIcon("edit.png"));
        }

        public void actionPerformed(ActionEvent arg0) {
            int row = expensesTable.getSelectedRow();
            Long id = (Long) expensesTable.getValueAt(row, ExpenseColumnEnum.ID.getIndex());
            PExpense selectedExpense = expensesTableModel.getItemById(id);
            ExpenseDialog.openEditDialog(getSelectedExpensePlan(), selectedExpense);
        }
    }

    //

    private class RemoveExpenseAction extends AbstractAction {
        public RemoveExpenseAction() {
            super(Resources.getString("buttons.remove"), Resources.getIcon("remove.png"));
        }

        public void actionPerformed(ActionEvent arg0) {
            int result = MyDialogs.showYesNoDialog(ActiveExpensesPanel.this, Resources.getString("dialogs.questions.removeexpense") + "?");
            if (result == MyDialogs.YES) {
                int row = expensesTable.getSelectedRow();
                Long id = (Long) expensesTable.getValueAt(row, ExpenseColumnEnum.ID.getIndex());
                PExpense expense = expensesTableModel.getItemById(id);
                ExpenseController.instance().delete(expense);
            }
        }
    }

    private class ImportExpensesAction extends AbstractAction {
        public ImportExpensesAction() {
            super(Resources.getString("buttons.import"), Resources.getIcon("remove.png"));
        }

        public void actionPerformed(ActionEvent arg0) {
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
            int option = fileChooser.showOpenDialog(ActiveExpensesPanel.this);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();

                PrivateBankTemporaryImporter privateBankTemporaryImporter = new PrivateBankTemporaryImporter();
                privateBankTemporaryImporter.importExpenses(file);
            }
        }
    }
        
    private void addExpensePlanListeners() {
    	ExpensePlanController.instance().addListener(new ControllerListener<PExpensePlan>() {			
			@Override
			public void deleted(PExpensePlan expensePlan) {
				nameBox.removeItem(expensePlan);
                if (nameBox.getItemCount() == 0) {
                    disableEditActions();
                }
                checkIfThoughOneBudgetExists();
                enableOrDisableChartsButton();
                updateRemoveExpensePlanActionState();
			}
			
			@Override
			public void createdOrUpdated(PExpensePlan budget) {
                if (budget.getState().equals(EntityStates.ACTIVE.getCode())) {
                    nameBox.addItem(budget);
                    nameBox.setSelectedItem(budget);
                    setUIBudgetFields();
                    updateSumsAndBalanceFields(getSelectedExpensePlan());

                    if (nameBox.getItemCount() > 0) {
                        enableEditActions();
                    }
                    
                    updateNewAsCopyActionState();
                } else if (budget.getState().equals(EntityStates.CLOSED.getCode())) { //if budget closed
                    nameBox.removeItem(budget);
                    checkIfThoughOneBudgetExists();
                }	                
                
                updateRemoveExpensePlanActionState();
                enableOrDisableChartsButton();
			}
		});
    	
    	ExpensePlanItemController.instance().addListener(new ControllerListener<PExpensePlanItem>() {
			@Override
			public void createdOrUpdated(PExpensePlanItem item) {                
                itemsTableModel.putItem(item);              
                updateSumsAndBalanceFields(getSelectedExpensePlan());
                enableOrDisableAddExpenses();
                enableOrDisableChartsButton();
                updateRemoveExpensePlanActionState();
			}

			@Override
			public void deleted(PExpensePlanItem item) {								
                itemsTableModel.removeItem(item);
                updateSumsAndBalanceFields(getSelectedExpensePlan());
                enableOrDisableAddExpenses();		
                enableOrDisableChartsButton();
                updateRemoveExpensePlanActionState();
			}
		});
    	
    	ExpenseController.instance().addListener(new ControllerListener<PExpense>() {
			@Override
			public void createdOrUpdated(PExpense expense) {
				updateTablesData();
				updateSumsAndBalanceFields(getSelectedExpensePlan());
			}

			@Override
			public void deleted(PExpense expense) {
                updateTablesData();
                updateSumsAndBalanceFields(getSelectedExpensePlan());
			}
		});

        MobileDataManager.addMobileDataListener(new MobileDataListener() {
            @Override
            public void mobileDataSaved() {
                updateUIData();
            }
        });
    }

    private void disableEditActions() {    	
        editExpensePlanAction.setEnabled(false);
        prepareToCloseAction.setEnabled(false);        
        closeExpensePlanAction.setEnabled(false);
        removeExpensePlanAction.setEnabled(false);
        addItemAction.setEnabled(false);
        addExpenseAction.setEnabled(false);
    }

    private void enableEditActions() {
        editExpensePlanAction.setEnabled(true);
        prepareToCloseAction.setEnabled(true);
        closeExpensePlanAction.setEnabled(true);
        //We cannot add items if there's no open categories
        //addItemAction.setEnabled(DBConditions.existOpenCategories());
        addItemAction.setEnabled(true);
    }

    private void enableOrDisableAddExpenses() {
        if (getSelectedExpensePlan() == null) {
            addExpenseAction.setEnabled(false);
            return;
        }

        if (itemsTableModel.getRowCount() == 0) {
            addExpenseAction.setEnabled(false);
            return;
        }

        boolean allItemsBlocked = true;
        for (PExpensePlanItem item : itemsTableModel.getDataCollection()) {
            if (item.getState().equals(EntityStates.ACTIVE.getCode())) {
                allItemsBlocked = false;
                break;
            }
        }

        if (allItemsBlocked) {
            addExpenseAction.setEnabled(false);
            return;
        }

        addExpenseAction.setEnabled(true);
	}
    
	private void checkIfThoughOneBudgetExists() {
		if (nameBox.getItemCount() == 0) {
		    cleanBudgetFields();
		    disableEditActions();
		} else {
			enableEditActions();
		}
	}
	
	private void updateNewAsCopyActionState() {
		newExpensePlanAsCopyAction.setEnabled(ExpensePlanController.instance().countAll() > 0);
	}

	@Override
	protected void loadExpensesPlans() {
		nameBox.removeItemListener(nameBoxItemListener);
        List<PExpensePlan> activeExpensePlans = ExpensePlanController.instance().listActive();        
        NammableSorter.sort(activeExpensePlans);
        for(PExpensePlan expensePlan : activeExpensePlans) {
        	nameBox.addItem(expensePlan);
        }
        nameBox.addItemListener(nameBoxItemListener);
	}	
	
	public void saveStateParams() {		
		MyPreferences.save(PrefKeys.ACTIVE_EXPENSES_CHART_INDEX, chartsButton.getSelectedIndex());
	}

	@Override
	protected void restoreState() {
		int selectedChartIndex = MyPreferences.getInteger(PrefKeys.ACTIVE_EXPENSES_CHART_INDEX, 0);
		chartsButton.setSelectedIndex(selectedChartIndex);		
	}
	
	private void updateRemoveExpensePlanActionState() {
		if(getSelectedExpensePlan() != null) {
			removeExpensePlanAction.setEnabled(!DBConditions.hasExpensePlanAnyItems(getSelectedExpensePlan()));	
		} else {
			removeExpensePlanAction.setEnabled(false);
		}
	}	
}
