package com.monyrama.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.hibernate.Session;

import com.monyrama.db.enumarations.EntityStates;
import com.monyrama.entity.BaseEntity;

abstract class AbstractController<T extends BaseEntity> {
	private Class<T> clazz;
	
	protected AbstractController(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	protected List<ControllerListener<T>> listeners = new ArrayList<ControllerListener<T>>();
	
	public List<T> getAll() {
		return HibernateUtil.queryInTransaction(new Resultable<List<T>>() {
			@Override
			public List<T> getResult(Session session) {
				return session.createSelectionQuery("from " + clazz.getName(), clazz).list();
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
				HibernateUtil.saveOrUpdate(session, entity);
			}
		});
		fireCreatedOrUpdated(entity);
	}
	
	public T getById(final Long id) {
		return HibernateUtil.queryInTransaction(new Resultable<T>() {
			@Override
			public T getResult(Session session) {
				return session.find(clazz, id);
			}			
		});
	}

	public void delete(final T idable) {
		HibernateUtil.doInTransaction(new Executable() {			
			@Override
			public void execute(Session session) {
				HibernateUtil.delete(session, idable);
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
				HibernateUtil.saveOrUpdate(session, entity);
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
				List<T> entities = session.createSelectionQuery("from " + clazz.getName() + " e where e.state = :state", clazz)
						.setParameter("state", state.getCode())
						.list();
				
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
				return session.createSelectionQuery("select count(*) from " + clazz.getName(), Long.class).uniqueResult();
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
