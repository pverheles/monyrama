package com.monyrama.ui.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ExpandableLabel extends JPanel {
	private JPanel panel;
	private JLabel label;
	private JButton button;
	private JDialog dialog;
	private JTextArea textArea;
	
	private int popUpAreaHeight = 80;
	private int buttonWidth = 25;
	private int buttonHeight = 20;

	public ExpandableLabel() {
		setLayout(new GridBagLayout());		
		
		panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		add(panel);

		GridBagConstraints gridBagConstraints;
		
		label = new JLabel();
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.weightx = 1.0;
		panel.add(label);
		
		button = new JButton("...");
		button.setMargin(new Insets(0, 2, 0, 2));
		button.setMinimumSize(new Dimension(buttonWidth, buttonHeight));
		button.setMaximumSize(new Dimension(buttonWidth, buttonHeight));
		button.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
		button.setVisible(false);
		button.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {				
				if(dialog == null) {
					textArea = new JTextArea();
					textArea.setWrapStyleWord(true);
					textArea.setLineWrap(true);
					textArea.setFont(label.getFont());
					textArea.addFocusListener(new FocusListener() {
						
						@Override
						public void focusLost(FocusEvent arg0) {
							dialog.setVisible(false);
							
						}
						
						@Override
						public void focusGained(FocusEvent arg0) {
							// TODO Auto-generated method stub
							
						}
					});
					
					textArea.setEditable(false);
					JScrollPane textAreaPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
					
					dialog = new JDialog();
					dialog.setUndecorated(true);
					dialog.setLayout(new BorderLayout());
					
					dialog.add(textAreaPane, BorderLayout.CENTER);					
				}
				
				textArea.setText(label.getText());
				textArea.setCaretPosition(0);
				dialog.setBounds(panel.getLocationOnScreen().x, panel.getLocationOnScreen().y + label.getHeight(), panel.getWidth(), popUpAreaHeight);
				dialog.setVisible(true);
			}
		});
		
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 3, 0, 0);
		panel.add(button);
	}

	public void setText(String text) {
		label.setText(text);
		if(text == null || text.equals("")) {
			button.setVisible(false);
		} else {
			button.setVisible(true);
		}
	}
	
	public int getPopUpAreaHeight() {
		return popUpAreaHeight;
	}

	public void setPopUpAreaHeight(int popUpAreaHeight) {
		this.popUpAreaHeight = popUpAreaHeight;
	}

	@Override
	public void setMaximumSize(Dimension maximumSize) {
		super.setMaximumSize(maximumSize);		
		int width = maximumSize.width - buttonWidth;
		label.setMaximumSize(new Dimension(width > 0 ? width : 0, maximumSize.height));
		button.setMaximumSize(new Dimension(buttonWidth, maximumSize.height));
	}

	@Override
	public void setMinimumSize(Dimension minimumSize) {
		super.setMinimumSize(minimumSize);
		int width = minimumSize.width - buttonWidth;
		label.setMinimumSize(new Dimension(width > 0 ? width : 0, minimumSize.height));
		button.setMinimumSize(new Dimension(buttonWidth, minimumSize.height));
	}

	@Override
	public void setPreferredSize(Dimension preferredSize) {
		super.setPreferredSize(preferredSize);
		int width = preferredSize.width - buttonWidth;
		label.setPreferredSize(new Dimension(width > 0 ? width : 0, preferredSize.height));
		button.setPreferredSize(new Dimension(buttonWidth, preferredSize.height));
	}

	@Override
	public void setSize(Dimension d) {
		super.setSize(d);
		int width = d.width - buttonWidth;
		label.setPreferredSize(new Dimension(width > 0 ? width : 0, d.height));
		button.setPreferredSize(new Dimension(buttonWidth, d.height));		
	}

	@Override
	public void setSize(int cwidth, int cheight) {
		super.setSize(cwidth, cheight);
		int width = cwidth - buttonWidth;
		label.setSize(width > 0 ? width : 0, cheight);
		button.setSize(buttonWidth, cheight);		
	}
	
	
}
