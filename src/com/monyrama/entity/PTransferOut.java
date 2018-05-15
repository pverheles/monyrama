package com.monyrama.entity;

import com.monyrama.entity.PMoneyMovement;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(PMoneyMovement.TRANSFER_OUT)
public class PTransferOut extends PMoneyMovementOut {
	
}
