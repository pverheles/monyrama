package com.monyrama.server;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.util.ajax.JSON;

public class ErrorJson {
	private String message;
	
	public ErrorJson(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		Map<String, Object> errorElement = new HashMap<String, Object>(1);
		errorElement.put("errorText", message);
		
		return JSON.toString(errorElement);
	}
}
