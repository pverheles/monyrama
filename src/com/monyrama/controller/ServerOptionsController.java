package com.monyrama.controller;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import com.monyrama.preferences.MyPreferences;
import com.monyrama.preferences.PrefKeys;
import com.monyrama.ui.constants.ServerConstants;

public class ServerOptionsController {
	private static ServerOptionsController instance = new ServerOptionsController();
	
	private ServerOptionsController() {}
	
	public static ServerOptionsController instance() {
		return instance;
	}
	
	public String getServerIP() {
		String serverIP = "";
		try {
		    String os = System.getProperty("os.name").toLowerCase();
			try {
			    if(os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0) {   
			        NetworkInterface ni;	
					ni = NetworkInterface.getByName("wlan0");		
			        Enumeration<InetAddress> ias = ni.getInetAddresses();	
			        InetAddress iaddress = null;
			        if(ias.hasMoreElements()) {
				        do {
				            iaddress = ias.nextElement();
				        } while(!(iaddress instanceof Inet4Address) && ias.hasMoreElements());
			        }
			        
			        if(iaddress != null) {
			        	serverIP = iaddress.getHostAddress();
			        }
			    } else {
			    	serverIP = InetAddress.getLocalHost().getHostAddress(); //for windows and mac
			    }
			} catch (SocketException e) {
				serverIP = "";
			}
									
		} catch (UnknownHostException e) {
			serverIP = "";
		}
		
		return serverIP;
	}
	
	public boolean hasConnection() {
		String ip = getServerIP();
		return !ip.equals("127.0.1.1") && !ip.equals("127.0.0.1") && !ip.equals("");
	}
	
	public int getServerPort() {
		return MyPreferences.getInteger(PrefKeys.SERVER_PORT, ServerConstants.DEFAULT_SERVER_PORT);
	}
	
	public void saveServerPort(int port) {
		MyPreferences.save(PrefKeys.SERVER_PORT, port);
	}
	
	public void saveAutoStart(boolean auto) {
		MyPreferences.save(PrefKeys.SERVER_AUTO_START, auto);
	}
	
	public boolean getAutoStart() {
		return MyPreferences.getBoolean(PrefKeys.SERVER_AUTO_START, false);
	}
}
