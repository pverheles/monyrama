package com.monyrama.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.monyrama.server.GetDataHandler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import com.monyrama.ui.resources.Resources;

class IncomingController extends AbstractHandler {
	private static final int PROTOCOL_VERSION = 2;

	private static final String VERSION_PREFIX = "/v";

	private static IncomingController instance = new IncomingController();
	
	private Map<String, RequestHandler> handlerMap;
	
	private IncomingController() {
		handlerMap = new HashMap<String, RequestHandler>();
		handlerMap.put("/getData", new GetDataHandler());
		handlerMap.put("/sendData", new SendDataHandler());
	}
	
	public static IncomingController getInstance() {
		return instance;
	}
	
	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException, ServletException {
		httpResponse.setContentType("application/json;charset=utf-8");
        baseRequest.setHandled(true);
        
        //Getting client protocol version
        String requestURI = httpRequest.getRequestURI();
        if(!requestURI.startsWith(VERSION_PREFIX)) {
        	httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);     
        	ErrorJson errorJson = new ErrorJson(Resources.getString("server.message.error.cannotprocess"));
        	httpResponse.getWriter().println(errorJson.toString());
        	return;
        } else {
        	String uriWithoutPrefix = requestURI.substring(VERSION_PREFIX.length());
        	String versionStr = uriWithoutPrefix.substring(0, uriWithoutPrefix.indexOf("/"));
        	int clientProtocolVersion = Integer.valueOf(versionStr);
        	if(clientProtocolVersion != PROTOCOL_VERSION) {
        		httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            	ErrorJson errorJson = new ErrorJson(Resources.getString("server.message.error.incompatible"));
            	httpResponse.getWriter().println(errorJson.toString());
            	return;
        	} else {
        		String requestType = uriWithoutPrefix.substring(uriWithoutPrefix.indexOf("/"));
                RequestHandler requestHandler = handlerMap.get(requestType);
                if(requestHandler == null) {
                	httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);        	
                	ErrorJson errorJson = new ErrorJson(Resources.getString("server.message.error.cannotprocess"));
                	httpResponse.getWriter().println(errorJson.toString());
                } else {
                	Response response = requestHandler.handle(httpRequest);
                	httpResponse.setStatus(response.getStatusCode());        	
                	httpResponse.getWriter().println(response.getResponseJSON());
                }
        	}
        }                     
	}

}
