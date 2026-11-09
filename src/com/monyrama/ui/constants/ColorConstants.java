/*
 * ColorConstants.java
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

package com.monyrama.ui.constants;

import java.awt.Color;
import java.awt.SystemColor;

/**
 * Constants of colors used in UI
 * 
 * @author Petro_Verheles
 *
 */
public class ColorConstants {
	public static Color BLOCKED_COLOR;
	public static Color BE_ATTANTIVE_COLOR; 	
	public static final Color ERROR_COLOR = SystemColor.RED;	
	public static Color USED; 	
	public static Color SELECTED_BACKGROUND;
	public static Color SELECTED_FOREGROUND;
	public static final Color SPLASH_TITLE = new Color(0, 0, 200);
	public static final Color ASTERICS_COLOR = Color.RED;
	public static Color MAIN_CURRENCY_COLOR;
	public static final Color ACTIVE_NAVIGATION_TAB_FONT_COLOR = Color.BLUE;
	public static final Color INACTIVE_NAVIGATION_TAB_FONT_COLOR = Color.GRAY;

	static {
		usePalette(false);
	}

	/**
	 * Row highlight and selection colors. Tables read them when painting, so a dark theme
	 * needs dark tints (the light pastels would sit under light text).
	 */
	private static boolean dark;

	/** True when the active theme is a dark one */
	public static boolean isDark() {
		return dark;
	}

	public static void usePalette(boolean dark) {
		ColorConstants.dark = dark;
		if (dark) {
			BLOCKED_COLOR = new Color(72, 72, 72);
			BE_ATTANTIVE_COLOR = new Color(110, 45, 50);
			USED = new Color(40, 80, 50);
			SELECTED_BACKGROUND = new Color(38, 79, 120);
			SELECTED_FOREGROUND = Color.WHITE;
			MAIN_CURRENCY_COLOR = new Color(95, 88, 35);
		} else {
			BLOCKED_COLOR = new Color(220, 220, 220);
			BE_ATTANTIVE_COLOR = new Color(255, 200, 200);
			USED = new Color(192, 251, 192);
			SELECTED_BACKGROUND = new Color(135, 206, 250);
			SELECTED_FOREGROUND = new Color(0, 0, 205);
			MAIN_CURRENCY_COLOR = new Color(255, 255, 153);
		}
	}
}
