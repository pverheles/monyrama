package com.monyrama.controller;

import com.monyrama.utils.Trimmer;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.monyrama.controller.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import com.monyrama.db.enumarations.EntityStates;
import com.monyrama.entity.PExpensePlan;
import com.monyrama.entity.PExpensePlanItem;
import com.monyrama.validator.util.StringValidator;


public class ExpensePlanItemController extends AbstractController<PExpensePlanItem> {
	protected ExpensePlanItemController(Class<PExpensePlanItem> clazz) {
		super(clazz);
	}

	private static ExpensePlanItemController instance;
	
	public static ExpensePlanItemController instance() {		
		if(instance == null) {
			instance = new ExpensePlanItemController(PExpensePlanItem.class);
		}
		return instance;		
	}
	
	public List<PExpensePlanItem> listOpenedByExpensePlan(final PExpensePlan expensePlan) {
		return HibernateUtil.queryInTransaction(new Resultable<List<PExpensePlanItem>>() {
			@Override
			public List<PExpensePlanItem> getResult(Session session) {
				Query query = session.createQuery("from " + PExpensePlanItem.class.getSimpleName()
						+ " expensePlanItem where expensePlanItem.expensePlan = :expensePlan and expensePlanItem.state = :state");
				query.setParameter("expensePlan", expensePlan);
				query.setParameter("state", EntityStates.ACTIVE.getCode());
				List<PExpensePlanItem> list = query.list();
				sort(list);
				return list;
			}
		});
	}
	
	public List<PExpensePlanItem> listByExpensePlan(final PExpensePlan expensePlan) {
		return HibernateUtil.queryInTransaction(new Resultable<List<PExpensePlanItem>>() {
			@Override
			public List<PExpensePlanItem> getResult(Session session) {
				Query query = session.createQuery("from " + PExpensePlanItem.class.getSimpleName() + " expensePlanItem where expensePlanItem.expensePlan = :expensePlan");
				query.setParameter("expensePlan", expensePlan);
				List<PExpensePlanItem> list = query.list();
				sort(list);
				return list;
			}
		});
	}
	
	private void sort(List<PExpensePlanItem> list) {	
		Collections.sort(list, new Comparator<PExpensePlanItem>() {
			@Override
			public int compare(PExpensePlanItem epi1, PExpensePlanItem epi2) {
				String name1 = !StringValidator.isStringNullOrEmpty(epi1.getName()) ? epi1.getName() : epi1.getCategory().getName();
				String name2 = !StringValidator.isStringNullOrEmpty(epi2.getName()) ? epi2.getName() : epi2.getCategory().getName();
				
				return name1.compareToIgnoreCase(name2);
			}
		});
	}

	public void createDefault(PExpensePlan expensePlan) {
		PExpensePlanItem newItem = new PExpensePlanItem();

		newItem.setState(EntityStates.ACTIVE.getCode());
		newItem.setExpensePlan(expensePlan);
		newItem.setCategory(CategoryController.instance().getDefaultCategory());
		newItem.setSumStr("0");

		createOrUpdate(newItem);
	}
}
