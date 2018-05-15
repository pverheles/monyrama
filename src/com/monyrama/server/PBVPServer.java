package com.monyrama.server;

import org.eclipse.jetty.server.Server;

import com.monyrama.controller.ServerOptionsController;

public class PBVPServer {

	private static PBVPServer instance = new PBVPServer();
	
	private Server server;
	
	private PBVPServer() {
		createServer();
	}
	
	public static PBVPServer getServer() {
		return instance;
	}
	
	public synchronized void start() throws Exception {
		server.start();
	}
	
	public synchronized void stop() throws Exception {
		server.stop();
	}
	
	public synchronized void restartWithNewPort() throws Exception {
		server.stop();
		createServer();
		server.start();
	}
	
	public synchronized boolean isRunning() {
		return server.isRunning();
	}

	private void createServer() {
		server = new Server(ServerOptionsController.instance().getServerPort());
		server.setHandler(IncomingController.getInstance());
	}
}
