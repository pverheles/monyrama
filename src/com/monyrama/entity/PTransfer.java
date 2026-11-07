package com.monyrama.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class PTransfer extends BaseEntity {
	private PTransferOut transferOut;
	private PTransferIn transferIn;

	@JoinColumn(nullable = false)
	@ManyToOne(cascade = CascadeType.REMOVE)
	public PTransferOut getTransferOut() {
		return transferOut;
	}

	public void setTransferOut(PTransferOut transferOut) {
		this.transferOut = transferOut;
	}

	@JoinColumn(nullable = false)
	@ManyToOne(cascade = CascadeType.REMOVE)
	public PTransferIn getTransferIn() {
		return transferIn;
	}

	public void setTransferIn(PTransferIn transferIn) {
		this.transferIn = transferIn;
	}
}
