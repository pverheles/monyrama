package com.monyrama.entity;

import com.monyrama.entity.PMoneyMovement;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue(PMoneyMovement.TRANSFER_IN)
public class PTransferIn extends PMoneyMovementIn {
}
