package com.monyrama.ui.components.totalsumspanel;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import com.monyrama.controller.CurrencyController;
import com.monyrama.entity.PCurrency;
import com.monyrama.ui.components.MyJTable;
import com.monyrama.ui.constants.ColorConstants;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.utils.MyFormatter;
import com.monyrama.utils.Calc;

public class TotalSumsPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JScrollPane sumsScrollPane;
	private MyJTable sumsTable;

	private TotalSumsTableModel totalSumsTableModel;

	private JComboBox currencyBox;

	private JLabel sumValueLabel;

	public TotalSumsPanel() {
		setLayout(new BorderLayout());
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		centerPanel.setBorder(new TitledBorder(null, Resources.getString("labels.totalsumbycurrency"),
	                        TitledBorder.DEFAULT_JUSTIFICATION,
	                        TitledBorder.DEFAULT_POSITION, null,
	                        SystemColor.activeCaption));
	    		
        sumsScrollPane = new JScrollPane();

        sumsTable = new MyJTable();
        totalSumsTableModel = new TotalSumsTableModel();
		sumsTable.setModel(totalSumsTableModel);
        sumsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        sumsTable.setSelectionBackground(ColorConstants.SELECTED_BACKGROUND);
        sumsTable.setSelectionForeground(ColorConstants.SELECTED_FOREGROUND);
        sumsScrollPane.setViewportView(sumsTable);
        centerPanel.add(sumsScrollPane, BorderLayout.CENTER);
        
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridBagLayout());
		southPanel.setBorder(new TitledBorder(null, Resources.getString("labels.totalsumincurrency"),
	                        TitledBorder.DEFAULT_JUSTIFICATION,
	                        TitledBorder.DEFAULT_POSITION, null,
	                        SystemColor.activeCaption));
		
		final JLabel currencyLabel = new JLabel(Resources.getString("labels.currency") + ":");
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(5, 5, 5, 5);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridx = 0;
		southPanel.add(currencyLabel, gridBagConstraints);
		
		currencyBox = new JComboBox();
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(5, 0, 5, 5);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridx = 1;
		southPanel.add(currencyBox, gridBagConstraints);
		
		final JLabel sumLabel = new JLabel(Resources.getString("labels.sum") + ":");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(5, 20, 5, 5);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridx = 2;
		southPanel.add(sumLabel, gridBagConstraints);
		
		sumValueLabel = new JLabel();
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(5, 0, 5, 5);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridx = 3;
		southPanel.add(sumValueLabel, gridBagConstraints);
		
		JLabel emptyLabel = new JLabel();
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(5, 5, 5, 5);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridx = 4;
		gridBagConstraints.weightx = 1;
		southPanel.add(emptyLabel, gridBagConstraints);
        		
        add(centerPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
        
        currencyBox.addItemListener(new ItemListener() {			
			@Override
			public void itemStateChanged(ItemEvent ev) {
				if(ev.getStateChange() == ItemEvent.SELECTED) {
					calculateSumBySelectedCurrency();
				}				
			}
		});
	}
	
	public void clearData() {
		totalSumsTableModel.clearData();
	}

	public void setData(Map<PCurrency, BigDecimal> sumsByCurrency) {
		totalSumsTableModel.setData(sumsByCurrency);
		calculateSumBySelectedCurrency();
	}
	
	public MyJTable getSumsTable() {
		return sumsTable;
	}
	
	public int getAreaWidth() {
		return sumsScrollPane.getWidth();
	}
	
	public void updateCurrencies() {
		PCurrency selectedCurrency = (PCurrency)currencyBox.getSelectedItem();
		currencyBox.removeAllItems();
		for(PCurrency currency : CurrencyController.instance().listActive()) {
			currencyBox.addItem(currency);
		}
		if(selectedCurrency != null) {
			currencyBox.setSelectedItem(selectedCurrency);
		}
	}
	
	private void calculateSumBySelectedCurrency() {
		List<Entry<PCurrency,BigDecimal>> data = totalSumsTableModel.getData();
		BigDecimal totalSumBySelectedCurrency = new BigDecimal(0);
		PCurrency toCurrency = (PCurrency)currencyBox.getSelectedItem();

		//TODO: KOSTYL
		if(toCurrency == null) {
			return;
		}

		for(Entry<PCurrency,BigDecimal> entry : data) {
			PCurrency fromCurrency = entry.getKey();
			BigDecimal value = entry.getValue();
			totalSumBySelectedCurrency = totalSumBySelectedCurrency.add(Calc.convertSum(fromCurrency, toCurrency, value));
		}
		sumValueLabel.setText(MyFormatter.formatNumberToLocal(totalSumBySelectedCurrency.toPlainString()));
	}
}
