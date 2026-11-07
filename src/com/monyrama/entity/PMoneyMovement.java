package com.monyrama.entity;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;

@MappedSuperclass
public abstract class PMoneyMovement extends SumEntity implements Summable {
	protected final static String TRANSFER_IN = "A";
	protected final static String TRANSFER_OUT = "B";
	protected final static String EXPENSE = "E";
	protected final static String INCOME = "I";
	protected final static String DEBT = "D";
	protected final static String LEND = "L";
	protected final static String PAING_BACK = "P";
	protected final static String TAKING_BACK = "T";
	
	private PAccount account;

	@ManyToOne(optional = false)
	public PAccount getAccount() {
		return account;
	}

	public void setAccount(PAccount account) {
		this.account = account;
	}

	@Override
	@Transient
	public PCurrency getCurrency() {
		return account.getCurrency();
	}
		
}
