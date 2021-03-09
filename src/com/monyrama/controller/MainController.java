/*
 * MainController.java
 * 
 * Version: 1.1
 * 
 * Date: 01.04.2009
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

package com.monyrama.controller;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.monyrama.db.enumarations.EntityStates;
import com.monyrama.entity.PCategory;
import com.monyrama.entity.PCurrency;
import com.monyrama.entity.PSetting;
import com.monyrama.preferences.MyPreferences;
import com.monyrama.preferences.PrefKeys;
import com.monyrama.preferences.ThemeEnum;
import com.monyrama.ui.components.MyProgressDialog;
import com.monyrama.ui.dialogs.AuthenticationDialog;
import com.monyrama.ui.dialogs.DataFolderDialog;
import com.monyrama.ui.dialogs.LanguageDialog;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.utils.LAFUtil;
import com.monyrama.ui.view.AccountsTabsPanel;
import com.monyrama.ui.view.ActiveAndClosedTabsPanel;
import com.monyrama.ui.view.ActiveIncomesPanel;
import com.monyrama.ui.view.ClosedIncomesPanel;
import com.monyrama.ui.view.CurrencyPanel;
import com.monyrama.ui.view.ExpensesTabsPanel;
import com.monyrama.ui.view.LendsAndDebtsTabsPanel;
import com.monyrama.ui.view.MainWindow;
import com.monyrama.ui.view.SplashScreenFrame;


/**
 * This class creates all basic controllers and initializes them.
 * Not to frustrate user it shows a splash screen before this
 * operation and closes it after.
 *
 * @author Petro_Verheles
 */
public class MainController {
	private static final int UPDATE_RATES_HOUR_INTERVAL = 1;

	private static MainController instance;
	
	private Map<String, MyProgressDialog> uiblockers = new HashMap<String, MyProgressDialog>();
	
    private MainWindow view;

    public MainWindow getView() {
		return view;
	}

	/**
     * Constructor
     * <p/>
     * Creates an instance of MainWindow - container for all the other views
     */
    private MainController() {
    }
    
    public static MainController instance() {
    	if(instance == null) {
    		instance = new MainController();
    	}
    	return instance;
    }

    /**
     * Creates all basic controllers and initializes them
     */
    public void start() {
    	String languageCode = MyPreferences.getString(PrefKeys.LANGUAGE_CODE);
    	if(languageCode == null) {    		
    		Resources.setLocale(new Locale("en"));    		
    		new LanguageDialog().setVisible(true);	
    	} else {
    		Resources.setLocale(new Locale(languageCode));
    	}
    	
    	String dataBaseFolderPath = MyPreferences.getString(PrefKeys.DATAFOLDER_PATH);
    	if(dataBaseFolderPath == null) {
    		new DataFolderDialog() {
				@Override
				protected void actionOnEscape() {
					System.exit(0);
				}    			
    		}.setVisible(true);
    	}    	   	
    	
        view = new MainWindow(); 
        
    	ThemeEnum theme = ThemeEnum.valueOf(MyPreferences.getString(PrefKeys.THEME, ThemeEnum.SYSTEM.name()));

        try {
			LAFUtil.setUpTheme(theme, view);
		} catch (Exception e1) {
			e1.printStackTrace();
		}          
    	
        //Creating and showing the splash screen
        SplashScreenFrame splashScreen = new SplashScreenFrame();
        splashScreen.setVisible(true);

        splashScreen.setProgress(0);

        String passwordHash = MyPreferences.getString(PrefKeys.PASSWORD_HASH);
        if (passwordHash != null) {
            AuthenticationDialog authenticationDialog = new AuthenticationDialog(passwordHash);
            authenticationDialog.setLocationRelativeTo(null);
            authenticationDialog.setVisible(true);
            if (!authenticationDialog.isAuthenticated()) {
                System.exit(0);
            }
        }

        //Localize some system titles
        localizeSystem();

        splashScreen.setProgress(0);
        
        //Configure database
        HibernateUtil.configureApp();

        // Create default category, if not existing
        CategoryController.instance().getDefaultCategory();
        
        splashScreen.setProgress(10);
        
        view.setCurrenciesPanel(new CurrencyPanel());
        
        splashScreen.setProgress(20);
        
        view.setAccountsPanel(new AccountsTabsPanel());

        splashScreen.setProgress(30);

        view.setIncomesPanel(new ActiveAndClosedTabsPanel(new ActiveIncomesPanel(), new ClosedIncomesPanel()));

        splashScreen.setProgress(40);
        
        view.setExpensesPanel(new ExpensesTabsPanel());

        splashScreen.setProgress(50);
        
        view.setLendsAndDebtsTabsPanel(new LendsAndDebtsTabsPanel());

        splashScreen.setProgress(70);        

        splashScreen.setProgress(80);

        splashScreen.setProgress(85);

        splashScreen.setProgress(90);

        upgradeDBIfNeeded();
        
        scheduleExchangeRateUpdate();

        splashScreen.setProgress(100);

        //Disposing of the splash screen
        splashScreen.dispose();
    	
        //Initializing the main view
        view.init();
              
    }
    
	private void upgradeDBIfNeeded() {		
		List<PSetting> allSettings = SettingController.instance().getAll();
		if(allSettings == null || allSettings.isEmpty()) {
			PSetting versionSetting = new PSetting();
			versionSetting.setName(PSetting.DB_VERSION_KEY);
			versionSetting.setValue("1");
			versionSetting.setLastChangeDate(Calendar.getInstance().getTime());
			SettingController.instance().createOrUpdate(versionSetting);
			
			createDefaultCurrency();
			createSampleCategories();
		}
	}
	
	private void scheduleExchangeRateUpdate() {
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		
        Runnable task = new Runnable() {
            public void run() {
            	//System.out.println("Updating rates");
            	if(ExchangeRatesController.instance().forceUpdateOnline()) {
	            	List<PCurrency> allCurrencies = CurrencyController.instance().getAll();
	            	for(PCurrency currency : allCurrencies) {
	            		if(currency.getStandard() && currency.getUpdateOnline()) {
	            			BigDecimal newRate = ExchangeRatesController.instance().getRate(currency.getCode());
	            			currency.setExchangeRate(newRate);
	            			CurrencyController.instance().createOrUpdate(currency);
	            		}
	            	}
            	}
            }
        };
        
        scheduler.scheduleAtFixedRate(task, 0, UPDATE_RATES_HOUR_INTERVAL, TimeUnit.HOURS);
	}

	public void createUIBlock(final String key, final String title, final String message) {
		SwingUtilities.invokeLater(new Runnable() {			
			@Override
			public void run() {
				final MyProgressDialog myProgressDialog = new MyProgressDialog(MainController.instance().getView(), true);
				myProgressDialog.setTitle(title);
				myProgressDialog.setLabelText(message);
				uiblockers.put(key, myProgressDialog);
				myProgressDialog.showMe();				
			}
		});
	}
	
	public void releaseUIBlock(final String key) {
		SwingUtilities.invokeLater(new Runnable() {			
			@Override
			public void run() {
				MyProgressDialog dialog = uiblockers.get(key);
				if(dialog != null) {
					dialog.hideMe();
					uiblockers.remove(key);
				}
			}
		});
	}    

    private void createSampleCategories() {
        //Create though one category if needed
        if (CategoryController.instance().countAll() == 0) {
			for(String categoryName : Resources.getCommaSeparatedStrings("firststart.categories")) {
				PCategory category = new PCategory();
				category.setId(UniqueID.get());
				category.setName(categoryName);
				category.setLastChangeDate(Calendar.getInstance().getTime());
				category.setState(EntityStates.ACTIVE.getCode());
				CategoryController.instance().createOrUpdate(category);
			}
        }
    }

    private void createDefaultCurrency() {
        //Create though one currency if needed
        if (CurrencyController.instance().countAll() == 0) {
            PCurrency currency = new PCurrency();
            currency.setId(UniqueID.get());
            String defaultCurrencyCode = Resources.getString("firststart.currencycode");
            currency.setCode(defaultCurrencyCode);            
            currency.setName(Resources.getCurrencyName(defaultCurrencyCode));
            currency.setExchangeRate(new BigDecimal(1));
            currency.setUpdateOnline(false);
            currency.setStandard(true);
            currency.setLastChangeDate(Calendar.getInstance().getTime());
            CurrencyController.instance().createOrUpdate(currency);
            
			PSetting mainCurrencySetting = new PSetting();
			mainCurrencySetting.setName(PSetting.MAIN_CURRENCY_KEY);
			mainCurrencySetting.setValue(currency.getCode());
			mainCurrencySetting.setLastChangeDate(Calendar.getInstance().getTime());
			SettingController.instance().createOrUpdate(mainCurrencySetting);
        }
    }

    /**
     * Localizes some system labels
     */
    private void localizeSystem() {
        UIManager.put("OptionPane.yesButtonText", Resources.getString("joptionpane.yes"));
        UIManager.put("OptionPane.noButtonText", Resources.getString("joptionpane.no"));

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
