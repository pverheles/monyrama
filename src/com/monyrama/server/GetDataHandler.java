package com.monyrama.server;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.monyrama.controller.*;
import com.monyrama.db.enumarations.EntityStates;
import com.monyrama.entity.*;
import org.eclipse.jetty.util.ajax.JSON;

import com.monyrama.ui.resources.Resources;
import com.monyrama.utils.Calc;

public class GetDataHandler implements RequestHandler {

	@Override
	/**
	 * Method selects open expenses plans and sends only those
	 * who has open items
	 */
	public Response handle(HttpServletRequest request) {
		final String BLOCK_ID = UUID.randomUUID().toString();
		MainController.instance().createUIBlock(BLOCK_ID, Resources.getString("server"), Resources.getString("labels.server.block.sendingexpensesplans"));
										
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		Future<Response> responseFuture = executorService.submit(new Callable<Response>() {
			@Override
			public Response call() throws Exception {
				Response response = new Response();
				
				Map<String, Object> data = new HashMap<String, Object>();

				List<Map<String, Object>> currenciesList = buildCurrencies();				
				data.put("currencies", currenciesList);		
				
				List<Map<String, Object>> accountsList = buildAccounts();	
				data.put("accounts", accountsList);	
				
				List<Map<String, Object>> openPlansList = buildExpensePlans();				
				data.put("expensePlans", openPlansList);
				
				List<Map<String, Object>> incomeSourcesList = buildIncomeSources();
				data.put("incomeSources", incomeSourcesList);
				
				response.setStatusCode(HttpServletResponse.SC_OK);
				response.setResponseText(JSON.toString(data));

				MainController.instance().releaseUIBlock(BLOCK_ID);
				
				return response;
			}
		});
		
		Response response;
		try {
			response = responseFuture.get();
		} catch (Exception e) {
			response = new Response();
			response.setStatusCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			ErrorJson errorJson = new ErrorJson(Resources.getString("server.message.error.cannotprocess"));
			response.setResponseText(errorJson.toString());
		}
		
		return response;
	}

	protected boolean isItemOpened(PExpensePlanItem budgetUnit) {
		return budgetUnit.getState().equals(EntityStates.ACTIVE.getCode());
	}
	
	
	private List<Map<String, Object>> buildCurrencies() {
        String mainCurrencyKey = SettingController.instance().getSettingValue(PSetting.MAIN_CURRENCY_KEY);
		List<PCurrency> currencies = CurrencyController.instance().getAll();
		List<Map<String, Object>> currenciesListJSON = new ArrayList<Map<String,Object>>(currencies.size());
		for(PCurrency currency : currencies) {
			Map<String, Object> currencyJSON = new HashMap<String, Object>();
			currencyJSON.put("id", currency.getId());
			currencyJSON.put("name", currency.getName());
			currencyJSON.put("code", currency.getCode());
			currencyJSON.put("exchangeRate", currency.getExchangeRate());
            boolean main = mainCurrencyKey.equals(currency.getCode());
            currencyJSON.put("main", main);
			currenciesListJSON.add(currencyJSON);
		}
		return currenciesListJSON;
	}
	
	private List<Map<String, Object>> buildAccounts() {
		List<PAccount> accounts = AccountController.instance().listActive();
		List<Map<String, Object>> accountsListJSON = new ArrayList<Map<String,Object>>(accounts.size());
		for(PAccount account : accounts) {
			Map<String, Object> accountJSON = new HashMap<String, Object>();
			accountJSON.put("id", account.getId());
			accountJSON.put("name", account.getName());
			accountJSON.put("sum", toCents(account.getSumm()));
			accountJSON.put("currencyId", account.getCurrency().getId());
			accountsListJSON.add(accountJSON);
		}
		return accountsListJSON;
	}
	
	private List<Map<String, Object>> buildIncomeSources() {
		List<PIncomeSource> incomeSources = IncomeSourceController.instance().listActive();
		List<Map<String, Object>> incomeSourcesListJSON = new ArrayList<Map<String,Object>>(incomeSources.size());
		for(PIncomeSource incomeSource : incomeSources) {
			Map<String, Object> incomeSourceJSON = new HashMap<String, Object>();
			incomeSourceJSON.put("id", incomeSource.getId());
			incomeSourceJSON.put("name", incomeSource.getName());
			incomeSourceJSON.put("currencyId", incomeSource.getCurrency().getId());
			BigDecimal sum = Calc.sum(IncomeItemController.instance().listByIncomeSource(incomeSource));
			incomeSourceJSON.put("sum", toCents(sum));
			incomeSourcesListJSON.add(incomeSourceJSON);
		}
		
		return incomeSourcesListJSON;
	}

	private List<Map<String, Object>> buildExpensePlans() {
		List<PExpensePlan> expensePlans = ExpensePlanController.instance().listActive();			
		List<Map<String, Object>> openPlansListJSON= new ArrayList<Map<String,Object>>(expensePlans.size());
		for(PExpensePlan expensePlan : expensePlans) {
			
			boolean budgetHasOpenItems = DBConditions.hasExpensePlanOpenItems(expensePlan);
			
			if(budgetHasOpenItems) {
				
				List<PExpensePlanItem> expensePlanItems = ExpensePlanItemController.instance().listByExpensePlan(expensePlan);
				BigDecimal expensePlanSum = new BigDecimal(0); //For counting expenses plan planned sum
				BigDecimal expensePlanRemainder = new BigDecimal(0); //For counting expenses plan remainder
				
				Map<String, Object> openPlansJSONMap = new HashMap<String, Object>(4);
				openPlansJSONMap.put("id", expensePlan.getId());
				openPlansJSONMap.put("name", expensePlan.getName());
				openPlansJSONMap.put("currencyId", expensePlan.getCurrency().getId());
										
				List<Map<String, Object>> itemsListJSON = new ArrayList<Map<String,Object>>(expensePlanItems.size());
				
				for(PExpensePlanItem item : expensePlanItems) {
					Map<String, Object> itemJSON = new HashMap<String, Object>(5);
					if(isItemOpened(item)) {					
						itemJSON.put("id", item.getId());
						itemJSON.put("name", item.getName());
						itemJSON.put("category", item.getCategory().getName());
						itemJSON.put("sum", toCents(item.getSumm()));
						itemJSON.put("comment", item.getComment());	
					}
					
					List<PExpense> expensesByItem = ExpenseController.instance().listByExpensePlanItem(item);
					BigDecimal spent = Calc.sum(expensesByItem);
					BigDecimal itemRemainder = item.getSumm().subtract(spent);
					
					if(isItemOpened(item)) {		
						itemJSON.put("remainder", toCents(itemRemainder));
						itemsListJSON.add(itemJSON);
					}

					expensePlanSum = expensePlanSum.add(item.getSumm());
					expensePlanRemainder = expensePlanRemainder.add(itemRemainder);
				}
				
				openPlansJSONMap.put("sum", toCents(expensePlanSum));
				openPlansJSONMap.put("remainder", toCents(expensePlanRemainder));
				
				openPlansJSONMap.put("items", itemsListJSON);
										
				openPlansListJSON.add(openPlansJSONMap);
			}

		}
		return openPlansListJSON;
    }

    private BigDecimal toCents(BigDecimal sum) {
        return sum.multiply(new BigDecimal(100)).setScale(0);
    }
}
