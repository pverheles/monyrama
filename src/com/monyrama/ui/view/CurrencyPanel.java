package com.monyrama.ui.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
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

import com.monyrama.controller.ControllerListener;
import com.monyrama.entity.PCurrency;
import com.monyrama.entity.PSetting;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.utils.MyDialogs;
import com.monyrama.controller.ControllerListener;
import com.monyrama.controller.CurrencyController;
import com.monyrama.controller.DBConditions;
import com.monyrama.controller.SettingController;
import com.monyrama.entity.PCurrency;
import com.monyrama.entity.PSetting;
import com.monyrama.ui.components.CheckboxCell;
import com.monyrama.ui.components.MyJTable;
import com.monyrama.ui.constants.ColorConstants;
import com.monyrama.ui.dialogs.CurrencyDialog;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.tables.columns.CategoryColumnEnum;
import com.monyrama.ui.tables.columns.CurrencyColumnEnum;
import com.monyrama.ui.tables.model.CurrencyTableModel;
import com.monyrama.ui.utils.MyDialogs;
import com.monyrama.ui.view.GeneralPanel;
import com.monyrama.utils.Calc;


/**
 * Represents Currencies panel
 *
 * @author Petro_Verheles
 */
public class CurrencyPanel extends GeneralPanel {
    private CurrencyTableModel tableModel = new CurrencyTableModel();

    private JTable table;

    private JButton addButton;
    private JButton editButton;
    private JButton removeButton;
    private JButton setAsMainButton;

    private Action addAction;
    private Action editAction;
    private Action removeAction;
    private Action setAsMainAction;

    private boolean firstOpened = true;

    private JScrollPane scrollPane;

    private JPopupMenu popupMenu;

    /**
     * Constructor
     * <p/>
     * Creates the panel
     */
    public CurrencyPanel() {
        super();
        setLayout(new BorderLayout());
        createAndInitializeActions();
        createAndPaintComponents();
        initializeTable();
        createPopUp();
        addListeners();
        addCurrencyControllerListener();
    }

    private void createAndInitializeActions() {
        addAction = new AddAction();
        editAction = new EditAction();
        editAction.setEnabled(false);
        removeAction = new RemoveAction();
        removeAction.setEnabled(false);
        setAsMainAction = new SetAsMainAction();
        setAsMainAction.setEnabled(false);
    }

    private void createAndPaintComponents() {
        final JPanel northPanel = new JPanel();
        final FlowLayout flowLayout = new FlowLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        northPanel.setLayout(flowLayout);
        add(northPanel, BorderLayout.NORTH);

        addButton = new JButton(addAction);
        northPanel.add(addButton);

        editButton = new JButton(editAction);
        northPanel.add(editButton);

        removeButton = new JButton(removeAction);
        northPanel.add(removeButton);
        
        setAsMainButton = new JButton(setAsMainAction);
        northPanel.add(setAsMainButton);

//        JSplitPane splitPane = new JSplitPane();
//        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
//        splitPane.setDividerSize(3);

        scrollPane = new JScrollPane();
        //splitPane.setTopComponent(scrollPane);
        table = new MyJTable();
        table.setSelectionBackground(ColorConstants.SELECTED_BACKGROUND);
        table.setSelectionForeground(ColorConstants.SELECTED_FOREGROUND);
        scrollPane.setViewportView(table);
        table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //InfoPanel infoPanel = new InfoPanel(splitPane);
        
        add(scrollPane, BorderLayout.CENTER);

//        JPanel infoContentPanel = infoPanel.getContentPanel();
//        int gridy = 0;
//        infoContentPanel.setLayout(new GridBagLayout());
//        GridBagConstraints gridBagConstraints = new GridBagConstraints();
//        gridBagConstraints.gridx = 0;
//        gridBagConstraints.gridy = gridy;
//        gridBagConstraints.weightx = 1.0;
//        gridBagConstraints.insets = new Insets(0, 2, 2, 0);
//        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
//        infoContentPanel.add(new ColorSquareLabel(ColorConstants.MAIN_CURRENCY_COLOR, Resources.getString("explain.maincurrency"), 16, 16), gridBagConstraints);
//        
//        gridBagConstraints.gridy = ++gridy;
//        infoContentPanel.add(new ColorSquareLabel(table.getBackground(), Resources.getString("explain.unusedcurrencies"), 16, 16), gridBagConstraints);
//
//        gridBagConstraints.gridy = ++gridy;
//        infoContentPanel.add(new ColorSquareLabel(ColorConstants.USED, Resources.getString("explain.usedcurrencies"), 16, 16), gridBagConstraints);
//
//        gridBagConstraints.gridy = ++gridy;
//        infoContentPanel.add(new ColorSquareLabel(table.getSelectionBackground(), Resources.getString("explain.selectedcurrency"), 16, 16), gridBagConstraints);
//
//        splitPane.setBottomComponent(infoPanel);
//        splitPane.setResizeWeight(0.8);

//        add(splitPane, BorderLayout.CENTER);
    }

    private void initializeTable() {
        table.setModel(tableModel);

        table.getColumnModel().getColumn(CategoryColumnEnum.ID.getIndex()).setMinWidth(0);
        table.getColumnModel().getColumn(CategoryColumnEnum.ID.getIndex()).setMaxWidth(0);

        RowCellRenderer cellRenderer = new RowCellRenderer();
        for (int i = 0; i < CurrencyColumnEnum.values().length; i++) {
            table.getColumn(CurrencyColumnEnum.values()[i].getName()).setCellRenderer(cellRenderer);
        }
    }

    @Override
    public void initOnVisible() {
        if (firstOpened) {
            initTableColumns();            
            
            firstOpened = false;
        }
        
        tableModel.reFill(CurrencyController.instance().getAll());
        tableModel.fireTableDataChanged(); //just to remove selection
    }

	private void initTableColumns() {
		TableColumnsUtil.setPercentColumnWidths(table, scrollPane.getWidth(), CurrencyColumnEnum.getColumnPercents());
		
		TableRowSorter<CurrencyTableModel> sorter = new TableRowSorter<CurrencyTableModel>(tableModel);
		table.setRowSorter(sorter);
		
		List<SortKey> list = new ArrayList<RowSorter.SortKey>();
		list.add( new RowSorter.SortKey(CurrencyColumnEnum.NAME.getIndex(), SortOrder.ASCENDING) );
		sorter.setSortKeys(list);
		sorter.sort();
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
                    editAction.setEnabled(false);
                    removeAction.setEnabled(false);
                } else {
                    editAction.setEnabled(true);
                    
                    int amountOfCurrencies = tableModel.getRowCount();
                    //At least one currency should exist
                    if (amountOfCurrencies < 2) {
                        removeAction.setEnabled(false);
                        return;
                    }

                    Long id = (Long) table.getValueAt(rowIndex, CategoryColumnEnum.ID.getIndex());
                    PCurrency currency = tableModel.getItemById(id);

                    if (DBConditions.currencyInUse(currency)) {                        
                        removeAction.setEnabled(false);
                    } else {
                    	removeAction.setEnabled(true);
                    }
                    
                    if(currency.getCode().equals(SettingController.instance().getSettingValue(PSetting.MAIN_CURRENCY_KEY))) {
                    	setAsMainAction.setEnabled(false);
                    } else {
                    	setAsMainAction.setEnabled(true);
                    }
                }
            }

        });
    }

    /**
     * Creates the popup menu for the table and scrollpane
     */
    public void createPopUp() {
        popupMenu = new JPopupMenu();
        popupMenu.add(new JMenuItem(addAction));
        popupMenu.add(new JMenuItem(editAction));
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

            if(column == CurrencyColumnEnum.STANDARD.getIndex()
            		|| column == CurrencyColumnEnum.UPDATE_ONLINE.getIndex()) {
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

            Long id = (Long) table.getValueAt(row, CategoryColumnEnum.ID.getIndex());

            PCurrency currency = tableModel.getItemById(id);

            if (DBConditions.currencyInUse(currency)) {
                comp.setBackground(ColorConstants.USED);
            } else {
                comp.setBackground(table.getBackground());
            }   
            
            if(currency.getCode().equals(SettingController.instance().getSettingValue(PSetting.MAIN_CURRENCY_KEY))) {
            	comp.setBackground(ColorConstants.MAIN_CURRENCY_COLOR);
            }

            return comp;
        }
    }
    
	

    /*---------------------ACTIONS----------------------------------*/

    private class AddAction extends AbstractAction {
        public AddAction() {
            super(Resources.getString("buttons.add") + "...",
            		Resources.getIcon("add.png"));
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            CurrencyDialog.openNewDialog();
        }
    }

    private class EditAction extends AbstractAction {
        public EditAction() {
            super(Resources.getString("buttons.edit") + "...",
            		Resources.getIcon("edit.png"));
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            int rowIndex = table.getSelectedRow();
            Long id = (Long) table.getValueAt(rowIndex, CategoryColumnEnum.ID.getIndex());
            CurrencyDialog.openEditDialog(tableModel.getItemById(id));
        }
    }

    private class RemoveAction extends AbstractAction {
        public RemoveAction() {
            super(Resources.getString("buttons.remove"),
            		Resources.getIcon("remove.png"));
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            int rowIndex = table.getSelectedRow();
            Long id = (Long) table.getValueAt(rowIndex, CategoryColumnEnum.ID.getIndex());
            PCurrency currency = tableModel.getItemById(id);
            int result = MyDialogs.showYesNoDialog(CurrencyPanel.this, Resources.getString("dialogs.questions.removecurrency") + "?");
            if (result == MyDialogs.YES) {
                CurrencyController.instance().delete(currency);
            }
        }
    }
    
    private class SetAsMainAction extends AbstractAction {
        public SetAsMainAction() {
            super(Resources.getString("buttons.setasmain"),
            		Resources.getIcon("ok.png"));
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            int rowIndex = table.getSelectedRow();
            Long id = (Long) table.getValueAt(rowIndex, CategoryColumnEnum.ID.getIndex());
            PCurrency currency = tableModel.getItemById(id);
            int result = MyDialogs.showYesNoDialog(CurrencyPanel.this, Resources.getString("dialogs.questions.changemaincurrency") + "?");
            if (result == MyDialogs.YES) {
            	//Recalculate exchange rates
            	String currentMainCode = SettingController.instance().getSettingValue(PSetting.MAIN_CURRENCY_KEY);
            	String newMainCode = currency.getCode();            	
            	            	
                BigDecimal newMainCurrCurrentExchangeRate = currency.getExchangeRate();
                BigDecimal newMainCurrNewExchangeRate = Calc.ONE.divide(newMainCurrCurrentExchangeRate, Calc.MATH_CONTEXT);
                
                List<PCurrency> allCurrencies = CurrencyController.instance().getAll();
                for(PCurrency nextCurrency : allCurrencies) {
                	if(nextCurrency.getCode().equals(newMainCode)) {
                		nextCurrency.setExchangeRate(Calc.ONE);
                	} else if(nextCurrency.getCode().equals(currentMainCode)) {
                		nextCurrency.setExchangeRate(newMainCurrNewExchangeRate);
                	} else {
                		BigDecimal newExchangeRate = nextCurrency.getExchangeRate().divide(newMainCurrCurrentExchangeRate, Calc.MATH_CONTEXT);
                		nextCurrency.setExchangeRate(newExchangeRate);
                	}
                	
                	CurrencyController.instance().createOrUpdate(nextCurrency);
                }
                
                SettingController.instance().createOrUpdateSetting(PSetting.MAIN_CURRENCY_KEY, newMainCode);
            }
        }
    }    

    private void addCurrencyControllerListener() {
    	CurrencyController.instance().addListener(new ControllerListener<PCurrency>() {
			@Override
			public void deleted(PCurrency currency) {
				tableModel.removeItem(currency);				
			}
			
			@Override
			public void createdOrUpdated(PCurrency currency) {
				tableModel.putItem(currency);				
			}
		});
	}

}
