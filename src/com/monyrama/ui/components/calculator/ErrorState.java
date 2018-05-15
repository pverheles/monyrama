package com.monyrama.ui.components.calculator;

import com.monyrama.ui.components.calculator.Model;

import java.util.Map;

public class ErrorState extends State {

	ErrorState(Model model, Map<StateName, State> states, Listener listener) {
		super(model, states, listener);
	}

	@Override
	State doEffectiveTransition(char k) {
		return this;
	}

}
