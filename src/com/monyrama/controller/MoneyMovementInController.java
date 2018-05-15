package com.monyrama.controller;

import com.monyrama.controller.HibernateUtil;
import org.hibernate.Session;

import com.monyrama.entity.PAccount;
import com.monyrama.entity.PMoneyMovementIn;

class MoneyMovementInController<T extends PMoneyMovementIn> extends AbstractController<T> {

	protected MoneyMovementInController(Class<T> clazz) {
		super(clazz);
	}
		
	@Override
	public void createOrUpdate(T entity) {
		throw new RuntimeException("You must use one of create or update method for this controller");
	}

	public void create(final T moneyMovementIn) {
		moneyMovementIn.prepareToPersist();
		final PAccount account = moneyMovementIn.getAccount();
		account.setSumm(account.getSumm().add(moneyMovementIn.getSumm()));
		account.prepareToPersist();
		HibernateUtil.doInTransaction(new Executable() {
			@Override
			public void execute(Session session) {
				session.save(moneyMovementIn);
				session.update(account);
			}
		});
		fireCreatedOrUpdated(moneyMovementIn);
		AccountController.instance().fireCreatedOrUpdated(account);
	}
	
	public void update(final T mmInOld, final T mmInNew) {
		mmInNew.prepareToPersist();
		
		final PAccount mmInOldAccount = mmInOld.getAccount();
		final PAccount mmInNewAccount = mmInNew.getAccount();
		mmInOldAccount.setSumm(mmInOldAccount.getSumm().subtract(mmInOld.getSumm()));
		if(mmInOldAccount.equals(mmInNewAccount)) {
			mmInOldAccount.setSumm(mmInOldAccount.getSumm().add(mmInNew.getSumm()));	
		} else {
			mmInNewAccount.setSumm(mmInNewAccount.getSumm().add(mmInNew.getSumm()));	
		}							
				
		mmInNew.setId(mmInOld.getId());
		
		mmInNewAccount.prepareToPersist();
		mmInOldAccount.prepareToPersist();
		
		HibernateUtil.doInTransaction(new Executable() {			
			@Override
			public void execute(Session session) {
				session.update(mmInNew);
				session.update(mmInOldAccount);
				if(!mmInNewAccount.equals(mmInOldAccount)) {
					session.update(mmInNewAccount);
				}			
			}
		});
		fireCreatedOrUpdated(mmInNew);
		AccountController.instance().fireCreatedOrUpdated(mmInOldAccount);
		AccountController.instance().fireCreatedOrUpdated(mmInNewAccount);
	}	
	
	public void delete(final T moneyMovementIn) {
		final PAccount account = moneyMovementIn.getAccount();
		account.setSumm(account.getSumm().subtract(moneyMovementIn.getSumm()));
		account.prepareToPersist();
		HibernateUtil.doInTransaction(new Executable() {			
			@Override
			public void execute(Session session) {
				session.delete(moneyMovementIn);
				session.update(account);
			}
		});
		fireDeleted(moneyMovementIn);
		AccountController.instance().fireCreatedOrUpdated(account);
	}		
}
