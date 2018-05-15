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
import com.monyrama.db.enumarations.EntityStates;
import com.monyrama.entity.PAccount;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.utils.MyDialogs;

public class ClosedAccountPanel extends AbstractAccountPanel {
	
    private JButton reopenButton;
    private Action reopenAction;
	
	@Override	
	protected JPanel getButtonsPanel() {
		final JPanel buttonsPanel = new JPanel();
        final FlowLayout flowLayout = new FlowLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        buttonsPanel.setLayout(flowLayout);        

        reopenButton = new JButton(reopenAction);
        buttonsPanel.add(reopenButton);
       
        return buttonsPanel;
	}
	
	@Override
    protected void createAndInitializeActions() {
		reopenAction = new ReopenAction();
		reopenAction.setEnabled(false);
    }	
	
    /**
     * Creates the popup menu for the table and scrollpane
     */
    public void createPopUp() {
    	final JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.add(new JMenuItem(reopenAction));

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
            reopenAction.setEnabled(false);
        } else {
        	reopenAction.setEnabled(true);
        }
	}
	
	@Override
    protected void addAccountListener() {
    	AccountController.instance().addListener(new ControllerListener<PAccount>() {
			
			@Override
			public void deleted(PAccount account) {}
			
			@Override
			public void createdOrUpdated(PAccount account) {
				if(EntityStates.CLOSED.getCode().equals(account.getState())) {
					tableModel.putItem(account);	                
				} else {
					tableModel.removeItem(account);
				}
				recalculateSumsByCurrency();	
			}
		});
    }		

	@Override
	protected List<PAccount> getAccounts() {
		return AccountController.instance().listClosed();
	} 		
	
    /*---------------------ACTIONS----------------------------------*/

    private class ReopenAction extends AbstractAction {
        public ReopenAction() {
            super(Resources.getString("buttons.reopen") + "...", Resources.getIcon("reopen.png"));
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            int result = MyDialogs.showYesNoDialog(ClosedAccountPanel.this, Resources.getString("dialogs.questions.reopenaccount") + "?");
            if (result == MyDialogs.YES) {
                PAccount account = getSelectedAccount();
                account.setState(EntityStates.ACTIVE.getCode());
                AccountController.instance().createOrUpdate(account);
            }
        }
    }
	
}
