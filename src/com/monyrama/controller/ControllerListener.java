package com.monyrama.controller;

public interface ControllerListener<T> {
	public void createdOrUpdated(T object);
	public void deleted(T object);
}
