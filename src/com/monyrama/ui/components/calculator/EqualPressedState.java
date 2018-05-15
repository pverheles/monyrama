package com.monyrama.ui.components.calculator;

import com.monyrama.ui.components.calculator.CalculatorException;
import com.monyrama.ui.components.calculator.Model;

import java.util.Map;

class EqualPressedState extends State {

	EqualPressedState(Model model, Map<StateName, State> states, Listener listener) {
		super(model, states, listener);
	}

	@Override
	protected State doEffectiveTransition(char k) {
		State returnState = this;
		if(isDigitalKey(k)) {
			model.setNumOne(String.valueOf(k));
			listener.valueChanged(model.getNumOne());
			returnState = states.get(StateName.KEY_FOR_NUM_ONE_PRESSED);
		} else if(isOperationKey(k)) {
			model.setOperation(k);
			returnState = states.get(StateName.KEY_FOR_OPERATION_PRESSED);			
		} else if(isEqualKey(k)) {
			String result;
			try {
				result = calculate();
				model.setNumOne(result);
				listener.valueChanged(result);
			} catch (CalculatorException e) {
				listener.undefinedResult();
			}						
		}
		
		return returnState;
	}

}
