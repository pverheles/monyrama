package com.monyrama.ui.view;

import java.awt.BorderLayout;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.monyrama.ui.constants.FontConstants;
import com.monyrama.ui.resources.Resources;

public class ActiveAndClosedTabsPanel extends GeneralPanel {

	private GeneralPanel activePanel;
	private GeneralPanel closedPanel;

    private boolean firstOpened = true;
	private JTabbedPane tabbedPane;
    
    private enum TabIndex {ACTIVE, CLOSED}
	
	public ActiveAndClosedTabsPanel(GeneralPanel activePanel, GeneralPanel closedPanel) {
		setLayout(new BorderLayout());
		tabbedPane = new JTabbedPane();
		this.activePanel = activePanel;
		this.closedPanel = closedPanel;
		tabbedPane.setFont(FontConstants.TABBED_PANE_TAB_FONT);
		tabbedPane.insertTab(Resources.getString("labels.active"), Resources.getIcon("green-ball.png"), activePanel, Resources.getString("labels.active"), TabIndex.ACTIVE.ordinal());
		tabbedPane.insertTab(Resources.getString("labels.closed"), Resources.getIcon("grey-ball.png"), closedPanel, Resources.getString("labels.closed"), TabIndex.CLOSED.ordinal());
		add(tabbedPane, BorderLayout.CENTER);
		tabbedPane.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent e) {
				int selectedTabIndex = tabbedPane.getSelectedIndex();
				if(selectedTabIndex == TabIndex.CLOSED.ordinal()) {
					ActiveAndClosedTabsPanel.this.closedPanel.initOnVisible();
				}				
			}
		});
	}
	
    @Override
    public void initOnVisible() {
        if (firstOpened) {
        	activePanel.initOnVisible();
            firstOpened = false;
        } else {
        	int selectedIndex = tabbedPane.getSelectedIndex();
			if(selectedIndex == TabIndex.ACTIVE.ordinal()) {
				activePanel.initOnVisible();
			} else if(selectedIndex == TabIndex.CLOSED.ordinal()) {
				closedPanel.initOnVisible();
			}
        }
    }

}
