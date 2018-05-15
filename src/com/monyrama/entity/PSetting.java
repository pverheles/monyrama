package com.monyrama.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class PSetting extends BaseEntity {
	
	public static final String DB_VERSION_KEY = "settings.dbversion";
	public static final String MAIN_CURRENCY_KEY = "settings.maincurrency";

	private String value;

	@Column(nullable = false)
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
		
}
