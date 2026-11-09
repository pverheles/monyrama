package com.monyrama.controller;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.monyrama.entity.PSetting;
import com.monyrama.utils.Calc;

/**
 * Online exchange rates from the free fawazahmed0 currency API
 * (https://github.com/fawazahmed0/exchange-api): no API key, no rate limit, updated daily.
 * It replaces the Yahoo Finance endpoint, which was shut down.
 */
public class ExchangeRatesController {

	private static final String USD = "USD";

	/** USD-based rates; the second URL is the project's documented fallback mirror */
	private static final String[] RATES_URLS = {
		"https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies/usd.json",
		"https://latest.currency-api.pages.dev/v1/currencies/usd.json"
	};

	/** Matches "eur": 0.8969 (and exponent forms such as 1.2e-05) inside the "usd" object */
	private static final Pattern RATE_PATTERN = Pattern.compile("\"([a-z0-9]+)\"\\s*:\\s*(-?[0-9][0-9.]*(?:[eE][-+]?[0-9]+)?)");

	private static final int TIMEOUT_MILLIS = 10_000;

	private static final long UPDATE_INTERVAL = 60 * 60 * 1000;

	/** After a failed download, wait this long before trying again instead of retrying on every call */
	private static final long RETRY_INTERVAL = 5 * 60 * 1000;

	/** Units of a currency per 1 USD, keyed by upper-case currency code */
	private Map<String, BigDecimal> usdRelatedRates = new HashMap<String, BigDecimal>();

	private long lastUpdateTimestamp = 0;

	private long lastAttemptTimestamp = 0;

	private ExchangeRatesController() {
	}

	private static ExchangeRatesController instance;

	public static synchronized ExchangeRatesController instance() {
		if (instance == null) {
			instance = new ExchangeRatesController();
		}
		return instance;
	}

	private synchronized boolean updateOnline() {
		lastAttemptTimestamp = System.currentTimeMillis();
		for (String url : RATES_URLS) {
			try {
				Map<String, BigDecimal> rates = parseRates(download(url));
				if (rates.containsKey("EUR")) { // sanity check that we got the real rates list
					usdRelatedRates = rates;
					lastUpdateTimestamp = System.currentTimeMillis();
					return true;
				}
				System.err.println("Unexpected exchange rates response from " + url);
			} catch (Exception e) {
				System.err.println("Failed to get exchange rates from " + url + ": " + e);
			}
		}
		// Keep the previously loaded rates, if any
		return false;
	}

	private static String download(String url) throws IOException {
		HttpURLConnection connection = (HttpURLConnection) URI.create(url).toURL().openConnection();
		connection.setConnectTimeout(TIMEOUT_MILLIS);
		connection.setReadTimeout(TIMEOUT_MILLIS);
		connection.setRequestProperty("Accept", "application/json");
		try {
			int status = connection.getResponseCode();
			if (status != HttpURLConnection.HTTP_OK) {
				throw new IOException("HTTP " + status);
			}
			try (InputStream is = connection.getInputStream()) {
				return new String(is.readAllBytes(), StandardCharsets.UTF_8);
			}
		} finally {
			connection.disconnect();
		}
	}

	static Map<String, BigDecimal> parseRates(String json) {
		Map<String, BigDecimal> rates = new HashMap<String, BigDecimal>();
		int start = json.indexOf("\"usd\"");
		if (start < 0) {
			return rates;
		}
		Matcher matcher = RATE_PATTERN.matcher(json);
		matcher.region(start + "\"usd\"".length(), json.length());
		while (matcher.find()) {
			BigDecimal rate = new BigDecimal(matcher.group(2));
			if (rate.signum() > 0) {
				rates.put(matcher.group(1).toUpperCase(Locale.ROOT), rate.stripTrailingZeros());
			}
		}
		return rates;
	}

	/**
	 * @return how many units of the main currency one unit of the given currency costs,
	 *         or BigDecimal.ZERO if the rate is unknown
	 */
	public synchronized BigDecimal getRate(String currencyCode) {
		long now = System.currentTimeMillis();
		if (now - lastUpdateTimestamp > UPDATE_INTERVAL && now - lastAttemptTimestamp > RETRY_INTERVAL) {
			updateOnline();
		}

		String mainCurrencyCode = SettingController.instance().getSettingValue(PSetting.MAIN_CURRENCY_KEY);

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
		return updateOnline();
	}
}
