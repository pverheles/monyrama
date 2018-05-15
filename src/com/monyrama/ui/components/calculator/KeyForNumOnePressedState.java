package com.monyrama.ui.components.calculator;

import com.monyrama.ui.components.calculator.Model;

import java.util.Map;

class KeyForNumOnePressedState extends State {

	KeyForNumOnePressedState(Model model, Map<StateName, State> states, Listener listener) {
		super(model, states, listener);
	}

	@Override
	protected State doEffectiveTransition(char k) {
		State returnState = this;
		if(isDigitalKey(k)) {
			model.appendNumOne(k);
			listener.valueChanged(model.getNumOne());
		} else if(isBackspaceKey(k)) {
			model.removeLastSymbolInNumOne();
			listener.valueChanged(model.getNumOne());
		} else if(isOperationKey(k)) {
			model.setOperation(k);
			returnState = states.get(StateName.KEY_FOR_OPERATION_PRESSED);
		}
		
		return returnState;
	}

}
