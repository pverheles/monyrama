package com.monyrama.ui.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.*;
import javax.swing.RowSorter.SortKey;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.monyrama.controller.ControllerListener;
import com.monyrama.controller.ExpenseController;
import com.monyrama.controller.ExpensePlanController;
import com.monyrama.controller.ExpensePlanItemController;
import com.monyrama.entity.PExpense;
import com.monyrama.entity.PExpensePlan;
import com.monyrama.entity.PExpensePlanItem;
import com.monyrama.ui.components.CheckboxCell;
import com.monyrama.ui.components.DropdownButton;
import com.monyrama.ui.components.ExpandableLabel;
import com.monyrama.ui.components.MyJTable;
import com.monyrama.ui.components.UniqueSortedComboboxModel;
import com.monyrama.ui.constants.ColorConstants;
import com.monyrama.ui.constants.DimensionConstants;
import com.monyrama.ui.enumeration.ExpensesTabEnum;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.tables.columns.ExpenseColumnEnum;
import com.monyrama.ui.tables.columns.ExpensePlanItemColumnEnum;
import com.monyrama.ui.tables.model.ExpenseItemTableModel;
import com.monyrama.ui.tables.model.ExpenseTableModel;
import com.monyrama.ui.utils.ComparatorFactory;
import com.monyrama.ui.utils.MyFormatter;
import com.monyrama.ui.view.charts.DailyExpenseSumChart;
import com.monyrama.ui.view.charts.DailyGeneralSumChart;
import com.monyrama.ui.view.charts.PlannedExpensesSumsByItemChart;
import com.monyrama.utils.Calc;


/**
 * Represents generic expenses panel for other kinds of expenses panel (active and closed)
 * 
 * @author Petro_Verheles
 *
 */
abstract class AbstractExpensePanel extends GeneralPanel {
	protected ExpenseTableModel expensesTableModel = new ExpenseTableModel();
	protected ExpenseItemTableModel itemsTableModel = new ExpenseItemTableModel(expensesTableModel);	
	
	protected JComboBox expensePlanBox;
	protected JLabel plannedSumValueLabel;
	protected JLabel currencyValueLabel;
	protected JLabel balanceValueLabel;	
	//protected JTextArea commentArea;
	protected ExpandableLabel commentsValueLabel;
	protected JLabel expensesSumValueLabel;	
	protected DropdownButton chartsButton;
	
	protected JTable itemsTable;
	protected JTable expensesTable;
	
	private static final long serialVersionUID = 1L;
	
	protected JScrollPane itemsPane;
	protected JScrollPane expensesPane; 
		
	private boolean firstOpened = true;
	
	protected final static Dimension BUTTON_DIMENSION = new Dimension(200, 23);
	private JTabbedPane tabbedPane;
	protected NameBoxItemListener nameBoxItemListener;

	Color BALANCE_VALUE_LABEL_COLOR;

	/**
	 * Create the panel
	 */
	public AbstractExpensePanel() {
		super();
		createActions();
		paintPanels();		    	
		createPopUp();
		addListeners();		
	}


	@Override
	public void initOnVisible() {
        if (firstOpened) {            	
            initTableColumns();            
            loadExpensesPlans();
            restoreState();
            firstOpened = false;            
        }                     
        
        if(getSelectedExpensePlan() != null) {
            updateUIData();
        }

        enableOrDisableChartsButton();
    }

	protected void paintPanels() {
		setLayout(new BorderLayout());
		
		final JPanel northPanel = getTopPanel();	
		add(northPanel, BorderLayout.NORTH);

		tabbedPane = getCentralPanel();
		add(tabbedPane, BorderLayout.CENTER);
		
		final JPanel southPanel = getSouthPanel();
		
		if(southPanel != null) {
			add(southPanel, BorderLayout.SOUTH);	
		}
	}
	
	/**
	 * Paints top panel and returns it
	 * 
	 * @return - top panel
	 */
	private JPanel getTopPanel() {		
		final JPanel topPanel = new JPanel();
		final GridBagLayout gridBagLayout = new GridBagLayout();
		topPanel.setLayout(gridBagLayout);		

		final JPanel leftTopPanel = new JPanel();
		leftTopPanel.setLayout(new GridBagLayout());
		final JLabel nameLabel = new JLabel();
		nameLabel.setText(Resources.getString("labels.name") + ":");
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(12, 5, 15, 0);
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridx = 0;
		leftTopPanel.add(nameLabel, gridBagConstraints);

		final JLabel emptyLabel = new JLabel();
		emptyLabel.setPreferredSize(new Dimension(5, 0));
		emptyLabel.setMinimumSize(new Dimension(5, 0));
		final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
		gridBagConstraints_3.gridy = 0;
		gridBagConstraints_3.gridx = 1;
		leftTopPanel.add(emptyLabel, gridBagConstraints_3);

		expensePlanBox = new JComboBox(new UniqueSortedComboboxModel<PExpensePlan>());
		expensePlanBox.setMinimumSize(DimensionConstants.STD_FIELD_DIMENSION);
		expensePlanBox.setMaximumSize(DimensionConstants.STD_FIELD_DIMENSION);
		expensePlanBox.setPreferredSize(DimensionConstants.STD_FIELD_DIMENSION);
		final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(2, 0, 5, 0);
		gridBagConstraints_1.anchor = GridBagConstraints.WEST;
		gridBagConstraints_1.gridy = 0;
		gridBagConstraints_1.gridx = 2;
		leftTopPanel.add(expensePlanBox, gridBagConstraints_1);

		final JLabel label = new JLabel();
		final GridBagConstraints gridBagConstraints_4 = new GridBagConstraints();
		gridBagConstraints_4.gridy = 0;
		gridBagConstraints_4.gridx = 5;
		leftTopPanel.add(label, gridBagConstraints_4);
		
		final JLabel currencyLabel = new JLabel();
		currencyLabel.setText(Resources.getString("labels.currency") + ":");
		final GridBagConstraints gridBagConstraints_20 = new GridBagConstraints();
		gridBagConstraints_20.insets = new Insets(0, 0, 10, 0);
		gridBagConstraints_20.anchor = GridBagConstraints.WEST;
		gridBagConstraints_20.gridy = 1;
		gridBagConstraints_20.gridx = 0;
		gridBagConstraints_20.insets.left = 5;
		leftTopPanel.add(currencyLabel, gridBagConstraints_20);
//
		currencyValueLabel = new JLabel();
		final GridBagConstraints gridBagConstraints_70 = new GridBagConstraints();
		gridBagConstraints_70.anchor = GridBagConstraints.WEST;
		gridBagConstraints_70.insets = new Insets(0, 0, 10, 0);
		gridBagConstraints_70.gridy = 1;
		gridBagConstraints_70.gridx = 2;
		leftTopPanel.add(currencyValueLabel, gridBagConstraints_70);		
		
		final JLabel activeSumLabel = new JLabel();
		activeSumLabel.setText(Resources.getString("labels.expenses.plannedsum") + ":");
		final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
		gridBagConstraints_2.insets = new Insets(0, 0, 10, 0);
		gridBagConstraints_2.anchor = GridBagConstraints.WEST;
		gridBagConstraints_2.gridy = 2;
		gridBagConstraints_2.gridx = 0;
		gridBagConstraints_2.insets.left = 5;
		leftTopPanel.add(activeSumLabel, gridBagConstraints_2);

		plannedSumValueLabel = new JLabel();
		final GridBagConstraints gridBagConstraints_10 = new GridBagConstraints();
		gridBagConstraints_10.anchor = GridBagConstraints.WEST;
		gridBagConstraints_10.insets = new Insets(0, 0, 10, 0);
		gridBagConstraints_10.gridy = 2;
		gridBagConstraints_10.gridx = 2;
		leftTopPanel.add(plannedSumValueLabel, gridBagConstraints_10);	
		
		JLabel generalSumLabel = new JLabel(Resources.getString("labels.expenses.realsum") + ":");
		final GridBagConstraints gridBagConstraints_14 = new GridBagConstraints();
		gridBagConstraints_14.insets = new Insets(0, 0, 10, 0);
		gridBagConstraints_14.anchor = GridBagConstraints.WEST;
		gridBagConstraints_14.gridy = 3;
		gridBagConstraints_14.gridx = 0;
		gridBagConstraints_14.insets.left = 5;
		leftTopPanel.add(generalSumLabel, gridBagConstraints_14);		
						
		expensesSumValueLabel = new JLabel();
		final GridBagConstraints gridBagConstraints_6 = new GridBagConstraints();
		gridBagConstraints_6.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints_6.gridy = 3;
		gridBagConstraints_6.gridx = 2;
		leftTopPanel.add(expensesSumValueLabel, gridBagConstraints_6);	

		final JLabel realBalanceLabel = new JLabel();
		realBalanceLabel.setText(Resources.getString("labels.balance") + ":");
		final GridBagConstraints gridBagConstraints_15 = new GridBagConstraints();
		gridBagConstraints_15.insets = new Insets(0, 0, 10, 0);
		gridBagConstraints_15.gridy = 4;
		gridBagConstraints_15.gridx = 0;
		gridBagConstraints_15.insets.left = 5;
		gridBagConstraints_15.anchor = GridBagConstraints.WEST;
		leftTopPanel.add(realBalanceLabel, gridBagConstraints_15);

		balanceValueLabel = new JLabel();
		final GridBagConstraints gridBagConstraints_16 = new GridBagConstraints();
		gridBagConstraints_16.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints_16.gridy = 4;
		gridBagConstraints_16.gridx = 2;
		leftTopPanel.add(balanceValueLabel, gridBagConstraints_16);

		BALANCE_VALUE_LABEL_COLOR = balanceValueLabel.getForeground();
		
		final JLabel commentsLabel = new JLabel();
		commentsLabel.setText(Resources.getString("labels.comments") + ":");
		final GridBagConstraints gridBagConstraints_17 = new GridBagConstraints();
		gridBagConstraints_17.insets = new Insets(0, 0, 10, 0);
		gridBagConstraints_17.gridy = 5;
		gridBagConstraints_17.gridx = 0;
		gridBagConstraints_17.insets.left = 5;
		gridBagConstraints_17.anchor = GridBagConstraints.WEST;
		leftTopPanel.add(commentsLabel, gridBagConstraints_17);
		
        commentsValueLabel = new ExpandableLabel();
        //commentsValueLabel.setMinimumSize(new Dimension(250, 15));
        commentsValueLabel.setMaximumSize(new Dimension(220, 15));
        commentsValueLabel.setPreferredSize(new Dimension(220, 15));
        final GridBagConstraints gridBagConstraints_7 = new GridBagConstraints();
        gridBagConstraints_7.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints_7.gridx = 2;
        gridBagConstraints_7.gridy = 5;

        leftTopPanel.add(commentsValueLabel, gridBagConstraints_7);		
		
        final GridBagConstraints leftTopPanelConstraints = new GridBagConstraints();
        leftTopPanelConstraints.gridx = 0;
        leftTopPanelConstraints.gridy = 0;
        leftTopPanelConstraints.gridheight = 2;
        leftTopPanelConstraints.anchor = GridBagConstraints.NORTH;
        leftTopPanelConstraints.insets = new Insets(0, 0, 0, 0);
        topPanel.add(leftTopPanel, leftTopPanelConstraints);
        
		//Buttons Panel
        final GridBagConstraints buttonsPanelConstraints = new GridBagConstraints();
        buttonsPanelConstraints.gridx = 1;
        buttonsPanelConstraints.gridy = 0;
        buttonsPanelConstraints.anchor = GridBagConstraints.NORTH;
        buttonsPanelConstraints.insets = new Insets(8, 5, 0, 0);
        topPanel.add(getButtonsPanel(), buttonsPanelConstraints);		

        //Charts button pannel
        final GridBagConstraints chartsPanelConstraints = new GridBagConstraints();
        chartsPanelConstraints.gridx = 1;
        chartsPanelConstraints.gridy = 1;
        chartsPanelConstraints.anchor = GridBagConstraints.NORTHWEST;
        chartsPanelConstraints.insets = new Insets(5, 5, 0, 0);
        topPanel.add(getChartsPanel(), chartsPanelConstraints);        
        
        final GridBagConstraints emptyPanelConstraints = new GridBagConstraints();
        emptyPanelConstraints.gridx = 3;
        emptyPanelConstraints.gridy = 0;
        emptyPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
        emptyPanelConstraints.weightx = 1.0;
        topPanel.add(new JPanel(), emptyPanelConstraints);
        
        topPanel.setBorder(new TitledBorder(null, Resources.getString("labels.expenses.plan"), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, SystemColor.activeCaption));
		
		return topPanel;
	}
	
	private Component getChartsPanel() {
		JPanel chartsPanel = new JPanel();		
		chartsPanel.setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints;
		
		JLabel chartsLabel = new JLabel(Resources.getString("labels.chart") + ":");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		chartsPanel.add(chartsLabel, gridBagConstraints);
		
		chartsButton = new DropdownButton();
		chartsButton.setMinimumSize(BUTTON_DIMENSION);
		chartsButton.setMaximumSize(BUTTON_DIMENSION);
		chartsButton.setPreferredSize(BUTTON_DIMENSION);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		chartsPanel.add(chartsButton, gridBagConstraints);
		
		//Add actions
		chartsButton.addAction(new ShowPlanByItemChart());
		chartsButton.addAction(new ShowDailyExpenseSumChart());
		chartsButton.addAction(new ShowExpenseGeneralSumChart());
		
		return chartsPanel;
	}

	/**
	 * Paints the central tabbed pane and returns it
	 * 
	 * @return - central panel
	 */
	private JTabbedPane getCentralPanel() {
		final JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setForeground(SystemColor.activeCaption);
		tabbedPane.insertTab(ExpensesTabEnum.ITEMS.getName(), Resources.getIcon("items.png"), getItemsPanel(), null, ExpensesTabEnum.ITEMS.getIndex());
		tabbedPane.insertTab(ExpensesTabEnum.EXPENSES.getName(), Resources.getIcon("expenses.png"), getExpensesPanel(), null, ExpensesTabEnum.EXPENSES.getIndex());		
		return tabbedPane;
	}
	
	/**
	 * Creates Budget Items Panel and returns it
	 * 
	 * @return - Budget Items Panel
	 */
	protected JComponent getItemsPanel() {
		final JPanel itemsPanel = new JPanel();
		itemsPanel.setLayout(new BorderLayout());

		itemsPane = new JScrollPane();
		itemsPanel.add(itemsPane, BorderLayout.CENTER);

		itemsTable = new MyJTable();
		
		itemsTable.setSelectionBackground(ColorConstants.SELECTED_BACKGROUND);
		itemsTable.setSelectionForeground(ColorConstants.SELECTED_FOREGROUND);		        
		
		itemsTable.setModel(itemsTableModel);
		
        ItemsRowCellRenderer cellRenderer = new ItemsRowCellRenderer();
        itemsTable.getColumn(ExpensePlanItemColumnEnum.BLOCKED.getName()).setCellRenderer(cellRenderer);		
		
		//We hide column with ID
		itemsTable.getColumnModel().getColumn(ExpensePlanItemColumnEnum.ID.getIndex()).setMinWidth(0);
		itemsTable.getColumnModel().getColumn(ExpensePlanItemColumnEnum.ID.getIndex()).setMaxWidth(0);		
		
		itemsTable.setRowHeight(20);
		
		itemsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		itemsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		itemsPane.setViewportView(itemsTable);				
		
		return itemsPanel;
	}
	
	/**
	 * Creates Expenses Panel and returns it
	 * 
	 * @return - Expenses Panel
	 */
	protected JComponent getExpensesPanel() {
		JPanel expensesPanel = new JPanel();
		
		expensesPanel.setLayout(new BorderLayout());				

		final JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		expensesPanel.add(centerPanel, BorderLayout.CENTER);

		expensesPane = new JScrollPane();
		centerPanel.add(expensesPane, BorderLayout.CENTER);

		expensesTable = createExpensesTable();
		expensesTable.setSelectionBackground(ColorConstants.SELECTED_BACKGROUND);
		expensesTable.setSelectionForeground(ColorConstants.SELECTED_FOREGROUND);
		expensesPane.setViewportView(expensesTable);
		
		expensesTable.setModel(expensesTableModel);

		expensesTable.getColumn(ExpenseColumnEnum.ID.getName()).setMinWidth(0);
		expensesTable.getColumn(ExpenseColumnEnum.ID.getName()).setMaxWidth(0);
		
		expensesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);	
	
		expensesTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		return expensesPanel;		
	}

	protected MyJTable createExpensesTable() {
		return new MyJTable();
	}

	/**
	 * Paints the south panel and returns it
	 * 
	 * @return - south panel
	 */
	protected JPanel getSouthPanel() {		
		return null;
	}

	/**
	 * Adds listeners needed for work of the panel 
	 *
	 * Children have to override this method if needed 
	 */
	protected void addListeners() {
		nameBoxItemListener = new NameBoxItemListener();
		expensePlanBox.addItemListener(nameBoxItemListener);
		
		tabbedPane.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				itemsTableModel.fireTableDataChanged();
				expensesTableModel.fireTableDataChanged();
				enableOrDisableChartsButton();
			}
		});
	}
	
	protected void updateTablesData() {
		if(getSelectedExpensePlan() == null) {
			return;
		}

		//we should refill expenses first because expensesTableModel then we use getExpenses By Item
		//This dependency is not very nice but it's much more efficient then query database
		List<PExpense> expenses = ExpenseController.instance().listByExpensePlan(getSelectedExpensePlan());
		expensesTableModel.reFill(expenses);

		List<PExpensePlanItem> envelopes = ExpensePlanItemController.instance().listByExpensePlan(getSelectedExpensePlan());
		itemsTableModel.reFill(envelopes);
	}

	protected void setUIBudgetFields() {	
		currencyValueLabel.setText(getSelectedExpensePlan().getCurrency().toString());
		commentsValueLabel.setText(getSelectedExpensePlan().getComment());
		updateSumsAndBalanceFields(getSelectedExpensePlan());
	}
	
	/**
	 * Create popups
	 * 
	 * Children have to override this method if needed
	 */
	protected void createPopUp() {	
	}
	
	/**
	 * Creates Actions and sets them to the elements
	 * 	
	 * Children have to override this method if needed
	 */
	protected void createActions() {
	}

	private void setPlannedSumValueLabel(String sum) {
		plannedSumValueLabel.setText(sum);
	}
	
	public void setTotalValueLabel(String number) {
		expensesSumValueLabel.setText(number);		
	}
		
	protected void setBalanceValueLabel(String balance) {	
		//TODO: QUICK FIX
		if(balance != null && !balance.equals("")) {
			balance = MyFormatter.formatNumberToStandard(balance);
			if(new BigDecimal(balance).compareTo(BigDecimal.ZERO) < 0) {
				balanceValueLabel.setForeground(SystemColor.RED);
			} else {
				balanceValueLabel.setForeground(BALANCE_VALUE_LABEL_COLOR);
			}
			balanceValueLabel.setText(MyFormatter.formatNumberToLocal(balance.toString()));
		} else {
			balanceValueLabel.setText("");	
		}		
	}	

	private final class NameBoxItemListener implements ItemListener {
		public void itemStateChanged(ItemEvent ev) {
			if(ev.getStateChange() == ItemEvent.SELECTED) {
				ExpensePlanController.instance().refresh(getSelectedExpensePlan());
				updateTablesData();
				setUIBudgetFields();				
			}				
		}
	}

	/**
	 * Sorter for Items table
	 * 
	 * Uses default comparators except for the Remainder column
	 * 
	 * @author Petro_Verheles
	 *
	 */
	private class BudgetUnitSorter extends TableRowSorter<TableModel> {

		private BudgetUnitSorter(TableModel model) {
			super(model);
		}

		@Override
		public Comparator<?> getComparator(int index) {			

			if(index == ExpensePlanItemColumnEnum.SUM.getIndex()
					|| index == ExpensePlanItemColumnEnum.REMAINDER.getIndex()) {
				return ComparatorFactory.getInstance().getBigDecimalComparator();
			}
			
			return super.getComparator(index);
		}		
	}

	/**
	 * Sorter for Expenses table
	 * 
	 * Uses default comparators except for the Price columns
	 * 
	 * @author Petro_Verheles
	 *
	 */	
	private class ExpensesSorter extends TableRowSorter<TableModel> {

		private ExpensesSorter(TableModel model) {
			super(model);
		}

		@Override
		public Comparator<?> getComparator(int index) {			

			if(index == ExpenseColumnEnum.PRICE.getIndex()) {
				return ComparatorFactory.getInstance().getBigDecimalComparator();
			}
			
			return super.getComparator(index);
		}		
	}
	
	protected void initTableColumns() {
		//QUICK FIX
		if(itemsTable == null || itemsPane == null) {
			return;
		}
		
		TableColumnsUtil.setPercentColumnWidths(itemsTable, itemsPane.getWidth(), ExpensePlanItemColumnEnum.getColumnPercents());
		TableColumnsUtil.setPercentColumnWidths(expensesTable, expensesPane.getWidth(), ExpenseColumnEnum.getColumnPercents());
		
		BudgetUnitSorter itemsSorter = new BudgetUnitSorter(itemsTable.getModel());
		itemsTable.setRowSorter(itemsSorter);
        List<SortKey> itemsSortKeys = new ArrayList<RowSorter.SortKey>();
        itemsSortKeys.add( new RowSorter.SortKey(ExpensePlanItemColumnEnum.NAME.getIndex(), SortOrder.ASCENDING) );
        itemsSorter.setSortKeys(itemsSortKeys);
        itemsSorter.sort();        
        		
		ExpensesSorter expenseSorter = new ExpensesSorter(expensesTable.getModel());
		expensesTable.setRowSorter(expenseSorter);
        List<SortKey> expenseSortKeys = new ArrayList<RowSorter.SortKey>();
        expenseSortKeys.add( new RowSorter.SortKey(ExpenseColumnEnum.DATE.getIndex(), SortOrder.DESCENDING) );
        expenseSorter.setSortKeys(expenseSortKeys);
        expenseSorter.sort();        
	}

	
	protected PExpensePlan getSelectedExpensePlan() {
		PExpensePlan budget = (PExpensePlan) expensePlanBox.getSelectedItem();
		return budget;
	}
	
	protected void updateSumsAndBalanceFields(PExpensePlan budget) {
		if(budget == null) {
			setPlannedSumValueLabel("");
			setTotalValueLabel("");
			setBalanceValueLabel("");
			return;
		}	
				
		BigDecimal plannedSum = Calc.sum(itemsTableModel.getDataCollection());
		BigDecimal realSum = Calc.sum(expensesTableModel.getDataCollection());
		BigDecimal balance = plannedSum.subtract(realSum);
				
		setPlannedSumValueLabel(MyFormatter.formatNumberToLocal(plannedSum.toPlainString()));
		setTotalValueLabel(MyFormatter.formatNumberToLocal(realSum.toPlainString()));
		setBalanceValueLabel(MyFormatter.formatNumberToLocal(balance.toPlainString()));
	}

	public void cleanBudgetFields() {
		plannedSumValueLabel.setText("");
		currencyValueLabel.setText("");
		expensesSumValueLabel.setText("");
		balanceValueLabel.setText("");
		commentsValueLabel.setText("");
		itemsTableModel.clear();
		expensesTableModel.clear();
	}
	
	protected abstract void loadExpensesPlans();
	
	protected JPanel getButtonsPanel() {
		JPanel buttonPanel = new JPanel();
		return buttonPanel;
	}

	protected void enableOrDisableChartsButton() {
		//if(getSelectedBudget() == null) {
		if(expensePlanBox.getItemCount() == 0) {
			chartsButton.setEnabled(false);
		} else {
			chartsButton.setEnabled(true);
		}
	}

	protected void updateUIData() {
		if(getSelectedExpensePlan() != null) {
			ExpensePlanController.instance().refresh(getSelectedExpensePlan());
			updateTablesData();
			setUIBudgetFields();
		}
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
                } else {
                	checkbox.setBackground(table.getBackground());
                }
            	comp = checkbox;
            } else {
            	comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }

            return comp;
        }
    }
    
    //--------------------------- Actions -----------------------------------
    private class ShowPlanByItemChart extends AbstractAction {    	
        public ShowPlanByItemChart() {
            super(Resources.getString("charts.actionname.plannedbyitem"), Resources.getIcon("pie_chart.png"));
        }
    	
		@Override
		public void actionPerformed(ActionEvent arg0) {
			PlannedExpensesSumsByItemChart planByItemChart = new PlannedExpensesSumsByItemChart(itemsTableModel.getDataCollection(), getSelectedExpensePlan().getName());
			planByItemChart.setIconImage(Resources.getImage("pie_chart.png"));
			planByItemChart.setLocationRelativeTo(AbstractExpensePanel.this);
			planByItemChart.setVisible(true);			
		}    	
    }
    
    private class ShowDailyExpenseSumChart extends AbstractAction {    	
    	public ShowDailyExpenseSumChart() {
    		super(Resources.getString("charts.actionname.dailyexpense"), Resources.getIcon("barchart.png"));
    	}
    	
		@Override
		public void actionPerformed(ActionEvent arg0) {
			DailyExpenseSumChart dailyExpenseSumChart = new DailyExpenseSumChart(expensesTableModel.getDataCollection(), getSelectedExpensePlan().getName());
			dailyExpenseSumChart.setIconImage(Resources.getImage("barchart.png"));
			dailyExpenseSumChart.setLocationRelativeTo(AbstractExpensePanel.this);
			dailyExpenseSumChart.setVisible(true);
		}     	
    }
    
    private class ShowExpenseGeneralSumChart extends AbstractAction {
    	
    	public ShowExpenseGeneralSumChart() {
    		super(Resources.getString("charts.actionname.generalsumbydate"), Resources.getIcon("growchart.png"));
    	}
    	
		@Override
		public void actionPerformed(ActionEvent arg0) {
			DailyGeneralSumChart dailyGeneralSumChart = new DailyGeneralSumChart(expensesTableModel.getDataCollection(), getSelectedExpensePlan().getName());
			dailyGeneralSumChart.setIconImage(Resources.getImage("growchart.png"));
			dailyGeneralSumChart.setLocationRelativeTo(AbstractExpensePanel.this);
			dailyGeneralSumChart.setVisible(true);
		}     	
    }
    
    protected abstract void restoreState();
}
