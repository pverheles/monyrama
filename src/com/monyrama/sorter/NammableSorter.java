package com.monyrama.sorter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.monyrama.entity.BaseEntity;


public class NammableSorter {
	public static void sort(List<? extends BaseEntity> list) {
		Collections.sort(list, new Comparator<BaseEntity>() {
			@Override
			public int compare(BaseEntity o1, BaseEntity o2) {
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});
	}
}
