package com.monyrama.ui.view;

import java.awt.event.ActionEvent;
import java.util.Collection;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.monyrama.controller.ControllerListener;
import com.monyrama.controller.IncomeSourceController;
import com.monyrama.db.enumarations.EntityStates;
import com.monyrama.entity.PIncomeSource;
import com.monyrama.preferences.MyPreferences;
import com.monyrama.preferences.PrefKeys;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.utils.MyDialogs;


public class ClosedIncomesPanel extends AbstractIncomesPanel {

	private Action reopenIncomeSourceAction;
		
	public ClosedIncomesPanel() {
		super();
		addControllerListener();
	}	
	
	@Override
	public void initOnVisible() {
		super.initOnVisible();
		updateActionState();
	}

	@Override
	protected JPanel getButtonsPanel() {
		JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new java.awt.GridBagLayout());
        
        java.awt.GridBagConstraints gridBagConstraints;		
        
        JButton reopenButton = new JButton(reopenIncomeSourceAction);
        reopenButton.setPreferredSize(BUTTON_DIMENSION);
        reopenButton.setMinimumSize(BUTTON_DIMENSION);
        reopenButton.setHorizontalAlignment(JButton.LEFT);        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        buttonsPanel.add(reopenButton, gridBagConstraints);
        
		return buttonsPanel;
	}

	@Override
	protected void createActions() {
		reopenIncomeSourceAction = new ReopenIncomeSourceAction();
	}

	@Override
	protected Collection<PIncomeSource> loadIncomeSources() {
		Collection<PIncomeSource> closedIncomeSources = IncomeSourceController.instance().listClosed();
		return closedIncomeSources;
	}

	private void addControllerListener() {		
		IncomeSourceController.instance().addListener(new ControllerListener<PIncomeSource>() {
			@Override
			public void createdOrUpdated(PIncomeSource incomeSource) {		
				if(incomeSource.getState().equals(EntityStates.CLOSED.getCode())) {					
					nameBox.addItem(incomeSource);
					nameBox.setSelectedItem(incomeSource);
				} else if(incomeSource.getState().equals(EntityStates.ACTIVE.getCode())) {
                	if(nameBox.getItemCount() != 0 && getSelectedIncomeSource().equals(incomeSource)) {
                		nameBox.removeItem(incomeSource);
                		checkIfThoughOneSourceExists();
                	}
                }
				updateActionState();
				enableOrDisableChartsButton();
			}

			@Override
			public void deleted(PIncomeSource incomeSource) {
				nameBox.removeItem(incomeSource);
				updateActionState();
				checkIfThoughOneSourceExists();
				enableOrDisableChartsButton();
			}
		});
	}	
	
	private void updateActionState() {
		if(getSelectedIncomeSource() == null) {
			reopenIncomeSourceAction.setEnabled(false);
		} else {
			reopenIncomeSourceAction.setEnabled(true);
		}
	}	
    
    private class ReopenIncomeSourceAction extends AbstractAction {
        public ReopenIncomeSourceAction() {
            super(Resources.getString("buttons.reopen") + "...", Resources.getIcon("reopen.png"));
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            int result = MyDialogs.showYesNoDialog(ClosedIncomesPanel.this, Resources.getString("dialogs.questions.reopenincomesource") + "?");
            if (result == MyDialogs.YES) {
                PIncomeSource incomeSource = getSelectedIncomeSource();
                incomeSource.setState(EntityStates.ACTIVE.getCode());
                IncomeSourceController.instance().createOrUpdate(incomeSource);
            }
        }
    }    

	public void saveStateParams() {		
		MyPreferences.save(PrefKeys.CLOSED_INCOMES_CHART_INDEX, chartsButton.getSelectedIndex());
	}

	@Override
	protected void restoreState() {
		int selectedChartIndex = MyPreferences.getInteger(PrefKeys.CLOSED_INCOMES_CHART_INDEX, 0);
		chartsButton.setSelectedIndex(selectedChartIndex);		
	}	    
    
}
