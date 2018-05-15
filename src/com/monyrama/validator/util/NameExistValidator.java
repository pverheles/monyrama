package com.monyrama.validator.util;

import java.util.Collection;

import com.monyrama.entity.BaseEntity;

public class NameExistValidator {
	private NameExistValidator() {}
	
	public static boolean nameExistsForNew(Collection<? extends BaseEntity> entityCollection, BaseEntity entity) {	
		for (BaseEntity next : entityCollection) {
			if (StringValidator.areEqualEgnoreCase(next.getName(), entity.getName())) {				
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean nameExistsForEdited(Collection<? extends BaseEntity> entityCollection, BaseEntity entity) {	
		for (BaseEntity next : entityCollection) {
			if (!next.getId().equals(entity.getId()) && StringValidator.areEqualEgnoreCase(next.getName(), entity.getName())) {
				return true;
			}
		}
		
		return false;
	}	
}
