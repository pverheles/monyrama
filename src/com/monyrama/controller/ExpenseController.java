package com.monyrama.controller;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.monyrama.entity.PExpense;
import com.monyrama.entity.PExpensePlan;
import com.monyrama.entity.PExpensePlanItem;


public class ExpenseController extends MoneyMovementOutController<PExpense> {
	protected ExpenseController(Class<PExpense> clazz) {
		super(clazz);
	}

	private static ExpenseController instance;
	
	public static ExpenseController instance() {		
		if(instance == null) {
			instance = new ExpenseController(PExpense.class);
		}
		return instance;		
	}

	public void updateExpenseExpensePlanItem(final Long expenseId,
											 final PExpensePlanItem expensePlanItem) {
		PExpense expense = getById(expenseId);
		HibernateUtil.doInTransaction(new Executable() {
			@Override
			public void execute(Session session) {
				expense.setExpensePlanItem(expensePlanItem);
				session.saveOrUpdate(expense);
			}
		});
		fireCreatedOrUpdated(expense);
	}
	
	public List<PExpense> listByExpensePlan(final PExpensePlan expensePlan) {
		return HibernateUtil.queryInTransaction(new Resultable<List<PExpense>>() {
			@Override
			public List<PExpense> getResult(Session session) {
				Query query = session.createQuery("from " + PExpense.class.getSimpleName() + " expense where expense.expensePlanItem.expensePlan = :expensePlan");
				query.setParameter("expensePlan", expensePlan);
				return query.list();
			}
		});
	}
	
	public List<PExpense> listByExpensePlanItem(final PExpensePlanItem expensePlanItem) {
		return HibernateUtil.queryInTransaction(new Resultable<List<PExpense>>() {
			@Override
			public List<PExpense> getResult(Session session) {			
				Query query = session.createQuery("from " + PExpense.class.getSimpleName() + " expense where expense.expensePlanItem = :expensePlanItem");
				query.setParameter("expensePlanItem", expensePlanItem);
				return query.list();
			}
		});
	}

}
