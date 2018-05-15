package com.monyrama.db.enumarations;

public enum EntityStates {
	ACTIVE(new Character('A')),
	CLOSED(new Character('C'));
		
	private Character code;

	public Character getCode() {
		return code;
	}

	private EntityStates(Character code) {
		this.code = code;
	}	
}
