package com.monyrama.controller;

import com.monyrama.entity.PCategory;


/**
 * @author Petro_Verheles
 */
public class CategoryController extends AbstractController<PCategory> {
	private CategoryController(Class<PCategory> clazz) {
		super(clazz);
	}

	private static CategoryController instance;
	
	
	public static CategoryController instance() {
		if(instance == null) {
			instance = new CategoryController(PCategory.class);
		}
		return instance;
	}
}
