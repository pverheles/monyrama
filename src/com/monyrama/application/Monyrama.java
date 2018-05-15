package com.monyrama.application;

import com.monyrama.controller.MainController;
import com.monyrama.ui.components.calculator.PBVPCalculator;
import com.monyrama.ui.resources.Resources;

/**
 * Starts the application with Locale as a parameter
 * 
 * @author Petro_Verheles
 */
public class Monyrama {

	/**
	 * @param args
	 *            - language code accordingly to ISO 639-1. See
	 *            http://www.loc.gov/standards/iso639-2/php/code_list.php
	 */
	public static void main(String[] args) {
		
		// Accelerating calc appearance in future
		PBVPCalculator calc = new PBVPCalculator("", "", false, false);
		calc.dispose();

		Resources.initSupportedLocales();

		// Create the application
		MainController mainController = MainController.instance();

		// Start the application
		mainController.start();
	}
}
