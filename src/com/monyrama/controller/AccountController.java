package com.monyrama.controller;

import java.util.List;

import org.hibernate.Session;

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
				List<PAccount> accounts = session.createSelectionQuery(
						"from PAccount a where a.state = :state and a.currency = :currency", PAccount.class)
						.setParameter("state", EntityStates.ACTIVE.getCode())
						.setParameter("currency", currency)
						.list();
				NammableSorter.sort(accounts);
				return accounts;
			}
		});
	}
}
