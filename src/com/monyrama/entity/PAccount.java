package com.monyrama.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.monyrama.entity.PCurrency;
import com.monyrama.entity.Summable;
import com.monyrama.ui.utils.MyFormatter;

/**
 * Representation of DB object of a depository
 * 
 * @author Petro_Verheles
 */
@Entity
public class PAccount extends SumEntity implements Summable {
	private PCurrency currency;
	private Boolean saving;
	private AccountBank accountBank;

	@Override
	@ManyToOne(optional = false)
	public PCurrency getCurrency() {
		return currency;
	}
	
	public void setCurrency(PCurrency currency) {
		this.currency = currency;
	}
	
	public Boolean getSaving() {
		return saving;
	}

	public void setSaving(Boolean saving) {
		this.saving = saving;
	}

	public AccountBank getAccountBank() {
		return accountBank;
	}

	public void setAccountBank(AccountBank accountBank) {
		this.accountBank = accountBank;
	}

	@Override
	public String toString() {
		return getName() + " (" + MyFormatter.formatNumberToLocal(getSumm().toPlainString()) + " " + getCurrency().getCode() + ")";		
	}
	
	
			
}
