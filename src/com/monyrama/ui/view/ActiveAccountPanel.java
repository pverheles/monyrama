package com.monyrama.ui.view;

import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import com.monyrama.controller.AccountController;
import com.monyrama.controller.ControllerListener;
import com.monyrama.controller.DBConditions;
import com.monyrama.db.enumarations.EntityStates;
import com.monyrama.entity.PAccount;
import com.monyrama.server.MobileDataListener;
import com.monyrama.server.MobileDataManager;
import com.monyrama.ui.dialogs.AccountDialog;
import com.monyrama.ui.dialogs.TransferDialog;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.tables.columns.AccountColumnEnum;
import com.monyrama.ui.utils.MyDialogs;

public class ActiveAccountPanel extends AbstractAccountPanel {
	
    private JButton addButton;
    private JButton editButton;
    private JButton transferButton;
    private JButton closeButton;
    private JButton removeButton;

    private Action addAction;
    private Action editAction;
    private Action transferAction;
    private Action closeAction;
    private Action removeAction;
	
	@Override	
	protected JPanel getButtonsPanel() {
		final JPanel buttonsPanel = new JPanel();
        final FlowLayout flowLayout = new FlowLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        buttonsPanel.setLayout(flowLayout);        

        addButton = new JButton(addAction);
        buttonsPanel.add(addButton);

        editButton = new JButton(editAction);
        buttonsPanel.add(editButton);

        transferButton = new JButton(transferAction);
        buttonsPanel.add(transferButton);

        closeButton = new JButton(closeAction);
        buttonsPanel.add(closeButton);
        
        removeButton = new JButton(removeAction);
        buttonsPanel.add(removeButton);        
        
        return buttonsPanel;
	}
	
	@Override
    protected void createAndInitializeActions() {
        addAction = new AddAction();
        editAction = new EditAction();
        editAction.setEnabled(false);
        transferAction = new TransferAction();
        transferAction.setEnabled(DBConditions.moreThanOneActiveAccountExist());
        closeAction = new CloseAction();
        closeAction.setEnabled(false);
		removeAction = new RemoveAction();
		removeAction.setEnabled(false);
    }	
	
    /**
     * Creates the popup menu for the table and scrollpane
     */
    public void createPopUp() {
    	final JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.add(new JMenuItem(addAction));
        popupMenu.add(new JMenuItem(editAction));
        popupMenu.add(new JMenuItem(transferAction));
        popupMenu.add(new JMenuItem(closeAction));
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
        tablePane.addMouseListener(mouseAdapter);
    }

	@Override
	protected void accountSelectionChanged() {
		PAccount selectedAccount = getSelectedAccount();
        if (selectedAccount == null) {
            editAction.setEnabled(false);
            closeAction.setEnabled(false);
            removeAction.setEnabled(false);
        } else {
            editAction.setEnabled(true);
            closeAction.setEnabled(true);
            removeAction.setEnabled(!DBConditions.accountHasMoneyMovements(selectedAccount));
        }

	}
	
	@Override
    protected void addAccountListener() {
    	AccountController.instance().addListener(new ControllerListener<PAccount>() {
			
			@Override
			public void deleted(PAccount account) {
                tableModel.removeItem(account);
            	tableModel.fireTableDataChanged();
                recalculateSumsByCurrency();	
                transferAction.setEnabled(DBConditions.moreThanOneActiveAccountExist());
			}
			
			@Override
			public void createdOrUpdated(PAccount account) {
				if(EntityStates.ACTIVE.getCode().equals(account.getState())) {
	                tableModel.putItem(account);	                
				} else {
					tableModel.removeItem(account);
				}
				recalculateSumsByCurrency();
				transferAction.setEnabled(DBConditions.moreThanOneActiveAccountExist());
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
	protected List<PAccount> getAccounts() {
		return AccountController.instance().listActive();
	} 	    
    
    /*---------------------ACTIONS----------------------------------*/

	private class AddAction extends AbstractAction {
        public AddAction() {
            super(Resources.getString("buttons.add") + "...", Resources.getIcon("add.png"));
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            AccountDialog.openNewDialog();
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
            Long id = (Long) table.getValueAt(rowIndex, AccountColumnEnum.ID.getIndex());
            PAccount depository = tableModel.getItemById(id);
            AccountDialog.openEditDialog(depository);
        }
    }
    
    private class TransferAction extends AbstractAction {
        public TransferAction() {
            super(Resources.getString("buttons.transfer") + "...", Resources.getIcon("transfer.png"));
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
        	TransferDialog.openDialog();
            
        }
    }    

    private class CloseAction extends AbstractAction {
		public CloseAction() {
			super(Resources.getString("buttons.close") + "...", Resources.getIcon("close_budget.png"));
		}

		@Override
		public void actionPerformed(ActionEvent e) {			
            int decision = MyDialogs.showYesNoDialog(ActiveAccountPanel.this, Resources.getString("dialogs.questions.closeaccount") + "?");
            if (decision == MyDialogs.YES) {
                PAccount account = getSelectedAccount();
                account.setState(EntityStates.CLOSED.getCode());
                AccountController.instance().createOrUpdate(account);
            }
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
            Long id = (Long) table.getValueAt(rowIndex, AccountColumnEnum.ID.getIndex());
            PAccount account = tableModel.getItemById(id);
            int result = MyDialogs.showYesNoDialog(ActiveAccountPanel.this, Resources.getString("dialogs.questions.removeaccount")
                    + " "
                    + account.getName()
                    + "?");

            if (result == MyDialogs.YES) {
                AccountController.instance().delete(account);
            }
        }
    }   
}
