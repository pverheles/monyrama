/*
 * ComparatorFactory.java
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

package com.monyrama.ui.utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import com.monyrama.ui.resources.Resources;


/**
 * A holder for different comparators
 * 
 * @author Petro_Verheles
 *
 */
public class ComparatorFactory {

	private volatile static ComparatorFactory instance;
    
	private static Comparator<String> bigDecimalComparator;
	private static Comparator<String> dateComparator;
	
    private ComparatorFactory() {}
 
    /**
     * 
     * @return - a singleton instance of this object
     */
    public static ComparatorFactory getInstance() {
        if (instance == null) {
            synchronized (ComparatorFactory.class) {
                if (instance == null) {
                    instance = new ComparatorFactory();
                }
            }
        }
        return instance;
    }
    
    /**
     * Gets the comparator, which compares two string formatted as
     * BigDecimals
     * 
     * @return - comparator for BigDecimal strings
     */
    public Comparator<String> getBigDecimalComparator() {    	    	
    	if (bigDecimalComparator == null) {
    		bigDecimalComparator = new Comparator<String>() {
    			public int compare(String s1, String s2) {
					if((s1 == null || s1.equals(""))
							&& (s2 == null || s2.equals(""))) {
						return 0;
					}
					
					if(s1 == null || s1.equals("")) {
						return 1;						
					}
					
					if(s2 == null || s2.equals("")) {
						return -1;
					}
    				
    				s1 = MyFormatter.formatNumberToStandard(s1);
					s2 = MyFormatter.formatNumberToStandard(s2);
					BigDecimal b1 = new BigDecimal(s1);
					BigDecimal b2 = new BigDecimal(s2);
					return b1.compareTo(b2);
				}
    		};
		}
		    	
    	return bigDecimalComparator;
    }  
    
    /**
     * Gets the comparator, which compares two string formatted as
     * Date
     * 
     * @return - comparator for Date strings
     */
    public Comparator<String> getDateComparator() {
   		if(dateComparator == null) {
			dateComparator = new Comparator<String>() {
			    public int compare(String s1, String s2) {
			    	SimpleDateFormat formatter;
				    formatter = new SimpleDateFormat(Resources.getDateFormat(), Resources.getLocale());
				    try {
						Date d1 = formatter.parse(s1);
						Date d2 = formatter.parse(s2);
						return d1.compareTo(d2);
					} catch (ParseException e) {							
						e.printStackTrace();
						return 0;
					}
			    }
			};
   		}			

    	return dateComparator;
    }
    
}
