package com.monyrama.server;

import com.monyrama.server.Response;

import javax.servlet.http.HttpServletRequest;

interface RequestHandler {
	public Response handle(HttpServletRequest request);
}
