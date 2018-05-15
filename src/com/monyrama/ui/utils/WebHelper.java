 /*
 * 
 * WebHelper.java
 * 
 * Version: 1.0
 * 
 * Date: 06.05.2009
 * 
 * Copyright (c) 2009 Petro Verheles.
 * e-mail: vergeles.petiaaa@gmail.com
 * ICQ: 372-831-939
 * Skype: petro.vergeles
 * 
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Petro Verheles
 * ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Petro Verheles. 
 */

package com.monyrama.ui.utils;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebHelper {
	/**
	 * Opens URI
	 * 
	 * @param uri - the URI to open
	 */
    public static void openURI(URI uri) {
    	openURI(uri.toString());
    }
    
    
	/**
	 * Opens URI
	 * 
	 * @param uri - the URI to open
	 */
    public static void openURI(String url) {
    	String os = System.getProperty("os.name").toLowerCase();
    	Runtime rt = Runtime.getRuntime();
    	
		try {
			if (os.indexOf("win") >= 0) {
				// this doesn't support showing urls in the form of
				// "page.html#nameLink"
				// rt.exec( "rundll32 url.dll,FileProtocolHandler " + url);

				if (Desktop.isDesktopSupported()) {
					Desktop desktop = Desktop.getDesktop();
					try {
						desktop.browse(new URI(url));
					} catch (IOException e) {
						// TODO: error handling
					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					// TODO: error handling
				}

			} else if (os.indexOf("mac") >= 0) {

				rt.exec("open " + url);

			} else if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0) {

				// Do a best guess on unix until we get a platform independent
				// way
				// Build a list of browsers to try, in this order.
				String[] browsers = { "epiphany", "firefox", "mozilla",
						"konqueror", "netscape", "opera", "links", "lynx" };

				// Build a command string which looks like
				// "browser1 "url" || browser2 "url" ||..."
				StringBuffer cmd = new StringBuffer();
				for (int i = 0; i < browsers.length; i++)
					cmd.append((i == 0 ? "" : " || ") + browsers[i] + " \""
							+ url + "\" ");

				rt.exec(new String[] { "sh", "-c", cmd.toString() });

			} else {
				return;
			}
		} catch (Exception e) {
			return;
		}
		
		return;
	}
}
