package com.monyrama.entity;

import com.monyrama.entity.PMoneyMovement;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(PMoneyMovement.TRANSFER_IN)
public class PTransferIn extends PMoneyMovementIn {
}
