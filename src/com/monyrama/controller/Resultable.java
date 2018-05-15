package com.monyrama.controller;

import org.hibernate.Session;

public interface Resultable<T> {
	public T getResult(Session session);
}
