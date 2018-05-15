package com.monyrama.ui.view;

import com.monyrama.ui.resources.Resources;

import javax.swing.JPanel;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class GeneralPanel extends JPanel {

	public GeneralPanel() {
		super();
	}

	public abstract void initOnVisible();

	protected String formatDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat(Resources.getDateFormat(), Resources.getLocale());
		return formatter.format(date);
	}
}
