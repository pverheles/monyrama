package com.monyrama.ui.view.charts;

import java.awt.Color;
import java.awt.GradientPaint;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.CategoryToolTipGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import com.monyrama.entity.PExpense;
import com.monyrama.ui.resources.Resources;

public class DailyExpenseSumChart extends JFrame {
	public DailyExpenseSumChart(Collection<PExpense> spentUnits, String expensesPlanName) {        
        SortedMap<Long, BigDecimal> sumByDateMap = new TreeMap<Long, BigDecimal>();
        
        for(PExpense spentUnit : spentUnits) {
        	BigDecimal sumByDate = sumByDateMap.get(dateTimeToLongOnlyDate(spentUnit.getLastChangeDate()));
        	if(sumByDate == null) {
        		sumByDateMap.put(dateTimeToLongOnlyDate(spentUnit.getLastChangeDate()), spentUnit.getSumm());
        	} else {
        		sumByDate = sumByDate.add(spentUnit.getSumm());
        		sumByDateMap.put(dateTimeToLongOnlyDate(spentUnit.getLastChangeDate()), sumByDate);
        	}        	
        }	      
        
        
        DefaultCategoryDataset xyDataset = new DefaultCategoryDataset();
        for(Long date : sumByDateMap.keySet()) {
        	xyDataset.addValue(sumByDateMap.get(date), Resources.getString("labels.date"), DateFormat.getDateInstance(DateFormat.SHORT, Resources.getLocale()).format(new Date(date)));
        }
       
        String title = Resources.getString("charts.labels.dailyexpense") + " (" + Resources.getString("labels.expenses.plan") + ": " + expensesPlanName + ")";

        JFreeChart chart = createChart(xyDataset, title);
        ChartPanel chartPanel = new ChartPanel(chart);
        JScrollPane scrollPane = new JScrollPane(chartPanel);
		setContentPane(scrollPane);
		
		setTitle(title);
		setSize(850, 600);
	}

	private long dateTimeToLongOnlyDate(Date datetime) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(datetime);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis();
	}
	
    private JFreeChart createChart(final CategoryDataset dataset, String title) {
        
        // create the chart...
        final JFreeChart chart = ChartFactory.createBarChart(
        	title,         // chart title
            Resources.getString("labels.date"),               // domain axis label
            Resources.getString("labels.sum"),                  // range axis label
            dataset,                  // data
            PlotOrientation.VERTICAL, // orientation
            true,                     // include legend
            true,                     // tooltips?
            false                     // URLs?
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

        // set the background color for the chart...
        chart.setBackgroundPaint(Color.white);

        // get a reference to the plot for further customisation...
        final CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        plot.setNoDataMessage(Resources.getString("charts.labels.nodata"));

        // set the range axis to display integers only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits(Resources.getLocale()));

        // disable bar outlines...
        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        renderer.setBaseSeriesVisibleInLegend(false);
        NumberFormat numberFormat = NumberFormat.getInstance(Resources.getLocale());
        numberFormat.setGroupingUsed(false);
        CategoryToolTipGenerator tooltipGenerator = new StandardCategoryToolTipGenerator("{2}", numberFormat);
        renderer.setBaseToolTipGenerator(tooltipGenerator);        
        
        // set up gradient paints for series...
        final GradientPaint gp0 = new GradientPaint(
            0.0f, 0.0f, Color.blue, 
            0.0f, 0.0f, Color.lightGray
        );

        renderer.setSeriesPaint(0, gp0);

        final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
            CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
        );
        
        return chart;
        
    }	
}
