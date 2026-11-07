package com.monyrama.controller;

import org.hibernate.Session;
import org.hibernate.query.SelectionQuery;

import com.monyrama.db.enumarations.EntityStates;
import com.monyrama.entity.PAccount;
import com.monyrama.entity.PCategory;
import com.monyrama.entity.PCurrency;
import com.monyrama.entity.PExpensePlan;
import com.monyrama.entity.PExpensePlanItem;
import com.monyrama.entity.PIncome;
import com.monyrama.entity.PIncomeSource;
import com.monyrama.entity.PMoneyMovementIn;
import com.monyrama.entity.PMoneyMovementOut;


public class DBConditions {	
	public static boolean currencyInUse(final PCurrency currency) {
		boolean result = HibernateUtil.queryInTransaction(new Resultable<Boolean>() {
			@Override
			public Boolean getResult(Session session) {
				SelectionQuery<Long> accountQuery = session.createSelectionQuery("select account.id from " + PAccount.class.getSimpleName() + " as account where account.currency = :currency", Long.class);
				accountQuery.setParameter("currency", currency);
				accountQuery.setMaxResults(1);

				SelectionQuery<Long> incomeSourceQuery = session.createSelectionQuery("select incomeSource.id from " + PIncomeSource.class.getSimpleName() + " as incomeSource where incomeSource.currency = :currency", Long.class);
				incomeSourceQuery.setParameter("currency", currency);
				incomeSourceQuery.setMaxResults(1);

				SelectionQuery<Long> expensePlanQuery = session.createSelectionQuery("select expensePlan.id from " + PExpensePlan.class.getSimpleName() + " as expensePlan where expensePlan.currency = :currency", Long.class);
				expensePlanQuery.setParameter("currency", currency);
				expensePlanQuery.setMaxResults(1);

				return accountQuery.uniqueResult() != null
						|| incomeSourceQuery.uniqueResult() != null
						|| expensePlanQuery.uniqueResult() != null;
			}
		});
		
		return result;
	}
	
	public static boolean categoryHasExpensesItems(final PCategory category) {		
		boolean result = HibernateUtil.queryInTransaction(new Resultable<Boolean>() {
			@Override
			public Boolean getResult(Session session) {
				SelectionQuery<Long> query = session.createSelectionQuery("select expenseItem.id from " + PExpensePlanItem.class.getSimpleName() + " as expenseItem where expenseItem.category = :category", Long.class);
				query.setParameter("category", category);
				query.setMaxResults(1);
				return query.uniqueResult() != null;
			}
		});
		
		return result;
	}
	
	public static boolean accountHasMoneyMovements(final PAccount account) {		
		boolean result = HibernateUtil.queryInTransaction(new Resultable<Boolean>() {
			@Override
			public Boolean getResult(Session session) {
				SelectionQuery<Long> moneyMovementInQuery = session.createSelectionQuery("select moneyMovementIn.id from " + PMoneyMovementIn.class.getSimpleName() + " as moneyMovementIn where moneyMovementIn.account = :account", Long.class);
				moneyMovementInQuery.setParameter("account", account);
				moneyMovementInQuery.setMaxResults(1);
				
				SelectionQuery<Long> moneyMovementOutQuery = session.createSelectionQuery("select moneyMovementOut.id from " + PMoneyMovementOut.class.getSimpleName() + " as moneyMovementOut where moneyMovementOut.account = :account", Long.class);
				moneyMovementOutQuery.setParameter("account", account);
				moneyMovementOutQuery.setMaxResults(1);
				
				return moneyMovementInQuery.uniqueResult() != null || moneyMovementOutQuery.uniqueResult() != null;
			}
		});
		
		return result;
	}
	
	public static boolean moreThanOneActiveAccountExist() {		
		boolean result = HibernateUtil.queryInTransaction(new Resultable<Boolean>() {
			@Override
			public Boolean getResult(Session session) {
				SelectionQuery<Long> query = session.createSelectionQuery("select account.id from " + PAccount.class.getSimpleName() + " as account where account.state = :state", Long.class);
				query.setParameter("state", EntityStates.ACTIVE.getCode());
				query.setMaxResults(2);
								
				return query.list().size() == 2;
			}
		});
		
		return result;
	}	
	
	public static boolean existOpenCategories() {
		boolean result = HibernateUtil.queryInTransaction(new Resultable<Boolean>() {
			@Override
			public Boolean getResult(Session session) {
				SelectionQuery<Long> query = session.createSelectionQuery("select category.id from " + PCategory.class.getSimpleName() + " as category where category.state = :state", Long.class);
				query.setParameter("state", EntityStates.ACTIVE.getCode());
				query.setMaxResults(1);
				return query.uniqueResult() != null;
			}
		});
		
		return result;	
	}
	
	public static boolean hasIncomeSourceIncomes(final PIncomeSource incomeSource) {
		boolean result = HibernateUtil.queryInTransaction(new Resultable<Boolean>() {
			@Override
			public Boolean getResult(Session session) {
				SelectionQuery<Long> query = session.createSelectionQuery("select income.id from " + PIncome.class.getSimpleName() + " as income where income.incomeSource = :incomeSource", Long.class);
				query.setParameter("incomeSource", incomeSource);
				query.setMaxResults(1);
				return query.uniqueResult() != null;
			}
		});
		
		return result;
	}
	
	public static boolean hasExpensePlanOpenItems(final PExpensePlan expensePlan) {
		return HibernateUtil.queryInTransaction(new Resultable<Boolean>() {
			@Override
			public Boolean getResult(Session session) {
				Long count = session.createSelectionQuery(
						"select count(*) from PExpensePlanItem i where i.expensePlan = :expensePlan and i.state = :state", Long.class)
						.setParameter("expensePlan", expensePlan)
						.setParameter("state", EntityStates.ACTIVE.getCode())
						.uniqueResult();
				return count > 0;
			}
		});
	}	
	
	public static boolean hasExpensePlanAnyItems(final PExpensePlan expensePlan) {
		return HibernateUtil.queryInTransaction(new Resultable<Boolean>() {
			@Override
			public Boolean getResult(Session session) {
				Long count = session.createSelectionQuery(
						"select count(*) from PExpensePlanItem i where i.expensePlan = :expensePlan", Long.class)
						.setParameter("expensePlan", expensePlan)
						.uniqueResult();
				return count > 0;
			}
		});
	}	
}
