package com.monyrama.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.BeforeClass;
import org.junit.Test;

import com.monyrama.controller.UniqueID;
import com.monyrama.entity.PAccount;
import com.monyrama.entity.PCurrency;
import com.monyrama.ui.resources.Resources;


public class EditAccountValidatorTest {
	private static List<PAccount> accounts = new ArrayList<PAccount>();
	private static PCurrency currency = new PCurrency();
	
	@BeforeClass
	public static void prepareData() {
		Resources.initSupportedLocales();
		Resources.setLocale(new Locale("en"));
		
		currency.setId(UniqueID.get());
		currency.setCode("USD");
		
		PAccount depository_1 = new PAccount();
		depository_1.setId(UniqueID.get());
		depository_1.setName("Depository 1");
		depository_1.setSumm(new BigDecimal("100"));
		depository_1.setCurrency(currency);
		depository_1.setComment("No comments");
		accounts.add(depository_1);
		
		PAccount depository_2 = new PAccount();
		depository_2.setId(UniqueID.get());
		depository_2.setName("Existing");
		depository_2.setSumm(new BigDecimal("100"));
		depository_2.setCurrency(currency);
		depository_2.setComment("No comments");
		accounts.add(depository_2);
		
		PAccount depository_3 = new PAccount();
		depository_3.setId(UniqueID.get());
		depository_3.setName("Check me");
		depository_3.setSumm(new BigDecimal("100"));
		depository_3.setCurrency(currency);
		depository_3.setComment("No comments");
		accounts.add(depository_3);
		
		PAccount depository_4 = new PAccount();
		depository_4.setId(UniqueID.get());
		depository_4.setName("Depository 1");
		depository_4.setSumm(new BigDecimal("100"));
		depository_4.setCurrency(currency);
		depository_4.setComment("No comments");
		accounts.add(depository_4);
		
		PAccount depository_5 = new PAccount();
		depository_5.setId(UniqueID.get());
		depository_5.setName("Depository 5");
		depository_5.setSumm(new BigDecimal("100"));
		depository_5.setCurrency(currency);
		depository_5.setComment("No comments");
		accounts.add(depository_5);
	}

	@Test
	public void testEmptyNameFor() {
		PAccount depository_1 = new PAccount();
		depository_1.setId(accounts.get(1).getId());
		depository_1.setName(null);
		depository_1.setSumStr("100");
		depository_1.setCurrency(currency);
		depository_1.setComment("No comments");
		EntityValidator validator_1 = new EditAccountValidator(accounts, depository_1);
		assertFalse(validator_1.validate());
		assertEquals(Resources.getString("dialogs.warnings.nameempty") + "!", validator_1.message());

		PAccount depository_2 = new PAccount();
		depository_2.setId(accounts.get(1).getId());
		depository_2.setName("");
		depository_2.setSumStr("100");
		depository_2.setCurrency(currency);
		depository_2.setComment("No comments");
		EntityValidator validator_2 = new EditAccountValidator(accounts, depository_2);
		assertFalse(validator_2.validate());
		assertEquals(Resources.getString("dialogs.warnings.nameempty") + "!", validator_2.message());
	}

	@Test
	public void compareNameWithExisting() {
		PAccount depository_1 = new PAccount();
		depository_1.setId(accounts.get(1).getId());
		depository_1.setName("Existing");
		depository_1.setSumStr("100");
		depository_1.setCurrency(currency);
		depository_1.setComment("No comments");
		EntityValidator validator_1 = new EditAccountValidator(accounts, depository_1);
		assertTrue(validator_1.validate());
		assertNull(validator_1.message());

		PAccount depository_2 = new PAccount();
		depository_2.setId(accounts.get(1).getId());
		depository_2.setName("The newest depository");
		depository_2.setSumStr("100");
		depository_2.setCurrency(currency);
		depository_2.setComment("No comments");
		EntityValidator validator_2 = new EditAccountValidator(accounts, depository_2);
		assertTrue(validator_2.validate());
		assertNull(validator_2.message());
		
		PAccount depository_3 = new PAccount();
		depository_3.setId(accounts.get(1).getId());
		depository_3.setName("Check me");
		depository_3.setSumStr("100");
		depository_3.setCurrency(currency);
		depository_3.setComment("No comments");
		EntityValidator validator_3 = new EditAccountValidator(accounts, depository_3);
		assertFalse(validator_3.validate());
		assertEquals(Resources.getString("dialogs.warnings.accountnameexist") + "!", validator_3.message());
	}
	
	@Test
	public void testEmptySum() {
		PAccount depository_1 = new PAccount();
		depository_1.setId(accounts.get(1).getId());
		depository_1.setName("Dummy depository one");
		depository_1.setSumm(null);
		depository_1.setCurrency(currency);
		depository_1.setComment("No comments");
		EntityValidator validator_1 = new EditAccountValidator(accounts, depository_1);
		assertFalse(validator_1.validate());
		assertEquals(Resources.getString("dialogs.warnings.sumempty") + "!", validator_1.message());

		PAccount depository_2 = new PAccount();
		depository_2.setId(accounts.get(1).getId());
		depository_2.setName("Dummy depository two");
		depository_2.setSumStr("");
		depository_2.setCurrency(currency);
		depository_2.setComment("No comments");
		EntityValidator validator_2 = new EditAccountValidator(accounts, depository_2);
		assertFalse(validator_2.validate());
		assertEquals(Resources.getString("dialogs.warnings.sumempty") + "!", validator_2.message());
	}
	
	@Test
	public void testValidSum() {
		for(Locale locale : Resources.getSupportedLocales()) {
			Resources.setLocale(locale);
			testValidSumForOneLocale();
		}
	}

	private void testValidSumForOneLocale() {
		PAccount depository_1 = new PAccount();
		
		depository_1.setName("Dummy depository one");
		depository_1.setId(accounts.get(1).getId());
		if(Resources.isSumDotSeparated()) {
			depository_1.setSumStr("25,25");	
		} else {
			depository_1.setSumStr("25.25");
		}
		depository_1.setCurrency(currency);
		depository_1.setComment("No comments");
		EntityValidator validator_1 = new EditAccountValidator(accounts, depository_1);
		assertFalse(validator_1.validate());
		assertEquals(Resources.getString("dialogs.warnings.invalidsum") + "!", validator_1.message());

		PAccount depository_2 = new PAccount();
		depository_2.setId(accounts.get(1).getId());
		depository_2.setName("Dummy depository two");
		depository_2.setSumStr("5834trtr435");
		depository_2.setCurrency(currency);
		depository_2.setComment("No comments");
		EntityValidator validator_2 = new EditAccountValidator(accounts, depository_2);
		assertFalse(validator_2.validate());
		assertEquals(Resources.getString("dialogs.warnings.invalidsum") + "!", validator_2.message());
		
		PAccount depositoryValid = new PAccount();
		depositoryValid.setId(accounts.get(1).getId());
		depositoryValid.setName("Dummy depository tree");
		if(Resources.isSumDotSeparated()) {
			depositoryValid.setSumStr("25.25");	
		} else {
			depositoryValid.setSumStr("25,25");
		}	
		depositoryValid.setCurrency(currency);
		depositoryValid.setComment("No comments");
		EntityValidator validatorValid = new EditAccountValidator(accounts, depositoryValid);
		assertTrue(validatorValid.validate());
		assertNull(validatorValid.message());
	}
}
