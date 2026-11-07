package com.monyrama.entity;

import com.monyrama.entity.PDebt;
import com.monyrama.entity.PMoneyMovementOut;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;


/**
 * Representation of DB object of a paying back
 * 
 * @author Petro_Verheles
 */
@Entity
@DiscriminatorValue(PMoneyMovement.PAING_BACK)
public class PPayingBack extends PMoneyMovementOut {
	private PDebt debt;

	@ManyToOne
	public PDebt getDebt() {
		return debt;
	}

	public void setDebt(PDebt debt) {
		this.debt = debt;
	}
	
}
