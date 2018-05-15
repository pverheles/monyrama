package com.monyrama.ui.resources;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class I18nPropertiesTest {
	//public static final String projectFolder = "D:/projects/personalbudgetvp";
	public static final String projectFolder = "D:/myfolder/projects/personalbudgetvp";
	
	private Pattern GET_STRING_RESOURCE_PATTERN = Pattern.compile("(?<=Resources.getString\\(\")[^)]*(?=\"\\))");
	//TODO: FIX
	//private Pattern GET_STRING_RESOURCE_PATTERN = Pattern.compile("(?<=Resources\\s{0,1000}\\.\\s{0,1000}getString\\(\")[^)]*(?=\"\\))");
	
	
	@Test
	public void i18nPropertiesTest() throws Exception {
		Properties enProperties = new Properties();
		enProperties.load(new FileInputStream(projectFolder + "/i18n/UIResources_en.properties"));
		
		Properties ruProperties = new Properties();
		ruProperties.load(new FileInputStream(projectFolder + "/i18n/UIResources_ru.properties"));
		
		Properties ukProperties = new Properties();
		ukProperties.load(new FileInputStream(projectFolder + "/i18n/UIResources_uk.properties"));
		
		Set<Object> allKeys = new HashSet<Object>();
		allKeys.addAll(enProperties.keySet());
		allKeys.addAll(ruProperties.keySet());
		allKeys.addAll(ukProperties.keySet());
		
		Set<Object> unusedKeys = new HashSet<Object>(allKeys);

		System.out.println("Checking consistency of resource files");
		for(Object key : allKeys) {
			if(!enProperties.containsKey(key)) {
				System.out.println("EN, missisng property: " + key);
			}
			
			if(!ruProperties.containsKey(key)) {
				System.out.println("RU, missisng property: " + key);
			}
			
			if(!ukProperties.containsKey(key)) {
				System.out.println("UK, missisng property: " + key);
			}			
		}
		
		System.out.println("Finish");
		
		//
		Set<String> scannedFilesPaths = FilesLister.filesList(new File(projectFolder), "java");
		
		System.out.println("Checking java files for using keys");
		for (String nextFilePath : scannedFilesPaths) {
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(nextFilePath);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(
					new InputStreamReader(in));
			String strLine;
			// Read File Line By Line
			int lineNumber = 0;
			while ((strLine = br.readLine()) != null) {					
				lineNumber++;
												
				Matcher matcher = GET_STRING_RESOURCE_PATTERN.matcher(strLine);
				while (matcher.find()) {
					String resourceKey = matcher.group();
					if(!allKeys.contains(resourceKey)) {
						System.out.println("UNKNOWN RESOURCE KEY: " + nextFilePath + "(" + lineNumber + ")" + " - " + resourceKey);	
					} else {
						unusedKeys.remove(resourceKey);
					}					
				}
				
			}
						
			// Close the input stream
			in.close();	    		  
	    }
		
		System.out.println("UNUSED KEYS: " + unusedKeys);
		
		System.out.println("Finish");
	}
}
