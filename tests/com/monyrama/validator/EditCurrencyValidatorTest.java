package com.monyrama.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.monyrama.entity.PCurrency;
import com.monyrama.validator.EntityValidator;
import com.monyrama.validator.NewCurrencyValidator;
import org.junit.BeforeClass;
import org.junit.Test;

import com.monyrama.controller.UniqueID;
import com.monyrama.entity.PCurrency;
import com.monyrama.ui.resources.Resources;


public class EditCurrencyValidatorTest {
	private static List<PCurrency> currencies = new ArrayList<PCurrency>();
	
	@BeforeClass
	public static void prepareData() {
		Resources.initSupportedLocales();
		Resources.setLocale(new Locale("en"));
		
		PCurrency currency_1 = new PCurrency();
		currency_1.setId(UniqueID.get());
		currency_1.setName("Canada dollar");
		currency_1.setCode("USD");
		currency_1.setUpdateOnline(true);
		currency_1.setExchangeRate(new BigDecimal("1"));
		currency_1.setComment("American dollar");
		currencies.add(currency_1);
		
		PCurrency currency_2 = new PCurrency();
		currency_2.setId(UniqueID.get());
		currency_2.setName("Hrivnia Ukraine");
		currency_2.setCode("UAH");
		currency_2.setUpdateOnline(true);
		currency_2.setExchangeRate(new BigDecimal("8.05"));		
		currency_2.setComment("Hrivnia");
		currencies.add(currency_2);
		
		PCurrency currency_3 = new PCurrency();
		currency_3.setId(UniqueID.get());
		currency_3.setName("RUBLA");
		currency_3.setCode("RUB");
		currency_2.setUpdateOnline(false);
		currency_2.setExchangeRate(new BigDecimal("5"));			
		currencies.add(currency_3);
		
		PCurrency currency_4 = new PCurrency();
		currency_4.setId(UniqueID.get());
		currency_4.setCode("EUR");
		currency_4.setName("Evro");
		currency_4.setComment("");
		currencies.add(currency_4);
		
		PCurrency currency_5 = new PCurrency();
		currency_5.setId(UniqueID.get());
		currency_5.setCode("ZAY");
		currency_5.setName("Zaychiki");
		currency_5.setComment("Moldavskiye zaychiki");
		currencies.add(currency_5);
	}

	@Test
	public void testEmptyCode() {
		PCurrency PCurrencyOne = new PCurrency();
		PCurrencyOne.setCode(null);
		EntityValidator validatorOne = new NewCurrencyValidator(currencies, PCurrencyOne);
		assertFalse(validatorOne.validate());
		assertEquals(Resources.getString("dialogs.warnings.codeempty") + "!", validatorOne.message());
		
		PCurrency PCurrencyTwo = new PCurrency();
		PCurrencyTwo.setCode("");
		EntityValidator validatorTwo = new NewCurrencyValidator(currencies, PCurrencyTwo);
		assertFalse(validatorTwo.validate());
		assertEquals(Resources.getString("dialogs.warnings.codeempty") + "!", validatorTwo.message());
		
		PCurrency PCurrencyThree = new PCurrency();
		PCurrencyThree.setCode("   ");
		EntityValidator validatorTree = new NewCurrencyValidator(currencies, PCurrencyThree);
		assertFalse(validatorTree.validate());
		assertEquals(Resources.getString("dialogs.warnings.codeempty") + "!", validatorTree.message());
	}
	
	@Test
	public void testEmptyName() {
		PCurrency PCurrencyOne = new PCurrency();
		PCurrencyOne.setCode("BBB");
		PCurrencyOne.setName(null);
		EntityValidator validatorOne = new NewCurrencyValidator(currencies, PCurrencyOne);
		assertFalse(validatorOne.validate());
		assertEquals(Resources.getString("dialogs.warnings.nameempty") + "!", validatorOne.message());
		
		PCurrency PCurrencyTwo = new PCurrency();
		PCurrencyTwo.setCode("BBB");
		PCurrencyTwo.setName("");
		EntityValidator validatorTwo = new NewCurrencyValidator(currencies, PCurrencyTwo);
		assertFalse(validatorTwo.validate());
		assertEquals(Resources.getString("dialogs.warnings.nameempty") + "!", validatorTwo.message());
		
		PCurrency PCurrencyThree = new PCurrency();
		PCurrencyThree.setCode("BBB");
		PCurrencyThree.setName("   ");
		EntityValidator validatorTree = new NewCurrencyValidator(currencies, PCurrencyThree);
		assertFalse(validatorTree.validate());
		assertEquals(Resources.getString("dialogs.warnings.nameempty") + "!", validatorTree.message());
	}

	@Test
	public void compareCodeWithExisting() {
		PCurrency PCurrencyOne = new PCurrency();
		PCurrencyOne.setCode("USD");
		PCurrencyOne.setName("dollar bla");
		EntityValidator validatorOne = new NewCurrencyValidator(currencies, PCurrencyOne);
		assertFalse(validatorOne.validate());
		assertEquals(Resources.getString("dialogs.warnings.currencycodeexist") + "!", validatorOne.message());
		
		PCurrency PCurrencyTwo = new PCurrency();
		PCurrencyTwo.setCode("GBP");
		PCurrencyTwo.setName("Great Britain Pound");
		PCurrencyTwo.setExchangeRateStr("5.808");
		EntityValidator validatorTwo = new NewCurrencyValidator(currencies, PCurrencyTwo);
		assertTrue(validatorTwo.validate());
		assertNull(validatorTwo.message());
	}	
	
	@Test
	public void compareNameWithExisting() {
		PCurrency PCurrencyOne = new PCurrency();
		PCurrencyOne.setId(currencies.get(0).getId());
		PCurrencyOne.setCode("USD");
		PCurrencyOne.setName("c dollar");
		PCurrencyOne.setComment("Canada dollar");
		PCurrencyOne.setExchangeRateStr("5");
		EntityValidator validatorOne = new EditCurrencyValidator(currencies, PCurrencyOne);
		assertTrue(validatorOne.validate());
		assertNull(validatorOne.message());
		
		PCurrency PCurrencyTwo = new PCurrency();
		PCurrencyTwo.setId(currencies.get(0).getId());
		PCurrencyTwo.setCode("GBP");
		PCurrencyTwo.setName("Great Britain Pound");
		PCurrencyTwo.setComment("Great Britain Pound");
		PCurrencyTwo.setExchangeRateStr("5");
		EntityValidator validatorTwo = new EditCurrencyValidator(currencies, PCurrencyTwo);
		assertTrue(validatorTwo.validate());
		assertNull(validatorTwo.message());
		
		PCurrency PCurrencyThree = new PCurrency();
		PCurrencyThree.setId(currencies.get(0).getId());
		PCurrencyThree.setCode("BLA");
		PCurrencyThree.setName("Zaychiki");
		PCurrencyThree.setExchangeRateStr("5");
		EntityValidator validatorThree = new EditCurrencyValidator(currencies, PCurrencyThree);
		assertFalse(validatorThree.validate());
		assertEquals(Resources.getString("dialogs.warnings.currencynameexist") + "!", validatorThree.message());
	}

}
