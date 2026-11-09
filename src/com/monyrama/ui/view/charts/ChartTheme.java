package com.monyrama.ui.view.charts;

import java.awt.Color;

import javax.swing.UIManager;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.Axis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.title.Title;

import com.monyrama.ui.constants.ColorConstants;

/**
 * Restyles a chart for the dark theme; light themes keep the charts' own white look.
 */
final class ChartTheme {

	private static final Color GRID = new Color(85, 85, 85);
	private static final Color LABEL_BACKGROUND = new Color(60, 63, 65);

	private ChartTheme() {
	}

	static void apply(JFreeChart chart) {
		if (!ColorConstants.isDark()) {
			return;
		}
		Color background = UIManager.getColor("Panel.background");
		Color foreground = UIManager.getColor("Label.foreground");

		chart.setBackgroundPaint(background);
		if (chart.getTitle() != null) {
			chart.getTitle().setPaint(foreground);
		}
		for (int i = 0; i < chart.getSubtitleCount(); i++) {
			Title title = chart.getSubtitle(i);
			if (title instanceof LegendTitle legend) {
				legend.setBackgroundPaint(background);
				legend.setItemPaint(foreground);
			} else if (title instanceof TextTitle text) {
				text.setPaint(foreground);
			}
		}

		Plot plot = chart.getPlot();
		plot.setBackgroundPaint(UIManager.getColor("Table.background"));
		plot.setOutlinePaint(GRID);
		if (plot instanceof XYPlot xy) {
			styleAxis(xy.getDomainAxis(), foreground);
			styleAxis(xy.getRangeAxis(), foreground);
			xy.setDomainGridlinePaint(GRID);
			xy.setRangeGridlinePaint(GRID);
		} else if (plot instanceof CategoryPlot category) {
			styleAxis(category.getDomainAxis(), foreground);
			styleAxis(category.getRangeAxis(), foreground);
			category.setDomainGridlinePaint(GRID);
			category.setRangeGridlinePaint(GRID);
		} else if (plot instanceof PiePlot pie) {
			pie.setLabelBackgroundPaint(LABEL_BACKGROUND);
			pie.setLabelPaint(foreground);
			pie.setLabelOutlinePaint(GRID);
			pie.setLabelShadowPaint(null);
			pie.setLabelLinkPaint(foreground);
			pie.setShadowPaint(null);
		}
	}

	private static void styleAxis(Axis axis, Color foreground) {
		if (axis == null) {
			return;
		}
		axis.setLabelPaint(foreground);
		axis.setTickLabelPaint(foreground);
		axis.setAxisLinePaint(GRID);
		axis.setTickMarkPaint(GRID);
	}
}
