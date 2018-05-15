package com.monyrama.controller;

import com.monyrama.entity.PIncomeSource;


public class IncomeSourceController extends AbstractController<PIncomeSource> {
	protected IncomeSourceController(Class<PIncomeSource> clazz) {
		super(clazz);
	}

	private static IncomeSourceController instance;
	
	public static IncomeSourceController instance() {		
		if(instance == null) {
			instance = new IncomeSourceController(PIncomeSource.class);
		}
		return instance;		
	}
}
