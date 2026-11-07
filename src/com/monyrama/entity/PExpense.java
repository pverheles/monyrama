package com.monyrama.entity;

import com.monyrama.entity.PMoneyMovementOut;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

/**
 * Representation of DB object of an outcome item
 * 
 * @author Petro_Verheles
 */
@Entity
@DiscriminatorValue(PMoneyMovement.EXPENSE)
public class PExpense extends PMoneyMovementOut implements Summable {
	private PExpensePlanItem expensePlanItem;

	public PExpense() {
	}

	@ManyToOne
	public PExpensePlanItem getExpensePlanItem() {
		return expensePlanItem;
	}

	public void setExpensePlanItem(PExpensePlanItem expensePlanItem) {
		this.expensePlanItem = expensePlanItem;
	}

	@Override
	@Transient
	public PCurrency getCurrency() {
		return expensePlanItem.getExpensePlan().getCurrency();
	}
}
