package com.monyrama.server;

import com.monyrama.server.MobileDataListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by petroverheles on 9/23/16.
 */
public class MobileDataManager {
    private static List<MobileDataListener> mobileDataListeners = new ArrayList<MobileDataListener>();

    public static void addMobileDataListener(MobileDataListener mobileDataListener) {
        mobileDataListeners.add(mobileDataListener);
    }

    static void fireMobileDataSaved() {
        for(MobileDataListener listener : mobileDataListeners) {
            listener.mobileDataSaved();
        }
    }
}
