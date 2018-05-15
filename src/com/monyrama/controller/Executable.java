package com.monyrama.controller;

import org.hibernate.Session;

public interface Executable {
	public void execute(Session session);
}
