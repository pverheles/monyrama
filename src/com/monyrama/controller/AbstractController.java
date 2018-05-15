package com.monyrama.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.monyrama.db.enumarations.EntityStates;
import com.monyrama.controller.Executable;
import com.monyrama.controller.Resultable;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.monyrama.db.enumarations.EntityStates;
import com.monyrama.entity.BaseEntity;

abstract class AbstractController<T extends BaseEntity> {
	private Class<T> clazz;
	
	protected AbstractController(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	protected List<ControllerListener<T>> listeners = new ArrayList<ControllerListener<T>>();
	
	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		return HibernateUtil.queryInTransaction(new Resultable<List<T>>() {
			@Override
			public List<T> getResult(Session session) {
				Criteria crit = session.createCriteria(clazz);
				return crit.list();
			}			
		});
	}
	
	public void addListener(ControllerListener<T> listener) {
		listeners.add(listener);
	}
	
	public void removeListener(ControllerListener<T> listener) {
		listeners.remove(listener);
	}
	
	public void createOrUpdate(final T entity) {
		entity.prepareToPersist();
		
		HibernateUtil.doInTransaction(new Executable() {
			@Override
			public void execute(Session session) {
				session.saveOrUpdate(entity);
			}
		});
		fireCreatedOrUpdated(entity);
	}
	
	public T getById(final Long id) {
		return HibernateUtil.queryInTransaction(new Resultable<T>() {
			@Override
			public T getResult(Session session) {
				return (T)session.get(clazz, id);
			}			
		});
	}

	public void delete(final T idable) {
		HibernateUtil.doInTransaction(new Executable() {			
			@Override
			public void execute(Session session) {
				session.delete(idable);
			}
		});
		fireDeleted(idable);
	}
	
	public void updateState(final T entity, final EntityStates state) {
		entity.prepareToPersist();
		
		HibernateUtil.doInTransaction(new Executable() {			
			@Override
			public void execute(Session session) {
				entity.setState(state.getCode());
				session.saveOrUpdate(entity);
			}
		});
		fireCreatedOrUpdated(entity);
	}	
	
	public List<T> listActive() {
		return listByState(EntityStates.ACTIVE);	
	}
	
	public List<T> listClosed() {
		return listByState(EntityStates.CLOSED);	
	}	

	private List<T> listByState(final EntityStates state) {
		return HibernateUtil.queryInTransaction(new Resultable<List<T>>() {
			@Override
			public List<T> getResult(Session session) {
				Criteria crit = session.createCriteria(clazz);
				crit.add(Restrictions.eq("state", state.getCode()));
				List<T> entities = crit.list();
				
				Collections.sort(entities, new Comparator<T>() {
					@Override
					public int compare(T t1, T t2) {
						return t1.getName().compareToIgnoreCase(t2.getName());
					}
				});
				
				return entities;
			}			
		});
	}	
	
	protected void fireCreatedOrUpdated(T idable) {
		for(ControllerListener<T> listener : listeners) {
			listener.createdOrUpdated(idable);
		}
	}
	
	protected void fireDeleted(T entity) {
		for(ControllerListener<T> listener : listeners) {
			listener.deleted(entity);
		}	
	}

	public Long countAll() {
		return HibernateUtil.queryInTransaction(new Resultable<Long>() {
			@Override
			public Long getResult(Session session) {
				Query query = session.createQuery("select count(*) from " + clazz.getSimpleName());
				return (Long)query.uniqueResult();
			}
		});
	}
	
	public void refresh(final T entity) {
		HibernateUtil.doInTransaction(new Executable() {			
			@Override
			public void execute(Session session) {
				session.load(entity, entity.getId());
			}
		});
	}
}
