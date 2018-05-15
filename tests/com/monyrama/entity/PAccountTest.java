package com.monyrama.entity;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

import com.monyrama.db.enumarations.EntityStates;

public class PAccountTest {
	@Test
	public void newAccountTest() {
		PAccount account = new PAccount();
		account.setSumStr("24.794");
		account.prepareToPersist();
		
		assertNotNull(account.getId());
		assertNotNull(account.getLastChangeDate());
		assertEquals(EntityStates.ACTIVE.getCode(), account.getState());
		assertEquals(new BigDecimal(account.getSumStr()), account.getSumm());
	}
	
	@Test
	public void updateAccountTest() {
		PAccount account = new PAccount();
		account.setId(7L);
		account.setSumStr("24.794");
		account.setState(EntityStates.CLOSED.getCode());
		account.prepareToPersist();
		
		assertEquals(new Long(7), account.getId());
		assertEquals(EntityStates.CLOSED.getCode(), account.getState());
		assertNotNull(account.getLastChangeDate());
		assertEquals(new BigDecimal(account.getSumStr()), account.getSumm());	}	
}
