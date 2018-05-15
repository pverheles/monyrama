package com.monyrama.ui.components;

import javax.swing.JLabel;

import com.monyrama.ui.constants.ColorConstants;

public class ComponentsHelper {
	public static JLabel createAstericsLabel() {
		JLabel astericsLabelForCode = new JLabel("*");
		astericsLabelForCode.setForeground(ColorConstants.ASTERICS_COLOR);
		return astericsLabelForCode;
	}
}
