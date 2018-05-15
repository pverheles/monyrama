package com.monyrama.controller;

import com.monyrama.entity.PDebt;


public class DebtController extends MoneyMovementInController<PDebt> {
	protected DebtController(Class<PDebt> clazz) {
		super(clazz);
	}

	private static DebtController instance;
	
	public static DebtController instance() {		
		if(instance == null) {
			instance = new DebtController(PDebt.class);
		}
		return instance;		
	}
}
