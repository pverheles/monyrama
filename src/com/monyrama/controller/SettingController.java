package com.monyrama.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.monyrama.entity.PSetting;

public class SettingController extends AbstractController<PSetting> {

	private Map<String, PSetting> keyValueMap;
	
	private SettingController(Class<PSetting> clazz) {
		super(clazz);
	}

	private static SettingController instance;	
	
	public static SettingController instance() {
		if(instance == null) {
			instance = new SettingController(PSetting.class);
		}
		return instance;
	}
	
	public String getSettingValue(String key) {
		updateMap();
		return keyValueMap.get(key).getValue();
	}

	public void createOrUpdateSetting(String key, String value) {
		updateMap();
		PSetting setting = keyValueMap.get(key);
		if(setting == null) {
			setting = new PSetting();
			setting.setName(key);
			keyValueMap.put(key, setting);
		}
		setting.setValue(value);
		createOrUpdate(setting);		
	}	
	
	private void updateMap() {
		if(keyValueMap == null) {
			List<PSetting> allSettings = getAll();
			keyValueMap = new HashMap<String, PSetting>(allSettings.size());
			for(PSetting setting : allSettings) {
				keyValueMap.put(setting.getName(), setting);
			}
		}
	}
}
