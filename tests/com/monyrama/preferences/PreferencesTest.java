package com.monyrama.preferences;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import org.junit.Test;

public class PreferencesTest {
	@Test
	public void listPrefs() throws BackingStoreException {
		Preferences prefs = Preferences.userRoot().node("com/pbudgetvp");
		
		for(String key : prefs.keys()) {
			System.out.println(key + " = " + prefs.get(key, ""));
		}
	}
	
//	@Test
//	public void removeAllPrefs() throws BackingStoreException {
//		Preferences prefs = Preferences.userRoot().node("com/pbudgetvp");
//		for(String key : prefs.keys()) {
//			prefs.remove(key);
//		}	
//	}
	
	@Test
	public void removeOnePref() {
		String key = "MAIN_WINDOW_WIDTH";
		Preferences prefs = Preferences.userRoot().node("com/pbudgetvp");
		prefs.remove(key);
	}

}
