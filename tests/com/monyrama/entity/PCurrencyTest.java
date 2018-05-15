package com.monyrama.entity;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import com.monyrama.entity.PCurrency;
import org.junit.Test;

public class PCurrencyTest {
	@Test
	public void newCurrencyTest() {
		PCurrency currency = new PCurrency();
		currency.setExchangeRateStr("24.794");
		currency.prepareToPersist();
		
		assertNotNull(currency.getId());
		assertNotNull(currency.getLastChangeDate());
		assertEquals(new BigDecimal(currency.getExchangeRateStr()), currency.getExchangeRate());
	}
	
	@Test
	public void updateCurrencyTest() {
		PCurrency currency = new PCurrency();
		currency.setId(4L);
		currency.setExchangeRateStr("24.794");
		currency.prepareToPersist();
		
		assertEquals(new Long(4), currency.getId());
		assertNotNull(currency.getLastChangeDate());
		assertEquals(new BigDecimal(currency.getExchangeRateStr()), currency.getExchangeRate());
	}	
}
