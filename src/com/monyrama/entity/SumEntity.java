package com.monyrama.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.monyrama.entity.DBConstants;
import com.monyrama.ui.utils.MyFormatter;

@MappedSuperclass
public abstract class SumEntity extends BaseEntity {
	private BigDecimal summ;
	
	private String sumStr;

	@Column(nullable = false, scale = DBConstants.SUM_SCALE, precision = DBConstants.SUM_PRECISION)
	public BigDecimal getSumm() {
		return summ;
	}

	public void setSumm(BigDecimal summ) {
		this.summ = summ;
	}
	
	@Transient
	public String getSumStr() {
		return sumStr;
	}

	public void setSumStr(String sumStr) {
		this.sumStr = sumStr;
	}	

	@Override
	public void prepareToPersist() {
		super.prepareToPersist();
		
		if(sumStr != null) {
			summ = new BigDecimal(MyFormatter.formatNumberToStandard(sumStr.trim()));	
		}		
	}
		
}
