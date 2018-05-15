package com.monyrama.ui.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.SystemColor;
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
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.monyrama.controller.ControllerListener;
import com.monyrama.controller.LendController;
import com.monyrama.entity.PLend;
import com.monyrama.ui.components.MyJTable;
import com.monyrama.ui.components.totalsumspanel.TotalSumsPanel;
import com.monyrama.ui.constants.ColorConstants;
import com.monyrama.ui.dialogs.LendDialog;
import com.monyrama.ui.dialogs.TakeBackDialog;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.tables.columns.LendColumnEnum;
import com.monyrama.ui.tables.columns.TotalSumsColumnEnum;
import com.monyrama.ui.tables.model.LendTableModel;
import com.monyrama.ui.utils.ComparatorFactory;
import com.monyrama.utils.Calc;


/**
 * @author Petro_Verheles
 */
public class LendPanel extends GeneralPanel {
    private JTable table;

    private JButton lendButton;
    private JButton takeBackButton;
    private JScrollPane scrollPane;
    private JPopupMenu popupMenu;
    private JSplitPane splitPane;
   
    private Action lendAction;
    private Action takeBackAction;
    
    private LendTableModel tableModel = new LendTableModel();
    
    private boolean firstOpened = true;

	private TotalSumsPanel totalSumsPanel;

    public LendPanel() {
        super();
        setLayout(new BorderLayout());
        createActions();
        createAndPaintComponents();
        initializeTables();
        createPopUp();
        addListeners();
        addLendListener();
    }

	private void createAndPaintComponents() {
		final JPanel northPanel = new JPanel();
        final FlowLayout flowLayout = new FlowLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        northPanel.setLayout(flowLayout);
        add(northPanel, BorderLayout.NORTH);

        lendButton = new JButton(lendAction);
        northPanel.add(lendButton);

        takeBackButton = new JButton(takeBackAction);
        northPanel.add(takeBackButton);

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

        JPanel southPanel = new JPanel();
        southPanel.setBorder(new TitledBorder(null, Resources.getString("labels.totaldepositorysum"), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, SystemColor.activeCaption));
        southPanel.setLayout(new BorderLayout());

        totalSumsPanel = new TotalSumsPanel();

        splitPane.setBottomComponent(totalSumsPanel);

        add(splitPane, BorderLayout.CENTER);
	}

	private void createActions() {
		lendAction = new LendAction();
        takeBackAction = new TakeBackAction();
        takeBackAction.setEnabled(false);
	}

    /**
     * Adds listener to the components
     */
    private void addListeners() {
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                int rowIndex = table.getSelectedRow();
                if (rowIndex == -1) {
                    takeBackAction.setEnabled(false);
                } else {
                    takeBackAction.setEnabled(true);
                }
            }

        });
    }

    /**
     * Initializes the panel
     */
    private void initializeTables() {
        table.setModel(tableModel);
    	
        table.getColumnModel().getColumn(LendColumnEnum.ID.getIndex()).setMinWidth(0);
        table.getColumnModel().getColumn(LendColumnEnum.ID.getIndex()).setMaxWidth(0);

        RowCellRenderer cellRenderer = new RowCellRenderer();
        for (int i = 0; i < LendColumnEnum.values().length; i++) {
            table.getColumn(LendColumnEnum.values()[i].getName()).setCellRenderer(cellRenderer);
        }

    }

    /**
     * Creates the popup menu for the table and scrollpane
     */
    public void createPopUp() {
        popupMenu = new JPopupMenu();
        popupMenu.add(new JMenuItem(lendAction));
        popupMenu.add(new JMenuItem(takeBackAction));

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

            return comp;
        }
    }

    /*---------------------ACTIONS----------------------------------*/

    private class LendAction extends AbstractAction {
        public LendAction() {
            super(Resources.getString("buttons.lend") + "...", Resources.getIcon("lends.png"));
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {        	
            LendDialog.openDialog();
        }
    }

    private class TakeBackAction extends AbstractAction {
        public TakeBackAction() {
            super(Resources.getString("buttons.takeback") + "...", Resources.getIcon("debts.png"));
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            int rowIndex = table.getSelectedRow();
            Long id = (Long) table.getValueAt(rowIndex, LendColumnEnum.ID.getIndex());
            PLend lend = tableModel.getItemById(id);
            TakeBackDialog.openDialog(lend);
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
        
        tableModel.reFill(LendController.instance().listActive());
        totalSumsPanel.updateCurrencies();
        recalculateSumsByCurrency();
    }

	private class LendsSorter extends TableRowSorter<TableModel> {

		private LendsSorter(TableModel model) {
			super(model);
		}

		@Override
		public Comparator<?> getComparator(int index) {			

			if(index == LendColumnEnum.SUM.getIndex()) {
				return ComparatorFactory.getInstance().getBigDecimalComparator();
			}
			
			if(index == LendColumnEnum.DATE.getIndex()) {
				return ComparatorFactory.getInstance().getDateComparator();
			}
			
			return super.getComparator(index);
		}		
	}    
    
    private void initTableColumns() {
        TableColumnsUtil.setPercentColumnWidths(table, scrollPane.getWidth(), LendColumnEnum.getColumnPercents());
        TableColumnsUtil.setPercentColumnWidths(totalSumsPanel.getSumsTable(), totalSumsPanel.getAreaWidth(), TotalSumsColumnEnum.getColumnPercents());
        
        LendsSorter sorter = new LendsSorter(tableModel);
        table.setRowSorter(sorter);
        List<SortKey> list = new ArrayList<RowSorter.SortKey>();
        list.add( new RowSorter.SortKey(LendColumnEnum.DEBTOR_NAME.getIndex(), SortOrder.ASCENDING) );
        sorter.setSortKeys(list);
        sorter.sort();        
	}
    
    private void addLendListener() {
    	LendController.instance().addListener(new ControllerListener<PLend>() {
			
			@Override
			public void deleted(PLend lend) {
				tableModel.removeItem(lend);
				recalculateSumsByCurrency();
			}
			
			@Override
			public void createdOrUpdated(PLend lend) {
				if(lend.isActive()) {
					tableModel.putItem(lend);	
				} else {
					tableModel.removeItem(lend);
				}
                
                recalculateSumsByCurrency();				
			}
		});
    }
    
	private void recalculateSumsByCurrency() {
		Collection<PLend> lendList = tableModel.getDataCollection();
        List<PLend> recalculatedLends = new ArrayList<PLend>();
        for(PLend lend : lendList) {
            PLend recalculatedLend = new PLend();
            recalculatedLend.setAccount(lend.getAccount());
            recalculatedLend.setSumm(lend.getSumm().subtract(Calc.sum(lend.getTakingBacks())));
            recalculatedLends.add(recalculatedLend);
        }
		if(lendList == null || lendList.size() == 0) {
			totalSumsPanel.clearData();
		} else {			
			totalSumsPanel.setData(Calc.sumsByCurrency(recalculatedLends));
		}
	}

}
