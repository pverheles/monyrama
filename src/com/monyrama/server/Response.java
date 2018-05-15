package com.monyrama.server;

class Response {
	private int statusCode;
	private String responseText;
		
	public Response() {
		super();
	}

	public Response(int statusCode, String responseText) {
		super();
		this.statusCode = statusCode;
		this.responseText = responseText;
	}

	public int getStatusCode() {
		return statusCode;
	}
	
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	public String getResponseJSON() {
		return responseText;
	}
	
	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}	
}
