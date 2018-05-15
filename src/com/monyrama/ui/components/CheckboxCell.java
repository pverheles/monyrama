package com.monyrama.ui.components;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class CheckboxCell extends JPanel {
	private JCheckBox checkBox;
	
	public CheckboxCell() {
		FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 0, 2);
		setLayout(flowLayout);
		checkBox = new JCheckBox();
		checkBox.setBorder(BorderFactory.createEmptyBorder());
		add(checkBox, BorderLayout.CENTER);
	}
	
	public void setSelected(boolean selected) {
		checkBox.setSelected(selected);
	}
	
	public void setEnabled(boolean enabled) {
		checkBox.setEnabled(enabled);
	}
}
