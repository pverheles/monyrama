package com.monyrama.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.monyrama.entity.DBConstants;
import com.monyrama.entity.PCurrency;
import com.monyrama.entity.Summable;


/**
 * 
 * @author Petro_Verheles
 *
 */
public class Calc {	
	public final static MathContext MATH_CONTEXT = new MathContext(DBConstants.EXCHANGERATE_SCALE, RoundingMode.HALF_UP);
	public static final BigDecimal ONE = new BigDecimal(1);
	
	public static BigDecimal sum(Collection<? extends Summable> summables) {
		BigDecimal sum = new BigDecimal(0);

		if(summables != null) {
			for (Summable summable : summables) {
				sum = sum.add(summable.getSumm());
			}	
		}

		return sum;
	}
	
	public static Map<PCurrency, BigDecimal> sumsByCurrency(Collection<? extends Summable> summables) {
		Map<PCurrency, BigDecimal> generalSums = new HashMap<PCurrency, BigDecimal>();
		
		for(Summable summable : summables) {
			PCurrency currency = summable.getCurrency();	
			
			BigDecimal sum = generalSums.get(currency);
			if(sum != null) {
				sum = sum.add(summable.getSumm());
				generalSums.put(currency, sum);
			} else {
				sum = summable.getSumm();
				generalSums.put(currency, sum);
			}				
		}
		
		return generalSums;
	}
	
	public static BigDecimal convertSum(PCurrency fromCurrency, PCurrency toCurrency, BigDecimal inputSumValue) {
		
		if(fromCurrency.equals(toCurrency)) {
			return inputSumValue;
		}
		
		MathContext mc = MathContext.DECIMAL128;
		BigDecimal fromCurrencyExchangeRate = fromCurrency.getExchangeRate();
		BigDecimal toCurrencyExchangeRate = toCurrency.getExchangeRate();
		BigDecimal resultSumValue = inputSumValue.multiply(fromCurrencyExchangeRate).divide(toCurrencyExchangeRate, mc);
		resultSumValue = resultSumValue.setScale(2, RoundingMode.HALF_UP);
		
		return resultSumValue;
	}		
}
