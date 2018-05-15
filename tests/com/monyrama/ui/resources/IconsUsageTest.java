package com.monyrama.ui.resources;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.monyrama.ui.resources.FilesLister;
import org.junit.Test;

public class IconsUsageTest {
	public static final String projectFolder = "D:/projects/personalbudgetvp";
	//public static final String projectFolder = "D:/myfolder/projects/personalbudgetvp/BudgetSystem";
	
	private Pattern GET_ICON_RESOURCE_PATTERN = Pattern.compile("(?<=Resources.getIcon\\(\")[^)]*(?=\"\\))");
		
	@Test
	public void iconUsageTest() throws Exception {
		Set<String> imagesNames = FilesLister.filesShortNamesList(new File(projectFolder + "/src/com/pbudgetvp/bs/ui/resources/icons"));
		Set<Object> unusedImages = new HashSet<Object>(imagesNames);
		
		Set<String> scannedFilesPaths = FilesLister.filesList(new File(projectFolder), "java");
		
		System.out.println("Checking java files for using images");
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
												
				Matcher matcher = GET_ICON_RESOURCE_PATTERN.matcher(strLine);
				while (matcher.find()) {
					String imageName = matcher.group();
					if(!imagesNames.contains(imageName)) {
						System.out.println("UNKNOWN IMAGE: " + nextFilePath + "(" + lineNumber + ")" + " - " + imageName);	
					} else {
						unusedImages.remove(imageName);
					}					
				}
				
			}
						
			// Close the input stream
			in.close();	    		  
	    }
		
		System.out.println("UNUSED IMAGES: " + unusedImages);
		
		System.out.println("Finish");
	}
}
