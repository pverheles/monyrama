package com.monyrama.ui.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.monyrama.controller.ControllerListener;
import com.monyrama.controller.DBConditions;
import com.monyrama.controller.IncomeItemController;
import com.monyrama.controller.IncomeSourceController;
import com.monyrama.db.enumarations.EntityStates;
import com.monyrama.entity.PIncome;
import com.monyrama.entity.PIncomeSource;
import com.monyrama.preferences.MyPreferences;
import com.monyrama.preferences.PrefKeys;
import com.monyrama.server.MobileDataListener;
import com.monyrama.server.MobileDataManager;
import com.monyrama.ui.dialogs.IncomeItemDialog;
import com.monyrama.ui.dialogs.IncomeSourceDialog;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.tables.columns.ExpenseColumnEnum;
import com.monyrama.ui.utils.MyDialogs;


public class ActiveIncomesPanel extends AbstractIncomesPanel {
	
	private Action newIncomeSourceAction;
	private Action editIncomeSourceAction;
	private Action removeIncomeSourceAction;
	private Action closeIncomeSourceAction;
	private Action addIncomeItemAction;
	private Action editIncomeItemAction;
	private Action removeIncomeItemAction;
				
	public ActiveIncomesPanel() {
		super();
		createPopUp();
		addControllerListener();
	}
	
	@Override
	public void initOnVisible() {
		super.initOnVisible();
		updateActionsState();
	}

	@Override
	protected JPanel getButtonsPanel() {
        javax.swing.JPanel buttonsPanel = new javax.swing.JPanel();
        javax.swing.JButton newSourceButton = new javax.swing.JButton();
        javax.swing.JButton editSourceButton = new javax.swing.JButton();
        javax.swing.JButton closeSourceButton = new javax.swing.JButton();
		
        buttonsPanel.setLayout(new java.awt.GridBagLayout());

        java.awt.GridBagConstraints gridBagConstraints;

        editSourceButton.setAction(editIncomeSourceAction);
        editSourceButton.setPreferredSize(BUTTON_DIMENSION);
        editSourceButton.setMinimumSize(BUTTON_DIMENSION);
        editSourceButton.setHorizontalAlignment(JButton.LEFT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        buttonsPanel.add(editSourceButton, gridBagConstraints);

        closeSourceButton.setAction(closeIncomeSourceAction);
        closeSourceButton.setPreferredSize(BUTTON_DIMENSION);
        closeSourceButton.setHorizontalAlignment(JButton.LEFT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        buttonsPanel.add(closeSourceButton, gridBagConstraints);
                
        JButton removeButton = new JButton(removeIncomeSourceAction);
        removeButton.setPreferredSize(BUTTON_DIMENSION);
        removeButton.setMinimumSize(BUTTON_DIMENSION);
        removeButton.setHorizontalAlignment(JButton.LEFT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        buttonsPanel.add(removeButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 3);
        gridBagConstraints.fill = GridBagConstraints.VERTICAL;
        buttonsPanel.add(new JSeparator(JSeparator.VERTICAL), gridBagConstraints);        
        
        newSourceButton.setAction(newIncomeSourceAction);
        newSourceButton.setPreferredSize(BUTTON_DIMENSION);
        newSourceButton.setMinimumSize(BUTTON_DIMENSION);
        newSourceButton.setHorizontalAlignment(JButton.LEFT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        buttonsPanel.add(newSourceButton, gridBagConstraints);        
       
        return buttonsPanel;
	}
	
	@Override
	protected JPanel getItemsPanel() {		
		JPanel itemsPanel = super.getItemsPanel();
		JPanel buttonsPanel = new JPanel();
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		buttonsPanel.setLayout(flowLayout);
		itemsPanel.add(buttonsPanel, BorderLayout.NORTH);
		
        JButton addExpenseButton = new JButton(addIncomeItemAction);
        buttonsPanel.add(addExpenseButton);

        JButton editExpenseButton = new JButton(editIncomeItemAction);
        buttonsPanel.add(editExpenseButton);

        JButton removeExpenseButton = new JButton(removeIncomeItemAction);
        buttonsPanel.add(removeExpenseButton);
		
		return itemsPanel;
	}

	@Override
	protected void createActions() {
		newIncomeSourceAction = new NewIncomeSourceAction();
		editIncomeSourceAction = new EditIncomeSourceAction();
		removeIncomeSourceAction = new RemoveIncomeSourceAction();	
		closeIncomeSourceAction = new CloseIncomeSourceAction();
		addIncomeItemAction = new AddIncomeItemAction();
		editIncomeItemAction = new EditIncomeItemAction();
		removeIncomeItemAction = new RemoveIncomeItemAction();
		
        editIncomeItemAction.setEnabled(false);
        removeIncomeItemAction.setEnabled(false);
	}
	
	//------------------ UI Actions -----------------
	private class NewIncomeSourceAction extends AbstractAction {

		public NewIncomeSourceAction() {
			super(Resources.getString("buttons.new") + "...", Resources.getIcon("newbudget.png"));
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			IncomeSourceDialog.openNewDialog();
		} 
		
	}
	
	private class EditIncomeSourceAction extends AbstractAction {
		public EditIncomeSourceAction() {
			super(Resources.getString("buttons.edit") + "...", Resources.getIcon("edit_budget.png"));
		}

		@Override
		public void actionPerformed(ActionEvent e) {			
			IncomeSourceDialog.openEditDialog(getSelectedIncomeSource(), itemsTableModel.getRowCount() == 0);
		}
	}
		
    private class RemoveIncomeSourceAction extends AbstractAction {
        public RemoveIncomeSourceAction() {
            super(Resources.getString("buttons.remove") + "...", Resources.getIcon("removebudget.png"));
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            int result = MyDialogs.showYesNoDialog(ActiveIncomesPanel.this, Resources.getString("dialogs.questions.removeincomesource") + "?");
            if (result == MyDialogs.YES) {
                IncomeSourceController.instance().delete(getSelectedIncomeSource());
            }
        }
    }	
	
	private class CloseIncomeSourceAction extends AbstractAction {
		public CloseIncomeSourceAction() {
			super(Resources.getString("buttons.close") + "...", Resources.getIcon("close_budget.png"));
		}

		@Override
		public void actionPerformed(ActionEvent e) {			
            int decision = MyDialogs.showYesNoDialog(ActiveIncomesPanel.this, Resources.getString("dialogs.questions.closeincomesource") + "?");
            if (decision == MyDialogs.YES) {
                PIncomeSource incomeSource = getSelectedIncomeSource();
                incomeSource.setState(EntityStates.CLOSED.getCode());
                IncomeSourceController.instance().createOrUpdate(incomeSource);
            }
		}
	}
	
	//
    private class AddIncomeItemAction extends AbstractAction {
        public AddIncomeItemAction() {
            super(Resources.getString("buttons.add") + "...", Resources.getIcon("add.png"));
        }

        public void actionPerformed(ActionEvent arg0) {
        	IncomeItemDialog.openNewDialog(getSelectedIncomeSource()); 	
        }
    }

    //
	private class EditIncomeItemAction extends AbstractAction {
        public EditIncomeItemAction() {
            super(Resources.getString("buttons.edit") + "...", Resources.getIcon("edit.png"));
        }

        public void actionPerformed(ActionEvent arg0) {
        	IncomeItemDialog.openEditDialog(getSelectedIncomeItem(), getSelectedIncomeSource());
        }
    }

    //

    private class RemoveIncomeItemAction extends AbstractAction {
        public RemoveIncomeItemAction() {
            super(Resources.getString("buttons.remove"), Resources.getIcon("remove.png"));
        }

        public void actionPerformed(ActionEvent arg0) {
            int result = MyDialogs.showYesNoDialog(ActiveIncomesPanel.this, Resources.getString("dialogs.questions.removeIncomeItem") + "?");
            if(result == MyDialogs.YES) {
                PIncome incomeItem = getSelectedIncomeItem();
            	IncomeItemController.instance().delete(incomeItem);
            }
        }
    }	

	private void addControllerListener() {		
		IncomeSourceController.instance().addListener(new ControllerListener<PIncomeSource>() {
			@Override
			public void createdOrUpdated(PIncomeSource incomeSource) {		
				if(incomeSource.getState().equals(EntityStates.ACTIVE.getCode())) {
					nameBox.addItem(incomeSource);
					nameBox.setSelectedItem(incomeSource);
				} else if(incomeSource.getState().equals(EntityStates.CLOSED.getCode())) {					
					nameBox.removeItem(incomeSource);
					checkIfThoughOneSourceExists();
					updateActionsState();
				}
				enableOrDisableChartsButton();
				updateActionsState();
			}

			@Override
			public void deleted(PIncomeSource incomeSource) {
				nameBox.removeItem(incomeSource);
				updateActionsState();				
			}
		});
		
		IncomeItemController.instance().addListener(new ControllerListener<PIncome>() {

			@Override
			public void createdOrUpdated(PIncome item) {				
				itemsTableModel.putItem(item);
				updateTotalLabel();
				updateActionsState();
			}

			@Override
			public void deleted(PIncome item) {
				itemsTableModel.removeItem(item);
				updateTotalLabel();
				updateActionsState();
			}
		});

		MobileDataManager.addMobileDataListener(new MobileDataListener() {
			@Override
			public void mobileDataSaved() {
				updateUIData();
			}
		});
	}

	@Override
	protected void addListeners() {
		super.addListeners();
		itemsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = itemsTable.getSelectedRow();
                if (selectedRow == -1) {
                    editIncomeItemAction.setEnabled(false);
                    removeIncomeItemAction.setEnabled(false);
                } else {
                    editIncomeItemAction.setEnabled(true);
                    removeIncomeItemAction.setEnabled(true);
                }
            }
        });	
		
		nameBox.addItemListener(new ItemListener() {			
			@Override
			public void itemStateChanged(ItemEvent ev) {
				if(ev.getStateChange() == ItemEvent.SELECTED) {
					updateActionsState();
				}
			}
		});		
	}
	
	private void updateActionsState() {
		PIncomeSource selectedIncomeSource = getSelectedIncomeSource();
		if(selectedIncomeSource == null) {
			editIncomeSourceAction.setEnabled(false);
			closeIncomeSourceAction.setEnabled(false);
			addIncomeItemAction.setEnabled(false);
			removeIncomeSourceAction.setEnabled(false);
		} else {
			editIncomeSourceAction.setEnabled(true);
			closeIncomeSourceAction.setEnabled(true);
			addIncomeItemAction.setEnabled(true);
			if(DBConditions.hasIncomeSourceIncomes(selectedIncomeSource)) {
				removeIncomeSourceAction.setEnabled(false);
			} else {
				removeIncomeSourceAction.setEnabled(true);
			}
		}
	}

	@Override
	protected Collection<PIncomeSource> loadIncomeSources() {
		Collection<PIncomeSource> activeIncomeSources = IncomeSourceController.instance().listActive();
		return activeIncomeSources;
	}

	private PIncome getSelectedIncomeItem() {
		int row = itemsTable.getSelectedRow();
		Long id = (Long) itemsTable.getValueAt(row, ExpenseColumnEnum.ID.getIndex());
		PIncome incomeItem = itemsTableModel.getItemById(id);
		return incomeItem;
	}
	
    private void createPopUp() {
        final JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.add(new JMenuItem(addIncomeItemAction));
        popupMenu.add(new JMenuItem(editIncomeItemAction));
        popupMenu.add(new JMenuItem(removeIncomeItemAction));

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
                        int rowNumber = itemsTable.rowAtPoint(p);
                        ListSelectionModel model = itemsTable.getSelectionModel();
                        model.setSelectionInterval(rowNumber, rowNumber);
                    }
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        };

        itemsTable.addMouseListener(mouseAdapter);
    }

	@Override
	protected void restoreState() {
		int selectedChartIndex = MyPreferences.getInteger(PrefKeys.ACTIVE_INCOMES_CHART_INDEX, 0);
		chartsButton.setSelectedIndex(selectedChartIndex);		
	}	
}
