package com.monyrama.ui.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

import com.monyrama.ui.view.TableColumnsUtil;
import com.monyrama.controller.CategoryController;
import com.monyrama.controller.ControllerListener;
import com.monyrama.controller.DBConditions;
import com.monyrama.db.enumarations.EntityStates;
import com.monyrama.entity.PCategory;
import com.monyrama.ui.components.CheckboxCell;
import com.monyrama.ui.components.MyJTable;
import com.monyrama.ui.constants.ColorConstants;
import com.monyrama.ui.dialogs.CategoryDialog;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.tables.columns.CategoryColumnEnum;
import com.monyrama.ui.tables.columns.ExpensePlanItemColumnEnum;
import com.monyrama.ui.tables.model.CategoryTableModel;
import com.monyrama.ui.utils.MyDialogs;


/**
 * Represents Categories panel
 *
 * @author Petro_Verheles
 */
public class CategoryPanel extends GeneralPanel {
    
    private CategoryTableModel tableModel = new CategoryTableModel();

    private JTable table;

    private JButton addButton;
    private JButton editButton;
    private JButton removeButton;

    private Action addAction;
    private Action editAction;
    private Action removeAction;
    private Action blockAction;
    private Action unblockAction;

    private JScrollPane scrollPane;

    private JPopupMenu popupMenu;

    private boolean visibleFirstTime = true;

    /**
     * Constructor
     * <p/>
     * Creates the panel
     */
    public CategoryPanel() {
        super();
        setLayout(new BorderLayout());
        createAndInitializeActions();
        createAndPaintComponents();
        initializeTable();
        addListeners();
        createPopUp();
        addCategoriesControllerListener();
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
                    blockAction.setEnabled(false);
                    unblockAction.setEnabled(false);
                } else {
                    editAction.setEnabled(true);

                    //At least one category should exist
                    int amountOfCategories = tableModel.getRowCount();
                    if (amountOfCategories < 2) {
                        removeAction.setEnabled(false);
                        return;
                    }

                    Long id = (Long) table.getValueAt(rowIndex, CategoryColumnEnum.ID.getIndex());
                    PCategory category = tableModel.getItemById(id);

                    if (DBConditions.categoryHasExpensesItems(category)) {                        
                        removeAction.setEnabled(false);
                    } else {
                    	removeAction.setEnabled(true);
                    }
                    
                    if(isBlocked(category)) {
                    	blockAction.setEnabled(false);
                    	unblockAction.setEnabled(true);                    	
                    } else {
                    	blockAction.setEnabled(true);
                    	unblockAction.setEnabled(false);
                    }
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

        addButton = new JButton(addAction);
        northPanel.add(addButton);

        editButton = new JButton(editAction);
        northPanel.add(editButton);

        removeButton = new JButton(removeAction);
        northPanel.add(removeButton);
        
        JButton blockButton = new JButton(blockAction);
        northPanel.add(blockButton);

        JButton unblockButton = new JButton(unblockAction);
        northPanel.add(unblockButton);

//        JSplitPane splitPane = new JSplitPane();
//        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
//        splitPane.setDividerSize(3);

        scrollPane = new JScrollPane();
        //splitPane.setTopComponent(scrollPane);
        table = new MyJTable();
        table.setSelectionBackground(ColorConstants.SELECTED_BACKGROUND);
        table.setSelectionForeground(ColorConstants.SELECTED_FOREGROUND);
        table.setRowHeight(20);
        scrollPane.setViewportView(table);        
        table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //InfoPanel infoPanel = new InfoPanel(splitPane);
        
        add(scrollPane, BorderLayout.CENTER);

//        JPanel infoContentPanel = infoPanel.getContentPanel();
//        infoContentPanel.setLayout(new GridBagLayout());
//        GridBagConstraints gridBagConstraints = new GridBagConstraints();
//        gridBagConstraints.gridx = 0;
//        gridBagConstraints.gridy = 0;
//        gridBagConstraints.weightx = 1.0;
//        gridBagConstraints.insets = new Insets(0, 2, 2, 0);
//        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
//        infoContentPanel.add(new ColorSquareLabel(table.getBackground(), Resources.getString("explain.unusedcategories"), 16, 16), gridBagConstraints);
//
//        gridBagConstraints.gridy = 1;
//        infoContentPanel.add(new ColorSquareLabel(ColorConstants.USED, Resources.getString("explain.usedcategories"), 16, 16), gridBagConstraints);
//
//        gridBagConstraints.gridy = 2;
//        infoContentPanel.add(new ColorSquareLabel(ColorConstants.BLOCKED_COLOR, Resources.getString("explain.blockedcategories"), 16, 16), gridBagConstraints);        
//        
//        gridBagConstraints.gridy = 3;
//        infoContentPanel.add(new ColorSquareLabel(table.getSelectionBackground(), Resources.getString("explain.selectedcategory"), 16, 16), gridBagConstraints);
//
//        splitPane.setBottomComponent(infoPanel);
//        splitPane.setResizeWeight(0.8);
//
//        add(splitPane, BorderLayout.CENTER);
    }

    private void createAndInitializeActions() {
        addAction = new AddAction();
        editAction = new EditAction();
        editAction.setEnabled(false);
        removeAction = new RemoveAction();
        removeAction.setEnabled(false);
        blockAction = new BlockCategoryAction();
        blockAction.setEnabled(false);
        unblockAction = new UnblockItemAction();
        unblockAction.setEnabled(false);
    }

    private void initializeTable() {
        table.setModel(tableModel);
        table.getColumnModel().getColumn(CategoryColumnEnum.ID.getIndex()).setMinWidth(0);
        table.getColumnModel().getColumn(CategoryColumnEnum.ID.getIndex()).setMaxWidth(0);
        
        RowCellRenderer cellRenderer = new RowCellRenderer();
        for (int i = 0; i < CategoryColumnEnum.values().length; i++) {
            table.getColumn(CategoryColumnEnum.values()[i].getName()).setCellRenderer(cellRenderer);
        }
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
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
                 
        tableModel.reFill(CategoryController.instance().getAll());
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

            Component comp;
            
            if(column == CategoryColumnEnum.BLOCKED.getIndex()) {
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

            PCategory category = tableModel.getItemById(id);
                        
            if(isBlocked(category)) {
            	comp.setBackground(ColorConstants.BLOCKED_COLOR);
            } else if (DBConditions.categoryHasExpensesItems(category)) {
                comp.setBackground(ColorConstants.USED);
            } else {
                comp.setBackground(table.getBackground());
            }           

            return comp;
        }
    }
    
	private boolean isBlocked(PCategory category) {
		return new Character(EntityStates.CLOSED.getCode()).equals(category.getState());
	}

    /**
     * Sets table model
     *
     * @param model - CategoriesTableModel
     */
    public void setTableModel(CategoryTableModel model) {
        table.setModel(model);
    }


    /*---------------------ACTIONS----------------------------------*/

    private class AddAction extends AbstractAction {
        public AddAction() {
            super(Resources.getString("buttons.add") + "...",
                    Resources.getIcon("add.png"));
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            CategoryDialog.openNewDialog();
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
            CategoryDialog.openEditDialog(tableModel.getItemById(id));
        }
    }

    private class RemoveAction extends AbstractAction {
        public RemoveAction() {
            super(Resources.getString("buttons.remove"),
                    Resources.getIcon("remove.png"));
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            int result = MyDialogs.showYesNoDialog(CategoryPanel.this, Resources.getString("dialogs.questions.removecategory") + "?");
            if (result == MyDialogs.YES) {
                int rowIndex = table.getSelectedRow();
                Long id = (Long) table.getValueAt(rowIndex, CategoryColumnEnum.ID.getIndex());
                PCategory category = tableModel.getItemById(id);
                CategoryController.instance().delete(category);
            }
        }
    }
    
    //

    private class BlockCategoryAction extends AbstractAction {
        public BlockCategoryAction() {
            super(Resources.getString("buttons.block"),
                    Resources.getIcon("block.png"));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            changeCategoryState(EntityStates.CLOSED);
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
        	changeCategoryState(EntityStates.ACTIVE);
        }
    } 
    
	private void changeCategoryState(EntityStates state) {
		int row = table.getSelectedRow();
        Long id = (Long) table.getValueAt(row, ExpensePlanItemColumnEnum.ID.getIndex());
        PCategory category = tableModel.getItemById(id);
        category.setState(state.getCode());
        CategoryController.instance().createOrUpdate(category);
	}

    private void initTableColumns() {
        TableColumnsUtil.setPercentColumnWidths(table, scrollPane.getWidth(), CategoryColumnEnum.getColumnPercents());
        
		TableRowSorter<CategoryTableModel> sorter = new TableRowSorter<CategoryTableModel>(tableModel);
        table.setRowSorter(sorter);

        List<SortKey> list = new ArrayList<RowSorter.SortKey>();
        list.add( new RowSorter.SortKey(CategoryColumnEnum.NAME.getIndex(), SortOrder.ASCENDING) );
        sorter.setSortKeys(list);
        sorter.sort();
    }

    private void addCategoriesControllerListener() {
    	CategoryController.instance().addListener(new ControllerListener<PCategory>() {			
			@Override
			public void deleted(PCategory category) {
				tableModel.removeItem(category);				
			}
			
			@Override
			public void createdOrUpdated(PCategory category) {
				tableModel.putItem(category);				
			}
		});
	}
}
