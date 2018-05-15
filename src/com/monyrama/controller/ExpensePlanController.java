package com.monyrama.controller;

import java.util.List;

import com.monyrama.controller.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.monyrama.db.enumarations.EntityStates;
import com.monyrama.entity.PExpensePlan;
import com.monyrama.entity.PExpensePlanItem;

public class ExpensePlanController extends AbstractController<PExpensePlan> {
	protected ExpensePlanController(Class<PExpensePlan> clazz) {
		super(clazz);
	}

	private static ExpensePlanController instance;
	
	public static ExpensePlanController instance() {		
		if(instance == null) {
			instance = new ExpensePlanController(PExpensePlan.class);
		}
		return instance;		
	}
	
	public void createAsCopy(final PExpensePlan newExpensePlan, final PExpensePlan copiedExpensePlan) {
		newExpensePlan.prepareToPersist();
		
		HibernateUtil.doInTransaction(new Executable() {
			@Override
			public void execute(Session session) {
				Criteria expensePlanItemsCriteria = session.createCriteria(PExpensePlanItem.class);
				expensePlanItemsCriteria.add(Restrictions.eq("expensePlan", copiedExpensePlan));
				List<PExpensePlanItem> expensePlanItems = expensePlanItemsCriteria.list();
				session.save(newExpensePlan);

				for (PExpensePlanItem expensePlanItem : expensePlanItems) {
					PExpensePlanItem newExpensePlanItem = new PExpensePlanItem();
					newExpensePlanItem.setExpensePlan(newExpensePlan);
					newExpensePlanItem.setState(EntityStates.ACTIVE.getCode());

					newExpensePlanItem.setCategory(expensePlanItem.getCategory());
					newExpensePlanItem.setName(expensePlanItem.getName());
					newExpensePlanItem.setSumm(expensePlanItem.getSumm());
					newExpensePlanItem.setComment(expensePlanItem.getComment());

					newExpensePlanItem.prepareToPersist();
					session.save(newExpensePlanItem);
				}
			}
		});
		
		fireCreatedOrUpdated(newExpensePlan);
	}
}
