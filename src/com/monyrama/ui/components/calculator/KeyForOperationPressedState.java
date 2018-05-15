package com.monyrama.ui.components.calculator;

import com.monyrama.ui.components.calculator.CalculatorException;
import com.monyrama.ui.components.calculator.Model;

import java.util.Map;

class KeyForOperationPressedState extends State {

	KeyForOperationPressedState(Model model, Map<StateName, State> states, Listener listener) {
		super(model, states, listener);
	}

	@Override
	protected State doEffectiveTransition(char k) {
		State returnState = this;
		if(isDigitalKey(k)) {
			model.setNumTwo(String.valueOf(k));
			listener.valueChanged(model.getNumTwo());
			returnState = states.get(StateName.KEY_FOR_NUM_TWO_PRESSED);
		} else if(isOperationKey(k)) {
			model.setOperation(k);	
		} else if(isEqualKey(k)) {
			model.setNumTwo(model.getNumOne());
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
