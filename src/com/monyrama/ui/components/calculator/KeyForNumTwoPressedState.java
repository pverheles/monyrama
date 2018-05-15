package com.monyrama.ui.components.calculator;

import com.monyrama.ui.components.calculator.CalculatorException;
import com.monyrama.ui.components.calculator.Model;

import java.util.Map;

class KeyForNumTwoPressedState extends State {

	KeyForNumTwoPressedState(Model model, Map<StateName, State> states, Listener listener) {
		super(model, states, listener);		
	}

	@Override
	protected State doEffectiveTransition(char k) {
		State returnState = this;
		if(isDigitalKey(k)) {
			model.appendNumTwo(k);
			listener.valueChanged(model.getNumTwo());
		} if(isBackspaceKey(k)) {
			model.removeLastSymbolInNumTwo();
			listener.valueChanged(model.getNumTwo());
		} else if(isOperationKey(k)) {
			String result;
			try {
				result = calculate();
				model.setNumOne(result);
				listener.valueChanged(result);
				model.setOperation(k);
				returnState = states.get(StateName.KEY_FOR_OPERATION_PRESSED);
			} catch (CalculatorException e) {
				listener.undefinedResult();
				returnState = states.get(StateName.ERROR);
			}		
						
		} else if(isEqualKey(k)) {
			String result;
			try {
				result = calculate();
				model.setNumOne(result);
				listener.valueChanged(result);
				returnState = states.get(StateName.EQUAL_PRESSED);
			} catch (CalculatorException e) {
				listener.undefinedResult();
				returnState = states.get(StateName.ERROR);
			}								
		}
		
		return returnState;
	}

}
