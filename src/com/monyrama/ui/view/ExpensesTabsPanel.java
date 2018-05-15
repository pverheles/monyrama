package com.monyrama.ui.view;

import java.awt.BorderLayout;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.monyrama.ui.constants.FontConstants;
import com.monyrama.ui.resources.Resources;

public class ExpensesTabsPanel extends GeneralPanel {

	private GeneralPanel activePanel;
	private GeneralPanel closedPanel;
	private GeneralPanel categoriesPanel;
	private GeneralPanel searchPanel;

    private boolean firstOpened = true;
	private JTabbedPane tabbedPane;
    
    private enum TabIndex {ACTIVE, CLOSED, CATEGORIES, SEARCH}
	
	public ExpensesTabsPanel() {
		setLayout(new BorderLayout());
		tabbedPane = new JTabbedPane();
		activePanel = new ActiveExpensesPanel();
		closedPanel = new ClosedExpensesPanel();
		categoriesPanel = new CategoryPanel();
		searchPanel = new ExpenseSearchPanel();
		tabbedPane.setFont(FontConstants.TABBED_PANE_TAB_FONT);
		tabbedPane.insertTab(Resources.getString("labels.active"), Resources.getIcon("green-ball.png"), activePanel, Resources.getString("labels.active"), TabIndex.ACTIVE.ordinal());
		tabbedPane.insertTab(Resources.getString("labels.closed"), Resources.getIcon("grey-ball.png"), closedPanel, Resources.getString("labels.closed"), TabIndex.CLOSED.ordinal());
		tabbedPane.insertTab(Resources.getString("labels.categories"), Resources.getIcon("categories.png"), categoriesPanel, Resources.getString("labels.categories"), TabIndex.CATEGORIES.ordinal());
		tabbedPane.insertTab(Resources.getString("labels.search"), Resources.getIcon("search.png"), searchPanel, Resources.getString("labels.search"), TabIndex.SEARCH.ordinal());
		add(tabbedPane, BorderLayout.CENTER);
		tabbedPane.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent e) {
				int selectedTabIndex = tabbedPane.getSelectedIndex();
				if(selectedTabIndex == TabIndex.CLOSED.ordinal()) {
					ExpensesTabsPanel.this.closedPanel.initOnVisible();
				} else if(selectedTabIndex == TabIndex.CATEGORIES.ordinal()) {
					ExpensesTabsPanel.this.categoriesPanel.initOnVisible();
				} else if(selectedTabIndex == TabIndex.SEARCH.ordinal()) {
					ExpensesTabsPanel.this.searchPanel.initOnVisible();
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
				ExpensesTabsPanel.this.activePanel.initOnVisible();
			} else if(selectedIndex == TabIndex.CLOSED.ordinal()) {
				ExpensesTabsPanel.this.closedPanel.initOnVisible();
			} else if(selectedIndex == TabIndex.CATEGORIES.ordinal()) {
				ExpensesTabsPanel.this.categoriesPanel.initOnVisible();
			} else if(selectedIndex == TabIndex.SEARCH.ordinal()) {
				ExpensesTabsPanel.this.searchPanel.initOnVisible();
			}
        }
    }

}
