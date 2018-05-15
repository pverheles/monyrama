package com.monyrama.ui.view.charts;

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

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.monyrama.entity.PExpense;
import com.monyrama.ui.resources.Resources;

public class DailyGeneralSumChart extends JFrame {
	public DailyGeneralSumChart(Collection<PExpense> spentUnits, String expensesPlanName) {
        final XYSeries dataSeries = new XYSeries(Resources.getString("charts.dailyexpense.tooltip.generalsumfordate"));
        
        DateAxis dateAxis = new DateAxis(Resources.getString("labels.date"));
        dateAxis.setVerticalTickLabels(true);

        DateTickUnit unit = null;
        unit = new DateTickUnit(DateTickUnitType.DAY, 5);

        
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Resources.getLocale());
        dateAxis.setDateFormatOverride(dateFormat);
        dateAxis.setTickUnit(unit);
        
        NumberAxis valueAxis = new NumberAxis(Resources.getString("labels.sum"));
        valueAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits(Resources.getLocale()));
        
        SortedMap<Long, BigDecimal> sumByDateMap = new TreeMap<Long, BigDecimal>();
        
        //First count sum by date
        for(PExpense spentUnit : spentUnits) {
        	BigDecimal sumByDate = sumByDateMap.get(dateTimeToLongOnlyDate(spentUnit.getLastChangeDate()));
        	if(sumByDate == null) {
        		sumByDateMap.put(dateTimeToLongOnlyDate(spentUnit.getLastChangeDate()), spentUnit.getSumm());
        	} else {
        		sumByDate = sumByDate.add(spentUnit.getSumm());
        		sumByDateMap.put(dateTimeToLongOnlyDate(spentUnit.getLastChangeDate()), sumByDate);
        	}        	
        }	      
        
        //Then count general sum to date
        BigDecimal generalSum = new BigDecimal(0);
        for(Long date : sumByDateMap.keySet()) {
        	generalSum = generalSum.add(sumByDateMap.get(date));
        	sumByDateMap.put(date, generalSum);
        }     
        
        for(Long date : sumByDateMap.keySet()) {
        	dataSeries.add(date,sumByDateMap.get(date));
        }
        
        XYSeriesCollection xyDataset = new XYSeriesCollection(dataSeries);

        NumberFormat numberFormat = NumberFormat.getInstance(Resources.getLocale());
        numberFormat.setGroupingUsed(false);
        StandardXYToolTipGenerator ttg = new StandardXYToolTipGenerator(
                "{0} {1}: {2}", dateFormat, numberFormat);


        StandardXYItemRenderer renderer = new StandardXYItemRenderer(StandardXYItemRenderer.SHAPES_AND_LINES, ttg, null);

        renderer.setShapesFilled(true);

        XYPlot plot = new XYPlot(xyDataset, dateAxis, valueAxis, renderer);
       
        plot.setNoDataMessage(Resources.getString("charts.labels.nodata"));

        String title = Resources.getString("charts.labels.generalsumbydate") + " (" + Resources.getString("labels.expenses.plan") + ": " + expensesPlanName + ")";
        JFreeChart chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, false);
        chart.setBackgroundPaint(java.awt.Color.WHITE);
       		
        JScrollPane scrollPane = new JScrollPane(new ChartPanel(chart));
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
}
