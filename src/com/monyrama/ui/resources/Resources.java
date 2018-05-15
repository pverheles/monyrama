/*
 * Resources.java
 * 
 * Version: 1.0
 * 
 * Date: 06.05.2009
 * 
 * Copyright (c) 2009 Petro Verheles.
 * e-mail: vergeles.petiaaa@gmail.com
 * ICQ: 372-831-939
 * Skype: petro.vergeles
 * 
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Petro Verheles
 * ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Petro Verheles. 
 */

package com.monyrama.ui.resources;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Represents Locale resourses of the project
 * 
 * @author Petro_Verheles
 *
 */
public class Resources {
	private static final String BUNDLE_NAME = Resources.class.getPackage().getName() + ".UIResources";
	private static final String CURRENCIES_BUNDLE_NAME = Resources.class.getPackage().getName() + ".Currencies";
	private static ResourceBundle RESOURCE_BUNDLE = null;
	private static ResourceBundle CURRENCIES_BUNDLE = null;
	private static Locale locale;
	private final static String DATE_FORMAT = "d MMMM yyyy";
	
	private static List<Locale> locales;

	private static boolean isSumDotSeparated = false;
	private static List<String> allCurrencyCodes;
	private static List<String> allCurrencyNames;

	private Resources() {

	}
	
	public static void initSupportedLocales() {
		if(locales == null) {
			locales = new ArrayList<Locale>();
			locales.add(new Locale("en"));
			locales.add(new Locale("ru"));
			locales.add(new Locale("uk"));	
		}
	}
	
	/**
	 * Gets the string array from resources
	 * 
	 * @param key - the identifier of the string array
	 * @return - the localized string
	 */
	public static String[] getCommaSeparatedStrings(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key).split(",");
		} catch (MissingResourceException e) {
			return new String[]{"!" + key + "!"};
		}
	}

	/**
	 * Gets the string from resources
	 *
	 * @param key - the identifier of the string
	 * @return - the localized string
	 */
	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return "!" + key + "!";
		}
	}
	
	/**
	 * Gets the currency name by code
	 * 
	 * @param code - currency code
	 * @return - the localized currency name
	 */
	public static String getCurrencyName(String code) {
		try {
			return CURRENCIES_BUNDLE.getString(code);
		} catch (MissingResourceException e) {
			return "!" + code + "!";
		}
	}	
	
	/**
	 * Initializes the resources
	 * This method should be called before any other use of Resources
	 * 
	 * @param loc - Locale
	 * @see - Locale
	 */
	public static void setLocale(Locale loc) {
		if(!locales.contains(loc)) {
			throw new IllegalArgumentException("Unsupported locale: " + loc.toString());
		}
		
		if(loc.getLanguage().equals("uk") || loc.getLanguage().equals("ru")) {
			isSumDotSeparated = false;
		} else {
			isSumDotSeparated = true;
		}
		
		RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME, loc);
		CURRENCIES_BUNDLE = ResourceBundle.getBundle(CURRENCIES_BUNDLE_NAME, loc);
		
		Locale.setDefault(loc);
		
		locale = loc;
	}
	
	/**
	 * Gets the locale
	 * 
	 * @return - the locale of this Resources
	 */
	public static Locale getLocale() {
		return locale;
	}

	/**
	 * Gets the date format
	 * 
	 * @return - the date format
	 */
	public static String getDateFormat() {
		return DATE_FORMAT;
	}
	
	public static List<Locale> getSupportedLocales() {
		return locales;
	}
	
	public static boolean isSumDotSeparated() {
		return isSumDotSeparated;
	}
	
	public static Icon getIcon(String name) {
		return new ImageIcon(Resources.class.getResource("icons/" + name));
	}
	
	public static Image getImage(String name) {
		return Toolkit.getDefaultToolkit().getImage(Resources.class.getResource("icons/" + name));
	}
	
	public static List<String> getAllCurrencyCodes() {
		if(allCurrencyCodes == null) {
			allCurrencyCodes = new ArrayList<String>(CURRENCIES_BUNDLE.keySet());
			Collections.sort(allCurrencyCodes);	
		}
		
		return allCurrencyCodes;		
	}
	
	public static List<String> getAllCurrencyNames() {
		if(allCurrencyNames == null) {
			allCurrencyNames = new ArrayList<String>();
			for(String currencyCode : CURRENCIES_BUNDLE.keySet()) {
				allCurrencyNames.add(CURRENCIES_BUNDLE.getString(currencyCode));
			}
			Collections.sort(allCurrencyNames);
		}
		
		return allCurrencyNames;
	}
	
	public static String getCurrencyCode(String name) {
		for(String currencyCode : CURRENCIES_BUNDLE.keySet()) {
			String nextCurrencyName = getCurrencyName(currencyCode);
			if(nextCurrencyName.equals(name)) {
				return currencyCode;
			}
		}
		return null;
	}
}