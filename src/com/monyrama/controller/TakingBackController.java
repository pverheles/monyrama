package com.monyrama.controller;

import java.util.List;

import com.monyrama.controller.HibernateUtil;
import com.monyrama.controller.LendController;
import org.hibernate.Query;
import org.hibernate.Session;

import com.monyrama.entity.PLend;
import com.monyrama.entity.PTakingBack;


public class TakingBackController extends MoneyMovementInController<PTakingBack> {
	protected TakingBackController(Class<PTakingBack> clazz) {
		super(clazz);
	}

	private static TakingBackController instance;
	
	public static TakingBackController instance() {		
		if(instance == null) {
			instance = new TakingBackController(PTakingBack.class);
		}
		return instance;		
	}
	
//	public List<PTakingBack> listByLend(final PLend lend) {
//		return HibernateUtil.queryInTransaction(new Resultable<List<PTakingBack>>() {
//			@Override
//			public List<PTakingBack> getResult(Session session) {
//				Query query = session.createQuery("from " + PTakingBack.class.getSimpleName() + " takingBack where takingBack.lend = :lend");
//				query.setParameter("lend", lend);
//				List<PTakingBack> list = query.list();
//				return list;
//			}
//		});
//	}

	@Override
	public void create(PTakingBack takingBack) {
		super.create(takingBack);
		LendController.instance().fireCreatedOrUpdated(takingBack.getLend());
	}
}
