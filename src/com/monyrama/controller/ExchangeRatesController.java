package com.monyrama.controller;

import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.monyrama.entity.PSetting;
import com.monyrama.utils.Calc;

public class ExchangeRatesController {

	private static final String USD = "USD";

	private final static String YAHOO_QUOTES_URL = "https://finance.yahoo.com/webservice/v1/symbols/allcurrencies/quote";

	private final static long UPDATE_INTERVAL = 60 * 60 * 1000;

	private Map<String, BigDecimal> usdRelatedRates;

	private long lastUpdateTimestamp = 0;

	private ExchangeRatesController() {
	}

	private static ExchangeRatesController instance;

	public static ExchangeRatesController instance() {
		if (instance == null) {
			instance = new ExchangeRatesController();
		}
		return instance;
	}

	private boolean updateOnline() {				
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			URL updateUrl = new URL(YAHOO_QUOTES_URL);
			InputStream is = updateUrl.openStream();
			Document document = dBuilder.parse(is);
			Element listElement = document.getDocumentElement();
			Node resourcesNode = listElement.getElementsByTagName("resources")
					.item(0);
			NodeList resourceList = resourcesNode.getChildNodes();
			for (int i = 0; i < resourceList.getLength(); i++) {
				Node resourceNode = resourceList.item(i);
				NodeList fieldsList = resourceNode.getChildNodes();
				String currencyCode = null;
				String rateValue = null;
				for (int k = 0; k < fieldsList.getLength(); k++) {
					Node fieldNode = fieldsList.item(k);
					if (fieldNode.getNodeType() == Node.DOCUMENT_POSITION_DISCONNECTED) {
						NamedNodeMap attributes = fieldNode.getAttributes();
						String fieldName = attributes.item(0).getNodeValue();
						if ("name".equals(fieldName)) {
							String currencyRelationName = fieldNode
									.getFirstChild().getNodeValue();
							if (currencyRelationName.startsWith("USD/")) {
								currencyCode = currencyRelationName
										.substring("USD/".length());
							}
						} else if ("price".equals(fieldName)) {
							rateValue = fieldNode.getFirstChild()
									.getNodeValue();
						}
					}
				}

				if (currencyCode != null && new BigDecimal(rateValue).compareTo(BigDecimal.ZERO) > 0) {
					usdRelatedRates
							.put(currencyCode, new BigDecimal(rateValue).stripTrailingZeros());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		lastUpdateTimestamp = Calendar.getInstance().getTimeInMillis();
		
		return true;
	}

	public BigDecimal getRate(String currencyCode) {
		if (usdRelatedRates == null
				|| Calendar.getInstance().getTimeInMillis()
						- lastUpdateTimestamp > UPDATE_INTERVAL) {
			usdRelatedRates = new HashMap<String, BigDecimal>();
			updateOnline();
		}

		String mainCurrencyCode = SettingController.instance().getSettingValue(PSetting.MAIN_CURRENCY_KEY);

		//String mainCurrencyCode = "UAH";

		BigDecimal currencyToMainCurrencyRate;
		if (USD.equals(mainCurrencyCode)) { //when main currency is USD
			if(USD.equals(currencyCode)) {
				return Calc.ONE;
			}
			
			BigDecimal currencyToUsdRate = usdRelatedRates.get(currencyCode);
			if (currencyToUsdRate == null) {
				return BigDecimal.ZERO;
			}

			currencyToMainCurrencyRate = Calc.ONE.divide(currencyToUsdRate, Calc.MATH_CONTEXT);
		} else { //not USD currency is main
			BigDecimal mainCurrencyToUsdRate = usdRelatedRates.get(mainCurrencyCode);

			if (mainCurrencyToUsdRate == null) {
				return BigDecimal.ZERO;
			}

			if (USD.equals(currencyCode)) {
				currencyToMainCurrencyRate = mainCurrencyToUsdRate;
			} else {
				BigDecimal currencyToUsdRate = usdRelatedRates.get(currencyCode);
				if (currencyToUsdRate == null) {
					return BigDecimal.ZERO;
				}
				
				currencyToMainCurrencyRate = mainCurrencyToUsdRate.divide(currencyToUsdRate, Calc.MATH_CONTEXT);
			}
		}

		return currencyToMainCurrencyRate;
	}
	
	public boolean forceUpdateOnline() {
		if(usdRelatedRates == null) {
			usdRelatedRates = new HashMap<String, BigDecimal>();
		}
		
		return updateOnline();
	}

//	public static void main(String[] args) {
//		System.out.println();
//		System.out.println(ExchangeRatesController.instance().getRate(USD));
//		System.out.println(ExchangeRatesController.instance().getRate("UAH"));
//		System.out.println(ExchangeRatesController.instance().getRate("RUB"));
//		System.out.println(ExchangeRatesController.instance().getRate("EUR"));
//		System.out.println(ExchangeRatesController.instance().getRate("AAA"));
//	}
}
