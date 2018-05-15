package com.monyrama.ui.components;

import java.util.HashSet;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;

public class StringSortedHashComboboxModel<T> extends DefaultComboBoxModel {
	private Set<T> elements = new HashSet<T>();
	
	public void addElement(Object object) {
		if(elements.contains(object)) {
			if(getSelectedItem() != null && getSelectedItem().equals(object)) {
				updateElement(object);
				setSelectedItem(object);
			} else {
				updateElement(object);
			}
			
			return;
		}
		
		elements.add((T)object);
		int size = getSize();
		for (int i = 0; i < size; i++) {
			if (getElementAt(i).toString().compareTo(object.toString()) > 0) {
				super.insertElementAt(object, i);
				return;
			}
		}
		super.addElement(object);
	}

	private void updateElement(Object object) {
		removeElement(object);
		addElement(object);
	}

	@Override
	public void insertElementAt(Object anObject, int index) {
		addElement(anObject);
	}

	@Override
	public void removeElement(Object anObject) {
		elements.remove(anObject);
		super.removeElement(anObject);
	}
}
