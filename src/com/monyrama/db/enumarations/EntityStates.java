package com.monyrama.db.enumarations;

public enum EntityStates {
	ACTIVE(Character.valueOf('A')),
	CLOSED(Character.valueOf('C'));
		
	private Character code;

	public Character getCode() {
		return code;
	}

	private EntityStates(Character code) {
		this.code = code;
	}	
}
