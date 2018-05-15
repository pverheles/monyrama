package com.monyrama.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 * Representation of DB object of an item
 * 
 * @author Petro_Verheles
 * 
 */
@Entity
public class PExpensePlanItem extends SumEntity implements Summable {
	private PExpensePlan expensePlan;
	private PCategory category;

	public PExpensePlanItem() {
	};

	@ManyToOne(optional = false)
	@JoinColumn(nullable = false)
	public PCategory getCategory() {
		return category;
	}

	public void setCategory(PCategory category) {
		this.category = category;
	}

	@ManyToOne(optional = false)
	public PExpensePlan getExpensePlan() {
		return expensePlan;
	}

	public void setExpensePlan(PExpensePlan expensePlan) {
		this.expensePlan = expensePlan;
	}

	@Override
	@Transient
	public PCurrency getCurrency() {
		return expensePlan.getCurrency();
	}

	@Override
	public String toString() {
		if (getName() == null || getName().trim().equals("")) {
			return category.getName();
		} else {
			return getName();
		}
	}
}
