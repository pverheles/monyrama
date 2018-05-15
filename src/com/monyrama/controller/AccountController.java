package com.monyrama.controller;

import java.util.List;

import com.monyrama.controller.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.monyrama.db.enumarations.EntityStates;
import com.monyrama.entity.PAccount;
import com.monyrama.entity.PCurrency;
import com.monyrama.sorter.NammableSorter;

public class AccountController extends AbstractController<PAccount> {

	protected AccountController(Class<PAccount> clazz) {
		super(clazz);
	}

	private static AccountController instance;
		
	public static AccountController instance() {
		if(instance == null) {
			instance = new AccountController(PAccount.class);
		}
		return instance;
	}

	public List<PAccount> listActiveByCurrency(final PCurrency currency) {
		return HibernateUtil.queryInTransaction(new Resultable<List<PAccount>>() {
			@Override
			public List<PAccount> getResult(Session session) {
				Criteria crit = session.createCriteria(PAccount.class);
				crit.add(Restrictions.eq("state", EntityStates.ACTIVE.getCode()));
				crit.add(Restrictions.eq("currency", currency));
				List<PAccount> accounts = crit.list();
				NammableSorter.sort(accounts);
				return accounts;
			}
		});
	}
}
