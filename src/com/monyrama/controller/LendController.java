package com.monyrama.controller;

import com.monyrama.entity.PLend;

public class LendController extends MoneyMovementOutController<PLend> {
	protected LendController(Class<PLend> clazz) {
		super(clazz);
	}

	private static LendController instance;

	public static LendController instance() {		
		if(instance == null) {
			instance = new LendController(PLend.class);
		}
		return instance;		
	}
}
