package com.monyrama.controller;

public class UniqueID {
	static long current = System.currentTimeMillis();

	public static synchronized long get() {
		return current++;
	}
}
