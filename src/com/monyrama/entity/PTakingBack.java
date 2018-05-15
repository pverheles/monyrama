package com.monyrama.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Representation of DB object of a taking back
 * 
 * @author Petro_Verheles
 */
@Entity
@DiscriminatorValue(PMoneyMovement.TAKING_BACK)
public class PTakingBack extends PMoneyMovementIn {
	private PLend lend;

	@ManyToOne
	public PLend getLend() {
		return lend;
	}

	public void setLend(PLend lend) {
		this.lend = lend;
	}
	
}
