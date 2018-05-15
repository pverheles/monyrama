package com.monyrama.ui.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.RowSorter.SortKey;
import javax.swing.SortOrder;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.monyrama.controller.SavedExpenseCommentController;
import com.monyrama.entity.*;
import com.monyrama.controller.CategoryController;
import com.monyrama.controller.CurrencyController;
import com.monyrama.controller.ExpensePlanController;
import com.monyrama.db.search.ExpensesSearch;
import com.monyrama.db.search.parameters.ExpensesSearchParameters;
import com.monyrama.sorter.NammableSorter;
import com.monyrama.ui.components.*;
import com.monyrama.ui.components.totalsumspanel.TotalSumsPanel;
import com.monyrama.ui.constants.ColorConstants;
import com.monyrama.ui.enumeration.LogicalEnum;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.tables.columns.ExpensesSearchColumnEnum;
import com.monyrama.ui.tables.columns.TotalSumsColumnEnum;
import com.monyrama.ui.tables.model.SearchResultsTableModel;
import com.monyrama.ui.utils.ComparatorFactory;
import com.monyrama.ui.utils.MyDialogs;
import com.monyrama.ui.utils.MyFormatter;
import com.monyrama.utils.Calc;
import com.monyrama.utils.Trimmer;
import com.monyrama.validator.util.StringSumValidator;


/**
 * Represents Search of Expenses Panel
 *
 * @author Petro_Verheles
 */
public class ExpenseSearchPanel extends GeneralPanel {

    private SearchResultsTableModel tableModel = new SearchResultsTableModel();

    private JTable table;
    private JTextField priceField;
    private JComboBox logicalComboBox;
    private JComboBox categoryBox;
    private JComboBox expensePlanBox;
    private JComboBox currencyBox;
    private JButton findButton;
    private JButton cleanButton;
    private JCheckBox periodCheckBox;
    private JDateChooser toChooser;
    private JDateChooser fromChooser;
    private AutosuggestTextFieldLimited commentsField;

    private JSplitPane splitTablePane;

    private JScrollPane scrollPane;

    private boolean firstOpen = true;

	private TotalSumsPanel totalSumsPanel;

    /**
     * Create the panel
     */
    public ExpenseSearchPanel() {
        super();
        paintForm();
        initTables();
        addUIListeners();
    }

    private void paintForm() {
        setLayout(new BorderLayout());

        final JPanel northPanel = new JPanel();
        northPanel
               .setBorder(new TitledBorder(null, Resources.getString("labels.searchparams"),
                        TitledBorder.DEFAULT_JUSTIFICATION,
                        TitledBorder.DEFAULT_POSITION, null,
                        SystemColor.activeCaption));
        final FlowLayout flowLayout = new FlowLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        northPanel.setLayout(flowLayout);
        add(northPanel, BorderLayout.NORTH);

        final JPanel northInnerPanel = new JPanel();
        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.rowHeights = new int[]{0, 7, 7, 7};
        gridBagLayout.columnWidths = new int[]{0, 7, 7, 7};
        northInnerPanel.setLayout(gridBagLayout);
        northPanel.add(northInnerPanel);

        final JLabel budgetLabel = new JLabel();
        budgetLabel.setText(Resources.getString("labels.budget") + ":");
        final GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(10, 0, 10, 0);
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridx = 0;
        northInnerPanel.add(budgetLabel, gridBagConstraints);

        expensePlanBox = new JComboBox();
        expensePlanBox.setPreferredSize(new Dimension(250, 20));
        final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
        gridBagConstraints_1.insets = new Insets(0, 5, 0, 40);
        gridBagConstraints_1.gridy = 0;
        gridBagConstraints_1.gridx = 1;
        northInnerPanel.add(expensePlanBox, gridBagConstraints_1);

        final JLabel periodLabel = new JLabel();
        periodLabel.setText(Resources.getString("labels.period") + ":");
        final GridBagConstraints gridBagConstraints_8 = new GridBagConstraints();
        gridBagConstraints_8.insets = new Insets(0, 0, 0, 5);
        gridBagConstraints_8.gridy = 0;
        gridBagConstraints_8.gridx = 2;
        northInnerPanel.add(periodLabel, gridBagConstraints_8);

        periodCheckBox = new JCheckBox();
        periodCheckBox.setSelected(false);
        periodCheckBox.setMargin(new Insets(0, 0, 0, 0));
        periodCheckBox.setHorizontalAlignment(SwingConstants.LEFT);
        final GridBagConstraints gridBagConstraints_9 = new GridBagConstraints();
        gridBagConstraints_9.anchor = GridBagConstraints.WEST;
        gridBagConstraints_9.gridy = 0;
        gridBagConstraints_9.gridx = 3;
        northInnerPanel.add(periodCheckBox, gridBagConstraints_9);

        final JLabel currencyLabel = new JLabel();
        currencyLabel.setText(Resources.getString("labels.currency") + ":");
        final GridBagConstraints currLabConstraints = new GridBagConstraints();
        currLabConstraints.anchor = GridBagConstraints.WEST;
        currLabConstraints.insets = new Insets(3, 0, 13, 0);
        currLabConstraints.gridy = 1;
        currLabConstraints.gridx = 0;
        northInnerPanel.add(currencyLabel, currLabConstraints);

        currencyBox = new JComboBox();
        currencyBox.setPreferredSize(new Dimension(250, 20));
        final GridBagConstraints currBoxConstraints = new GridBagConstraints();
        currBoxConstraints.anchor = GridBagConstraints.NORTHWEST;
        currBoxConstraints.insets = new Insets(1, 5, 0, 0);
        currBoxConstraints.gridy = 1;
        currBoxConstraints.gridx = 1;
        northInnerPanel.add(currencyBox, currBoxConstraints);

        final JLabel categoryLabel = new JLabel();
        categoryLabel.setText(Resources.getString("labels.category") + ":");
        final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
        gridBagConstraints_2.insets = new Insets(3, 0, 13, 0);
        gridBagConstraints_2.anchor = GridBagConstraints.WEST;
        gridBagConstraints_2.gridy = 2;
        gridBagConstraints_2.gridx = 0;
        northInnerPanel.add(categoryLabel, gridBagConstraints_2);

        categoryBox = new JComboBox();
        categoryBox.setPreferredSize(new Dimension(250, 20));
        final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
        gridBagConstraints_3.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints_3.insets = new Insets(0, 5, 0, 0);
        gridBagConstraints_3.gridy = 2;
        gridBagConstraints_3.gridx = 1;
        northInnerPanel.add(categoryBox, gridBagConstraints_3);

        final JLabel fromLabel = new JLabel();
        fromLabel.setText(Resources.getString("labels.from") + ":");
        final GridBagConstraints gridBagConstraints_10 = new GridBagConstraints();
        gridBagConstraints_10.insets = new Insets(3, 0, 0, 5);
        gridBagConstraints_10.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints_10.gridy = 1;
        gridBagConstraints_10.gridx = 2;
        northInnerPanel.add(fromLabel, gridBagConstraints_10);

        JCalendar jcalFrom = new JCalendar();
        jcalFrom.setWeekOfYearVisible(false);

        fromChooser = new JDateChooser(jcalFrom);
        fromChooser.setLocale(Resources.getLocale());
        final GridBagConstraints gridBagConstraints_13 = new GridBagConstraints();
        gridBagConstraints_13.anchor = GridBagConstraints.NORTHEAST;
        gridBagConstraints_13.gridy = 1;
        gridBagConstraints_13.gridx = 3;
        northInnerPanel.add(fromChooser, gridBagConstraints_13);

        final JLabel priceLabel = new JLabel();
        priceLabel.setText(Resources.getString("labels.price") + ":");
        final GridBagConstraints gridBagConstraints_4 = new GridBagConstraints();
        gridBagConstraints_4.anchor = GridBagConstraints.WEST;
        gridBagConstraints_4.gridy = 3;
        gridBagConstraints_4.gridx = 0;
        northInnerPanel.add(priceLabel, gridBagConstraints_4);

        final JPanel panel = new JPanel();
        final GridBagLayout gridBagLayout_1 = new GridBagLayout();
        gridBagLayout_1.columnWidths = new int[]{0, 7};
        panel.setLayout(gridBagLayout_1);
        panel.setPreferredSize(new Dimension(250, 20));
        final GridBagConstraints gridBagConstraints_5 = new GridBagConstraints();
        gridBagConstraints_5.anchor = GridBagConstraints.WEST;
        gridBagConstraints_5.insets = new Insets(0, 5, 0, 0);
        gridBagConstraints_5.gridy = 3;
        gridBagConstraints_5.gridx = 1;
        northInnerPanel.add(panel, gridBagConstraints_5);

        logicalComboBox = new JComboBox();
        logicalComboBox.setPreferredSize(new Dimension(145, 20));
        final GridBagConstraints gridBagConstraints_6 = new GridBagConstraints();
        gridBagConstraints_6.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints_6.insets = new Insets(0, 0, 0, 5);
        gridBagConstraints_6.gridy = 0;
        gridBagConstraints_6.gridx = 0;
        panel.add(logicalComboBox, gridBagConstraints_6);
        logicalComboBox.addItem("");
        for (int i = 0; i < LogicalEnum.values().length; i++) {
            logicalComboBox.addItem(LogicalEnum.values()[i]);
        }

        priceField = new JTextFieldLimited(63);
        priceField.setPreferredSize(new Dimension(100, 20));
        final GridBagConstraints gridBagConstraints_7 = new GridBagConstraints();
        gridBagConstraints_7.insets = new Insets(0, 0, 0, 0);
        gridBagConstraints_7.gridy = 0;
        gridBagConstraints_7.gridx = 1;
        panel.add(priceField, gridBagConstraints_7);

        final JLabel untilLabel = new JLabel();
        untilLabel.setText(Resources.getString("labels.to") + ":");
        final GridBagConstraints gridBagConstraints_11 = new GridBagConstraints();
        gridBagConstraints_11.insets = new Insets(3, 0, 0, 5);
        gridBagConstraints_11.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints_11.gridy = 2;
        gridBagConstraints_11.gridx = 2;
        northInnerPanel.add(untilLabel, gridBagConstraints_11);

        JCalendar jcalUntil = new JCalendar();
        jcalUntil.setWeekOfYearVisible(false);

        toChooser = new JDateChooser(jcalUntil);
        toChooser.setLocale(Resources.getLocale());
        final GridBagConstraints gridBagConstraints_14 = new GridBagConstraints();
        gridBagConstraints_14.anchor = GridBagConstraints.NORTHEAST;
        gridBagConstraints_14.gridy = 2;
        gridBagConstraints_14.gridx = 3;
        northInnerPanel.add(toChooser, gridBagConstraints_14);

        final JLabel commentLabel = new JLabel();
        commentLabel.setText(Resources.getString("labels.comments") + ":");
        final GridBagConstraints gridBagConstraints_15 = new GridBagConstraints();
        gridBagConstraints_15.anchor = GridBagConstraints.WEST;
        gridBagConstraints_15.insets = new Insets(10, 0, 10, 0);
        gridBagConstraints_15.gridy = 4;
        gridBagConstraints_15.gridx = 0;
        northInnerPanel.add(commentLabel, gridBagConstraints_15);

        commentsField = new AutosuggestTextFieldLimited(DBConstants.COMMENTS_LENGTH);
        commentsField.setPreferredSize(new Dimension(250, 20));
        final GridBagConstraints gridBagConstraints_16 = new GridBagConstraints();
        gridBagConstraints_16.anchor = GridBagConstraints.WEST;
        gridBagConstraints_16.insets = new Insets(0, 5, 0, 0);
        gridBagConstraints_16.gridy = 4;
        gridBagConstraints_16.gridx = 1;
        northInnerPanel.add(commentsField, gridBagConstraints_16);

        final JPanel panel_1 = new JPanel();
        final GridBagConstraints gridBagConstraints_12 = new GridBagConstraints();
        gridBagConstraints_12.insets = new Insets(10, 0, 0, 0);
        gridBagConstraints_12.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints_12.gridwidth = 4;
        gridBagConstraints_12.gridy = 5;
        gridBagConstraints_12.gridx = 0;
        northInnerPanel.add(panel_1, gridBagConstraints_12);

        findButton = new JButton();
        findButton.setText(Resources.getString("buttons.find"));
        findButton.setIcon(Resources.getIcon("find.png"));
        panel_1.add(findButton);

        cleanButton = new JButton();
        cleanButton.setText(Resources.getString("buttons.clear"));
        cleanButton.setIcon(Resources.getIcon("clear.gif"));
        panel_1.add(cleanButton);

        final JPanel centerPanel = new JPanel();
        centerPanel
                .setBorder(new TitledBorder(null, Resources.getString("labels.resulttable"),
                        TitledBorder.DEFAULT_JUSTIFICATION,
                        TitledBorder.DEFAULT_POSITION, null,
                        SystemColor.activeCaption));
        centerPanel.setLayout(new BorderLayout());
        // add(centerPanel, BorderLayout.CENTER);

        scrollPane = new JScrollPane();
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        table = new MyJTable();
        table.setSelectionBackground(ColorConstants.SELECTED_BACKGROUND);
        table.setSelectionForeground(ColorConstants.SELECTED_FOREGROUND);
        scrollPane.setViewportView(table);

        totalSumsPanel = new TotalSumsPanel();

        splitTablePane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, centerPanel,
        		totalSumsPanel);
        splitTablePane.setDividerSize(3);
        add(splitTablePane, BorderLayout.CENTER);
    }

    /**
     * Initializes the panel
     */
    public void initTables() {
        table.setModel(tableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }

    @Override
    public void initOnVisible() {
        if (firstOpen) {
            splitTablePane.setDividerLocation(0.7);
            initTableColumns();
            firstOpen = false;
        }
        
        fillComboBoxes();
        totalSumsPanel.updateCurrencies();
    }

    /**
     * Adds listeners to the compenents of the panel
     */
    private void addUIListeners() {
        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!validateData()) {
                    return;
                }

                ExpensesSearchParameters parameters = new ExpensesSearchParameters();
                fillInParameters(parameters);
                List<PExpense> expenses = ExpensesSearch.getInstance().findExpenses(parameters);
                
                if (expenses == null || expenses.size() == 0) {
                    tableModel.clear();
                    totalSumsPanel.clearData();
                    MyDialogs.showInfoDialog(ExpenseSearchPanel.this, Resources.getString("dialogs.info.noexpensesfound"));
                } else {
                    tableModel.reFill(expenses);
                    totalSumsPanel.setData(Calc.sumsByCurrency(expenses));
                }
            }
        });

        cleanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }

            private void clearForm() {
                expensePlanBox.setSelectedIndex(0);
                currencyBox.setSelectedIndex(0);
                categoryBox.setSelectedIndex(0);
                logicalComboBox.setSelectedIndex(0);
                priceField.setText("");
                periodCheckBox.setSelected(false);
                fromChooser.setDate(Calendar.getInstance().getTime());
                toChooser.setDate(Calendar.getInstance().getTime());
                tableModel.clear();
                totalSumsPanel.clearData();
                commentsField.setText("");
            }
        });

        expensePlanBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent arg0) {
                int index = expensePlanBox.getSelectedIndex();
                if (index > 0) {
                    currencyBox.setSelectedIndex(0);
                    currencyBox.setEnabled(false);
                } else {
                    currencyBox.setEnabled(true);
                }
            }
        });

        commentsField.setEnterKeyListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //do nothing
            }
        });

        commentsField.setSuggestionProvider(new AutosuggestTextFieldLimited.SuggestionProvider() {
            @Override
            public List<String> getValues(String text) {
                ArrayList<String> values = new ArrayList<String>();

                if(text != null && text.trim().length() > 0) {
                    values.addAll(SavedExpenseCommentController.instance().findComments(text.trim()));
                }

                return values;
            }
        });
    }

    /**
     * Validates the entered data This method has to be invoked before invoking
     * copyDataToModel()
     *
     * @return - true if the data is valid, otherwise false
     */
    private boolean validateData() {
        int logicalIndex = logicalComboBox.getSelectedIndex();
        if (logicalIndex != 0) {
            if (priceField.getText() == null
                    || priceField.getText().trim().equals("")) {
                MyDialogs.showWarningDialog(this, Resources.getString("dialogs.warnings.priceempty") + "!");
                return false;
            }

            if (!StringSumValidator.isValidPositiveFormat(Trimmer.trim(priceField.getText()))) {
                MyDialogs.showWarningDialog(this, Resources.getString("dialogs.warnings.invalidprice") + "!");
                return false;
            }
        }

        return true;
    }

    private void fillInParameters(ExpensesSearchParameters parameters) {
        int budgetIndex = expensePlanBox.getSelectedIndex() - 1;
        if (budgetIndex != -1) {
            parameters.setExpensePlan(getSelectedBudget());
        }

        int currencyIndex = currencyBox.getSelectedIndex() - 1;
        if (currencyIndex != -1) {
            parameters.setCurrency(getSelectedCurrency());
        }

        int categoryIndex = categoryBox.getSelectedIndex() - 1;
        if (categoryIndex != -1) {
            parameters.setCategory(getSelectedCategory());
        }

        int logicalIndex = logicalComboBox.getSelectedIndex() - 1;
        if (logicalIndex != -1) {
            parameters.setLogicalEnum(getSelectedLogical());
            parameters.setPrice(MyFormatter.formatNumberToStandard(priceField
                    .getText().trim()));
        }

        if (periodCheckBox.isSelected()) {
            parameters.setFromDate(fromChooser.getDate());
            parameters.setToDate(toChooser.getDate());
        }

        if(commentsField.getText() != null && commentsField.getText().trim().length() > 0) {
            parameters.setComment(commentsField.getText().trim());
        }
    }

    private PExpensePlan getSelectedBudget() {
        return (PExpensePlan) expensePlanBox.getSelectedItem();
    }

    private PCategory getSelectedCategory() {
        return (PCategory) categoryBox.getSelectedItem();
    }

    private PCurrency getSelectedCurrency() {
        return (PCurrency) currencyBox.getSelectedItem();
    }

    private LogicalEnum getSelectedLogical() {
        return (LogicalEnum) logicalComboBox.getSelectedItem();
    }

    /**
     * Sorter for the results table
     *
     * @author Petro_Verheles
     */
    private class SearchResultsSorter extends TableRowSorter<TableModel> {
        private SearchResultsSorter(TableModel model) {
            super(model);
        }

		@Override
        public Comparator<?> getComparator(int index) {

            if (index == ExpensesSearchColumnEnum.PRICE.getIndex()) {
                return ComparatorFactory.getInstance()
                        .getBigDecimalComparator();
            }

            if (index == ExpensesSearchColumnEnum.DATE.getIndex()) {
                return ComparatorFactory.getInstance().getDateComparator();
            }

            return super.getComparator(index);
        }

		
		
    }

    private void initTableColumns() {
        TableColumnsUtil.setPercentColumnWidths(table, scrollPane.getWidth(), ExpensesSearchColumnEnum.getColumnPercents());
        TableColumnsUtil.setPercentColumnWidths(totalSumsPanel.getSumsTable(), totalSumsPanel.getAreaWidth(), TotalSumsColumnEnum.getColumnPercents());
        
        SearchResultsSorter sorter = new SearchResultsSorter(table.getModel());
        table.setRowSorter(sorter);
        List<SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();
        sortKeys.add( new RowSorter.SortKey(ExpensesSearchColumnEnum.DATE.getIndex(), SortOrder.DESCENDING) );
        sorter.setSortKeys(sortKeys);
        sorter.sort();                
    }
    
    private void fillComboBoxes() {
    	expensePlanBox.removeAllItems();
    	currencyBox.removeAllItems();
    	categoryBox.removeAllItems();
    	
        PExpensePlan dummyBudget = new PExpensePlan();
        dummyBudget.setName("");
        expensePlanBox.addItem(dummyBudget);

        PCurrency dummyCurrency = new PCurrency();
        currencyBox.addItem(dummyCurrency);

        PCategory dummyCategory = new PCategory();
        dummyCategory.setName("");
        categoryBox.addItem(dummyCategory);
        
        List<PExpensePlan> sortedBudgets = new ArrayList<PExpensePlan>(ExpensePlanController.instance().getAll());
        NammableSorter.sort(sortedBudgets);
        for(PExpensePlan budget : sortedBudgets) {
        	expensePlanBox.addItem(budget);
        }
        
        List<PCurrency> sortedCurrencies = new ArrayList<PCurrency>(CurrencyController.instance().getAll());
        NammableSorter.sort(sortedCurrencies);
        for(PCurrency currency : sortedCurrencies) {
        	currencyBox.addItem(currency);
        }
        
        List<PCategory> sortedCategories = new ArrayList<PCategory>(CategoryController.instance().getAll());
        NammableSorter.sort(sortedCategories);
        for(PCategory category : sortedCategories) {
        	categoryBox.addItem(category);
        }
    }

}
