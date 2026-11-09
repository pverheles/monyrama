package com.monyrama.ui.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.plaf.DimensionUIResource;

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
			showTableGrid();
			break;
		case FLAT_DARK:
			UIManager.setLookAndFeel(OSUtil.isMac() ? new FlatMacDarkLaf() : new FlatDarkLaf());
			showTableGrid();
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
		
		Color oldSelectionBackground = ColorConstants.SELECTED_BACKGROUND;
		ColorConstants.usePalette(theme == ThemeEnum.FLAT_DARK);
		SwingUtilities.updateComponentTreeUI(rootFrame);
		updateTableSelectionColors(rootFrame, oldSelectionBackground);
	}

	/**
	 * FlatLaf hides table grid lines by default; show the full grid. These are the current
	 * look and feel's own defaults, so other themes are not affected.
	 */
	private static void showTableGrid() {
		UIDefaults defaults = UIManager.getLookAndFeelDefaults();
		defaults.put("Table.showHorizontalLines", true);
		defaults.put("Table.showVerticalLines", true);
		defaults.put("Table.intercellSpacing", new DimensionUIResource(1, 1));
	}

	/**
	 * Tables get the palette selection colors once, when they are built; repaint them with the new palette.
	 */
	private static void updateTableSelectionColors(Container container, Color oldSelectionBackground) {
		if (container == null) {
			return;
		}
		for (Component component : container.getComponents()) {
			if (component instanceof JTable table && oldSelectionBackground.equals(table.getSelectionBackground())) {
				table.setSelectionBackground(ColorConstants.SELECTED_BACKGROUND);
				table.setSelectionForeground(ColorConstants.SELECTED_FOREGROUND);
			}
			if (component instanceof Container child) {
				updateTableSelectionColors(child, oldSelectionBackground);
			}
		}
	}

	private static void setTinyLAF(String themeName) throws Exception {			
		Theme.loadTheme(TinyLookAndFeel.class.getResource("/themes/" + themeName + ".theme"));
		UIManager.setLookAndFeel("de.muntjak.tinylookandfeel.TinyLookAndFeel");
	}
}
