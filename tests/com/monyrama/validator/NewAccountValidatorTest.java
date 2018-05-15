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


public class NewAccountValidatorTest {
	private static List<PAccount> accounts = new ArrayList<PAccount>();
	private static PCurrency currency = new PCurrency();
	
	@BeforeClass
	public static void prepareData() {
		Resources.initSupportedLocales();
		Resources.setLocale(new Locale("en"));
		
		currency.setId(UniqueID.get());
		currency.setCode("USD");
		
		PAccount account_1 = new PAccount();
		account_1.setId(UniqueID.get());
		account_1.setName("Depository 1");
		account_1.setSumm(new BigDecimal("100"));
		account_1.setCurrency(currency);
		account_1.setComment("No comments");
		accounts.add(account_1);
		
		PAccount account_2 = new PAccount();
		account_2.setId(UniqueID.get());
		account_2.setName("Existing");
		account_2.setSumm(new BigDecimal("100"));
		account_2.setCurrency(currency);
		account_2.setComment("No comments");
		accounts.add(account_2);
		
		PAccount account_3 = new PAccount();
		account_3.setId(UniqueID.get());
		account_3.setName("Check me");
		account_3.setSumm(new BigDecimal("100"));
		account_3.setCurrency(currency);
		account_3.setComment("No comments");
		accounts.add(account_3);
		
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
	public void testEmptyName() {
		PAccount account_1 = new PAccount();
		account_1.setName(null);
		account_1.setSumStr("100");
		account_1.setCurrency(currency);
		account_1.setComment("No comments");
		EntityValidator validator_1 = new NewAccountValidator(accounts, account_1);
		assertFalse(validator_1.validate());
		assertEquals(Resources.getString("dialogs.warnings.nameempty") + "!", validator_1.message());

		PAccount account_2 = new PAccount();
		account_2.setName("");
		account_2.setSumStr("100");
		account_2.setCurrency(currency);
		account_2.setComment("No comments");
		EntityValidator validator_2 = new NewAccountValidator(accounts, account_2);
		assertFalse(validator_2.validate());
		assertEquals(Resources.getString("dialogs.warnings.nameempty") + "!", validator_2.message());
	}
	
	@Test
	public void compareNameWithExisting() {
		PAccount account_1 = new PAccount();
		account_1.setName("Existing");
		account_1.setSumStr("100");
		account_1.setCurrency(currency);
		account_1.setComment("No comments");
		EntityValidator validator_1 = new NewAccountValidator(accounts, account_1);
		assertFalse(validator_1.validate());
		assertEquals(Resources.getString("dialogs.warnings.accountnameexist") + "!", validator_1.message());

		PAccount account_2 = new PAccount();
		account_2.setName("The newest depository");
		account_2.setSumStr("100");
		account_2.setCurrency(currency);
		account_2.setComment("No comments");
		EntityValidator validator_2 = new NewAccountValidator(accounts, account_2);
		assertTrue(validator_2.validate());
		assertNull(validator_2.message());
	}
	
	@Test
	public void testValidSum() {
		for(Locale locale : Resources.getSupportedLocales()) {
			Resources.setLocale(locale);
			testValidSumForOneLocale();
		}
	}

	private void testValidSumForOneLocale() {
		PAccount account_1 = new PAccount();
		account_1.setName("Dummy depository one");
		if(Resources.isSumDotSeparated()) {
			account_1.setSumStr("25,25");	
		} else {
			account_1.setSumStr("25.25");
		}
		account_1.setCurrency(currency);
		account_1.setComment("No comments");
		EntityValidator validator_1 = new NewAccountValidator(accounts, account_1);
		assertFalse(validator_1.validate());
		assertEquals(Resources.getString("dialogs.warnings.invalidsum") + "!", validator_1.message());

		PAccount account_2 = new PAccount();
		account_2.setName("Dummy depository two");
		account_2.setSumStr("5834trtr435");
		account_2.setCurrency(currency);
		account_2.setComment("No comments");
		EntityValidator validator_2 = new NewAccountValidator(accounts, account_2);
		assertFalse(validator_2.validate());
		assertEquals(Resources.getString("dialogs.warnings.invalidsum") + "!", validator_2.message());
		
		PAccount depositoryValid = new PAccount();
		depositoryValid.setName("Dummy depository tree");
		if(Resources.isSumDotSeparated()) {
			depositoryValid.setSumStr("25.25");	
		} else {
			depositoryValid.setSumStr("25,25");
		}	
		depositoryValid.setCurrency(currency);
		depositoryValid.setComment("No comments");
		EntityValidator validatorValid = new NewAccountValidator(accounts, depositoryValid);
		assertTrue(validatorValid.validate());
		assertNull(validatorValid.message());
	}
}
