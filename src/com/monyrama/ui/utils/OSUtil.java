package com.monyrama.ui.utils;

/**
 * Created by petroverheles on 11/8/16.
 */
public class OSUtil {
    private static String OS = System.getProperty("os.name").toLowerCase();

    public static boolean isMac() {
        return (OS.indexOf("mac") >= 0);
    }

}
