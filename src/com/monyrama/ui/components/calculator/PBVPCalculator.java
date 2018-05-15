package com.monyrama.ui.components.calculator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PBVPCalculator extends JDialog {
	private final Font BIGGER_FONT = new Font("monspaced", Font.PLAIN, 20);
	private JTextField textfield;
	private ActionListener actionListener;	
	private StateMachine stateMachine;
	private PBVPCalculatorListener calculatorListener;

	private boolean commaSeparator = false;
	private JButton insertButton;

	public PBVPCalculator(final String title, final String errorMessage, boolean cSeparator, boolean insertVisible) {
		setTitle(title);
		setModal(true);
		this.commaSeparator = cSeparator;
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);	
		
		Listener listener = new Listener() {			
			@Override
			public void valueChanged(String value) {
				if(commaSeparator) {
					value = value.replaceFirst("\\.", ",");
				}
				textfield.setText(value);
			}

			@Override
			public void undefinedResult() {
				textfield.setText(errorMessage);				
			}
		};
		
		stateMachine = new StateMachine(listener);
		
		actionListener = new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				textfield.requestFocus();
				String command = e.getActionCommand();
				stateMachine.execute(command.charAt(0));				
			}
		};
		
		textfield = new JTextField();
		textfield.setText("0");
		textfield.setEditable(false);
		textfield.setBackground(Color.WHITE);
		textfield.setHorizontalAlignment(JTextField.RIGHT);
		textfield.setFont(BIGGER_FONT);

		String buttonOrder = "1234567890";		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(4, 3, 4, 4));
		for (int i = 0; i < buttonOrder.length(); i++) {
			char key = buttonOrder.charAt(i);
			JButton button = new JButton(String.valueOf(key));
			button.setActionCommand(String.valueOf(key));
			button.addActionListener(actionListener);
			button.setFont(BIGGER_FONT);
			buttonPanel.add(button);
		}
		
		JButton separatorButton = new JButton(commaSeparator ? "," : ".");
		separatorButton.setActionCommand(".");
		separatorButton.addActionListener(actionListener);
		separatorButton.setFont(BIGGER_FONT);
		buttonPanel.add(separatorButton);
		
		JButton backSpaceButton = new JButton("\u2190");
		backSpaceButton.setActionCommand("B");
		backSpaceButton.addActionListener(actionListener);
		backSpaceButton.setFont(BIGGER_FONT);
		buttonPanel.add(backSpaceButton);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4, 4, 4, 4));
		String[] opOrder = { "+", "-", "*", "/", "=", "C" };
		for (int i = 0; i < opOrder.length; i++) {
			JButton button = new JButton(opOrder[i]);
			button.setActionCommand(opOrder[i]);
			button.addActionListener(actionListener);
			button.setFont(BIGGER_FONT);
			panel.add(button);
		}		
		
		if(insertVisible) {
			panel.add(new JLabel());
			
			insertButton = new JButton("\u21E9");			
			insertButton.addActionListener(new ActionListener() {				
				@Override
				public void actionPerformed(ActionEvent e) {					
					notifyInsertPressed();
				}
			});
			insertButton.setFont(BIGGER_FONT);
			panel.add(insertButton);
		}
		 
		JPanel pan = new JPanel();
		pan.setLayout(new BorderLayout(4, 4));
		pan.add(textfield, BorderLayout.NORTH);
		pan.add(buttonPanel, BorderLayout.CENTER);
		pan.add(panel, BorderLayout.EAST);
		this.setContentPane(pan);
		this.pack();
		this.setResizable(false);
		
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new KeyDispatcher());

	}
	
	class KeyDispatcher implements KeyEventDispatcher {
		@Override
		public boolean dispatchKeyEvent(KeyEvent e) {
			if(e.getID() == KeyEvent.KEY_PRESSED) {
				String command = null;
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
					case '+':
					case '-':
					case '*':
					case '/':
					case '=':
					case 'C':
						command = String.valueOf(e.getKeyChar());
						break;
					default:
						break;
				}
				
				char separatorChar = commaSeparator ? ',' : '.';
				
				if(separatorChar == e.getKeyChar()) {
					command = ".";
				}
				
				if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					command = "B";
				} else if(e.getKeyCode() == KeyEvent.VK_DELETE) {
					command = "C";
				} else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					command = "=";
				} if(e.getKeyCode() == KeyEvent.VK_DOWN) {
					notifyInsertPressed();
				}
				
				if(command != null) {
					ActionEvent actionEvent = new ActionEvent(new Object(), 0, command);
					actionListener.actionPerformed(actionEvent);					
				}

			}
						
			return false;
		}		
	}

	private void notifyInsertPressed() {
		if(calculatorListener != null) {
			calculatorListener.insertPressed(textfield.getText());
		}
	}
	
	public void setCalculatorListener(PBVPCalculatorListener calculatorListener) {
		this.calculatorListener = calculatorListener;
	}
		
	public void setInsertTooltip(String insertTooltip) {
		insertButton.setToolTipText(insertTooltip);
	}	
	
//	public static void main(String[] args) {
//        try {
//            // System look and feel
//            UIManager.setLookAndFeel(
//                    UIManager.getSystemLookAndFeelClassName());
//        }
//        catch (UnsupportedLookAndFeelException e) {
//            // handle exception
//        }
//        catch (ClassNotFoundException e) {
//            // handle exception
//        }
//        catch (InstantiationException e) {
//            // handle exception
//        }
//        catch (IllegalAccessException e) {
//            // handle exception
//        }
//		        
//        final PBVPCalculator calculator = new PBVPCalculator("My Calculator", "Error", false, false);
//        calculator.setCalculatorListener(new PBVPCalculatorListener() {			
//			@Override
//			public void insertPressed(String value) {
//				System.out.println(value);
//				calculator.dispose();
//			}
//		});
//        calculator.setVisible(true);
//	}
}