package com.monyrama.ui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.monyrama.ui.components.JTextFieldLimited;

public class Calculator extends JFrame {
	private final Font BIGGER_FONT = new Font("monspaced", Font.PLAIN, 20);
	private JTextField textfield;
	private boolean number = true;
	private boolean isFirstDigit = true;
	private String operation = "=";
	private CalculatorOp op = new CalculatorOp();
	private ActionListener numberListener;
	private ActionListener operatorListener;

	public Calculator() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		textfield = new JTextFieldLimited(20);
		textfield.setText("0");
		textfield.setEditable(false);
		textfield.setBackground(Color.WHITE);
		textfield.setHorizontalAlignment(JTextField.RIGHT);
		textfield.setFont(BIGGER_FONT);

		numberListener = new NumberListener();
		String buttonOrder = "1234567890. ";
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(4, 4, 4, 4));
		for (int i = 0; i < buttonOrder.length(); i++) {
			String key = buttonOrder.substring(i, i + 1);
			if (key.equals(" ")) {
				buttonPanel.add(new JLabel(""));
			} else {
				JButton button = new JButton(key);
				button.addActionListener(numberListener);
				button.setFont(BIGGER_FONT);
				buttonPanel.add(button);
			}
		}
		
		operatorListener = new OperatorListener();
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4, 4, 4, 4));
		String[] opOrder = { "+", "-", "*", "/", "=", "C" };
		for (int i = 0; i < opOrder.length; i++) {
			JButton button = new JButton(opOrder[i]);
			button.addActionListener(operatorListener);
			button.setFont(BIGGER_FONT);
			panel.add(button);
		}
		JPanel pan = new JPanel();
		pan.setLayout(new BorderLayout(4, 4));
		pan.add(textfield, BorderLayout.NORTH);
		pan.add(buttonPanel, BorderLayout.CENTER);
		pan.add(panel, BorderLayout.EAST);
		this.setContentPane(pan);
		this.pack();
		this.setTitle("Calculator");
		this.setResizable(false);
		
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new KeyDispatcher());

	}

	private void clear() {
		number = true;
		textfield.setText("0");
		operation = "=";
		op.setTotal("0");
	}

	private class OperatorListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (number) {
				clear();
			} else {
				number = true;
				String displayText = textfield.getText();
				if (operation.equals("=")) {
					op.setTotal(displayText);
				} else if (operation.equals("+")) {
					op.add(displayText);
				} else if (operation.equals("-")) {
					op.subtract(displayText);
				} else if (operation.equals("*")) {
					op.multiply(displayText);
				} else if (operation.equals("/")) {
					op.divide(displayText);
				}
				
				textfield.setText(op.getTotalString());	
				operation = e.getActionCommand();
				
				if(operation.equals("C")) {
					clear();
				}			
			}
		}
	}

	private class NumberListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String digitOrDot = event.getActionCommand();
			if (isFirstDigit) {
				if(digitOrDot.equals(".")) {
					textfield.setText("0.");
				} else {
					textfield.setText(digitOrDot);
				}
				
				isFirstDigit = false;
			} else {
				textfield.setText(textfield.getText() + digitOrDot);
			}
		}
	}

	public class CalculatorOp {
		private BigDecimal total;

		public CalculatorOp() {
			total = new BigDecimal("0");
		}

		public String getTotalString() {
			return total.toPlainString();
		}

		public void setTotal(String n) {
			total = convertToNumber(n);
		}

		public void add(String n) {
			total = total.add(convertToNumber(n));
		}

		public void subtract(String n) {
			total = total.subtract(convertToNumber(n));
		}

		public void multiply(String n) {			
			total = total.multiply(convertToNumber(n));
		}

		public void divide(String n) {
			if("0".equals(n)) {
				JOptionPane.showMessageDialog(Calculator.this, "Division by zero! Discarding.", "Warning", JOptionPane.WARNING_MESSAGE);
				total = BigDecimal.ZERO;
			} else {
				total = total.divide(convertToNumber(n), 2, RoundingMode.HALF_EVEN);	
			}						
		}

		private BigDecimal convertToNumber(String n) {
			return new BigDecimal(n);
		}
	}
	
    private class KeyDispatcher implements KeyEventDispatcher {
        @Override
		public boolean dispatchKeyEvent(KeyEvent e) {
			if (e.getID() == KeyEvent.KEY_PRESSED) {
				System.out.println(e.getKeyChar());
				switch (e.getKeyChar()) {
					case '0':
					case '1':
					case '2':
					case '3':
					case '4':
					case '5':
					case '6':
					case '7':
					case '8':
					case '9':
					case '.':
						ActionEvent numberEvent = new ActionEvent(Calculator.this, 0, Character.toString(e.getKeyChar()));
						numberListener.actionPerformed(numberEvent);
					break;	
					case '+':
					case '-':
					case '/':
					case '*':
					case '=':
						ActionEvent operationEvent = new ActionEvent(Calculator.this, 0, Character.toString(e.getKeyChar()));
						operatorListener.actionPerformed(operationEvent);
					break;
					default:
					break;
				}
				
				switch (e.getKeyCode()) {
				case KeyEvent.VK_DELETE:
					
					break;

				default:
					break;
				}
			}
			return false;
        }
    }
	
	public static void main(String[] args) {
        try {
            // System look and feel
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException e) {
            // handle exception
        }
        catch (ClassNotFoundException e) {
            // handle exception
        }
        catch (InstantiationException e) {
            // handle exception
        }
        catch (IllegalAccessException e) {
            // handle exception
        }
		        
		new Calculator().setVisible(true);
	}
}