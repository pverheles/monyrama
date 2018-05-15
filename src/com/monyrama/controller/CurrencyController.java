package com.monyrama.controller;

import com.monyrama.entity.PCurrency;

public class CurrencyController extends AbstractController<PCurrency> {
	protected CurrencyController(Class<PCurrency> clazz) {
		super(clazz);
	}

	private static CurrencyController instance;	
	
	public static CurrencyController instance() {
		if(instance == null) {
			instance = new CurrencyController(PCurrency.class);
		}
		return instance;
	}	
}
