package com.monyrama.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

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
