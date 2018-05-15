package com.monyrama.controller;

import org.hibernate.Session;

import com.monyrama.entity.PAccount;
import com.monyrama.entity.PMoneyMovementOut;

class MoneyMovementOutController<T extends PMoneyMovementOut> extends AbstractController<T> {

	protected MoneyMovementOutController(Class<T> clazz) {
		super(clazz);
	}
	
	@Override
	public void createOrUpdate(T entity) {
		throw new RuntimeException("You must use one of create or update method for this controller");
	}	
	
	public void create(final T moneyMovementOut) {
		moneyMovementOut.prepareToPersist();
		final PAccount account = moneyMovementOut.getAccount();
		account.setSumm(account.getSumm().subtract(moneyMovementOut.getSumm()));
		account.prepareToPersist();
		HibernateUtil.doInTransaction(new Executable() {
			@Override
			public void execute(Session session) {
				session.save(moneyMovementOut);
				session.update(account);
			}
		});
		fireCreatedOrUpdated(moneyMovementOut);
		AccountController.instance().fireCreatedOrUpdated(account);
	}
	
	public void update(final T mmOutOld, final T mmOutNew) {
		mmOutNew.prepareToPersist();
		
		final PAccount mmOutOldAccount = mmOutOld.getAccount();
		final PAccount mmOutNewAccount = mmOutNew.getAccount();
		mmOutOldAccount.setSumm(mmOutOldAccount.getSumm().add(mmOutOld.getSumm()));
		if(mmOutOldAccount.equals(mmOutNewAccount)) {
			mmOutOldAccount.setSumm(mmOutOldAccount.getSumm().subtract(mmOutNew.getSumm()));
		} else {
			mmOutNewAccount.setSumm(mmOutNewAccount.getSumm().subtract(mmOutNew.getSumm()));
		}							
				
		mmOutNew.setId(mmOutOld.getId());
		
		mmOutNewAccount.prepareToPersist();
		mmOutOldAccount.prepareToPersist();
		
		HibernateUtil.doInTransaction(new Executable() {			
			@Override
			public void execute(Session session) {
				session.update(mmOutNew);
				session.update(mmOutOldAccount);
				if(!mmOutNewAccount.equals(mmOutOldAccount)) {
					session.update(mmOutNewAccount);
				}			
			}
		});
		fireCreatedOrUpdated(mmOutNew);
		AccountController.instance().fireCreatedOrUpdated(mmOutOldAccount);
		AccountController.instance().fireCreatedOrUpdated(mmOutNewAccount);
	}
	
	public void delete(final T moneyMovementOut) {
		final PAccount account = moneyMovementOut.getAccount();
		account.setSumm(account.getSumm().add(moneyMovementOut.getSumm()));
		account.prepareToPersist();
		HibernateUtil.doInTransaction(new Executable() {			
			@Override
			public void execute(Session session) {
				session.delete(moneyMovementOut);
				session.update(account);
			}
		});
		fireDeleted(moneyMovementOut);
		AccountController.instance().fireCreatedOrUpdated(account);
	}	
}
