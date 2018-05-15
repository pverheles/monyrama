package com.monyrama.preferences;

import java.util.prefs.Preferences;

/**
 * 
 * @author Petro_Verheles
 * 
 * TODO: Description here
 *
 */
public class MyPreferences {
	private static Preferences prefs =  Preferences.userRoot().node("com/monyrama");
	
	public static void save(PrefKeys key, Object value) {
		prefs.put(keyToString(key), value.toString());
	}
	
	public static void remove(PrefKeys key) {
		prefs.remove(keyToString(key));
	}	

	public static int getInteger(PrefKeys key, int def) {
		return prefs.getInt(keyToString(key), def);
	}

	public static long getLong(PrefKeys key, long def) {
		return prefs.getLong(keyToString(key), def);
	}
	
	public static boolean getBoolean(PrefKeys key, boolean def) {
		return prefs.getBoolean(keyToString(key), def);
	}	
	
	public static String getString(PrefKeys key, String def) {
		return prefs.get(keyToString(key), def);
	}
	
	public static String getString(PrefKeys key) {
		return prefs.get(keyToString(key), null);
	}
	
	private static String keyToString(PrefKeys key) {
		return key.toString();
	}
		
}
