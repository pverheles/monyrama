package com.monyrama.ui.components.calculator;

import com.monyrama.ui.components.calculator.CalculatorException;
import com.monyrama.ui.components.calculator.Model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

abstract class State {
	protected Model model;
	protected Map<StateName, State> states;
	protected Listener listener;
	
	State(Model model, Map<StateName, State> states, Listener listener) {
		this.model = model;
		this.states = states;
		this.listener = listener;
	}

	public State doTransition(char k) {
		if(k == 'C') {
			model.reset();
			listener.valueChanged("0");
			return states.get(StateName.START);
		}
		
		return doEffectiveTransition(k);
	}
	
	protected boolean isDigitalKey(char k) {
		return (k >= '0' && k <= '9') || (k == '.');
	}
	
	protected boolean isOperationKey(char k) {
		return k == '+' || k == '-' || k == '*' || k == '/';
	}
	
	protected boolean isEqualKey(char k) {
		return k == '=';
	}
	
	protected boolean isBackspaceKey(char k) {
		return k == 'B';
	}
	
	protected String calculate() throws CalculatorException {
		try {
			BigDecimal result = null;
			BigDecimal firstNum = convertToBigDecimal(model.getNumOne());
			BigDecimal secondNum = convertToBigDecimal(model.getNumTwo());
			
			switch (model.getOperation()) {
			case '+':
				result = firstNum.add(secondNum);
				break;
			case '-':
				result = firstNum.subtract(secondNum);
				break;
			case '*':
				result = firstNum.multiply(secondNum);
				break;
			case '/':
				result = firstNum.divide(secondNum, 10, RoundingMode.HALF_EVEN);
				break;			
			default:
				break;
			}
			
			String resultString = result.stripTrailingZeros().toPlainString();
			
			return resultString;
		} catch (ArithmeticException e) {
			throw new CalculatorException(e);
		}	
	}
	
	private BigDecimal convertToBigDecimal(String n) {
		return new BigDecimal(n);
	}	
	
	abstract State doEffectiveTransition(char k); 
}
