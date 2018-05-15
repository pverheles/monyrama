package com.monyrama.controller;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.monyrama.controller.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.monyrama.entity.PAccount;
import com.monyrama.entity.PTransfer;
import com.monyrama.entity.PTransferIn;
import com.monyrama.entity.PTransferOut;


public class TransferController extends AbstractController<PTransfer> {

	protected TransferController(Class<PTransfer> clazz) {
		super(clazz);
	}

	private static TransferController instance;
		
	public static TransferController instance() {
		if(instance == null) {
			instance = new TransferController(PTransfer.class);
		}
		return instance;
	}

	public void makeTransfer(final PAccount fromAccount, final BigDecimal fromSum, final PAccount toAccount, final BigDecimal toSum, final Date createDate) {	
		HibernateUtil.doInTransaction(new Executable() {
			@Override
			public void execute(Session session) {
				PTransferOut fromTransfer = new PTransferOut();
				fromTransfer.setAccount(fromAccount);
				fromTransfer.setSumm(fromSum);
				fromTransfer.setLastChangeDate(createDate);
				fromTransfer.prepareToPersist();

				BigDecimal fromAccountNewSum = fromAccount.getSumm().subtract(fromSum);
				fromAccount.setSumm(fromAccountNewSum);

				PTransferIn toTransfer = new PTransferIn();
				toTransfer.setAccount(toAccount);
				toTransfer.setSumm(toSum);
				toTransfer.setLastChangeDate(createDate);
				toTransfer.prepareToPersist();

				BigDecimal toAccountNewSum = toAccount.getSumm().add(toSum);
				toAccount.setSumm(toAccountNewSum);

				PTransfer transfer = new PTransfer();
				transfer.setLastChangeDate(createDate);
				transfer.setTransferOut(fromTransfer);
				transfer.setTransferIn(toTransfer);

				session.saveOrUpdate(fromTransfer);
				session.saveOrUpdate(fromAccount);
				session.saveOrUpdate(toTransfer);
				session.saveOrUpdate(toAccount);
				transfer.prepareToPersist();
				session.saveOrUpdate(transfer);
				TransferController.instance().fireCreatedOrUpdated(transfer);
			}
		});
		
		AccountController.instance().fireCreatedOrUpdated(fromAccount);
		AccountController.instance().fireCreatedOrUpdated(toAccount);		
	}
	
	
	
	@Override
	public void delete(final PTransfer transfer) {
		HibernateUtil.doInTransaction(new Executable() {			
			@Override
			public void execute(Session session) {				
				PAccount fromAccount = transfer.getTransferOut().getAccount();
				BigDecimal fromSum = transfer.getTransferOut().getSumm();
				
				BigDecimal fromAccountNewSum = fromAccount.getSumm().add(fromSum);
				fromAccount.setSumm(fromAccountNewSum);
				
				PAccount toAccount = transfer.getTransferIn().getAccount();
				BigDecimal toSum = transfer.getTransferIn().getSumm();
				
				BigDecimal toAccountNewSum = toAccount.getSumm().subtract(toSum);
				toAccount.setSumm(toAccountNewSum);
				
				fromAccount.prepareToPersist();
				toAccount.prepareToPersist();
				
				//session.delete(transfer.getTransferOut());
				session.saveOrUpdate(fromAccount);
				//session.delete(transfer.getTransferIn());
				session.saveOrUpdate(toAccount);
				session.delete(transfer);
				TransferController.instance().fireDeleted(transfer);
				AccountController.instance().fireCreatedOrUpdated(fromAccount);
				AccountController.instance().fireCreatedOrUpdated(toAccount);				
			}
		});	
	}

	public List<PTransfer> listByDates(final Date fromDate, final Date toDate) {
		return HibernateUtil.queryInTransaction(new Resultable<List<PTransfer>>() {
			@Override
			public List<PTransfer> getResult(Session session) {
				Criteria crit = session.createCriteria(PTransfer.class);
				Calendar startDate = Calendar.getInstance();
				startDate.setTime(fromDate);
				startDate.set(Calendar.HOUR_OF_DAY, 0);
				startDate.set(Calendar.MINUTE, 0);
				startDate.set(Calendar.SECOND, 0);
				startDate.set(Calendar.MILLISECOND, 0);
				
				Calendar endDate = Calendar.getInstance();
				endDate.setTime(toDate);
				endDate.set(Calendar.HOUR_OF_DAY, 23);
				endDate.set(Calendar.MINUTE, 59);
				endDate.set(Calendar.SECOND, 59);
				endDate.set(Calendar.MILLISECOND, 0);
				
				crit.add(Restrictions.ge("lastChangeDate", startDate.getTime()));
				crit.add(Restrictions.le("lastChangeDate", endDate.getTime()));
				List<PTransfer> entities = crit.list();
				
				return entities;
			}			
		});
	}
	
}
