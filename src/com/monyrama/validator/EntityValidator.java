package com.monyrama.validator;


public abstract class EntityValidator {	
	
	private String message;
	
	public abstract boolean validate();
	
	public final String message() {
		return message;
	}
	
	protected final void setMessage(String message) {
		this.message = message;
	}
}
