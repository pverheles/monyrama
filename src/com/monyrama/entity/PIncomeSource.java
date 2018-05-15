package com.monyrama.entity;

import com.monyrama.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class PIncomeSource extends BaseEntity {
	private PCurrency currency;

	@ManyToOne(optional = false)
	public PCurrency getCurrency() {
		return currency;
	}
	
	public void setCurrency(PCurrency currency) {
		this.currency = currency;
	}	

	@Override
	public String toString() {
		return getName();
	}
}
