package com.monyrama.controller;

import com.monyrama.controller.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

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
				Query accountQuery = session.createQuery("select account.id from " + PAccount.class.getSimpleName() + " as account where account.currency = :currency");
				accountQuery.setParameter("currency", currency);
				accountQuery.setMaxResults(1);

				Query incomeSourceQuery = session.createQuery("select incomeSource.id from " + PIncomeSource.class.getSimpleName() + " as incomeSource where incomeSource.currency = :currency");
				incomeSourceQuery.setParameter("currency", currency);
				incomeSourceQuery.setMaxResults(1);

				Query expensePlanQuery = session.createQuery("select expensePlan.id from " + PExpensePlan.class.getSimpleName() + " as expensePlan where expensePlan.currency = :currency");
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
				Query query = session.createQuery("select expenseItem.id from " + PExpensePlanItem.class.getSimpleName() + " as expenseItem where expenseItem.category = :category");
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
				Query moneyMovementInQuery = session.createQuery("select moneyMovementIn.id from " + PMoneyMovementIn.class.getSimpleName() + " as moneyMovementIn where moneyMovementIn.account = :account");
				moneyMovementInQuery.setParameter("account", account);
				moneyMovementInQuery.setMaxResults(1);
				
				Query moneyMovementOutQuery = session.createQuery("select moneyMovementOut.id from " + PMoneyMovementOut.class.getSimpleName() + " as moneyMovementOut where moneyMovementOut.account = :account");
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
				Query query = session.createQuery("select account.id from " + PAccount.class.getSimpleName() + " as account where account.state = :state");
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
				Query query = session.createQuery("select category.id from " + PCategory.class.getSimpleName() + " as category where category.state = :state");
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
				Query query = session.createQuery("select income.id from " + PIncome.class.getSimpleName() + " as income where income.incomeSource = :incomeSource");
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
				Criteria criteria = session.createCriteria(PExpensePlanItem.class);
				criteria.add(Restrictions.eq("expensePlan", expensePlan));
				criteria.add(Restrictions.eq("state", EntityStates.ACTIVE.getCode()));
				criteria.setProjection(Projections.rowCount());
				Number count = (Number) criteria.uniqueResult();	
				return count.intValue() > 0;
			}
		});
	}	
	
	public static boolean hasExpensePlanAnyItems(final PExpensePlan expensePlan) {
		return HibernateUtil.queryInTransaction(new Resultable<Boolean>() {
			@Override
			public Boolean getResult(Session session) {
				Criteria criteria = session.createCriteria(PExpensePlanItem.class);
				criteria.add(Restrictions.eq("expensePlan", expensePlan));
				criteria.setProjection(Projections.rowCount());
				Number count = (Number) criteria.uniqueResult();	
				return count.intValue() > 0;
			}
		});
	}	
}
