package com.monyrama.ui.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
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
import javax.swing.table.TableRowSorter;

import com.monyrama.controller.AccountController;
import com.monyrama.controller.ControllerListener;
import com.monyrama.controller.DBConditions;
import com.monyrama.controller.TransferController;
import com.monyrama.entity.PAccount;
import com.monyrama.entity.PTransfer;
import com.monyrama.server.MobileDataListener;
import com.monyrama.server.MobileDataManager;
import com.monyrama.ui.components.JDateChooser;
import com.monyrama.ui.components.MyJTable;
import com.monyrama.ui.constants.ColorConstants;
import com.monyrama.ui.dialogs.TransferDialog;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.tables.columns.TransferColumnEnum;
import com.monyrama.ui.tables.model.TransferTableModel;
import com.monyrama.ui.utils.MyDialogs;


/**
 * Represents Categories panel
 *
 * @author Petro_Verheles
 */
public class TransferPanel extends GeneralPanel {
    
    private TransferTableModel transferTableModel = new TransferTableModel();

    private JTable transferTable;

    private JButton addButton;
    private JButton removeButton;

    private Action transferAction;
    private Action removeAction;
    private Action applyAction;

    private JScrollPane scrollPane;

    private JPopupMenu popupMenu;

    private boolean visibleFirstTime = true;

	private JDateChooser fromDateChooser;
    private JDateChooser toDateChooser;
    private JButton applyButton;


    /**
     * Constructor
     * <p/>
     * Creates the panel
     */
    public TransferPanel() {
        super();
        setLayout(new BorderLayout());
        createAndInitializeActions();
        createAndPaintComponents();
        initializeTable();
        addListeners();
        createPopUp();
        addTransferControllerListener();
        checkIfTransferAllowed();
        addAccountsListener();
    }

	private void checkIfTransferAllowed() {
		if(DBConditions.moreThanOneActiveAccountExist()) {
        	transferAction.setEnabled(true);
        } else {
        	transferAction.setEnabled(false);
        }
	}

    /**
     * Adds listener to the components
     */
    private void addListeners() {
        transferTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                int rowIndex = transferTable.getSelectedRow();
                if (rowIndex == -1) {
                    removeAction.setEnabled(false);
                } else {
                	removeAction.setEnabled(true);
                }

            }
        });
    }

    private void createAndPaintComponents() {
        final JPanel northPanel = new JPanel();
        final FlowLayout flowLayout = new FlowLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        northPanel.setLayout(flowLayout);
        add(northPanel, BorderLayout.NORTH);

        addButton = new JButton(transferAction);
        northPanel.add(addButton);

        removeButton = new JButton(removeAction);
        northPanel.add(removeButton);
        
        JSplitPane splitPane = new JSplitPane();
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerSize(3);

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        splitPane.setTopComponent(tablePanel);

        JPanel filterPanel = new JPanel();
        tablePanel.add(filterPanel, BorderLayout.NORTH);
        
        filterPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        filterPanel.add(new JLabel(Resources.getString("labels.from") + ": "));
        
        fromDateChooser = new JDateChooser();
        fromDateChooser.setLocale(Resources.getLocale());
        filterPanel.add(fromDateChooser);

        Calendar monthAgoCal = Calendar.getInstance();
        monthAgoCal.add(Calendar.MONTH, -1);
        Date initialFromDate = monthAgoCal.getTime();
        fromDateChooser.setDate(initialFromDate);

        filterPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        filterPanel.add(new JLabel("  " + Resources.getString("labels.to") + ": "));

        toDateChooser = new JDateChooser();
        toDateChooser.setLocale(Resources.getLocale());
        filterPanel.add(toDateChooser);

        applyButton = new JButton(applyAction);
        filterPanel.add(applyButton);
        
        scrollPane = new JScrollPane();
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        transferTable = new MyJTable();
        transferTable.setSelectionBackground(ColorConstants.SELECTED_BACKGROUND);
        transferTable.setSelectionForeground(ColorConstants.SELECTED_FOREGROUND);
        transferTable.setRowHeight(20);
        scrollPane.setViewportView(transferTable);
        transferTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        add(tablePanel, BorderLayout.CENTER);

        transferTable.setModel(transferTableModel);
        TransferRowCellRenderer cellRenderer = new TransferRowCellRenderer();
        for (int i = 0; i < TransferColumnEnum.values().length; i++) {
            transferTable.getColumn(TransferColumnEnum.values()[i].getName()).setCellRenderer(cellRenderer);
        }
    }

    private void createAndInitializeActions() {
        transferAction = new AddAction();
        removeAction = new RemoveAction();
        removeAction.setEnabled(false);
        applyAction = new ApplyAction();
    }

    private void initializeTable() {
        transferTable.getColumnModel().getColumn(TransferColumnEnum.ID.getIndex()).setMinWidth(0);
        transferTable.getColumnModel().getColumn(TransferColumnEnum.ID.getIndex()).setMaxWidth(0);
        transferTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }

    /**
     * Initializes the panel when it becomes visible
     */
    @Override
    public void initOnVisible() {
        if (visibleFirstTime) {
            initTableColumns();
            visibleFirstTime = false;
        }

        updateTransfersTable();
    }

    protected void updateTransfersTable() {
        transferTableModel.reFill(TransferController.instance().listByDates(fromDateChooser.getDate(), toDateChooser.getDate()));
    }

    /**
     * Creates the popup menu for the transferTable and scrollpane
     */
    public void createPopUp() {
        popupMenu = new JPopupMenu();
        popupMenu.add(new JMenuItem(transferAction));
        popupMenu.add(new JMenuItem(removeAction));

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
                        int rowNumber = transferTable.rowAtPoint(p);
                        ListSelectionModel model = transferTable.getSelectionModel();
                        model.setSelectionInterval(rowNumber, rowNumber);
                    }
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        };

        transferTable.addMouseListener(mouseAdapter);
        scrollPane.addMouseListener(mouseAdapter);
    }
    /*---------------------ACTIONS----------------------------------*/

    private class AddAction extends AbstractAction {
        public AddAction() {
            super(Resources.getString("buttons.transfer") + "...",
                    Resources.getIcon("transfer.png"));
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
           TransferDialog.openDialog();
        }
    }

    private class RemoveAction extends AbstractAction {
        public RemoveAction() {
            super(Resources.getString("buttons.remove"),
                    Resources.getIcon("remove.png"));
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            int result = MyDialogs.showYesNoDialog(TransferPanel.this, Resources.getString("dialogs.questions.removetransfer") + "?");
            if (result == MyDialogs.YES) {
                int rowIndex = transferTable.getSelectedRow();
                Long id = (Long) transferTable.getValueAt(rowIndex, TransferColumnEnum.ID.getIndex());
                PTransfer category = transferTableModel.getItemById(id);
                TransferController.instance().delete(category);
            }
        }
    }

    private class ApplyAction extends AbstractAction {
        public ApplyAction() {
            super(Resources.getString("buttons.apply"),
                    Resources.getIcon("ok.png"));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            updateTransfersTable();
        }
    }

    private void initTableColumns() {
        TableColumnsUtil.setPercentColumnWidths(transferTable, scrollPane.getWidth(), TransferColumnEnum.getColumnPercents());
        
		TableRowSorter<TransferTableModel> sorter = new TableRowSorter<TransferTableModel>(transferTableModel);
        transferTable.setRowSorter(sorter);

        List<SortKey> list = new ArrayList<RowSorter.SortKey>();
        list.add( new RowSorter.SortKey(TransferColumnEnum.DATE.getIndex(), SortOrder.DESCENDING) );
        sorter.setSortKeys(list);
        sorter.sort();
    }

    private void addTransferControllerListener() {
    	TransferController.instance().addListener(new ControllerListener<PTransfer>() {			
			@Override
			public void deleted(PTransfer transfer) {
				transferTableModel.removeItem(transfer);
			}
			
			@Override
			public void createdOrUpdated(PTransfer transfer) {
				transferTableModel.putItem(transfer);
			}
		});
	}
    
	private void addAccountsListener() {
		AccountController.instance().addListener(new ControllerListener<PAccount>() {

			@Override
			public void createdOrUpdated(PAccount object) {
				checkIfTransferAllowed();
			}

			@Override
			public void deleted(PAccount object) {
				checkIfTransferAllowed();
			}
		});

        MobileDataManager.addMobileDataListener(new MobileDataListener() {
            @Override
            public void mobileDataSaved() {
                updateTransfersTable();
            }
        });
	}

    private class TransferRowCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table,
                                                       Object value, boolean isSelected, boolean hasFocus, int row,
                                                       int column) {
            Long id = (Long) table.getValueAt(row, TransferColumnEnum.ID.getIndex());
            PTransfer transfer = transferTableModel.getItemById(id);

            if(column == TransferColumnEnum.DATE.getIndex()) {
                value = formatDate(transfer.getLastChangeDate());
            }

            Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            return comp;
        }
    }
}
