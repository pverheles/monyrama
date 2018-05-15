package com.monyrama.ui.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.RowSorter.SortKey;
import javax.swing.SortOrder;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.monyrama.controller.ControllerListener;
import com.monyrama.controller.DebtController;
import com.monyrama.entity.PDebt;
import com.monyrama.ui.components.MyJTable;
import com.monyrama.ui.components.totalsumspanel.TotalSumsPanel;
import com.monyrama.ui.constants.ColorConstants;
import com.monyrama.ui.dialogs.DebtDialog;
import com.monyrama.ui.dialogs.PayBackDialog;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.tables.columns.CategoryColumnEnum;
import com.monyrama.ui.tables.columns.DebtColumnEnum;
import com.monyrama.ui.tables.columns.TotalSumsColumnEnum;
import com.monyrama.ui.tables.model.DebtTableModel;
import com.monyrama.ui.utils.ComparatorFactory;
import com.monyrama.utils.Calc;


/**
 * @author Petro_Verheles
 */
public class DebtPanel extends GeneralPanel {
    private DebtTableModel tableModel = new DebtTableModel();
    
    private JTable table;

    private JButton borrowButton;
    private JButton payBackButton;

    private Action borrowAction;
    private Action payBackAction;

    private boolean firstOpened = true;

    private JScrollPane scrollPane;

    private JPopupMenu popupMenu;
    private JSplitPane splitPane;

	private TotalSumsPanel totalSumsPanel;

    public DebtPanel() {
        super();
        setLayout(new BorderLayout());
        createAndInitializeActions();
        createAndPaintComponents();
        initializeTables();
        createPopUp();
        addListeners();
        addDebtListeners();
    }

    private void createAndInitializeActions() {
        borrowAction = new BorrowAction();
        payBackAction = new PayBackAction();
        payBackAction.setEnabled(false);
    }

    private void createAndPaintComponents() {
        setLayout(new BorderLayout());
        final JPanel northPanel = new JPanel();
        final FlowLayout flowLayout = new FlowLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        northPanel.setLayout(flowLayout);
        add(northPanel, BorderLayout.NORTH);

        borrowButton = new JButton(borrowAction);
        northPanel.add(borrowButton);

        payBackButton = new JButton(payBackAction);
        northPanel.add(payBackButton);

        splitPane = new JSplitPane();
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerSize(3);

        scrollPane = new JScrollPane();
        splitPane.setTopComponent(scrollPane);
        table = new MyJTable();
        table.setSelectionBackground(ColorConstants.SELECTED_BACKGROUND);
        table.setSelectionForeground(ColorConstants.SELECTED_FOREGROUND);
        scrollPane.setViewportView(table);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        totalSumsPanel = new TotalSumsPanel();
        
        splitPane.setBottomComponent(totalSumsPanel);

        add(splitPane, BorderLayout.CENTER);
        createPopUp();
    }

    private void addDebtListeners() {
    	DebtController.instance().addListener(new ControllerListener<PDebt>() {
			
			@Override
			public void deleted(PDebt debt) {
                tableModel.removeItem(debt);
                recalculateSumsByCurrency();			
			}
			
			@Override
			public void createdOrUpdated(PDebt debt) {
				if(debt.isActive()) {
					tableModel.putItem(debt);	
				} else {
					tableModel.removeItem(debt);
				}
                
                recalculateSumsByCurrency();
			}
		});
    }

    private void initializeTables() {

        table.setModel(tableModel);

        table.getColumnModel().getColumn(CategoryColumnEnum.ID.getIndex()).setMinWidth(0);
        table.getColumnModel().getColumn(CategoryColumnEnum.ID.getIndex()).setMaxWidth(0);

        RowCellRenderer cellRenderer = new RowCellRenderer();
        for (int i = 0; i < DebtColumnEnum.values().length; i++) {
            table.getColumn(DebtColumnEnum.values()[i].getName()).setCellRenderer(cellRenderer);
        }

    }

    public void createPopUp() {
        popupMenu = new JPopupMenu();
        popupMenu.add(new JMenuItem(borrowAction));
        popupMenu.add(new JMenuItem(payBackAction));

        MouseAdapter mouseAdapter = new MouseAdapter() {
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
                        int rowNumber = table.rowAtPoint(p);
                        ListSelectionModel model = table.getSelectionModel();
                        model.setSelectionInterval(rowNumber, rowNumber);
                    }
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        };

        table.addMouseListener(mouseAdapter);
        scrollPane.addMouseListener(mouseAdapter);
    }

    private void addListeners() {
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int rowIndex = table.getSelectedRow();
                if (rowIndex == -1) {
                    payBackAction.setEnabled(false);
                } else {
                    payBackAction.setEnabled(true);
                }
            }

        });

    }

    private class RowCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table,
                                                       Object value, boolean isSelected, boolean hasFocus, int row,
                                                       int column) {

            Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (isSelected == true) {
                return comp;
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
        
        tableModel.reFill(DebtController.instance().listActive());
        totalSumsPanel.updateCurrencies();
        recalculateSumsByCurrency();
    }
    
	private class DebtsSorter extends TableRowSorter<TableModel> {

		private DebtsSorter(TableModel model) {
			super(model);
		}

		@Override
		public Comparator<?> getComparator(int index) {			

			if(index == DebtColumnEnum.SUM.getIndex()) {
				return ComparatorFactory.getInstance().getBigDecimalComparator();
			}
			
			if(index == DebtColumnEnum.DATE.getIndex()) {
				return ComparatorFactory.getInstance().getDateComparator();
			}
			
			return super.getComparator(index);
		}		
	}
	
    private void initTableColumns() {
        TableColumnsUtil.setPercentColumnWidths(table, scrollPane.getWidth(), DebtColumnEnum.getColumnPercents());
        TableColumnsUtil.setPercentColumnWidths(totalSumsPanel.getSumsTable(), totalSumsPanel.getAreaWidth(), TotalSumsColumnEnum.getColumnPercents());
        
        DebtsSorter sorter = new DebtsSorter(tableModel);
        table.setRowSorter(sorter);
        List<SortKey> list = new ArrayList<RowSorter.SortKey>();
        list.add( new RowSorter.SortKey(DebtColumnEnum.LENDER_NAME.getIndex(), SortOrder.ASCENDING) );
        sorter.setSortKeys(list);
        sorter.sort();
    }


    /*---------------------ACTIONS----------------------------------*/

    private class BorrowAction extends AbstractAction {
        public BorrowAction() {
            super(Resources.getString("buttons.borrow") + "...", Resources.getIcon("debts.png"));
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            DebtDialog.openDialog();
        }
    }

    private class PayBackAction extends AbstractAction {
        public PayBackAction() {
            super(Resources.getString("buttons.payback") + "...", Resources.getIcon("lends.png"));
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            int rowIndex = table.getSelectedRow();
            Long id = (Long) table.getValueAt(rowIndex, DebtColumnEnum.ID.getIndex());
            PDebt debt = tableModel.getItemById(id);
            PayBackDialog.openDialog(debt);
        }
    }
    
    private void recalculateSumsByCurrency() {
        Collection<PDebt> debtList = tableModel.getDataCollection();
        List<PDebt> recalculatedDebts = new ArrayList<PDebt>();
        for(PDebt debt : debtList) {
            PDebt recalculatedDebt = new PDebt();
            recalculatedDebt.setAccount(debt.getAccount());
            recalculatedDebt.setSumm(debt.getSumm().subtract(Calc.sum(debt.getPayingBacks())));
            recalculatedDebts.add(recalculatedDebt);
        }
        if (debtList == null || debtList.size() == 0) {
        	totalSumsPanel.clearData();
        } else {
        	totalSumsPanel.setData(Calc.sumsByCurrency(recalculatedDebts));
        }
    }

}

