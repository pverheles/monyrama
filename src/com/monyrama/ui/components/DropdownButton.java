package com.monyrama.ui.components;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public class DropdownButton extends JPanel {
	private JButton mainButton;
	private JButton arrowButton;
	private JPopupMenu popupMenu;
	
	private int selectedIndex = 0;
	
	private List<Action> actions = new ArrayList<Action>();
	
	public DropdownButton() {
		setLayout(new GridBagLayout());
	
		GridBagConstraints constraints;
		
		mainButton = new JButton();
		mainButton.setHorizontalAlignment(JButton.LEFT);
		mainButton.setMargin(new Insets(0, 15, 0, 0));
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(0, 0, 0, 0);
		add(mainButton, constraints);
		
		mainButton.setLayout(new GridBagLayout());
		
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1.0;	
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.NORTH;
		mainButton.add(new JLabel(), constraints);
		
		arrowButton = new JButton("\u25BC"); //arrow down code
		arrowButton.setMargin(new Insets(2, 2, 2, 2));
		arrowButton.setHorizontalTextPosition(JButton.CENTER);
		arrowButton.setHorizontalAlignment(JButton.CENTER);
		arrowButton.setVerticalAlignment(JButton.CENTER);
		arrowButton.setVerticalTextPosition(JButton.CENTER);
		constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.weighty = 1.0;
		constraints.insets = new Insets(-2, -2, -2, -2);
		constraints.fill = GridBagConstraints.VERTICAL;
		constraints.anchor = GridBagConstraints.EAST;
		mainButton.add(arrowButton, constraints);
				
		arrowButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				popupMenu.show(DropdownButton.this, 1, mainButton.getHeight());
			}
		});
		
		popupMenu = new JPopupMenu();
		
	}
	
	public void addAction(final Action action) {
		actions.add(action);
		JMenuItem menuItem = new JMenuItem(action);
		menuItem.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedIndex = actions.indexOf(action);
				mainButton.setAction(action);
			}
		});
		popupMenu.add(menuItem);
		mainButton.setAction(action);
	}
	
	public void setSelectedIndex(int index) {
		if(index >= 0 && index < actions.size()) {
			selectedIndex = index;
			mainButton.setAction(actions.get(index));
		}
	}
	
	public int getSelectedIndex() {
		return selectedIndex;
	}
			
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setLayout(new GridBagLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		DropdownButton dropdownButton = new DropdownButton();
		dropdownButton.setPreferredSize(new Dimension(200, 200));
		
		dropdownButton.addAction(dropdownButton.new TestAction());
		dropdownButton.addAction(dropdownButton.new Test2Action());
		
		dropdownButton.setSelectedIndex(0);
				
		frame.add(dropdownButton);
		frame.setSize(900, 600);
		
		frame.setVisible(true);
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		mainButton.setEnabled(enabled);
		arrowButton.setEnabled(enabled);
	}

	private class TestAction extends AbstractAction {
				
		public TestAction() {
			super("Test", null);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {	
			System.out.println("Test 1");
		}
	}
	
	private class Test2Action extends AbstractAction {
		
		public Test2Action() {
			super("Test2", null);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Test 2");
		}
	}	
	
}
