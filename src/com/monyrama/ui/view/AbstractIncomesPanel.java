package com.monyrama.ui.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.RowSorter.SortKey;
import javax.swing.SortOrder;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.monyrama.controller.IncomeItemController;
import com.monyrama.ui.view.TableColumnsUtil;
import com.monyrama.controller.IncomeSourceController;
import com.monyrama.entity.PIncome;
import com.monyrama.entity.PIncomeSource;
import com.monyrama.ui.components.DropdownButton;
import com.monyrama.ui.components.ExpandableLabel;
import com.monyrama.ui.components.MyJTable;
import com.monyrama.ui.components.UniqueSortedComboboxModel;
import com.monyrama.ui.constants.ColorConstants;
import com.monyrama.ui.constants.DimensionConstants;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.tables.columns.IncomeItemColumnEnum;
import com.monyrama.ui.tables.model.IncomeItemTableModel;
import com.monyrama.ui.utils.ComparatorFactory;
import com.monyrama.ui.utils.MyFormatter;
import com.monyrama.ui.view.charts.GeneralIncomeSumChart;
import com.monyrama.ui.view.charts.IncomeByDateSumChart;
import com.monyrama.utils.Calc;

abstract class AbstractIncomesPanel extends GeneralPanel {

	protected final static Dimension BUTTON_DIMENSION = new Dimension(200, 23);
	
    private JLabel currencyValueLabel;
    protected JComboBox nameBox;
    private JLabel totalSumValueLabel;
    private ExpandableLabel commentsValueLabel;
	protected DropdownButton chartsButton;    
    
    protected IncomeItemTableModel itemsTableModel = new IncomeItemTableModel();
    
	private boolean firstOpened = true;
	protected JTable itemsTable;
	private JScrollPane itemsPane;
	
	public AbstractIncomesPanel() {
		setLayout(new BorderLayout());
		
		createActions();
		
		final JPanel northPanel = getTopPanel();	
		add(northPanel, BorderLayout.NORTH);

		final JTabbedPane centerPanel = getCentralPanel();
		add(centerPanel, BorderLayout.CENTER);	
	
	}

	private JTabbedPane getCentralPanel() {
		final JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setForeground(SystemColor.activeCaption);
		tabbedPane.addTab(Resources.getString("labels.incomes.items"), Resources.getIcon("items.png"), getItemsPanel());
		return tabbedPane;
	}

	private JPanel getTopPanel() {
        JPanel topPanel = new JPanel();
        
        topPanel.setBorder(new TitledBorder(null, Resources.getString("labels.incomes.source"), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, SystemColor.activeCaption));
		
		java.awt.GridBagConstraints gridBagConstraints;

		JPanel leftPanel = new javax.swing.JPanel();
        javax.swing.JLabel nameLabel = new javax.swing.JLabel();
        nameBox = new javax.swing.JComboBox(new UniqueSortedComboboxModel<PIncomeSource>());
        javax.swing.JLabel currencyLabel = new javax.swing.JLabel();
        currencyValueLabel = new javax.swing.JLabel();
        javax.swing.JLabel totalSumLabel = new javax.swing.JLabel();
        totalSumValueLabel = new javax.swing.JLabel();
        javax.swing.JLabel commentsLabel = new javax.swing.JLabel();
        commentsValueLabel = new ExpandableLabel();

        topPanel.setLayout(new java.awt.GridBagLayout());

        leftPanel.setLayout(new java.awt.GridBagLayout());

        nameLabel.setText(Resources.getString("labels.name") + ":");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 0, 15, 0);
        leftPanel.add(nameLabel, gridBagConstraints);

		nameBox.setMinimumSize(DimensionConstants.STD_FIELD_DIMENSION);
		nameBox.setPreferredSize(DimensionConstants.STD_FIELD_DIMENSION);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 5, 0);
        leftPanel.add(nameBox, gridBagConstraints);

        currencyLabel.setText(Resources.getString("labels.currency") + ":");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 5);
        leftPanel.add(currencyLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        leftPanel.add(currencyValueLabel, gridBagConstraints);

        totalSumLabel.setText(Resources.getString("labels.incomes.total") + ":");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 5);
        leftPanel.add(totalSumLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        leftPanel.add(totalSumValueLabel, gridBagConstraints);        
        
        commentsLabel.setText(Resources.getString("labels.comments") + ":");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 5);
        leftPanel.add(commentsLabel, gridBagConstraints);
        
        commentsValueLabel.setMaximumSize(new Dimension(220, 15));
        commentsValueLabel.setPreferredSize(new Dimension(220, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        leftPanel.add(commentsValueLabel, gridBagConstraints);;
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;        
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        topPanel.add(leftPanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 0, 0);
        topPanel.add(getButtonsPanel(), gridBagConstraints);            

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        topPanel.add(getChartsPanel(), gridBagConstraints);        
        
        JPanel emtyPanel = new JPanel();
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;       
        topPanel.add(emtyPanel, gridBagConstraints);
        
        return topPanel;
	}
	
	private Component getChartsPanel() {
		JPanel chartsPanel = new JPanel();
		chartsPanel.setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints;
		
		JLabel chartsLabel = new JLabel(Resources.getString("labels.chart") + ":");		
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		chartsPanel.add(chartsLabel, gridBagConstraints);
		
		chartsButton = new DropdownButton();
		chartsButton.setMinimumSize(BUTTON_DIMENSION);
		chartsButton.setMaximumSize(BUTTON_DIMENSION);
		chartsButton.setPreferredSize(BUTTON_DIMENSION);		
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 10, 0);
		chartsPanel.add(chartsButton, gridBagConstraints);
		
		//Add actions
		chartsButton.addAction(new ShowIncomeByDateChart());
		chartsButton.addAction(new ShowGeneralIncomeChart());
		
		return chartsPanel;
	}
	
	protected abstract JPanel getButtonsPanel();
	
	protected JPanel getItemsPanel() {
		final JPanel itemsPanel = new JPanel();
		itemsPanel.setLayout(new BorderLayout());

		itemsPane = new JScrollPane();
		itemsPanel.add(itemsPane, BorderLayout.CENTER);

		itemsTable = new MyJTable();
		
		itemsTable.setSelectionBackground(ColorConstants.SELECTED_BACKGROUND);
		itemsTable.setSelectionForeground(ColorConstants.SELECTED_FOREGROUND);
		
		itemsTable.setModel(itemsTableModel);
		
		//We hide column with ID
		itemsTable.getColumnModel().getColumn(IncomeItemColumnEnum.ID.getIndex()).setMinWidth(0);
		itemsTable.getColumnModel().getColumn(IncomeItemColumnEnum.ID.getIndex()).setMaxWidth(0);		
		
		itemsTable.setRowHeight(20);
		
		itemsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		itemsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		itemsPane.setViewportView(itemsTable);				
		
		return itemsPanel;
	}
	
	@Override
	public void initOnVisible() {
        if (firstOpened) {
    		addListeners();
        	
            initTableColumns();
            
    		for(PIncomeSource incomeSource : loadIncomeSources()) {
    			nameBox.addItem(incomeSource);
    		}
            
            if(nameBox.getItemCount() > 0) {
            	nameBox.setSelectedIndex(0);
            }
            
            restoreState();
            
            firstOpened = false;
        }
        enableOrDisableChartsButton();
        updateUIData();
    }	
	
	private void initTableColumns() {
		TableColumnsUtil.setPercentColumnWidths(itemsTable, itemsPane.getWidth(), IncomeItemColumnEnum.getColumnPercents());
		
		IncomeItemSorter itemsSorter = new IncomeItemSorter(itemsTable.getModel());
		itemsTable.setRowSorter(itemsSorter);
        List<SortKey> itemsSortKeys = new ArrayList<RowSorter.SortKey>();
        itemsSortKeys.add( new RowSorter.SortKey(IncomeItemColumnEnum.DATE.getIndex(), SortOrder.DESCENDING) );
        itemsSorter.setSortKeys(itemsSortKeys);
        itemsSorter.sort();
	}
	
	private class IncomeItemSorter extends TableRowSorter<TableModel> {

		private IncomeItemSorter(TableModel model) {
			super(model);
		}

		@Override
		public Comparator<?> getComparator(int index) {			

			if(index == IncomeItemColumnEnum.SUM.getIndex()) {
				return ComparatorFactory.getInstance().getBigDecimalComparator();
			}
			
			if(index == IncomeItemColumnEnum.DATE.getIndex()) {
				return ComparatorFactory.getInstance().getDateComparator();
			}
			
			return super.getComparator(index);
		}		
	}	

	protected abstract void createActions();

	protected PIncomeSource getSelectedIncomeSource() {
		return (PIncomeSource)nameBox.getSelectedItem();
	}	
	
	protected void updateUIData() {
		if(getSelectedIncomeSource() != null) {
			IncomeSourceController.instance().refresh(getSelectedIncomeSource());
			currencyValueLabel.setText(getSelectedIncomeSource().getCurrency().toString());
			commentsValueLabel.setText(getSelectedIncomeSource().getComment());
			List<PIncome> incomeItems = IncomeItemController.instance().listByIncomeSource(getSelectedIncomeSource());
			itemsTableModel.reFill(incomeItems);
			updateTotalLabel();
		}	
	}

	protected void updateTotalLabel() {
		totalSumValueLabel.setText(MyFormatter.formatNumberToLocal(Calc.sum(itemsTableModel.getDataCollection()).toPlainString()));
	}
	
	protected void checkIfThoughOneSourceExists() {
		if(getSelectedIncomeSource() == null) {
			itemsTableModel.clear();
			currencyValueLabel.setText("");
			totalSumValueLabel.setText("");
			commentsValueLabel.setText("");
		}
	}
	
	protected void addListeners() {
		nameBox.addItemListener(new ItemListener() {			
			@Override
			public void itemStateChanged(ItemEvent ev) {
				if(ev.getStateChange() == ItemEvent.SELECTED) {
					updateUIData();
				}
			}
		});
	}		
	
	protected abstract Collection<PIncomeSource> loadIncomeSources();

	protected void enableOrDisableChartsButton() {
		if(getSelectedIncomeSource() == null) {
			chartsButton.setEnabled(false);
		} else {
			chartsButton.setEnabled(true);
		}
	}	
	
	private class ShowIncomeByDateChart extends AbstractAction {
    	
    	public ShowIncomeByDateChart() {
    		super(Resources.getString("charts.actionname.incomebydate"), Resources.getIcon("barchart.png"));
    	}
    	
		@Override
		public void actionPerformed(ActionEvent arg0) {
			IncomeByDateSumChart dailyExpenseSumChart = new IncomeByDateSumChart(itemsTableModel.getDataCollection(), getSelectedIncomeSource().getName());
			dailyExpenseSumChart.setIconImage(Resources.getImage("barchart.png"));
			dailyExpenseSumChart.setLocationRelativeTo(AbstractIncomesPanel.this);
			dailyExpenseSumChart.setVisible(true);
		}     	
    }
	
    private class ShowGeneralIncomeChart extends AbstractAction {
    	
    	public ShowGeneralIncomeChart() {
    		super(Resources.getString("charts.actionname.generalincomesum"), Resources.getIcon("growchart.png"));
    	}
    	
		@Override
		public void actionPerformed(ActionEvent arg0) {
			GeneralIncomeSumChart dailyGeneralSumChart = new GeneralIncomeSumChart(itemsTableModel.getDataCollection(), getSelectedIncomeSource().getName());
			dailyGeneralSumChart.setIconImage(Resources.getImage("growchart.png"));
			dailyGeneralSumChart.setLocationRelativeTo(AbstractIncomesPanel.this);
			dailyGeneralSumChart.setVisible(true);
		}     	
    }
    
    protected abstract void restoreState();
}
