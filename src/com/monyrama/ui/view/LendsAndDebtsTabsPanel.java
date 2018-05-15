package com.monyrama.ui.view;

import java.awt.BorderLayout;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.monyrama.ui.constants.FontConstants;
import com.monyrama.ui.resources.Resources;

public class LendsAndDebtsTabsPanel extends GeneralPanel {

	private GeneralPanel lendsPanel;
	private GeneralPanel debtsPanel;

    private boolean firstOpened = true;
	private JTabbedPane tabbedPane;
    
    private enum TabIndex {LENDS, DEBTS}
	
	public LendsAndDebtsTabsPanel() {
		setLayout(new BorderLayout());
		tabbedPane = new JTabbedPane();
		lendsPanel = new LendPanel();
		debtsPanel = new DebtPanel();
		tabbedPane.setFont(FontConstants.TABBED_PANE_TAB_FONT);
		tabbedPane.insertTab(Resources.getString("labels.lends"), Resources.getIcon("lends.png"), lendsPanel, Resources.getString("labels.lends"), TabIndex.LENDS.ordinal());
		tabbedPane.insertTab(Resources.getString("labels.debts"), Resources.getIcon("debts.png"), debtsPanel, Resources.getString("labels.debts"), TabIndex.DEBTS.ordinal());
		add(tabbedPane, BorderLayout.CENTER);
		tabbedPane.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent e) {
				int selectedTabIndex = tabbedPane.getSelectedIndex();
				if(selectedTabIndex == TabIndex.DEBTS.ordinal()) {
					LendsAndDebtsTabsPanel.this.debtsPanel.initOnVisible();
				}				
			}
		});
	}
	
    @Override
    public void initOnVisible() {
        if (firstOpened) {
        	lendsPanel.initOnVisible();
            firstOpened = false;
        } else {
        	int selectedIndex = tabbedPane.getSelectedIndex();
			if(selectedIndex == TabIndex.LENDS.ordinal()) {
				lendsPanel.initOnVisible();
			} else if(selectedIndex == TabIndex.DEBTS.ordinal()) {
				debtsPanel.initOnVisible();
			}
        }
    }

}
