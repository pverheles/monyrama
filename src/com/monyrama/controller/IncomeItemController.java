package com.monyrama.controller;

import java.util.List;

import com.monyrama.controller.MoneyMovementInController;
import com.monyrama.controller.Resultable;
import org.hibernate.query.SelectionQuery;
import org.hibernate.Session;

import com.monyrama.entity.PIncome;
import com.monyrama.entity.PIncomeSource;


public class IncomeItemController extends MoneyMovementInController<PIncome> {

	protected IncomeItemController(Class<PIncome> clazz) {
		super(clazz);
	}

	private static IncomeItemController instance;
	
	public static IncomeItemController instance() {		
		if(instance == null) {
			instance = new IncomeItemController(PIncome.class);
		}
		return instance;		
	}
	
	public List<PIncome> listByIncomeSource(final PIncomeSource incomeSource) {
		return HibernateUtil.queryInTransaction(new Resultable<List<PIncome>>() {
			@Override
			public List<PIncome> getResult(Session session) {
				SelectionQuery<PIncome> query = session.createSelectionQuery("from " + PIncome.class.getSimpleName() + " incomeItem where incomeItem.incomeSource = :incomeSource", PIncome.class);
				query.setParameter("incomeSource", incomeSource);
				return query.list();
			}
		});
	}
}
