; NSIS (Nullsoft Scriptable Install System) installer script
;
; To make installer of GUI execute following steps:
;
; 1) Change file name of destination exe file (it contain version number)
; 2) Install NSIS software
; 3) Make ant build by "ant" command line command
; 4) Into the windows explorer go to produced by ant "deploy" folder
; 5) Right click on this script file and chose "Complile NSIS script"

!include "MUI.nsh"

; The name of the installer
Name "Monyrama"

RequestExecutionLevel user

; The file to write
OutFile "MonyramaInstall_v@VERSION@.exe"

; The default installation directory
InstallDir "$PROGRAMFILES\Monyrama"

; Registry key to check for directory (so if you install again, it will
; overwrite the old one automatically)
InstallDirRegKey HKLM "Software\Monyrama" "Install_Dir"

;--------------------------------

LoadLanguageFile "${NSISDIR}\Contrib\Language files\English.nlf"
LoadLanguageFile "${NSISDIR}\Contrib\Language files\Russian.nlf"
LoadLanguageFile "${NSISDIR}\Contrib\Language files\Ukrainian.nlf"

; Set name using the normal interface (Name command)
LangString Name ${LANG_ENGLISH} "English"
LangString Name ${LANG_RUSSIAN} "Russian"
LangString Name ${LANG_UKRAINIAN} "Ukrainian"

; Pages

Page components
Page directory
Page instfiles

;--------------------------------

  LangString LANGUAGE_CODE_STR ${LANG_ENGLISH} "en"
  LangString LANGUAGE_CODE_STR ${LANG_RUSSIAN} "ru"
  LangString LANGUAGE_CODE_STR ${LANG_UKRAINIAN} "uk"

; The stuff to install
Section "Monyrama"

  SectionIn RO

  ; Set output path to the installation directory.
  SetOutPath $INSTDIR\lib

	File "lib\budgetsystem.jar"
	File "lib\antlr-2.7.6.jar"
	File "lib\commons-collections-3.1.jar"
	File "lib\dom4j-1.6.1.jar"
	File "lib\h2-1.3.172.jar"
	File "lib\hibernate-annotations-3.5.6-Final.jar"
	File "lib\hibernate-commons-annotations-3.2.0.Final.jar"
	File "lib\hibernate-jpa-2.0-api-1.0.1.Final.jar"
	File "lib\hibernate3.jar"
	File "lib\javassist-3.4.GA.jar"
	File "lib\jcommon-1.0.17.jar"
	File "lib\jetty-continuation-8.jar"
	File "lib\jetty-http-8.jar"
	File "lib\jetty-io-8.jar"
	File "lib\jetty-server-8.jar"
	File "lib\jetty-util-8.jar"
	File "lib\jfreechart-1.0.14.jar"
	File "lib\jsr173_1.0_api.jar"
	File "lib\jta-1.1.jar"
	File "lib\log4j-1.2.15.jar"
	File "lib\looks-1.2.2.jar"
	File "lib\resolver.jar"
	File "lib\servlet-api-3.0.jar"
	File "lib\slf4j-api-1.7.2.jar"
	File "lib\slf4j-log4j12-1.7.2.jar"
	File "lib\tinylaf.jar"
	
	

  ; Set output path to the installation directory.
  SetOutPath $INSTDIR

  ; Put file there
  File "releasenotes.txt"
  File "Monyrama.exe"
  File "main.ico"
  
  ; Write application language code that is used by the app
  DeleteRegValue HKCU "Software\JavaSoft\Prefs\com\pbudgetvp" "/L/A/N/G/U/A/G/E_/C/O/D/E"
  WriteRegStr HKCU "Software\JavaSoft\Prefs\com\pbudgetvp" "/L/A/N/G/U/A/G/E_/C/O/D/E" $(LANGUAGE_CODE_STR)  
  
  ; Write the installation path into the registry
  WriteRegStr HKLM "SOFTWARE\Monyrama" "Install_Dir" "$INSTDIR"

  ; Write the uninstall keys for Windows
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\Monyrama" "DisplayName" "Monyrama"
  WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\Monyrama" "UninstallString" '"$INSTDIR\uninstall.exe"'
  WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\Monyrama" "NoModify" 1
  WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\Monyrama" "NoRepair" 1
  WriteUninstaller "uninstall.exe"

SectionEnd

; Optional sections(can be disabled by the user)

LangString STARTMENU_SHORTCUT_STR ${LANG_ENGLISH} "Start Menu Shortcuts"
LangString STARTMENU_SHORTCUT_STR ${LANG_RUSSIAN} "Значки в главном меню"
LangString STARTMENU_SHORTCUT_STR ${LANG_UKRAINIAN} "Значки в головному меню"

Section $(STARTMENU_SHORTCUT_STR)

  CreateDirectory "$SMPROGRAMS\Monyrama"
  CreateShortCut "$SMPROGRAMS\Monyrama\Monyrama.lnk" "$INSTDIR\Monyrama.exe"  "" "$INSTDIR\main.ico"
  CreateShortCut "$SMPROGRAMS\Monyrama\Release notes.lnk" "$INSTDIR\releasenotes.txt"
  CreateShortCut "$SMPROGRAMS\Monyrama\Uninstall.lnk" "$INSTDIR\uninstall.exe" "" "$INSTDIR\uninstall.exe" 0

SectionEnd

LangString DESKTOP_SHORTCUT_STR ${LANG_ENGLISH} "Desktop Shortcut"
LangString DESKTOP_SHORTCUT_STR ${LANG_RUSSIAN} "Ярлык на рабочий стол"
LangString DESKTOP_SHORTCUT_STR ${LANG_UKRAINIAN} "Ярлик на робочий стіл"

Section $(DESKTOP_SHORTCUT_STR)
  CreateShortCut "$DESKTOP\Monyrama.lnk" "$INSTDIR\Monyrama.exe" "" "$INSTDIR\main.ico"
SectionEnd
;--------------------------------

; Uninstaller

Section "Uninstall"

  ; Remove registry keys
  DeleteRegKey HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\Monyrama"
  DeleteRegKey HKLM "SOFTWARE\Monyrama"

  ; Remove files and uninstaller
	Delete $INSTDIR\lib\budgetsystem.jar
	Delete $INSTDIR\lib\antlr-2.7.6.jar
	Delete $INSTDIR\lib\commons-collections-3.1.jar
	Delete $INSTDIR\lib\dom4j-1.6.1.jar
	Delete $INSTDIR\lib\h2-1.3.172.jar
	Delete $INSTDIR\lib\hibernate-annotations-3.5.6-Final.jar
	Delete $INSTDIR\lib\hibernate-commons-annotations-3.2.0.Final.jar
	Delete $INSTDIR\lib\hibernate-jpa-2.0-api-1.0.1.Final.jar
	Delete $INSTDIR\lib\hibernate3.jar
	Delete $INSTDIR\lib\javassist-3.4.GA.jar
	Delete $INSTDIR\lib\jcommon-1.0.17.jar
	Delete $INSTDIR\lib\jetty-continuation-8.jar
	Delete $INSTDIR\lib\jetty-http-8.jar
	Delete $INSTDIR\lib\jetty-io-8.jar
	Delete $INSTDIR\lib\jetty-server-8.jar
	Delete $INSTDIR\lib\jetty-util-8.jar
	Delete $INSTDIR\lib\jfreechart-1.0.14.jar
	Delete $INSTDIR\lib\jsr173_1.0_api.jar
	Delete $INSTDIR\lib\jta-1.1.jar
	Delete $INSTDIR\lib\log4j-1.2.15.jar
	Delete $INSTDIR\lib\looks-1.2.2.jar
	Delete $INSTDIR\lib\resolver.jar
	Delete $INSTDIR\lib\servlet-api-3.0.jar
	Delete $INSTDIR\lib\slf4j-api-1.7.2.jar
	Delete $INSTDIR\lib\slf4j-log4j12-1.7.2.jar
	Delete $INSTDIR\lib\tinylaf.jar
	
	Delete $INSTDIR\releasenotes.txt
	Delete $INSTDIR\Monyrama.exe
	Delete $INSTDIR\main.ico
	Delete $INSTDIR\uninstall.exe
  
  ; Remove log files, if any
  Delete $INSTDIR\*.log

  ; Remove shortcuts, if any
Delete "$SMPROGRAMS\Monyrama\*.*"
Delete "$DESKTOP\Monyrama.lnk"

; Remove directories used
RMDir "$SMPROGRAMS\Monyrama"
RMDir "$INSTDIR\lib"
RMDir "$INSTDIR"

SectionEnd

Function .onInit

	;Language selection dialog

	Push ""
	Push ${LANG_ENGLISH}
	Push English
	Push ${LANG_RUSSIAN}
	Push Русский
	Push ${LANG_UKRAINIAN}
	Push Українська
	Push A ; A means auto count languages
	       ; for the auto count to work the first empty push (Push "") must remain
	LangDLL::LangDialog "Installer Language" "Please select the language"

	Pop $LANGUAGE
	StrCmp $LANGUAGE "cancel" 0 +2
		Abort
		
FunctionEnd