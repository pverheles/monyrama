package com.monyrama.server;

import java.io.BufferedReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.monyrama.controller.*;
import com.monyrama.entity.*;
import org.eclipse.jetty.util.ajax.JSON;

import com.monyrama.db.enumarations.EntityStates;
import com.monyrama.ui.resources.Resources;
import org.hibernate.Session;

public class SendDataHandler implements RequestHandler {
	private final static SimpleDateFormat JSON_DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
	
	@Override
	public Response handle(final HttpServletRequest request) {
		final String BLOCK_ID = UUID.randomUUID().toString();
		MainController.instance().createUIBlock(BLOCK_ID, Resources.getString("server"), Resources.getString("labels.server.block.gettingexpenses"));
		
		final ExecutorService executorService = Executors.newSingleThreadExecutor();
		Future<Response> responseFuture = executorService.submit(new Callable<Response>() {
			@Override
			public Response call() throws Exception {
				try {
                    List<PExpensePlan> activeExpensePlans = ExpensePlanController.instance().listActive();
                    Map<Long, PExpensePlan> activeExpensePlansMap = new HashMap<Long, PExpensePlan>();
                    Map<Long, PExpensePlanItem> envelopesMap = new HashMap<Long, PExpensePlanItem>();
                    for(PExpensePlan expensePlan : activeExpensePlans) {
                        activeExpensePlansMap.put(expensePlan.getId(), expensePlan);
                        List<PExpensePlanItem> expensePlanItems = ExpensePlanItemController.instance().listByExpensePlan(expensePlan);
                        for(PExpensePlanItem expensePlanItem : expensePlanItems) {
                            envelopesMap.put(expensePlanItem.getId(), expensePlanItem);
                        }
                    }

                    List<PAccount> activeAccounts = AccountController.instance().listActive();
                    Map<Long, PAccount> activeAccountMap = new HashMap<Long, PAccount>();
                    for(PAccount account : activeAccounts) {
                        activeAccountMap.put(account.getId(), account);
                    }

                    List<PIncomeSource> activeIncomeSources = IncomeSourceController.instance().listActive();
                    Map<Long, PIncomeSource> activeIncomeSourceMap = new HashMap<Long, PIncomeSource>();
                    for(PIncomeSource incomeSource : activeIncomeSources) {
                        activeIncomeSourceMap.put(incomeSource.getId(), incomeSource);
                    }

                    //
					BufferedReader reader = new BufferedReader(request.getReader());

					Map<String, Object> mobileData = (Map<String, Object>)JSON.parse(reader);

                    Object[] expensesData = (Object[])mobileData.get("expenses");
                    final List<PExpense> expenses = new ArrayList<PExpense>();
                    for(Object rawExpense : expensesData) {
                        Map<String, Object> expenseFieldsMap = (Map<String, Object>) rawExpense;
                        //Long expenseMobileId = (Long) expenseFieldsMap.get("expenseMobileId");
                        Long accountId = (Long) expenseFieldsMap.get("accountid");
                        Long expensePlanId = (Long) expenseFieldsMap.get("expenseplanid");
                        Long envelopeid = (Long) expenseFieldsMap.get("envelopeid");
                        String comment = (String) expenseFieldsMap.get("comment");
                        Object sum = expenseFieldsMap.get("sum");
                        String dateStr = (String) expenseFieldsMap.get("date");
                        Date date;
                        try {
                            date = JSON_DATE_FORMAT.parse(dateStr);
                        } catch (ParseException e) {
                            return errorResponse(BLOCK_ID);
                        }

                        PExpensePlan expensePlan = activeExpensePlansMap.get(expensePlanId);
                        if(expensePlan == null) {
                            return errorResponse(BLOCK_ID);
                        }

                        PExpensePlanItem envelop = envelopesMap.get(envelopeid);
                        if(envelop == null) {
                            return errorResponse(BLOCK_ID);
                        }

                        PAccount account = activeAccountMap.get(accountId);
                        if(account == null) {
                            return errorResponse(BLOCK_ID);
                        }

                        PExpense expense = new PExpense();

                        expense.setAccount(account);
                        expense.setExpensePlanItem(envelop);
                        expense.setSumm(new BigDecimal(sum instanceof Double ? (Double)sum : (Long)sum));
						expense.setLastChangeDate(date);
                        expense.setComment(comment);
                        expenses.add(expense);
                    }

                    Object[] incomesData = (Object[])mobileData.get("incomes");
                    final List<PIncome> incomes = new ArrayList<PIncome>();
                    for(Object rawIncome : incomesData) {
                        Map<String, Object> incomeFieldsMap = (Map<String, Object>) rawIncome;
                        Long accountId = (Long) incomeFieldsMap.get("accountid");
                        Long incomeSourceId = (Long) incomeFieldsMap.get("incomeSourceId");
                        Object sum = incomeFieldsMap.get("sum");
                        String dateStr = (String) incomeFieldsMap.get("date");
                        Date date;
                        try {
                            date = JSON_DATE_FORMAT.parse(dateStr);
                        } catch (ParseException e) {
                            return errorResponse(BLOCK_ID);
                        }
                        String comment = (String) incomeFieldsMap.get("comment");

                        PAccount account = activeAccountMap.get(accountId);
                        if(account == null) {
                            return errorResponse(BLOCK_ID);
                        }

                        PIncomeSource incomeSource = activeIncomeSourceMap.get(incomeSourceId);
                        if(incomeSource == null) {
                            return errorResponse(BLOCK_ID);
                        }

                        PIncome income = new PIncome();
                        income.setAccount(account);
                        income.setIncomeSource(incomeSource);
                        income.setSumm(new BigDecimal(sum instanceof Double ? (Double)sum : (Long)sum));
                        income.setLastChangeDate(date);
                        income.setComment(comment);
                        incomes.add(income);
                    }

                    Object[] transfersData = (Object[])mobileData.get("transfers");
                    final List<PTransfer> transfers = new ArrayList<PTransfer>();
                    final List<PTransferIn> transfersIn = new ArrayList<PTransferIn>();
                    final List<PTransferOut> transfersOut = new ArrayList<PTransferOut>();

                    for(Object rawTransfer : transfersData) {
                        Map<String, Object> transferFieldsMap = (Map<String, Object>) rawTransfer;
                        Long fromAccountId = (Long) transferFieldsMap.get("fromAccountId");
                        Long toAccountId = (Long) transferFieldsMap.get("toAccountId");
                        Object fromSum = transferFieldsMap.get("fromSum");
                        Object toSum = transferFieldsMap.get("toSum");
                        String dateStr = (String) transferFieldsMap.get("date");
                        Date date;
                        try {
                            date = JSON_DATE_FORMAT.parse(dateStr);
                        } catch (ParseException e) {
                            return errorResponse(BLOCK_ID);
                        }

                        PAccount fromAccount = activeAccountMap.get(fromAccountId);
                        PAccount toAccount = activeAccountMap.get(toAccountId);
                        if(fromAccount == null || toAccount == null) {
                            return errorResponse(BLOCK_ID);
                        }

                        PTransferOut transferOut = new PTransferOut();
                        transferOut.setAccount(fromAccount);
                        transferOut.setSumm(new BigDecimal(fromSum instanceof Double ? (Double)fromSum : (Long)fromSum));
                        transferOut.setLastChangeDate(date);
                        transfersOut.add(transferOut);

                        PTransferIn transferIn = new PTransferIn();
                        transferIn.setAccount(toAccount);
                        transferIn.setSumm(new BigDecimal(toSum instanceof Double ? (Double)toSum : (Long)toSum));
                        transferIn.setLastChangeDate(date);
                        transfersIn.add(transferIn);

                        PTransfer transfer = new PTransfer();
                        transfer.setTransferIn(transferIn);
                        transfer.setTransferOut(transferOut);
                        transfer.setLastChangeDate(date);
                        transfers.add(transfer);
                    }

                    HibernateUtil.doInTransaction(new Executable() {
                        @Override
                        public void execute(Session session) {
                            Map<Long, PAccount> updatedAccounts = new HashMap<Long, PAccount>();
                            Map<Long, PExpensePlanItem> updatedEnvelopes = new HashMap<Long, PExpensePlanItem>();

                            for (PExpense expense : expenses) {
                                PAccount account = expense.getAccount();
                                account.setSumm(account.getSumm().subtract(expense.getSumm()));
                                updatedAccounts.put(account.getId(), account);
                                PExpensePlanItem envelope = expense.getExpensePlanItem();
                                if (!envelope.isActive()) {
                                    envelope.setState(EntityStates.ACTIVE.getCode());
                                    updatedEnvelopes.put(envelope.getId(), envelope);
                                }
                                expense.setId(UniqueID.get());
                                session.save(expense);
                            }

                            for (PExpensePlanItem envelope : updatedEnvelopes.values()) {
                                session.update(envelope);
                            }

                            for (PIncome income : incomes) {
                                PAccount account = income.getAccount();
                                account.setSumm(account.getSumm().add(income.getSumm()));
                                updatedAccounts.put(account.getId(), account);
                                income.setId(UniqueID.get());
                                session.save(income);
                            }

                            for (PTransferOut transferOut : transfersOut) {
                                PAccount account = transferOut.getAccount();
                                account.setSumm(account.getSumm().subtract(transferOut.getSumm()));
                                updatedAccounts.put(account.getId(), account);
                                transferOut.setId(UniqueID.get());
                                session.save(transferOut);
                            }

                            for (PTransferIn transferIn : transfersIn) {
                                PAccount account = transferIn.getAccount();
                                account.setSumm(account.getSumm().add(transferIn.getSumm()));
                                updatedAccounts.put(account.getId(), account);
                                transferIn.setId(UniqueID.get());
                                session.save(transferIn);
                            }

                            for (PTransfer transfer : transfers) {
                                transfer.setId(UniqueID.get());
                                session.save(transfer);
                            }

                            for (PAccount account : updatedAccounts.values()) {
                                session.update(account);
                            }
                        }
                    });
				} catch (Exception e) {
                    return errorResponse(BLOCK_ID);
				}

				return successResponse("server.message.success", BLOCK_ID);
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

        MobileDataManager.fireMobileDataSaved();

        return response;

	}

    private Response errorResponse(String blockId) {
        MainController.instance().releaseUIBlock(blockId);
        return new Response(HttpServletResponse.SC_BAD_REQUEST, Resources.getString("server.message.error.cannotprocess"));
    }

    private Response successResponse(String msg, String blockId) {
        MainController.instance().releaseUIBlock(blockId);
        return new Response(HttpServletResponse.SC_OK, msg);
    }

}
