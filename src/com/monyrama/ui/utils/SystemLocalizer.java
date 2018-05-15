 /*
 * 
 * SystemLocalizer.java
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

package com.monyrama.ui.utils;

import javax.swing.UIManager;

import com.monyrama.ui.resources.Resources;



public class SystemLocalizer {
	
    private volatile static SystemLocalizer instance;
    
    private SystemLocalizer() {}
 
    /**
     * 
     * @return - a singleton instance of this object
     */
    public static SystemLocalizer getInstance() {
        if (instance == null) {
            synchronized (SystemLocalizer.class) {
                if (instance == null) {
                    instance = new SystemLocalizer();
                }
            }
        }
        return instance;
    }
    
    public void localizeFileChooser() {		
		UIManager.put("FileChooser.lookInLabelText", Resources.getString("FileChooser.lookInLabelText"));
		UIManager.put("FileChooser.cancelButtonText", Resources.getString("FileChooser.cancelButtonText"));
		UIManager.put("FileChooser.cancelButtonToolTipText", Resources.getString("FileChooser.cancelButtonToolTipText"));
		UIManager.put("FileChooser.openButtonText", Resources.getString("FileChooser.openButtonText"));
		UIManager.put("FileChooser.openButtonToolTipText", Resources.getString("FileChooser.openButtonToolTipText"));
		UIManager.put("FileChooser.filesOfTypeLabelText", Resources.getString("FileChooser.filesOfTypeLabelText"));
		UIManager.put("FileChooser.fileNameLabelText", Resources.getString("FileChooser.fileNameLabelText"));
		UIManager.put("FileChooser.listViewButtonToolTipText", Resources.getString("FileChooser.listViewButtonToolTipText"));
		UIManager.put("FileChooser.listViewButtonAccessibleName", Resources.getString("FileChooser.listViewButtonAccessibleName"));
		UIManager.put("FileChooser.detailsViewButtonToolTipText", Resources.getString("FileChooser.detailsViewButtonToolTipText"));
		UIManager.put("FileChooser.detailsViewButtonAccessibleName", Resources.getString("FileChooser.detailsViewButtonAccessibleName"));
		UIManager.put("FileChooser.upFolderToolTipText", Resources.getString("FileChooser.upFolderToolTipText"));
		UIManager.put("FileChooser.upFolderAccessibleName", Resources.getString("FileChooser.upFolderAccessibleName"));
		UIManager.put("FileChooser.homeFolderToolTipText", Resources.getString("FileChooser.homeFolderToolTipText"));
		UIManager.put("FileChooser.homeFolderAccessibleName", Resources.getString("FileChooser.homeFolderAccessibleName"));
		UIManager.put("FileChooser.fileNameHeaderText", Resources.getString("FileChooser.fileNameHeaderText"));
		UIManager.put("FileChooser.fileSizeHeaderText", Resources.getString("FileChooser.fileSizeHeaderText"));
		UIManager.put("FileChooser.fileTypeHeaderText", Resources.getString("FileChooser.fileTypeHeaderText"));
		UIManager.put("FileChooser.fileDateHeaderText", Resources.getString("FileChooser.fileDateHeaderText"));
		UIManager.put("FileChooser.fileAttrHeaderText", Resources.getString("FileChooser.fileAttrHeaderText"));
		UIManager.put("FileChooser.openDialogTitleText", Resources.getString("FileChooser.openDialogTitleText"));
		UIManager.put("FileChooser.newFolderToolTipText", Resources.getString("FileChooser.newFolderToolTipText"));
    }
}
