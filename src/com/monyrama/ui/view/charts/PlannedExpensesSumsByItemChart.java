package com.monyrama.ui.view.charts;

import java.awt.Font;
import java.util.Collection;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import com.monyrama.entity.PExpensePlanItem;
import com.monyrama.ui.resources.Resources;

public class PlannedExpensesSumsByItemChart extends JFrame {
	public PlannedExpensesSumsByItemChart(Collection<PExpensePlanItem> budgetUnits, String expensesPlanName) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		
		for(PExpensePlanItem budgetUnit : budgetUnits) {
			dataset.setValue(budgetUnit.toString(), budgetUnit.getSumm());
		}		
				
		String title = Resources.getString("charts.labels.plannedsumsbyitems") + " (" + Resources.getString("labels.expenses.plan") + ": " + expensesPlanName + ")";
		JFreeChart jfc = ChartFactory.createPieChart(title, dataset, true, true, false);
		PiePlot pp = (PiePlot) jfc.getPlot();
		pp.setToolTipGenerator(new StandardPieToolTipGenerator(Resources.getLocale()));
		pp.setBackgroundAlpha(0.2f);
		pp.setSectionOutlinesVisible(false);
		pp.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
		pp.setNoDataMessage(Resources.getString("charts.labels.nodata"));
		pp.setCircular(false);
		pp.setLabelGap(0.02);
		
		setTitle(title);
        JScrollPane scrollPane = new JScrollPane(new ChartPanel(jfc));
		setContentPane(scrollPane);		
		setSize(850, 600);				
	}
}
