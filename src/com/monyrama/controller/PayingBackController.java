package com.monyrama.controller;

import com.monyrama.entity.PPayingBack;

public class PayingBackController extends MoneyMovementOutController<PPayingBack> {
	protected PayingBackController(Class<PPayingBack> clazz) {
		super(clazz);
	}

	private static PayingBackController instance;

	public static PayingBackController instance() {		
		if(instance == null) {
			instance = new PayingBackController(PPayingBack.class);
		}
		return instance;		
	}
}
