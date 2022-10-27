package com.monyrama.ui.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.apple.eawt.Application;
import com.monyrama.controller.HibernateUtil;
import com.monyrama.controller.ServerOptionsController;
import com.monyrama.preferences.MyPreferences;
import com.monyrama.preferences.PrefKeys;
import com.monyrama.preferences.ThemeEnum;
import com.monyrama.server.PBVPServer;
import com.monyrama.ui.components.calculator.PBVPCalculator;
import com.monyrama.ui.constants.ColorConstants;
import com.monyrama.ui.constants.DimensionConstants;
import com.monyrama.ui.constants.FontConstants;
import com.monyrama.ui.dialogs.AboutDialog;
import com.monyrama.ui.dialogs.DataFolderDialog;
import com.monyrama.ui.dialogs.PasswordDialog;
import com.monyrama.ui.dialogs.ServerOptionsDialog;
import com.monyrama.ui.resources.Resources;
import com.monyrama.ui.utils.LAFUtil;
import com.monyrama.ui.utils.MyDialogs;
import com.monyrama.ui.utils.OSUtil;
import com.monyrama.ui.utils.WebHelper;


/**
 * Main Window of the application
 * 
 * @author Petro_Verheles
 *
 */
public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;	
	
	private enum TabIndex {
		CURRENCIES_TAB_INDEX, ACCOUNTS_TAB_INDEX, INCOMES_TAB_INDEX, EXPENSES_TAB_INDEX, LENDS_AND_DEBTS_TAB_INDEX
	}
	
	private GeneralPanel currenciesPanel;
	private GeneralPanel accountsPanel;
	private GeneralPanel incomesPanel;
	private GeneralPanel expensesPanel;
	private GeneralPanel lendsAndDebtsTabsPanel;
	
	private final Image MAIN_ICON = getToolkit().getImage(getClass().getResource("images/main.png"));
	
	private Action exitAction;
	private Action showAboutDialogAction;
	private Action calculatorAction;
	private Action passwordAction;
	private Action openUpdatesActiion;
	private Action openAndroidMBAction;
	private Action openWelcomeAction;
	private Action openFacebookAction;
	private Action startServerAction;
	private Action stopServerAction;
	private Action openServerOptionsActions;
	private Action dataFolderAction;

	private ConnectionMonitor connectionMonitor;

	private JTabbedPane navigationTabbedPane;

	/**
	 * Create the frame
	 */
	public MainWindow() {
		super(Resources.getString("title"));
		setIconImage(MAIN_ICON);
		if(OSUtil.isMac()) {
			Application.getApplication().setDockIconImage(MAIN_ICON);
		}
		createActions();
//		Application.getApplication().setAboutHandler(new AboutHandler() {
//			@Override
//			public void handleAbout(AppEvent.AboutEvent aboutEvent) {
//				showAboutDialogAction.actionPerformed(null);
//			}
//		});
	}

	private void createActions() {
		showAboutDialogAction = new ShowAboutDialogAction();
		exitAction = new ExitAction();
		calculatorAction = new CalculatorAction();
		passwordAction = new PasswordAction();
		openAndroidMBAction = new OpenAndroidMBAction();
		openUpdatesActiion = new OpenUpdatesAction();
		openWelcomeAction = new OpenWelcomePanelAction();
		openFacebookAction = new OpenFacebookAction();
		startServerAction = new StartServerAction();
		stopServerAction = new StopServerAction();
		openServerOptionsActions = new OpenServerOptionsAction();
		dataFolderAction = new OpenDatabaseFolderDialogAction();
	}
	
	/**
	 * Paints the main window
	 *
	 */
	public void init() {	
		
		buildMenuBar();
		
		getContentPane().setLayout(new BorderLayout());
		
		Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
		setSize(dim.width, dim.height - 200);		
		setExtendedState(JFrame.MAXIMIZED_BOTH);	
		setLocation(0, 0);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		getContentPane().add(StatusBar.instance(), BorderLayout.SOUTH);
		
		navigationTabbedPane = new JTabbedPane(SwingConstants.LEFT);
		
		addNavigationTab(Resources.getString("navigation.currencies"), Resources.getIcon("currency25x25.png"), TabIndex.CURRENCIES_TAB_INDEX, currenciesPanel);
		addNavigationTab(Resources.getString("navigation.accounts"), Resources.getIcon("wallet25x25.png"), TabIndex.ACCOUNTS_TAB_INDEX, accountsPanel);
		addNavigationTab(Resources.getString("navigation.incomes"), Resources.getIcon("incomes25x25.png"), TabIndex.INCOMES_TAB_INDEX, incomesPanel);
		addNavigationTab(Resources.getString("navigation.expenses"), Resources.getIcon("expenses25x25.png"), TabIndex.EXPENSES_TAB_INDEX, expensesPanel);
		addNavigationTab(Resources.getString("navigation.lendsanddebts"), Resources.getIcon("debts_and_lends_25x25.png"), TabIndex.LENDS_AND_DEBTS_TAB_INDEX, lendsAndDebtsTabsPanel);
		
		getContentPane().add(navigationTabbedPane, BorderLayout.CENTER);	
		
		navigationTabbedPane.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				int selectedIndex = navigationTabbedPane.getSelectedIndex();
				JLabel selectedTab = (JLabel)navigationTabbedPane.getTabComponentAt(selectedIndex);
				selectedTab.setFont(FontConstants.NAVIGATION_TAB_FONT_ACTIVE);
				//selectedTab.setForeground(Color.BLUE);
				
				for(int i = 0; i < navigationTabbedPane.getTabCount(); i++) {
					if(i != selectedIndex) {
						JLabel inactiveTab = (JLabel)navigationTabbedPane.getTabComponentAt(i);
						inactiveTab.setFont(FontConstants.NAVIGATION_TAB_FONT_INACTIVE);
						inactiveTab.setForeground(ColorConstants.INACTIVE_NAVIGATION_TAB_FONT_COLOR);
					}
				}
				
				if(selectedIndex == TabIndex.CURRENCIES_TAB_INDEX.ordinal()) {
					currenciesPanel.initOnVisible();
				} else if(selectedIndex == TabIndex.ACCOUNTS_TAB_INDEX.ordinal()) {
					accountsPanel.initOnVisible();
				} else if(selectedIndex == TabIndex.INCOMES_TAB_INDEX.ordinal()) {
					incomesPanel.initOnVisible();
				} else if(selectedIndex == TabIndex.EXPENSES_TAB_INDEX.ordinal()) {
					expensesPanel.initOnVisible();
				} else if(selectedIndex == TabIndex.LENDS_AND_DEBTS_TAB_INDEX.ordinal()) {
					lendsAndDebtsTabsPanel.initOnVisible();
				}
			}
		});		
					
		setVisible(true);
		
		if(MyPreferences.getBoolean(PrefKeys.SHOW_WELCOME_SCREEN, true)) {
			openWelcomeAction.actionPerformed(null);
		}
		
		navigationTabbedPane.setSelectedIndex(TabIndex.ACCOUNTS_TAB_INDEX.ordinal());
		
		addWindowClosingListener();
		
		if(ServerOptionsController.instance().getAutoStart()) {
			try {
				PBVPServer.getServer().start();
			} catch (Exception e) {
				MyDialogs.showErrorDialog(MainWindow.this, Resources.getString("dialogs.errors.faildToStartServer"));
			}
		}
		
		checkServerState();
	}

	private void addNavigationTab(String title, Icon icon, TabIndex tabIndex, GeneralPanel panel) {
		JLabel currenciesTabLabel = new JLabel(title, icon, SwingConstants.LEFT);
		currenciesTabLabel.setFont(FontConstants.NAVIGATION_TAB_FONT_INACTIVE);
		currenciesTabLabel.setForeground(ColorConstants.INACTIVE_NAVIGATION_TAB_FONT_COLOR);			
		currenciesTabLabel.setPreferredSize(DimensionConstants.NAVIGATION_TAB_DIMENSION);		
		navigationTabbedPane.addTab(title, panel);
		navigationTabbedPane.setTabComponentAt(tabIndex.ordinal(), currenciesTabLabel);
	}
	
	private void buildMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorderPainted(true);
		//menuBar.setBorder(BorderFactory.createEtchedBorder());
		//Localize
		JMenu navigationMenu = new JMenu(Resources.getString("navigation"));
		JMenu toolsMenu = new JMenu(Resources.getString("tools"));
		JMenu serverMenu = new JMenu(Resources.getString("server"));
		JMenu optionsMenu = new JMenu(Resources.getString("options"));		
		JMenu helpMenu = new JMenu(Resources.getString("help"));					
		
		navigationMenu.add(exitAction);
		
		toolsMenu.add(calculatorAction);
		
		serverMenu.add(startServerAction);
		serverMenu.add(stopServerAction);
		serverMenu.addSeparator();
		serverMenu.add(openServerOptionsActions);
						
		Icon tickIcon = Resources.getIcon("tick.png");
		ThemeEnum themePref = ThemeEnum.valueOf(MyPreferences.getString(PrefKeys.THEME, ThemeEnum.SYSTEM.name()));
		
		JMenu appearanceMenu = new JMenu(Resources.getString("options.appearance"));
		optionsMenu.add(appearanceMenu);
		JMenuItem systemItem = new JMenuItem();
		ChangeAppearanceAction systemAppearanceAction = new ChangeAppearanceAction(Resources.getString("options.appearance.system"), ThemeEnum.SYSTEM, appearanceMenu, systemItem);
		systemItem.setAction(systemAppearanceAction);
		systemItem.setIcon(themePref.equals(systemAppearanceAction.getTheme()) ? tickIcon : null);
		appearanceMenu.add(systemItem);
		
		JMenuItem plasticItem = new JMenuItem();		
		ChangeAppearanceAction plasticAppearanceAction = new ChangeAppearanceAction(Resources.getString("options.appearance.plastic"), ThemeEnum.PLASTIC, appearanceMenu, plasticItem);
		plasticItem.setAction(plasticAppearanceAction);
		plasticItem.setIcon(themePref.equals(plasticAppearanceAction.getTheme()) ? tickIcon : null);
		appearanceMenu.add(plasticItem);			
		
		JMenuItem goldenItem = new JMenuItem();		
		ChangeAppearanceAction goldenAppearanceAction = new ChangeAppearanceAction(Resources.getString("options.appearance.golden"), ThemeEnum.GOLDEN, appearanceMenu, goldenItem);
		goldenItem.setAction(goldenAppearanceAction);
		goldenItem.setIcon(themePref.equals(goldenAppearanceAction.getTheme()) ? tickIcon : null);
		appearanceMenu.add(goldenItem);		
		
		JMenuItem forestItem = new JMenuItem();		
		ChangeAppearanceAction forestAppearanceAction = new ChangeAppearanceAction(Resources.getString("options.appearance.forest"), ThemeEnum.FOREST, appearanceMenu, forestItem);
		forestItem.setAction(forestAppearanceAction);
		forestItem.setIcon(themePref.equals(forestAppearanceAction.getTheme()) ? tickIcon : null);
		appearanceMenu.add(forestItem);	
		
		JMenuItem silverItem = new JMenuItem();		
		ChangeAppearanceAction silverAppearanceAction = new ChangeAppearanceAction(Resources.getString("options.appearance.silver"), ThemeEnum.SILVER, appearanceMenu, silverItem);
		silverItem.setAction(silverAppearanceAction);
		silverItem.setIcon(themePref.equals(silverAppearanceAction.getTheme()) ? tickIcon : null);
		appearanceMenu.add(silverItem);		
		
		JMenuItem nightlyItem = new JMenuItem();		
		ChangeAppearanceAction nightlyAppearanceAction = new ChangeAppearanceAction(Resources.getString("options.appearance.nightly"), ThemeEnum.NIGHTLY, appearanceMenu, nightlyItem);
		nightlyItem.setAction(nightlyAppearanceAction);
		nightlyItem.setIcon(themePref.equals(nightlyAppearanceAction.getTheme()) ? tickIcon : null);
		appearanceMenu.add(nightlyItem);				
		
		JMenuItem unicodeItem = new JMenuItem();		
		ChangeAppearanceAction unicodeAppearanceAction = new ChangeAppearanceAction(Resources.getString("options.appearance.unicode"), ThemeEnum.UNICODE, appearanceMenu, unicodeItem);
		unicodeItem.setAction(unicodeAppearanceAction);
		unicodeItem.setIcon(themePref.equals(unicodeAppearanceAction.getTheme()) ? tickIcon : null);
		appearanceMenu.add(unicodeItem);		
		
		JMenu languageMenu = new JMenu(Resources.getString("language"));			
		optionsMenu.add(languageMenu);
		JMenuItem englishMenu = new JMenuItem(new ChangeLanguageAction(Resources.getString("language.english"), "en"));
		JMenuItem russianMenu = new JMenuItem(new ChangeLanguageAction(Resources.getString("language.russian"), "ru"));
		JMenuItem ukrainianMenu = new JMenuItem(new ChangeLanguageAction(Resources.getString("language.ukrainian"), "uk"));		
		if(Resources.getLocale().getLanguage().equals("en")) {
			englishMenu.setIcon(tickIcon);
		} else if(Resources.getLocale().getLanguage().equals("ru")) {
			russianMenu.setIcon(tickIcon);
		} else if(Resources.getLocale().getLanguage().equals("uk")) {
			ukrainianMenu.setIcon(tickIcon);
		}
		languageMenu.add(englishMenu);
		languageMenu.add(russianMenu);
		languageMenu.add(ukrainianMenu);
		optionsMenu.addSeparator();
		optionsMenu.add(passwordAction);
		optionsMenu.add(dataFolderAction);
		
		helpMenu.add(openUpdatesActiion);
		helpMenu.add(openAndroidMBAction);
		helpMenu.addSeparator();
		helpMenu.add(openWelcomeAction);			
		helpMenu.add(showAboutDialogAction);
		helpMenu.addSeparator();
		helpMenu.add(openFacebookAction);
				
		menuBar.add(navigationMenu);
		menuBar.add(toolsMenu);
		menuBar.add(serverMenu);
		menuBar.add(optionsMenu);
		menuBar.add(helpMenu);
		
		setJMenuBar(menuBar);
	}

	public void setIncomesPanel(GeneralPanel incomesPanel) {
		this.incomesPanel = incomesPanel;
	}
		
	public void setCurrenciesPanel(CurrencyPanel currenciesPanel) {
		this.currenciesPanel = currenciesPanel;
	}
	
	public void setAccountsPanel(AccountsTabsPanel accountsPanel) {		
		this.accountsPanel = accountsPanel;
	}
	
	public void setExpensesPanel(ExpensesTabsPanel expensesPanel) {
		this.expensesPanel = expensesPanel;
	}
	
	public void setLendsAndDebtsTabsPanel(LendsAndDebtsTabsPanel lendsAndDebtsTabsPanel) {
		this.lendsAndDebtsTabsPanel = lendsAndDebtsTabsPanel;
	}
	
	//--------------------------------------------- ACTIONS -------------------------------------------------------
	
	private class ShowAboutDialogAction extends AbstractAction {
		public ShowAboutDialogAction() {
			super(Resources.getString("help.about"), Resources.getIcon("about.png"));
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			AboutDialog aboutDialog = new AboutDialog();
			aboutDialog.setLocationRelativeTo(MainWindow.this);
			aboutDialog.setVisible(true);
		}		
	}
		
	private class ExitAction extends AbstractAction {
		public ExitAction() {
			super(Resources.getString("navigation.exit"), Resources.getIcon("exit.png"));
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (MainWindow.this.isActive())
			{
				WindowEvent windowClosing = new WindowEvent(MainWindow.this, WindowEvent.WINDOW_CLOSING);
				MainWindow.this.dispatchEvent(windowClosing);
			}
		}
	}	
	
	private class PasswordAction extends AbstractAction {
		public PasswordAction() {
			super(Resources.getString("options.password") + "...", Resources.getIcon("password.png"));
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			PasswordDialog.openDialog();	
		}	
	}
	
	private class CalculatorAction extends AbstractAction {
		public CalculatorAction() {
			super(Resources.getString("tools.calculator") + "...",  Resources.getIcon("calc.png"));
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			boolean isCommaSeparator = !Resources.isSumDotSeparated();
			final PBVPCalculator calculator = new PBVPCalculator(Resources.getString("tools.calculator"), Resources.getString("calculator.errorMsg"), isCommaSeparator, false);
			calculator.setLocationRelativeTo(MainWindow.this);
	        calculator.setVisible(true);
		}
	}
		
	private class OpenUpdatesAction extends AbstractAction {
		public OpenUpdatesAction() {
			super(Resources.getString("help.updates"),  Resources.getIcon("updates.png"));
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			WebHelper.openURI(Resources.getString("links.updates"));			
		}
	}
	
	private class OpenAndroidMBAction extends AbstractAction {
		public OpenAndroidMBAction() {
			super(Resources.getString("help.androidmb"),  Resources.getIcon("android.png"));
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			WebHelper.openURI(Resources.getString("links.androidmb"));			
		}
	}
	
	private class OpenFacebookAction extends AbstractAction {
		public OpenFacebookAction() {
			super(Resources.getString("help.facebook"),  Resources.getIcon("facebook-small.png"));
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			WebHelper.openURI(Resources.getString("links.facebook"));			
		}
	}	
	
	private class OpenWelcomePanelAction extends AbstractAction {
		public OpenWelcomePanelAction() {
			super(Resources.getString("help.welcome"),  Resources.getIcon("welcome-small.png"));
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {			
			WelcomeDialog welcomeDialog = new WelcomeDialog();
			welcomeDialog.setSize(MainWindow.this.getSize());
			welcomeDialog.setVisible(true);					
		}
	}
	
	private class StartServerAction extends AbstractAction {

		public StartServerAction() {
			super(Resources.getString("server.start"),  Resources.getIcon("start_server.png"));
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {			
			try {
				PBVPServer.getServer().start();
				checkServerState();
			} catch (Exception e) {
				MyDialogs.showErrorDialog(MainWindow.this, Resources.getString("dialogs.errors.faildToStartServer"));
			}
		}		
	}
	
	private class StopServerAction extends AbstractAction {

		public StopServerAction() {
			super(Resources.getString("server.stop"),  Resources.getIcon("stop_server.png"));
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {			
			try {
				PBVPServer.getServer().stop();
				checkServerState();
			} catch (Exception e) {
				MyDialogs.showErrorDialog(MainWindow.this, Resources.getString("dialogs.errors.errorStopServer"));
			}
		}		
	}	
	
	private class OpenServerOptionsAction extends AbstractAction {
		public OpenServerOptionsAction() {
			super(Resources.getString("server.options") + "...", Resources.getIcon("server_options.png"));
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {			
			ServerOptionsDialog.openDialog();
		}
	}
	
	private class OpenDatabaseFolderDialogAction extends AbstractAction {
		public OpenDatabaseFolderDialogAction() {
			super(Resources.getString("options.database") + "...", Resources.getIcon("server_options.png"));
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {			
			new DataFolderDialog().setVisible(true);
			String newDatabaseFolderPath = MyPreferences.getString(PrefKeys.DATAFOLDER_PATH);
			if(!newDatabaseFolderPath.equals(HibernateUtil.getDatabaseFolderPath())) {
				MyDialogs.showInfoDialog(MainWindow.this, Resources.getString("dialogs.info.dbfolder.restartapp"));
				System.exit(0);
			}
		}
	}	
	
	private class ChangeAppearanceAction extends AbstractAction {
		private ThemeEnum theme;
		private JMenu menu;
		private JMenuItem item;		
		
		public ChangeAppearanceAction(String appearanceLabel, ThemeEnum theme, JMenu menu, JMenuItem item) {
			super(appearanceLabel);
						
			this.theme = theme;
			this.menu = menu;
			this.item = item;			
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			ThemeEnum themePref = ThemeEnum.valueOf(MyPreferences.getString(PrefKeys.THEME, ThemeEnum.SYSTEM.name()));
			if(!themePref.equals(theme)) {
				try {
					LAFUtil.setUpTheme(theme, MainWindow.this);
					MyPreferences.save(PrefKeys.THEME, theme.name());
					for(int i = 0; i < menu.getItemCount(); i++) {
						JMenuItem nextItem = menu.getItem(i);
						nextItem.setIcon(null);
					}
					item.setIcon(Resources.getIcon("tick.png"));
				} catch (Exception e) {
					MyDialogs.showErrorDialog(MainWindow.this, "Unknow error");
					e.printStackTrace();
				}
			}
		}
		
		public ThemeEnum getTheme() {
			return theme;
		}
	}	
	
	private class ChangeLanguageAction extends AbstractAction {
		private String languageCode;
		
		public ChangeLanguageAction(String languageLabel, String languageCode) {			
			super(languageLabel);			
			this.languageCode = languageCode;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {	
			if(!languageCode.equals(Resources.getLocale().getLanguage())) {
				int result = MyDialogs.showYesNoDialog(MainWindow.this, Resources.getString("dialogs.question.language.restartapp"));
				if(result == MyDialogs.YES) {
					MyPreferences.save(PrefKeys.LANGUAGE_CODE, languageCode);
					System.exit(0);
				}	
			}
		}
	}
		
	private void addWindowClosingListener() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				//TODO:
			}			
		});
		
	}

	private void checkServerState() {
		startServerAction.setEnabled(!PBVPServer.getServer().isRunning());
		stopServerAction.setEnabled(PBVPServer.getServer().isRunning());
		StatusBar.instance().setServerState(PBVPServer.getServer().isRunning(), ServerOptionsController.instance().getServerIP(),
				ServerOptionsController.instance().getServerPort(), ServerOptionsController.instance().hasConnection());
		
		if(PBVPServer.getServer().isRunning()) {
			connectionMonitor = new ConnectionMonitor();
			connectionMonitor.start();
		} else {
			if(connectionMonitor != null) {
				connectionMonitor.stopIt();
			}
		}
	}
	
	private class ConnectionMonitor extends Thread {

		private boolean run = true;
		
		@Override
		public void run() {
			while(run) {
				try {
					Thread.sleep(3000);
					//System.out.println("checking");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				SwingUtilities.invokeLater(new Runnable() {						
					@Override
					public void run() {
						StatusBar.instance().setServerState(PBVPServer.getServer().isRunning(), ServerOptionsController.instance().getServerIP(),
								ServerOptionsController.instance().getServerPort(), ServerOptionsController.instance().hasConnection());
					}
				});
			}
		}
		
		public void stopIt() {
			run = false;
		}
		
	}
}