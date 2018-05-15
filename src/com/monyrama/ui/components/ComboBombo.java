package com.monyrama.ui.components;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import com.monyrama.ui.constants.DimensionConstants;

public class ComboBombo<T> extends JPanel {

	private static final long serialVersionUID = 1L;
	private JComboBox comboBox;
	private JButton plusButton;

	public ComboBombo() {
		setLayout(new GridBagLayout());
		
		comboBox = new JComboBox();
		comboBox.setPreferredSize(new Dimension(comboBox.getPreferredSize().width, DimensionConstants.FIELD_HEIGHT));
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
		add(comboBox, gridBagConstraints);
		
		plusButton = new JButton(new ImageIcon(getClass().getResource("plus_blue.png")));
		plusButton.setPreferredSize(new Dimension(DimensionConstants.FIELD_HEIGHT, DimensionConstants.FIELD_HEIGHT));
		gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(0, 2, 0, 0);
		add(plusButton, gridBagConstraints);
	}
	
	public void addItem(T item) {
		comboBox.addItem(item);		
	}
	
	public void addItemListener(ItemListener itemListener) {
		comboBox.addItemListener(itemListener);
	}
	
	public void removeAllItems() {
		comboBox.removeAllItems();
	}

	public void setSelectedItem(T item) {
		comboBox.setSelectedItem(item);
	}

	@SuppressWarnings("unchecked")
	public T getSelectedItem() {
		return (T)comboBox.getSelectedItem();
	}
	
	public void addPlusButtonListener(ActionListener actionListener) {
		plusButton.addActionListener(actionListener);
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		comboBox.setEnabled(enabled);
		plusButton.setEnabled(enabled);
	}
	
}
