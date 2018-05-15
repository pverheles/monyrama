package com.monyrama.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue(PMoneyMovement.INCOME)
public class PIncome extends PMoneyMovementIn implements Summable {
	private PIncomeSource incomeSource;

	@ManyToOne
	public PIncomeSource getIncomeSource() {
		return incomeSource;
	}

	public void setIncomeSource(PIncomeSource incomeSource) {
		this.incomeSource = incomeSource;
	}

	@Override
	@Transient
	public PCurrency getCurrency() {
		return incomeSource.getCurrency();
	}	
}
