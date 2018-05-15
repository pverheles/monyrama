package com.monyrama.ui.tables.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import com.monyrama.entity.BaseEntity;


public abstract class AbstractIdableTableModel<T extends BaseEntity> extends AbstractTableModel {
	protected Map<Long, T> data = new HashMap<Long, T>();
	
	@Override
	public int getRowCount() {		
		return data.size();
	}
	
	public void putItem(T item) {
		data.put(item.getId(), item);
		fireTableDataChanged();
	}
		
	public void removeItem(T item) {
		data.remove(item.getId());
		fireTableDataChanged();
	}
		
	public T getItemById(Long id) {
		return data.get(id);
	}	
	
	/**
	 * Puts all items to the model creating all before this
	 * 
	 * @param items
	 */
	public void reFill(Collection<T> items) {
		data.clear();
		putAllItems(items);
	}

	/**
	 * Puts all items to the model
	 * 
	 * @param items
	 */
	public void putAllItems(Collection<T> items) {
		for(T item : items) {
			data.put(item.getId(), item);			
		}
		fireTableDataChanged();
	}	
		
	public void clear() {
		data.clear();
		fireTableDataChanged();
	}	
	
	public Collection<T> getDataCollection() {
		return Collections.unmodifiableCollection(data.values());
	}
}
