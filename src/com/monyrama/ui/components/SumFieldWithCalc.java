package com.monyrama.ui.components;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.monyrama.ui.components.calculator.PBVPCalculator;
import com.monyrama.ui.components.calculator.PBVPCalculatorListener;
import com.monyrama.ui.constants.DimensionConstants;

public class SumFieldWithCalc extends JPanel {
	private JTextField textField;
	private JButton calcButton;
	
	public SumFieldWithCalc(final int maxlength, final String calcTitle, final String calcError, final String calcInsertButtonTooltip, final boolean commaSeparatorForCalc) {
		setLayout(new GridBagLayout());
		
		textField = new JTextFieldLimited(maxlength);
		textField.setPreferredSize(new Dimension(textField.getPreferredSize().width, DimensionConstants.FIELD_HEIGHT));
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
		add(textField, gridBagConstraints);
		
		calcButton = new JButton();		
		calcButton.setIcon(new ImageIcon(getClass().getResource("calc.png")));
		calcButton.setPreferredSize(new Dimension(DimensionConstants.FIELD_HEIGHT, DimensionConstants.FIELD_HEIGHT));
		gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(0, 2, 0, 0);
		add(calcButton, gridBagConstraints);
		
		calcButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				final PBVPCalculator calculator = new PBVPCalculator(calcTitle, calcError, commaSeparatorForCalc, true);
		        calculator.setLocationRelativeTo(textField);
		        calculator.setInsertTooltip(calcInsertButtonTooltip);
		        calculator.setCalculatorListener(new PBVPCalculatorListener() {			
					@Override
					public void insertPressed(String value) {						
						if(commaSeparatorForCalc) {
							value = value.replaceFirst(",", ".");
						}
						
						BigDecimal bdValue = new BigDecimal(value);
						
						if(!bdValue.equals(BigDecimal.ZERO)) {
							bdValue = bdValue.setScale(2, RoundingMode.HALF_EVEN);						
							
							value = bdValue.stripTrailingZeros().toPlainString();
							
							if(commaSeparatorForCalc) {
								value = value.replaceFirst("\\.", ",");
							}						
							
							textField.setText(value); 
						}						

						calculator.setVisible(false);
					}
				});
		        calculator.setVisible(true);
			}
		});
	}
	
	public void setText(String text) {
		textField.setText(text);
	}
	
	public String getText() {
		return textField.getText();
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		textField.setEnabled(enabled);
		calcButton.setEnabled(enabled);		
	}
	
	@Override
	public void addKeyListener(KeyListener keyListener) {
		textField.addKeyListener(keyListener);
	}
}
