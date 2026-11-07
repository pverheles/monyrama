package com.monyrama.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

/**
 * Representation of DB object of a budget
 * 
 * @author Petro_Verheles
 *
 */
@Entity
public class PExpensePlan extends BaseEntity {
	private PCurrency currency;
	
	public PExpensePlan() {
	}
	
	@ManyToOne(optional = false)
	public PCurrency getCurrency() {
		return currency;
	}

	public void setCurrency(PCurrency currency) {
		this.currency = currency;
	}
}
