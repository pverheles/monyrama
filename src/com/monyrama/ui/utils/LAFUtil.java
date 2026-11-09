package com.monyrama.ui.utils;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.monyrama.preferences.ThemeEnum;
import com.monyrama.ui.constants.ColorConstants;

import de.muntjak.tinylookandfeel.Theme;
import de.muntjak.tinylookandfeel.TinyLookAndFeel;

public class LAFUtil {

	public static void setUpTheme(ThemeEnum theme, JFrame rootFrame) throws Exception {
		switch (theme) {
		case SYSTEM:
			if(OSUtil.isMac()) {
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			} else {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());	
			}
			break;
		case FLAT_LIGHT:
			UIManager.setLookAndFeel(OSUtil.isMac() ? new FlatMacLightLaf() : new FlatLightLaf());
			break;
		case FLAT_DARK:
			UIManager.setLookAndFeel(OSUtil.isMac() ? new FlatMacDarkLaf() : new FlatDarkLaf());
			break;
		case SILVER:
    		setTinyLAF("Silver");
			break;				
		case NIGHTLY:
    		setTinyLAF("Nightly");
			break;	
		case FOREST:
    		setTinyLAF("Forest");
			break;		
		case GOLDEN:
    		setTinyLAF("Golden");
			break;	
		case PLASTIC:
    		setTinyLAF("Plastic");
			break;	
		case UNICODE:
    		setTinyLAF("Unicode");
			break;				
		default:
			break;
		}
		
		ColorConstants.usePalette(theme == ThemeEnum.FLAT_DARK);
		SwingUtilities.updateComponentTreeUI(rootFrame);
	}

	private static void setTinyLAF(String themeName) throws Exception {			
		Theme.loadTheme(TinyLookAndFeel.class.getResource("/themes/" + themeName + ".theme"));
		UIManager.setLookAndFeel("de.muntjak.tinylookandfeel.TinyLookAndFeel");
	}
}
