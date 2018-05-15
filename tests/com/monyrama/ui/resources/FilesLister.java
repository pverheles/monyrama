package com.monyrama.ui.resources;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class FilesLister {

	public static Set<String> filesList(File rootDirectory, String fileExtension) {
		Set<String> filePaths = new HashSet<String>();
		listFilesInDirectory(rootDirectory, filePaths, fileExtension);
		return filePaths;
	}

	private static void listFilesInDirectory(File directory, Set<String> filePaths, String fileExtension) {
		File filesList[] = directory.listFiles();
		for (int i = 0; i < filesList.length; i++) {
			if (filesList[i].isDirectory()) {
				listFilesInDirectory(filesList[i], filePaths, fileExtension);
			} else if(filesList[i].getName().endsWith("." + fileExtension))	{			
				filePaths.add(filesList[i].getAbsolutePath());
			}
		}
	}
	
	public static Set<String> filesShortNamesList(File rootDirectory) {
		Set<String> filePaths = new HashSet<String>();
		filesShortNamesList(rootDirectory, filePaths);
		return filePaths;
	}

	private static void filesShortNamesList(File directory, Set<String> filePaths) {
		File filesList[] = directory.listFiles();
		for (int i = 0; i < filesList.length; i++) {
			filePaths.add(filesList[i].getName());
		}
	}	
}
