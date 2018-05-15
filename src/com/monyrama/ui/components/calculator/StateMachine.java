package com.monyrama.ui.components.calculator;

import com.monyrama.ui.components.calculator.Model;

import java.util.HashMap;
import java.util.Map;

class StateMachine {

	private State state;
	private Model model = new Model();
	
	public StateMachine(Listener listener) {
		Map<StateName, State> states = new HashMap<StateName, State>();
		State startState = state = new StartState(model, states, listener);
		states.put(StateName.START, startState);
		states.put(StateName.KEY_FOR_NUM_ONE_PRESSED, new KeyForNumOnePressedState(model, states, listener));
		states.put(StateName.KEY_FOR_OPERATION_PRESSED, new KeyForOperationPressedState(model, states, listener));
		states.put(StateName.KEY_FOR_NUM_TWO_PRESSED, new KeyForNumTwoPressedState(model, states, listener));
		states.put(StateName.EQUAL_PRESSED, new EqualPressedState(model, states, listener));
		states.put(StateName.ERROR, new ErrorState(model, states, listener));
	}
	
	public void execute(char k) {
		state = state.doTransition(k);
	}
}
