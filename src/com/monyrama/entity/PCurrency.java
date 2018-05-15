package com.monyrama.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.monyrama.entity.BaseEntity;
import com.monyrama.ui.resources.Resources;
import com.monyrama.validator.util.StringValidator;

/**
 * 
 * Representation of DB object of a currency
 * 
 * @author Petro_Verheles
 * 
 */
@Entity
public class PCurrency extends BaseEntity {

	private String code;
	private BigDecimal exchangeRate;
	private Boolean updateOnline;
	private Boolean standard;

	private String exchangeRateStr;
	
	public PCurrency() {
	}
	
	@Column(nullable = false, length = DBConstants.CURRENCY_CODE_LENGTH, unique = true)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}	

	@Column(nullable = false, precision = DBConstants.EXCHANGERATE_PRECISION, scale = DBConstants.EXCHANGERATE_SCALE)
	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	
	@Column(nullable = false)
	public Boolean getUpdateOnline() {
		return updateOnline;
	}

	public void setUpdateOnline(Boolean updateOnline) {
		this.updateOnline = updateOnline;
	}

	@Column(nullable = false)
	public Boolean getStandard() {
		return standard;
	}

	public void setStandard(Boolean standard) {
		this.standard = standard;
	}	

	@Transient
	public String getExchangeRateStr() {
		return exchangeRateStr;
	}

	public void setExchangeRateStr(String exchangeRateStr) {
		this.exchangeRateStr = exchangeRateStr;
	}

	@Override
	public void prepareToPersist() {
		super.prepareToPersist();
		
		if(!StringValidator.isStringNullOrEmpty(exchangeRateStr)) {
			exchangeRate = new BigDecimal(exchangeRateStr);	
		}
		
	}

	@Override
	public String toString() {
		if(getStandard() == null || getCode() == null || getName() == null) {
			return "";
		}
		
		String currName;
		if(getStandard()) {
			currName = Resources.getCurrencyName(getCode());
		} else {
			currName = getName();
		}
		return getCode() + " - " + currName;
	}
	
	
}
