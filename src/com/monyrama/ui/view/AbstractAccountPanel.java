package com.monyrama.ui.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.RowSorter.SortKey;
import javax.swing.SortOrder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.monyrama.ui.view.TableColumnsUtil;
import com.monyrama.controller.DBConditions;
import com.monyrama.entity.PAccount;
import com.monyrama.ui.components.MyJTable;
import com.monyrama.ui.components.totalsumspanel.TotalSumsPanel;
import com.monyrama.ui.constants.ColorConstants;
import com.monyrama.ui.tables.columns.AccountColumnEnum;
import com.monyrama.ui.tables.columns.TotalSumsColumnEnum;
import com.monyrama.ui.tables.model.AccountTableModel;
import com.monyrama.ui.utils.ComparatorFactory;
import com.monyrama.utils.Calc;


/**
 * @author Petro_Verheles
 */
abstract class AbstractAccountPanel extends GeneralPanel {    
    
    protected JTable table;
    protected AccountTableModel tableModel = new AccountTableModel();

    private boolean firstOpened = true;

    protected JScrollPane tablePane;

    private JSplitPane splitPane;

	private TotalSumsPanel totalSumsPanel;

    public AbstractAccountPanel() {
        super();
        createAndInitializeActions();
        createAndPaintComponents();
        initializeTables();
        createPopUp();
        addAccountListener();
        addListeners();
    }

    protected abstract void createAndInitializeActions();

    protected void createAndPaintComponents() {
        setLayout(new BorderLayout());

        JPanel buttonsPanel = getButtonsPanel();        
        add(buttonsPanel, BorderLayout.NORTH);

        splitPane = new JSplitPane();
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerSize(3);

        tablePane = new JScrollPane();
        splitPane.setTopComponent(tablePane);
        table = new MyJTable();
        table.setSelectionBackground(ColorConstants.SELECTED_BACKGROUND);
        table.setSelectionForeground(ColorConstants.SELECTED_FOREGROUND);
        tablePane.setViewportView(table);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        totalSumsPanel = new TotalSumsPanel();

        splitPane.setBottomComponent(totalSumsPanel);

        add(splitPane, BorderLayout.CENTER);
    }

	protected abstract JPanel getButtonsPanel();

    /**
     * Adds listener to the components
     */
    private void addListeners() {
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
            	accountSelectionChanged();
            }

        });
    }
    
    protected abstract void accountSelectionChanged();

    public void initializeTables() {
        table.setModel(tableModel);

        table.getColumnModel().getColumn(AccountColumnEnum.ID.getIndex()).setMinWidth(0);
        table.getColumnModel().getColumn(AccountColumnEnum.ID.getIndex()).setMaxWidth(0);

        RowCellRenderer cellRenderer = new RowCellRenderer();
        for (int i = 0; i < AccountColumnEnum.values().length; i++) {
            table.getColumn(AccountColumnEnum.values()[i].getName()).setCellRenderer(cellRenderer);
        }
    }

    /**
     * Creates the popup menu for the table and scrollpane
     */
    protected abstract void createPopUp();

    /**
     * Cell renderer for the table
     *
     * @author Petro_Verheles
     */
    private class RowCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table,
                                                       Object value, boolean isSelected, boolean hasFocus, int row,
                                                       int column) {

            Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            if (isSelected == true) {
                return comp;
            }

            Long id = (Long) table.getValueAt(row, AccountColumnEnum.ID.getIndex());

            PAccount account = tableModel.getItemById(id);
                        
            if(account.getSumm().compareTo(BigDecimal.ZERO) < 0) {
            	comp.setBackground(ColorConstants.BE_ATTANTIVE_COLOR);
            } else if (DBConditions.accountHasMoneyMovements(account)) {
                comp.setBackground(ColorConstants.USED);
            } else {
                comp.setBackground(table.getBackground());
            }           

            return comp;
        }
    }

    @Override
    public void initOnVisible() {
        if (firstOpened) {
            splitPane.setDividerLocation(0.7);
            splitPane.doLayout();
            initTableColumns();
            firstOpened = false;
        }

        updateUIData();
    }

    protected void updateUIData() {
        tableModel.reFill(getAccounts());
        totalSumsPanel.updateCurrencies();
        recalculateSumsByCurrency();
    }

    protected abstract List<PAccount> getAccounts();

	private class AccountSorter extends TableRowSorter<TableModel> {

		private AccountSorter(TableModel model) {
			super(model);
		}

		@Override
		public Comparator<?> getComparator(int index) {			

			if(index == AccountColumnEnum.SUM.getIndex()) {
				return ComparatorFactory.getInstance().getBigDecimalComparator();
			}
			
			return super.getComparator(index);
		}		
	}
	
    private void initTableColumns() {
        TableColumnsUtil.setPercentColumnWidths(table, tablePane.getWidth(), AccountColumnEnum.getColumnPercents());
        TableColumnsUtil.setPercentColumnWidths(totalSumsPanel.getSumsTable(), totalSumsPanel.getAreaWidth(), TotalSumsColumnEnum.getColumnPercents());
        
        AccountSorter sorter = new AccountSorter(tableModel);
        table.setRowSorter(sorter);
        List<SortKey> list = new ArrayList<RowSorter.SortKey>();
        list.add(new RowSorter.SortKey(AccountColumnEnum.NAME.getIndex(), SortOrder.ASCENDING) );
        sorter.setSortKeys(list);
        sorter.sort();
    }

    protected abstract void addAccountListener();

    protected void recalculateSumsByCurrency() {
    	Collection<PAccount> accountList = getAccounts();
        if (accountList == null || accountList.size() == 0) {
            totalSumsPanel.clearData();
        } else {
            totalSumsPanel.setData(Calc.sumsByCurrency(accountList));
		}	
	}

	protected PAccount getSelectedAccount() {
        int row = table.getSelectedRow();
        if(row > -1) {
            Long id = (Long) table.getValueAt(row, AccountColumnEnum.ID.getIndex());
            PAccount account = tableModel.getItemById(id);
            return account;	
        } else {
        	return null;
        }
	}	    
  
}