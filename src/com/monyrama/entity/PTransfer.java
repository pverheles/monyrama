package com.monyrama.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
public class PTransfer extends BaseEntity {
	private PTransferOut transferOut;
	private PTransferIn transferIn;

	@JoinColumn(nullable = false)
	@ManyToOne
	@Cascade({CascadeType.DELETE})
	public PTransferOut getTransferOut() {
		return transferOut;
	}

	public void setTransferOut(PTransferOut transferOut) {
		this.transferOut = transferOut;
	}

	@JoinColumn(nullable = false)
	@ManyToOne
	@Cascade({CascadeType.DELETE})
	public PTransferIn getTransferIn() {
		return transferIn;
	}

	public void setTransferIn(PTransferIn transferIn) {
		this.transferIn = transferIn;
	}
}
