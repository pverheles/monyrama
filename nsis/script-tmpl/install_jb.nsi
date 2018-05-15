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
OutFile "MonyramaInstall-v@VERSION@.exe"

; The default installation directory
InstallDir "$PROGRAMFILES\Monyrama"

; Registry key to check for directory (so if you install again, it will
; overwrite the old one automatically)
InstallDirRegKey HKLM "Software\Monyrama" "Install_Dir"

;-------------------------------------------------------

LoadLanguageFile "${NSISDIR}\Contrib\Language files\English.nlf"
LoadLanguageFile "${NSISDIR}\Contrib\Language files\Russian.nlf"
LoadLanguageFile "${NSISDIR}\Contrib\Language files\Ukrainian.nlf"

; Set name using the normal interface (Name command)
LangString Name ${LANG_ENGLISH} "English"
LangString Name ${LANG_RUSSIAN} "Russian"
LangString Name ${LANG_UKRAINIAN} "Ukrainian"

;--------------------------------

; Pages

Page components
Page directory
Page instfiles

;--------------------------------

  LangString LANGUAGE_CODE_STR ${LANG_ENGLISH} "en"
  LangString LANGUAGE_CODE_STR ${LANG_RUSSIAN} "ru"
  LangString LANGUAGE_CODE_STR ${LANG_UKRAINIAN} "uk"

;--------------------------------

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
  
  ;JVM files 
  SetOutPath $INSTDIR\jre6\bin 
  File "jre6\bin\awt.dll"
  File "jre6\bin\axbridge.dll"

  SetOutPath $INSTDIR\jre6\bin\client
  File "jre6\bin\client\classes.jsa"
  File "jre6\bin\client\jvm.dll"
  File "jre6\bin\client\Xusage.txt"

  SetOutPath $INSTDIR\jre6\bin
  File "jre6\bin\cmm.dll"
  File "jre6\bin\dcpr.dll"
  File "jre6\bin\deploy.dll"
  File "jre6\bin\deployJava1.dll"
  File "jre6\bin\dt_shmem.dll"
  File "jre6\bin\dt_socket.dll"
  File "jre6\bin\eula.dll"
  File "jre6\bin\fontmanager.dll"
  File "jre6\bin\hpi.dll"
  File "jre6\bin\hprof.dll"
  File "jre6\bin\instrument.dll"
  File "jre6\bin\ioser12.dll"
  File "jre6\bin\j2pcsc.dll"
  File "jre6\bin\j2pkcs11.dll"
  File "jre6\bin\jaas_nt.dll"
  File "jre6\bin\java-rmi.exe"
  File "jre6\bin\java.dll"
  File "jre6\bin\java.exe"
  File "jre6\bin\javacpl.exe"
  File "jre6\bin\javaw.exe"
  File "jre6\bin\javaws.exe"
  File "jre6\bin\java_crw_demo.dll"
  File "jre6\bin\jawt.dll"
  File "jre6\bin\jbroker.exe"
  File "jre6\bin\JdbcOdbc.dll"
  File "jre6\bin\jdwp.dll"
  File "jre6\bin\jkernel.dll"
  File "jre6\bin\jli.dll"
  File "jre6\bin\jp2iexp.dll"
  File "jre6\bin\jp2launcher.exe"
  File "jre6\bin\jp2native.dll"
  File "jre6\bin\jp2ssv.dll"
  File "jre6\bin\jpeg.dll"
  File "jre6\bin\jpicom.dll"
  File "jre6\bin\jpiexp.dll"
  File "jre6\bin\jpinscp.dll"
  File "jre6\bin\jpioji.dll"
  File "jre6\bin\jpishare.dll"
  File "jre6\bin\jqs.exe"
  File "jre6\bin\jqsnotify.exe"
  File "jre6\bin\jsound.dll"
  File "jre6\bin\jsoundds.dll"
  File "jre6\bin\keytool.exe"
  File "jre6\bin\kinit.exe"
  File "jre6\bin\klist.exe"
  File "jre6\bin\ktab.exe"
  File "jre6\bin\management.dll"
  File "jre6\bin\mlib_image.dll"
  File "jre6\bin\msvcr71.dll"
  File "jre6\bin\msvcrt.dll"
  File "jre6\bin\net.dll"

  SetOutPath $INSTDIR\jre6\bin\new_plugin
  File "jre6\bin\new_plugin\msvcr71.dll"
  File "jre6\bin\new_plugin\npdeployJava1.dll"
  File "jre6\bin\new_plugin\npjp2.dll"

  SetOutPath $INSTDIR\jre6\bin
  File "jre6\bin\nio.dll"
  File "jre6\bin\npdeployJava1.dll"
  File "jre6\bin\npjpi160_20.dll"
  File "jre6\bin\npoji610.dll"
  File "jre6\bin\npt.dll"
  File "jre6\bin\orbd.exe"
  File "jre6\bin\pack200.exe"
  File "jre6\bin\policytool.exe"
  File "jre6\bin\regutils.dll"
  File "jre6\bin\rmi.dll"
  File "jre6\bin\rmid.exe"
  File "jre6\bin\rmiregistry.exe"
  File "jre6\bin\servertool.exe"
  File "jre6\bin\splashscreen.dll"
  File "jre6\bin\ssv.dll"
  File "jre6\bin\ssvagent.exe"
  File "jre6\bin\sunmscapi.dll"
  File "jre6\bin\tnameserv.exe"
  File "jre6\bin\unicows.dll"
  File "jre6\bin\unpack.dll"
  File "jre6\bin\unpack200.exe"
  File "jre6\bin\verify.dll"
  File "jre6\bin\w2k_lsa_auth.dll"
  File "jre6\bin\wsdetect.dll"
  File "jre6\bin\zip.dll"

  SetOutPath $INSTDIR\jre6
  File "jre6\COPYRIGHT"

  SetOutPath $INSTDIR\jre6\lib\audio
  File "jre6\lib\audio\soundbank.gm"

  SetOutPath $INSTDIR\jre6\lib
  File "jre6\lib\calendars.properties"
  File "jre6\lib\charsets.jar"
  File "jre6\lib\classlist"

  SetOutPath $INSTDIR\jre6\lib\cmm
  File "jre6\lib\cmm\CIEXYZ.pf"
  File "jre6\lib\cmm\GRAY.pf"
  File "jre6\lib\cmm\LINEAR_RGB.pf"
  File "jre6\lib\cmm\PYCC.pf"
  File "jre6\lib\cmm\sRGB.pf"

  SetOutPath $INSTDIR\jre6\lib
  File "jre6\lib\content-types.properties"

  SetOutPath $INSTDIR\jre6\lib\deploy
  File "jre6\lib\deploy\ffjcext.zip"

  SetOutPath $INSTDIR\jre6\lib\deploy\jqs\ff\chrome\content
  File "jre6\lib\deploy\jqs\ff\chrome\content\overlay.js"
  File "jre6\lib\deploy\jqs\ff\chrome\content\overlay.xul"

  SetOutPath $INSTDIR\jre6\lib\deploy\jqs\ff
  File "jre6\lib\deploy\jqs\ff\chrome.manifest"
  File "jre6\lib\deploy\jqs\ff\install.rdf"

  SetOutPath $INSTDIR\jre6\lib\deploy\jqs\ie
  File "jre6\lib\deploy\jqs\ie\jqs_plugin.dll"

  SetOutPath $INSTDIR\jre6\lib\deploy\jqs
  File "jre6\lib\deploy\jqs\jqs.conf"
  File "jre6\lib\deploy\jqs\jqsmessages.properties"

  SetOutPath $INSTDIR\jre6\lib\deploy
  File "jre6\lib\deploy\lzma.dll"
  File "jre6\lib\deploy\messages.properties"
  File "jre6\lib\deploy\messages_de.properties"
  File "jre6\lib\deploy\messages_es.properties"
  File "jre6\lib\deploy\messages_fr.properties"
  File "jre6\lib\deploy\messages_it.properties"
  File "jre6\lib\deploy\messages_ja.properties"
  File "jre6\lib\deploy\messages_ko.properties"
  File "jre6\lib\deploy\messages_sv.properties"
  File "jre6\lib\deploy\messages_zh_CN.properties"
  File "jre6\lib\deploy\messages_zh_HK.properties"
  File "jre6\lib\deploy\messages_zh_TW.properties"
  File "jre6\lib\deploy\splash.gif"

  SetOutPath $INSTDIR\jre6\lib
  File "jre6\lib\deploy.jar"

  SetOutPath $INSTDIR\jre6\lib\ext
  File "jre6\lib\ext\dnsns.jar"
  File "jre6\lib\ext\localedata.jar"
  File "jre6\lib\ext\meta-index"
  File "jre6\lib\ext\sunjce_provider.jar"
  File "jre6\lib\ext\sunmscapi.jar"
  File "jre6\lib\ext\sunpkcs11.jar"

  SetOutPath $INSTDIR\jre6\lib
  File "jre6\lib\flavormap.properties"
  File "jre6\lib\fontconfig.98.bfc"
  File "jre6\lib\fontconfig.98.properties.src"
  File "jre6\lib\fontconfig.bfc"
  File "jre6\lib\fontconfig.properties.src"

  SetOutPath $INSTDIR\jre6\lib\fonts
  File "jre6\lib\fonts\LucidaBrightDemiBold.ttf"
  File "jre6\lib\fonts\LucidaBrightDemiItalic.ttf"
  File "jre6\lib\fonts\LucidaBrightItalic.ttf"
  File "jre6\lib\fonts\LucidaBrightRegular.ttf"
  File "jre6\lib\fonts\LucidaSansDemiBold.ttf"
  File "jre6\lib\fonts\LucidaSansRegular.ttf"
  File "jre6\lib\fonts\LucidaTypewriterBold.ttf"
  File "jre6\lib\fonts\LucidaTypewriterRegular.ttf"

  SetOutPath $INSTDIR\jre6\lib\i386
  File "jre6\lib\i386\jvm.cfg"

  SetOutPath $INSTDIR\jre6\lib\im
  File "jre6\lib\im\indicim.jar"
  File "jre6\lib\im\thaiim.jar"

  SetOutPath $INSTDIR\jre6\lib\images\cursors
  File "jre6\lib\images\cursors\cursors.properties"
  File "jre6\lib\images\cursors\invalid32x32.gif"
  File "jre6\lib\images\cursors\win32_CopyDrop32x32.gif"
  File "jre6\lib\images\cursors\win32_CopyNoDrop32x32.gif"
  File "jre6\lib\images\cursors\win32_LinkDrop32x32.gif"
  File "jre6\lib\images\cursors\win32_LinkNoDrop32x32.gif"
  File "jre6\lib\images\cursors\win32_MoveDrop32x32.gif"
  File "jre6\lib\images\cursors\win32_MoveNoDrop32x32.gif"

  SetOutPath $INSTDIR\jre6\lib
  File "jre6\lib\javaws.jar"
  File "jre6\lib\jce.jar"
  File "jre6\lib\jsse.jar"
  File "jre6\lib\jvm.hprof.txt"
  File "jre6\lib\logging.properties"

  SetOutPath $INSTDIR\jre6\lib\management
  File "jre6\lib\management\jmxremote.access"
  File "jre6\lib\management\jmxremote.password.template"
  File "jre6\lib\management\management.properties"
  File "jre6\lib\management\snmp.acl.template"

  SetOutPath $INSTDIR\jre6\lib
  File "jre6\lib\management-agent.jar"
  File "jre6\lib\meta-index"
  File "jre6\lib\net.properties"
  File "jre6\lib\plugin.jar"
  File "jre6\lib\psfont.properties.ja"
  File "jre6\lib\psfontj2d.properties"
  File "jre6\lib\resources.jar"
  File "jre6\lib\rt.jar"

  SetOutPath $INSTDIR\jre6\lib\security
  File "jre6\lib\security\blacklist"
  File "jre6\lib\security\cacerts"
  File "jre6\lib\security\java.policy"
  File "jre6\lib\security\java.security"
  File "jre6\lib\security\javaws.policy"
  File "jre6\lib\security\local_policy.jar"
  File "jre6\lib\security\trusted.libraries"
  File "jre6\lib\security\US_export_policy.jar"

  SetOutPath $INSTDIR\jre6\lib\servicetag
  File "jre6\lib\servicetag\jdk_header.png"
  File "jre6\lib\servicetag\registration.xml"

  SetOutPath $INSTDIR\jre6\lib
  File "jre6\lib\sound.properties"
  File "jre6\lib\task.xml"
  File "jre6\lib\task64.xml"
  File "jre6\lib\tzmappings"

  SetOutPath $INSTDIR\jre6\lib\zi\Africa
  File "jre6\lib\zi\Africa\Abidjan"
  File "jre6\lib\zi\Africa\Accra"
  File "jre6\lib\zi\Africa\Addis_Ababa"
  File "jre6\lib\zi\Africa\Algiers"
  File "jre6\lib\zi\Africa\Asmara"
  File "jre6\lib\zi\Africa\Bamako"
  File "jre6\lib\zi\Africa\Bangui"
  File "jre6\lib\zi\Africa\Banjul"
  File "jre6\lib\zi\Africa\Bissau"
  File "jre6\lib\zi\Africa\Blantyre"
  File "jre6\lib\zi\Africa\Brazzaville"
  File "jre6\lib\zi\Africa\Bujumbura"
  File "jre6\lib\zi\Africa\Cairo"
  File "jre6\lib\zi\Africa\Casablanca"
  File "jre6\lib\zi\Africa\Ceuta"
  File "jre6\lib\zi\Africa\Conakry"
  File "jre6\lib\zi\Africa\Dakar"
  File "jre6\lib\zi\Africa\Dar_es_Salaam"
  File "jre6\lib\zi\Africa\Djibouti"
  File "jre6\lib\zi\Africa\Douala"
  File "jre6\lib\zi\Africa\El_Aaiun"
  File "jre6\lib\zi\Africa\Freetown"
  File "jre6\lib\zi\Africa\Gaborone"
  File "jre6\lib\zi\Africa\Harare"
  File "jre6\lib\zi\Africa\Johannesburg"
  File "jre6\lib\zi\Africa\Kampala"
  File "jre6\lib\zi\Africa\Khartoum"
  File "jre6\lib\zi\Africa\Kigali"
  File "jre6\lib\zi\Africa\Kinshasa"
  File "jre6\lib\zi\Africa\Lagos"
  File "jre6\lib\zi\Africa\Libreville"
  File "jre6\lib\zi\Africa\Lome"
  File "jre6\lib\zi\Africa\Luanda"
  File "jre6\lib\zi\Africa\Lubumbashi"
  File "jre6\lib\zi\Africa\Lusaka"
  File "jre6\lib\zi\Africa\Malabo"
  File "jre6\lib\zi\Africa\Maputo"
  File "jre6\lib\zi\Africa\Maseru"
  File "jre6\lib\zi\Africa\Mbabane"
  File "jre6\lib\zi\Africa\Mogadishu"
  File "jre6\lib\zi\Africa\Monrovia"
  File "jre6\lib\zi\Africa\Nairobi"
  File "jre6\lib\zi\Africa\Ndjamena"
  File "jre6\lib\zi\Africa\Niamey"
  File "jre6\lib\zi\Africa\Nouakchott"
  File "jre6\lib\zi\Africa\Ouagadougou"
  File "jre6\lib\zi\Africa\Porto-Novo"
  File "jre6\lib\zi\Africa\Sao_Tome"
  File "jre6\lib\zi\Africa\Tripoli"
  File "jre6\lib\zi\Africa\Tunis"
  File "jre6\lib\zi\Africa\Windhoek"

  SetOutPath $INSTDIR\jre6\lib\zi\America
  File "jre6\lib\zi\America\Adak"
  File "jre6\lib\zi\America\Anchorage"
  File "jre6\lib\zi\America\Anguilla"
  File "jre6\lib\zi\America\Antigua"
  File "jre6\lib\zi\America\Araguaina"

  SetOutPath $INSTDIR\jre6\lib\zi\America\Argentina
  File "jre6\lib\zi\America\Argentina\Buenos_Aires"
  File "jre6\lib\zi\America\Argentina\Catamarca"
  File "jre6\lib\zi\America\Argentina\Cordoba"
  File "jre6\lib\zi\America\Argentina\Jujuy"
  File "jre6\lib\zi\America\Argentina\La_Rioja"
  File "jre6\lib\zi\America\Argentina\Mendoza"
  File "jre6\lib\zi\America\Argentina\Rio_Gallegos"
  File "jre6\lib\zi\America\Argentina\Salta"
  File "jre6\lib\zi\America\Argentina\San_Juan"
  File "jre6\lib\zi\America\Argentina\San_Luis"
  File "jre6\lib\zi\America\Argentina\Tucuman"
  File "jre6\lib\zi\America\Argentina\Ushuaia"

  SetOutPath $INSTDIR\jre6\lib\zi\America
  File "jre6\lib\zi\America\Aruba"
  File "jre6\lib\zi\America\Asuncion"
  File "jre6\lib\zi\America\Atikokan"
  File "jre6\lib\zi\America\Bahia"
  File "jre6\lib\zi\America\Barbados"
  File "jre6\lib\zi\America\Belem"
  File "jre6\lib\zi\America\Belize"
  File "jre6\lib\zi\America\Blanc-Sablon"
  File "jre6\lib\zi\America\Boa_Vista"
  File "jre6\lib\zi\America\Bogota"
  File "jre6\lib\zi\America\Boise"
  File "jre6\lib\zi\America\Cambridge_Bay"
  File "jre6\lib\zi\America\Campo_Grande"
  File "jre6\lib\zi\America\Cancun"
  File "jre6\lib\zi\America\Caracas"
  File "jre6\lib\zi\America\Cayenne"
  File "jre6\lib\zi\America\Cayman"
  File "jre6\lib\zi\America\Chicago"
  File "jre6\lib\zi\America\Chihuahua"
  File "jre6\lib\zi\America\Costa_Rica"
  File "jre6\lib\zi\America\Cuiaba"
  File "jre6\lib\zi\America\Curacao"
  File "jre6\lib\zi\America\Danmarkshavn"
  File "jre6\lib\zi\America\Dawson"
  File "jre6\lib\zi\America\Dawson_Creek"
  File "jre6\lib\zi\America\Denver"
  File "jre6\lib\zi\America\Detroit"
  File "jre6\lib\zi\America\Dominica"
  File "jre6\lib\zi\America\Edmonton"
  File "jre6\lib\zi\America\Eirunepe"
  File "jre6\lib\zi\America\El_Salvador"
  File "jre6\lib\zi\America\Fortaleza"
  File "jre6\lib\zi\America\Glace_Bay"
  File "jre6\lib\zi\America\Godthab"
  File "jre6\lib\zi\America\Goose_Bay"
  File "jre6\lib\zi\America\Grand_Turk"
  File "jre6\lib\zi\America\Grenada"
  File "jre6\lib\zi\America\Guadeloupe"
  File "jre6\lib\zi\America\Guatemala"
  File "jre6\lib\zi\America\Guayaquil"
  File "jre6\lib\zi\America\Guyana"
  File "jre6\lib\zi\America\Halifax"
  File "jre6\lib\zi\America\Havana"
  File "jre6\lib\zi\America\Hermosillo"

  SetOutPath $INSTDIR\jre6\lib\zi\America\Indiana
  File "jre6\lib\zi\America\Indiana\Indianapolis"
  File "jre6\lib\zi\America\Indiana\Knox"
  File "jre6\lib\zi\America\Indiana\Marengo"
  File "jre6\lib\zi\America\Indiana\Petersburg"
  File "jre6\lib\zi\America\Indiana\Tell_City"
  File "jre6\lib\zi\America\Indiana\Vevay"
  File "jre6\lib\zi\America\Indiana\Vincennes"
  File "jre6\lib\zi\America\Indiana\Winamac"

  SetOutPath $INSTDIR\jre6\lib\zi\America
  File "jre6\lib\zi\America\Inuvik"
  File "jre6\lib\zi\America\Iqaluit"
  File "jre6\lib\zi\America\Jamaica"
  File "jre6\lib\zi\America\Juneau"

  SetOutPath $INSTDIR\jre6\lib\zi\America\Kentucky
  File "jre6\lib\zi\America\Kentucky\Louisville"
  File "jre6\lib\zi\America\Kentucky\Monticello"

  SetOutPath $INSTDIR\jre6\lib\zi\America
  File "jre6\lib\zi\America\La_Paz"
  File "jre6\lib\zi\America\Lima"
  File "jre6\lib\zi\America\Los_Angeles"
  File "jre6\lib\zi\America\Maceio"
  File "jre6\lib\zi\America\Managua"
  File "jre6\lib\zi\America\Manaus"
  File "jre6\lib\zi\America\Martinique"
  File "jre6\lib\zi\America\Matamoros"
  File "jre6\lib\zi\America\Mazatlan"
  File "jre6\lib\zi\America\Menominee"
  File "jre6\lib\zi\America\Merida"
  File "jre6\lib\zi\America\Mexico_City"
  File "jre6\lib\zi\America\Miquelon"
  File "jre6\lib\zi\America\Moncton"
  File "jre6\lib\zi\America\Monterrey"
  File "jre6\lib\zi\America\Montevideo"
  File "jre6\lib\zi\America\Montreal"
  File "jre6\lib\zi\America\Montserrat"
  File "jre6\lib\zi\America\Nassau"
  File "jre6\lib\zi\America\New_York"
  File "jre6\lib\zi\America\Nipigon"
  File "jre6\lib\zi\America\Nome"
  File "jre6\lib\zi\America\Noronha"

  SetOutPath $INSTDIR\jre6\lib\zi\America\North_Dakota
  File "jre6\lib\zi\America\North_Dakota\Center"
  File "jre6\lib\zi\America\North_Dakota\New_Salem"

  SetOutPath $INSTDIR\jre6\lib\zi\America
  File "jre6\lib\zi\America\Ojinaga"
  File "jre6\lib\zi\America\Panama"
  File "jre6\lib\zi\America\Pangnirtung"
  File "jre6\lib\zi\America\Paramaribo"
  File "jre6\lib\zi\America\Phoenix"
  File "jre6\lib\zi\America\Port-au-Prince"
  File "jre6\lib\zi\America\Porto_Velho"
  File "jre6\lib\zi\America\Port_of_Spain"
  File "jre6\lib\zi\America\Puerto_Rico"
  File "jre6\lib\zi\America\Rainy_River"
  File "jre6\lib\zi\America\Rankin_Inlet"
  File "jre6\lib\zi\America\Recife"
  File "jre6\lib\zi\America\Regina"
  File "jre6\lib\zi\America\Resolute"
  File "jre6\lib\zi\America\Rio_Branco"
  File "jre6\lib\zi\America\Santarem"
  File "jre6\lib\zi\America\Santa_Isabel"
  File "jre6\lib\zi\America\Santiago"
  File "jre6\lib\zi\America\Santo_Domingo"
  File "jre6\lib\zi\America\Sao_Paulo"
  File "jre6\lib\zi\America\Scoresbysund"
  File "jre6\lib\zi\America\St_Johns"
  File "jre6\lib\zi\America\St_Kitts"
  File "jre6\lib\zi\America\St_Lucia"
  File "jre6\lib\zi\America\St_Thomas"
  File "jre6\lib\zi\America\St_Vincent"
  File "jre6\lib\zi\America\Swift_Current"
  File "jre6\lib\zi\America\Tegucigalpa"
  File "jre6\lib\zi\America\Thule"
  File "jre6\lib\zi\America\Thunder_Bay"
  File "jre6\lib\zi\America\Tijuana"
  File "jre6\lib\zi\America\Toronto"
  File "jre6\lib\zi\America\Tortola"
  File "jre6\lib\zi\America\Vancouver"
  File "jre6\lib\zi\America\Whitehorse"
  File "jre6\lib\zi\America\Winnipeg"
  File "jre6\lib\zi\America\Yakutat"
  File "jre6\lib\zi\America\Yellowknife"

  SetOutPath $INSTDIR\jre6\lib\zi\Antarctica
  File "jre6\lib\zi\Antarctica\Casey"
  File "jre6\lib\zi\Antarctica\Davis"
  File "jre6\lib\zi\Antarctica\DumontDUrville"
  File "jre6\lib\zi\Antarctica\Mawson"
  File "jre6\lib\zi\Antarctica\McMurdo"
  File "jre6\lib\zi\Antarctica\Palmer"
  File "jre6\lib\zi\Antarctica\Rothera"
  File "jre6\lib\zi\Antarctica\Syowa"
  File "jre6\lib\zi\Antarctica\Vostok"

  SetOutPath $INSTDIR\jre6\lib\zi\Asia
  File "jre6\lib\zi\Asia\Aden"
  File "jre6\lib\zi\Asia\Almaty"
  File "jre6\lib\zi\Asia\Amman"
  File "jre6\lib\zi\Asia\Anadyr"
  File "jre6\lib\zi\Asia\Aqtau"
  File "jre6\lib\zi\Asia\Aqtobe"
  File "jre6\lib\zi\Asia\Ashgabat"
  File "jre6\lib\zi\Asia\Baghdad"
  File "jre6\lib\zi\Asia\Bahrain"
  File "jre6\lib\zi\Asia\Baku"
  File "jre6\lib\zi\Asia\Bangkok"
  File "jre6\lib\zi\Asia\Beirut"
  File "jre6\lib\zi\Asia\Bishkek"
  File "jre6\lib\zi\Asia\Brunei"
  File "jre6\lib\zi\Asia\Choibalsan"
  File "jre6\lib\zi\Asia\Chongqing"
  File "jre6\lib\zi\Asia\Colombo"
  File "jre6\lib\zi\Asia\Damascus"
  File "jre6\lib\zi\Asia\Dhaka"
  File "jre6\lib\zi\Asia\Dili"
  File "jre6\lib\zi\Asia\Dubai"
  File "jre6\lib\zi\Asia\Dushanbe"
  File "jre6\lib\zi\Asia\Gaza"
  File "jre6\lib\zi\Asia\Harbin"
  File "jre6\lib\zi\Asia\Hong_Kong"
  File "jre6\lib\zi\Asia\Hovd"
  File "jre6\lib\zi\Asia\Ho_Chi_Minh"
  File "jre6\lib\zi\Asia\Irkutsk"
  File "jre6\lib\zi\Asia\Jakarta"
  File "jre6\lib\zi\Asia\Jayapura"
  File "jre6\lib\zi\Asia\Jerusalem"
  File "jre6\lib\zi\Asia\Kabul"
  File "jre6\lib\zi\Asia\Kamchatka"
  File "jre6\lib\zi\Asia\Karachi"
  File "jre6\lib\zi\Asia\Kashgar"
  File "jre6\lib\zi\Asia\Kathmandu"
  File "jre6\lib\zi\Asia\Kolkata"
  File "jre6\lib\zi\Asia\Krasnoyarsk"
  File "jre6\lib\zi\Asia\Kuala_Lumpur"
  File "jre6\lib\zi\Asia\Kuching"
  File "jre6\lib\zi\Asia\Kuwait"
  File "jre6\lib\zi\Asia\Macau"
  File "jre6\lib\zi\Asia\Magadan"
  File "jre6\lib\zi\Asia\Makassar"
  File "jre6\lib\zi\Asia\Manila"
  File "jre6\lib\zi\Asia\Muscat"
  File "jre6\lib\zi\Asia\Nicosia"
  File "jre6\lib\zi\Asia\Novokuznetsk"
  File "jre6\lib\zi\Asia\Novosibirsk"
  File "jre6\lib\zi\Asia\Omsk"
  File "jre6\lib\zi\Asia\Oral"
  File "jre6\lib\zi\Asia\Phnom_Penh"
  File "jre6\lib\zi\Asia\Pontianak"
  File "jre6\lib\zi\Asia\Pyongyang"
  File "jre6\lib\zi\Asia\Qatar"
  File "jre6\lib\zi\Asia\Qyzylorda"
  File "jre6\lib\zi\Asia\Rangoon"
  File "jre6\lib\zi\Asia\Riyadh"
  File "jre6\lib\zi\Asia\Riyadh87"
  File "jre6\lib\zi\Asia\Riyadh88"
  File "jre6\lib\zi\Asia\Riyadh89"
  File "jre6\lib\zi\Asia\Sakhalin"
  File "jre6\lib\zi\Asia\Samarkand"
  File "jre6\lib\zi\Asia\Seoul"
  File "jre6\lib\zi\Asia\Shanghai"
  File "jre6\lib\zi\Asia\Singapore"
  File "jre6\lib\zi\Asia\Taipei"
  File "jre6\lib\zi\Asia\Tashkent"
  File "jre6\lib\zi\Asia\Tbilisi"
  File "jre6\lib\zi\Asia\Tehran"
  File "jre6\lib\zi\Asia\Thimphu"
  File "jre6\lib\zi\Asia\Tokyo"
  File "jre6\lib\zi\Asia\Ulaanbaatar"
  File "jre6\lib\zi\Asia\Urumqi"
  File "jre6\lib\zi\Asia\Vientiane"
  File "jre6\lib\zi\Asia\Vladivostok"
  File "jre6\lib\zi\Asia\Yakutsk"
  File "jre6\lib\zi\Asia\Yekaterinburg"
  File "jre6\lib\zi\Asia\Yerevan"
  
  SetOutPath $INSTDIR\jre6\lib\zi\Atlantic
  File "jre6\lib\zi\Atlantic\Azores"
  File "jre6\lib\zi\Atlantic\Bermuda"
  File "jre6\lib\zi\Atlantic\Canary"
  File "jre6\lib\zi\Atlantic\Cape_Verde"
  File "jre6\lib\zi\Atlantic\Faroe"
  File "jre6\lib\zi\Atlantic\Madeira"
  File "jre6\lib\zi\Atlantic\Reykjavik"
  File "jre6\lib\zi\Atlantic\South_Georgia"
  File "jre6\lib\zi\Atlantic\Stanley"
  File "jre6\lib\zi\Atlantic\St_Helena"
  
  SetOutPath $INSTDIR\jre6\lib\zi\Australia
  File "jre6\lib\zi\Australia\Adelaide"
  File "jre6\lib\zi\Australia\Brisbane"
  File "jre6\lib\zi\Australia\Broken_Hill"
  File "jre6\lib\zi\Australia\Currie"
  File "jre6\lib\zi\Australia\Darwin"
  File "jre6\lib\zi\Australia\Eucla"
  File "jre6\lib\zi\Australia\Hobart"
  File "jre6\lib\zi\Australia\Lindeman"
  File "jre6\lib\zi\Australia\Lord_Howe"
  File "jre6\lib\zi\Australia\Melbourne"
  File "jre6\lib\zi\Australia\Perth"
  File "jre6\lib\zi\Australia\Sydney"
  
  SetOutPath $INSTDIR\jre6\lib\zi
  File "jre6\lib\zi\CET"
  File "jre6\lib\zi\CST6CDT"
  File "jre6\lib\zi\EET"
  File "jre6\lib\zi\EST"
  File "jre6\lib\zi\EST5EDT"
  
  SetOutPath $INSTDIR\jre6\lib\zi\Etc
  File "jre6\lib\zi\Etc\GMT"
  File "jre6\lib\zi\Etc\GMT+1"
  File "jre6\lib\zi\Etc\GMT+10"
  File "jre6\lib\zi\Etc\GMT+11"
  File "jre6\lib\zi\Etc\GMT+12"
  File "jre6\lib\zi\Etc\GMT+2"
  File "jre6\lib\zi\Etc\GMT+3"
  File "jre6\lib\zi\Etc\GMT+4"
  File "jre6\lib\zi\Etc\GMT+5"
  File "jre6\lib\zi\Etc\GMT+6"
  File "jre6\lib\zi\Etc\GMT+7"
  File "jre6\lib\zi\Etc\GMT+8"
  File "jre6\lib\zi\Etc\GMT+9"
  File "jre6\lib\zi\Etc\GMT-1"
  File "jre6\lib\zi\Etc\GMT-10"
  File "jre6\lib\zi\Etc\GMT-11"
  File "jre6\lib\zi\Etc\GMT-12"
  File "jre6\lib\zi\Etc\GMT-13"
  File "jre6\lib\zi\Etc\GMT-14"
  File "jre6\lib\zi\Etc\GMT-2"
  File "jre6\lib\zi\Etc\GMT-3"
  File "jre6\lib\zi\Etc\GMT-4"
  File "jre6\lib\zi\Etc\GMT-5"
  File "jre6\lib\zi\Etc\GMT-6"
  File "jre6\lib\zi\Etc\GMT-7"
  File "jre6\lib\zi\Etc\GMT-8"
  File "jre6\lib\zi\Etc\GMT-9"
  File "jre6\lib\zi\Etc\UCT"
  File "jre6\lib\zi\Etc\UTC"
  
  SetOutPath $INSTDIR\jre6\lib\zi\Europe
  File "jre6\lib\zi\Europe\Amsterdam"
  File "jre6\lib\zi\Europe\Andorra"
  File "jre6\lib\zi\Europe\Athens"
  File "jre6\lib\zi\Europe\Belgrade"
  File "jre6\lib\zi\Europe\Berlin"
  File "jre6\lib\zi\Europe\Brussels"
  File "jre6\lib\zi\Europe\Bucharest"
  File "jre6\lib\zi\Europe\Budapest"
  File "jre6\lib\zi\Europe\Chisinau"
  File "jre6\lib\zi\Europe\Copenhagen"
  File "jre6\lib\zi\Europe\Dublin"
  File "jre6\lib\zi\Europe\Gibraltar"
  File "jre6\lib\zi\Europe\Helsinki"
  File "jre6\lib\zi\Europe\Istanbul"
  File "jre6\lib\zi\Europe\Kaliningrad"
  File "jre6\lib\zi\Europe\Kiev"
  File "jre6\lib\zi\Europe\Lisbon"
  File "jre6\lib\zi\Europe\London"
  File "jre6\lib\zi\Europe\Luxembourg"
  File "jre6\lib\zi\Europe\Madrid"
  File "jre6\lib\zi\Europe\Malta"
  File "jre6\lib\zi\Europe\Minsk"
  File "jre6\lib\zi\Europe\Monaco"
  File "jre6\lib\zi\Europe\Moscow"
  File "jre6\lib\zi\Europe\Oslo"
  File "jre6\lib\zi\Europe\Paris"
  File "jre6\lib\zi\Europe\Prague"
  File "jre6\lib\zi\Europe\Riga"
  File "jre6\lib\zi\Europe\Rome"
  File "jre6\lib\zi\Europe\Samara"
  File "jre6\lib\zi\Europe\Simferopol"
  File "jre6\lib\zi\Europe\Sofia"
  File "jre6\lib\zi\Europe\Stockholm"
  File "jre6\lib\zi\Europe\Tallinn"
  File "jre6\lib\zi\Europe\Tirane"
  File "jre6\lib\zi\Europe\Uzhgorod"
  File "jre6\lib\zi\Europe\Vaduz"
  File "jre6\lib\zi\Europe\Vienna"
  File "jre6\lib\zi\Europe\Vilnius"
  File "jre6\lib\zi\Europe\Volgograd"
  File "jre6\lib\zi\Europe\Warsaw"
  File "jre6\lib\zi\Europe\Zaporozhye"
  File "jre6\lib\zi\Europe\Zurich"
  
  SetOutPath $INSTDIR\jre6\lib\zi
  File "jre6\lib\zi\GMT"
  File "jre6\lib\zi\HST"
  
  SetOutPath $INSTDIR\jre6\lib\zi\Indian
  File "jre6\lib\zi\Indian\Antananarivo"
  File "jre6\lib\zi\Indian\Chagos"
  File "jre6\lib\zi\Indian\Christmas"
  File "jre6\lib\zi\Indian\Cocos"
  File "jre6\lib\zi\Indian\Comoro"
  File "jre6\lib\zi\Indian\Kerguelen"
  File "jre6\lib\zi\Indian\Mahe"
  File "jre6\lib\zi\Indian\Maldives"
  File "jre6\lib\zi\Indian\Mauritius"
  File "jre6\lib\zi\Indian\Mayotte"
  File "jre6\lib\zi\Indian\Reunion"
  
  SetOutPath $INSTDIR\jre6\lib\zi
  File "jre6\lib\zi\MET"
  File "jre6\lib\zi\MST"
  File "jre6\lib\zi\MST7MDT"
  
  SetOutPath $INSTDIR\jre6\lib\zi\Pacific
  File "jre6\lib\zi\Pacific\Apia"
  File "jre6\lib\zi\Pacific\Auckland"
  File "jre6\lib\zi\Pacific\Chatham"
  File "jre6\lib\zi\Pacific\Easter"
  File "jre6\lib\zi\Pacific\Efate"
  File "jre6\lib\zi\Pacific\Enderbury"
  File "jre6\lib\zi\Pacific\Fakaofo"
  File "jre6\lib\zi\Pacific\Fiji"
  File "jre6\lib\zi\Pacific\Funafuti"
  File "jre6\lib\zi\Pacific\Galapagos"
  File "jre6\lib\zi\Pacific\Gambier"
  File "jre6\lib\zi\Pacific\Guadalcanal"
  File "jre6\lib\zi\Pacific\Guam"
  File "jre6\lib\zi\Pacific\Honolulu"
  File "jre6\lib\zi\Pacific\Johnston"
  File "jre6\lib\zi\Pacific\Kiritimati"
  File "jre6\lib\zi\Pacific\Kosrae"
  File "jre6\lib\zi\Pacific\Kwajalein"
  File "jre6\lib\zi\Pacific\Majuro"
  File "jre6\lib\zi\Pacific\Marquesas"
  File "jre6\lib\zi\Pacific\Midway"
  File "jre6\lib\zi\Pacific\Nauru"
  File "jre6\lib\zi\Pacific\Niue"
  File "jre6\lib\zi\Pacific\Norfolk"
  File "jre6\lib\zi\Pacific\Noumea"
  File "jre6\lib\zi\Pacific\Pago_Pago"
  File "jre6\lib\zi\Pacific\Palau"
  File "jre6\lib\zi\Pacific\Pitcairn"
  File "jre6\lib\zi\Pacific\Ponape"
  File "jre6\lib\zi\Pacific\Port_Moresby"
  File "jre6\lib\zi\Pacific\Rarotonga"
  File "jre6\lib\zi\Pacific\Saipan"
  File "jre6\lib\zi\Pacific\Tahiti"
  File "jre6\lib\zi\Pacific\Tarawa"
  File "jre6\lib\zi\Pacific\Tongatapu"
  File "jre6\lib\zi\Pacific\Truk"
  File "jre6\lib\zi\Pacific\Wake"
  File "jre6\lib\zi\Pacific\Wallis"
  
  SetOutPath $INSTDIR\jre6\lib\zi
  File "jre6\lib\zi\PST8PDT"
  
  SetOutPath $INSTDIR\jre6\lib\zi\SystemV
  File "jre6\lib\zi\SystemV\AST4"
  File "jre6\lib\zi\SystemV\AST4ADT"
  File "jre6\lib\zi\SystemV\CST6"
  File "jre6\lib\zi\SystemV\CST6CDT"
  File "jre6\lib\zi\SystemV\EST5"
  File "jre6\lib\zi\SystemV\EST5EDT"
  File "jre6\lib\zi\SystemV\HST10"
  File "jre6\lib\zi\SystemV\MST7"
  File "jre6\lib\zi\SystemV\MST7MDT"
  File "jre6\lib\zi\SystemV\PST8"
  File "jre6\lib\zi\SystemV\PST8PDT"
  File "jre6\lib\zi\SystemV\YST9"
  File "jre6\lib\zi\SystemV\YST9YDT"
  
  SetOutPath $INSTDIR\jre6\lib\zi
  File "jre6\lib\zi\WET"
  File "jre6\lib\zi\ZoneInfoMappings"
  
  SetOutPath $INSTDIR\jre6
  File "jre6\LICENSE"
  File "jre6\README.txt"
  File "jre6\THIRDPARTYLICENSEREADME.txt"

  ; Put file there
  SetOutPath $INSTDIR
  File "releasenotes.txt"
  File "Monyrama_jb.exe"
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
  CreateShortCut "$SMPROGRAMS\Monyrama\Monyrama.lnk" "$INSTDIR\Monyrama_jb.exe" "" "$INSTDIR\main.ico"
  CreateShortCut "$SMPROGRAMS\Monyrama\Release notes.lnk" "$INSTDIR\releasenotes.txt"
  CreateShortCut "$SMPROGRAMS\Monyrama\Uninstall.lnk" "$INSTDIR\uninstall.exe" "" "$INSTDIR\uninstall.exe" 0

SectionEnd

LangString DESKTOP_SHORTCUT_STR ${LANG_ENGLISH} "Desktop Shortcut"
LangString DESKTOP_SHORTCUT_STR ${LANG_RUSSIAN} "Ярлык на рабочий стол"
LangString DESKTOP_SHORTCUT_STR ${LANG_UKRAINIAN} "Ярлик на робочий стіл"

Section $(DESKTOP_SHORTCUT_STR)
  CreateShortCut "$DESKTOP\Monyrama.lnk" "$INSTDIR\Monyrama_jb.exe" "" "$INSTDIR\main.ico"
SectionEnd
;--------------------------------

; Uninstaller

Section "Uninstall"

  ; Remove registry keys
  DeleteRegKey HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\Monyrama"
  DeleteRegKey HKLM "SOFTWARE\Monyrama"

  ; Remove files and uninstaller
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
	Delete $INSTDIR\Monyrama_jb.exe
	Delete $INSTDIR\main.ico
	Delete $INSTDIR\uninstall.exe

  ;Delete JVM files
  Delete $INSTDIR\jre6\bin\awt.dll
  Delete $INSTDIR\jre6\bin\axbridge.dll
  Delete $INSTDIR\jre6\bin\client\classes.jsa
  Delete $INSTDIR\jre6\bin\client\jvm.dll
  Delete $INSTDIR\jre6\bin\client\Xusage.txt
  Delete $INSTDIR\jre6\bin\cmm.dll
  Delete $INSTDIR\jre6\bin\dcpr.dll
  Delete $INSTDIR\jre6\bin\deploy.dll
  Delete $INSTDIR\jre6\bin\deployJava1.dll
  Delete $INSTDIR\jre6\bin\dt_shmem.dll
  Delete $INSTDIR\jre6\bin\dt_socket.dll
  Delete $INSTDIR\jre6\bin\eula.dll
  Delete $INSTDIR\jre6\bin\fontmanager.dll
  Delete $INSTDIR\jre6\bin\hpi.dll
  Delete $INSTDIR\jre6\bin\hprof.dll
  Delete $INSTDIR\jre6\bin\instrument.dll
  Delete $INSTDIR\jre6\bin\ioser12.dll
  Delete $INSTDIR\jre6\bin\j2pcsc.dll
  Delete $INSTDIR\jre6\bin\j2pkcs11.dll
  Delete $INSTDIR\jre6\bin\jaas_nt.dll
  Delete $INSTDIR\jre6\bin\java-rmi.exe
  Delete $INSTDIR\jre6\bin\java.dll
  Delete $INSTDIR\jre6\bin\java.exe
  Delete $INSTDIR\jre6\bin\javacpl.exe
  Delete $INSTDIR\jre6\bin\javaw.exe
  Delete $INSTDIR\jre6\bin\javaws.exe
  Delete $INSTDIR\jre6\bin\java_crw_demo.dll
  Delete $INSTDIR\jre6\bin\jawt.dll
  Delete $INSTDIR\jre6\bin\jbroker.exe
  Delete $INSTDIR\jre6\bin\JdbcOdbc.dll
  Delete $INSTDIR\jre6\bin\jdwp.dll
  Delete $INSTDIR\jre6\bin\jkernel.dll
  Delete $INSTDIR\jre6\bin\jli.dll
  Delete $INSTDIR\jre6\bin\jp2iexp.dll
  Delete $INSTDIR\jre6\bin\jp2launcher.exe
  Delete $INSTDIR\jre6\bin\jp2native.dll
  Delete $INSTDIR\jre6\bin\jp2ssv.dll
  Delete $INSTDIR\jre6\bin\jpeg.dll
  Delete $INSTDIR\jre6\bin\jpicom.dll
  Delete $INSTDIR\jre6\bin\jpiexp.dll
  Delete $INSTDIR\jre6\bin\jpinscp.dll
  Delete $INSTDIR\jre6\bin\jpioji.dll
  Delete $INSTDIR\jre6\bin\jpishare.dll
  Delete $INSTDIR\jre6\bin\jqs.exe
  Delete $INSTDIR\jre6\bin\jqsnotify.exe
  Delete $INSTDIR\jre6\bin\jsound.dll
  Delete $INSTDIR\jre6\bin\jsoundds.dll
  Delete $INSTDIR\jre6\bin\keytool.exe
  Delete $INSTDIR\jre6\bin\kinit.exe
  Delete $INSTDIR\jre6\bin\klist.exe
  Delete $INSTDIR\jre6\bin\ktab.exe
  Delete $INSTDIR\jre6\bin\management.dll
  Delete $INSTDIR\jre6\bin\mlib_image.dll
  Delete $INSTDIR\jre6\bin\msvcr71.dll
  Delete $INSTDIR\jre6\bin\msvcrt.dll
  Delete $INSTDIR\jre6\bin\net.dll
  Delete $INSTDIR\jre6\bin\new_plugin\msvcr71.dll
  Delete $INSTDIR\jre6\bin\new_plugin\npdeployJava1.dll
  Delete $INSTDIR\jre6\bin\new_plugin\npjp2.dll
  Delete $INSTDIR\jre6\bin\nio.dll
  Delete $INSTDIR\jre6\bin\npdeployJava1.dll
  Delete $INSTDIR\jre6\bin\npjpi160_20.dll
  Delete $INSTDIR\jre6\bin\npoji610.dll
  Delete $INSTDIR\jre6\bin\npt.dll
  Delete $INSTDIR\jre6\bin\orbd.exe
  Delete $INSTDIR\jre6\bin\pack200.exe
  Delete $INSTDIR\jre6\bin\policytool.exe
  Delete $INSTDIR\jre6\bin\regutils.dll
  Delete $INSTDIR\jre6\bin\rmi.dll
  Delete $INSTDIR\jre6\bin\rmid.exe
  Delete $INSTDIR\jre6\bin\rmiregistry.exe
  Delete $INSTDIR\jre6\bin\servertool.exe
  Delete $INSTDIR\jre6\bin\splashscreen.dll
  Delete $INSTDIR\jre6\bin\ssv.dll
  Delete $INSTDIR\jre6\bin\ssvagent.exe
  Delete $INSTDIR\jre6\bin\sunmscapi.dll
  Delete $INSTDIR\jre6\bin\tnameserv.exe
  Delete $INSTDIR\jre6\bin\unicows.dll
  Delete $INSTDIR\jre6\bin\unpack.dll
  Delete $INSTDIR\jre6\bin\unpack200.exe
  Delete $INSTDIR\jre6\bin\verify.dll
  Delete $INSTDIR\jre6\bin\w2k_lsa_auth.dll
  Delete $INSTDIR\jre6\bin\wsdetect.dll
  Delete $INSTDIR\jre6\bin\zip.dll
  Delete $INSTDIR\jre6\COPYRIGHT
  Delete $INSTDIR\jre6\lib\audio\soundbank.gm
  Delete $INSTDIR\jre6\lib\calendars.properties
  Delete $INSTDIR\jre6\lib\charsets.jar
  Delete $INSTDIR\jre6\lib\classlist
  Delete $INSTDIR\jre6\lib\cmm\CIEXYZ.pf
  Delete $INSTDIR\jre6\lib\cmm\GRAY.pf
  Delete $INSTDIR\jre6\lib\cmm\LINEAR_RGB.pf
  Delete $INSTDIR\jre6\lib\cmm\PYCC.pf
  Delete $INSTDIR\jre6\lib\cmm\sRGB.pf
  Delete $INSTDIR\jre6\lib\content-types.properties
  Delete $INSTDIR\jre6\lib\deploy\ffjcext.zip
  Delete $INSTDIR\jre6\lib\deploy\jqs\ff\chrome\content\overlay.js
  Delete $INSTDIR\jre6\lib\deploy\jqs\ff\chrome\content\overlay.xul
  Delete $INSTDIR\jre6\lib\deploy\jqs\ff\chrome.manifest
  Delete $INSTDIR\jre6\lib\deploy\jqs\ff\install.rdf
  Delete $INSTDIR\jre6\lib\deploy\jqs\ie\jqs_plugin.dll
  Delete $INSTDIR\jre6\lib\deploy\jqs\jqs.conf
  Delete $INSTDIR\jre6\lib\deploy\jqs\jqsmessages.properties
  Delete $INSTDIR\jre6\lib\deploy\lzma.dll
  Delete $INSTDIR\jre6\lib\deploy\messages.properties
  Delete $INSTDIR\jre6\lib\deploy\messages_de.properties
  Delete $INSTDIR\jre6\lib\deploy\messages_es.properties
  Delete $INSTDIR\jre6\lib\deploy\messages_fr.properties
  Delete $INSTDIR\jre6\lib\deploy\messages_it.properties
  Delete $INSTDIR\jre6\lib\deploy\messages_ja.properties
  Delete $INSTDIR\jre6\lib\deploy\messages_ko.properties
  Delete $INSTDIR\jre6\lib\deploy\messages_sv.properties
  Delete $INSTDIR\jre6\lib\deploy\messages_zh_CN.properties
  Delete $INSTDIR\jre6\lib\deploy\messages_zh_HK.properties
  Delete $INSTDIR\jre6\lib\deploy\messages_zh_TW.properties
  Delete $INSTDIR\jre6\lib\deploy\splash.gif
  Delete $INSTDIR\jre6\lib\deploy.jar
  Delete $INSTDIR\jre6\lib\ext\dnsns.jar
  Delete $INSTDIR\jre6\lib\ext\localedata.jar
  Delete $INSTDIR\jre6\lib\ext\meta-index
  Delete $INSTDIR\jre6\lib\ext\sunjce_provider.jar
  Delete $INSTDIR\jre6\lib\ext\sunmscapi.jar
  Delete $INSTDIR\jre6\lib\ext\sunpkcs11.jar
  Delete $INSTDIR\jre6\lib\flavormap.properties
  Delete $INSTDIR\jre6\lib\fontconfig.98.bfc
  Delete $INSTDIR\jre6\lib\fontconfig.98.properties.src
  Delete $INSTDIR\jre6\lib\fontconfig.bfc
  Delete $INSTDIR\jre6\lib\fontconfig.properties.src
  Delete $INSTDIR\jre6\lib\fonts\LucidaBrightDemiBold.ttf
  Delete $INSTDIR\jre6\lib\fonts\LucidaBrightDemiItalic.ttf
  Delete $INSTDIR\jre6\lib\fonts\LucidaBrightItalic.ttf
  Delete $INSTDIR\jre6\lib\fonts\LucidaBrightRegular.ttf
  Delete $INSTDIR\jre6\lib\fonts\LucidaSansDemiBold.ttf
  Delete $INSTDIR\jre6\lib\fonts\LucidaSansRegular.ttf
  Delete $INSTDIR\jre6\lib\fonts\LucidaTypewriterBold.ttf
  Delete $INSTDIR\jre6\lib\fonts\LucidaTypewriterRegular.ttf
  Delete $INSTDIR\jre6\lib\i386\jvm.cfg
  Delete $INSTDIR\jre6\lib\im\indicim.jar
  Delete $INSTDIR\jre6\lib\im\thaiim.jar
  Delete $INSTDIR\jre6\lib\images\cursors\cursors.properties
  Delete $INSTDIR\jre6\lib\images\cursors\invalid32x32.gif
  Delete $INSTDIR\jre6\lib\images\cursors\win32_CopyDrop32x32.gif
  Delete $INSTDIR\jre6\lib\images\cursors\win32_CopyNoDrop32x32.gif
  Delete $INSTDIR\jre6\lib\images\cursors\win32_LinkDrop32x32.gif
  Delete $INSTDIR\jre6\lib\images\cursors\win32_LinkNoDrop32x32.gif
  Delete $INSTDIR\jre6\lib\images\cursors\win32_MoveDrop32x32.gif
  Delete $INSTDIR\jre6\lib\images\cursors\win32_MoveNoDrop32x32.gif
  Delete $INSTDIR\jre6\lib\javaws.jar
  Delete $INSTDIR\jre6\lib\jce.jar
  Delete $INSTDIR\jre6\lib\jsse.jar
  Delete $INSTDIR\jre6\lib\jvm.hprof.txt
  Delete $INSTDIR\jre6\lib\logging.properties
  Delete $INSTDIR\jre6\lib\management\jmxremote.access
  Delete $INSTDIR\jre6\lib\management\jmxremote.password.template
  Delete $INSTDIR\jre6\lib\management\management.properties
  Delete $INSTDIR\jre6\lib\management\snmp.acl.template
  Delete $INSTDIR\jre6\lib\management-agent.jar
  Delete $INSTDIR\jre6\lib\meta-index
  Delete $INSTDIR\jre6\lib\net.properties
  Delete $INSTDIR\jre6\lib\plugin.jar
  Delete $INSTDIR\jre6\lib\psfont.properties.ja
  Delete $INSTDIR\jre6\lib\psfontj2d.properties
  Delete $INSTDIR\jre6\lib\resources.jar
  Delete $INSTDIR\jre6\lib\rt.jar
  Delete $INSTDIR\jre6\lib\security\blacklist
  Delete $INSTDIR\jre6\lib\security\cacerts
  Delete $INSTDIR\jre6\lib\security\java.policy
  Delete $INSTDIR\jre6\lib\security\java.security
  Delete $INSTDIR\jre6\lib\security\javaws.policy
  Delete $INSTDIR\jre6\lib\security\local_policy.jar
  Delete $INSTDIR\jre6\lib\security\trusted.libraries
  Delete $INSTDIR\jre6\lib\security\US_export_policy.jar
  Delete $INSTDIR\jre6\lib\servicetag\jdk_header.png
  Delete $INSTDIR\jre6\lib\servicetag\registration.xml
  Delete $INSTDIR\jre6\lib\sound.properties
  Delete $INSTDIR\jre6\lib\task.xml
  Delete $INSTDIR\jre6\lib\task64.xml
  Delete $INSTDIR\jre6\lib\tzmappings
  Delete $INSTDIR\jre6\lib\zi\Africa\Abidjan
  Delete $INSTDIR\jre6\lib\zi\Africa\Accra
  Delete $INSTDIR\jre6\lib\zi\Africa\Addis_Ababa
  Delete $INSTDIR\jre6\lib\zi\Africa\Algiers
  Delete $INSTDIR\jre6\lib\zi\Africa\Asmara
  Delete $INSTDIR\jre6\lib\zi\Africa\Bamako
  Delete $INSTDIR\jre6\lib\zi\Africa\Bangui
  Delete $INSTDIR\jre6\lib\zi\Africa\Banjul
  Delete $INSTDIR\jre6\lib\zi\Africa\Bissau
  Delete $INSTDIR\jre6\lib\zi\Africa\Blantyre
  Delete $INSTDIR\jre6\lib\zi\Africa\Brazzaville
  Delete $INSTDIR\jre6\lib\zi\Africa\Bujumbura
  Delete $INSTDIR\jre6\lib\zi\Africa\Cairo
  Delete $INSTDIR\jre6\lib\zi\Africa\Casablanca
  Delete $INSTDIR\jre6\lib\zi\Africa\Ceuta
  Delete $INSTDIR\jre6\lib\zi\Africa\Conakry
  Delete $INSTDIR\jre6\lib\zi\Africa\Dakar
  Delete $INSTDIR\jre6\lib\zi\Africa\Dar_es_Salaam
  Delete $INSTDIR\jre6\lib\zi\Africa\Djibouti
  Delete $INSTDIR\jre6\lib\zi\Africa\Douala
  Delete $INSTDIR\jre6\lib\zi\Africa\El_Aaiun
  Delete $INSTDIR\jre6\lib\zi\Africa\Freetown
  Delete $INSTDIR\jre6\lib\zi\Africa\Gaborone
  Delete $INSTDIR\jre6\lib\zi\Africa\Harare
  Delete $INSTDIR\jre6\lib\zi\Africa\Johannesburg
  Delete $INSTDIR\jre6\lib\zi\Africa\Kampala
  Delete $INSTDIR\jre6\lib\zi\Africa\Khartoum
  Delete $INSTDIR\jre6\lib\zi\Africa\Kigali
  Delete $INSTDIR\jre6\lib\zi\Africa\Kinshasa
  Delete $INSTDIR\jre6\lib\zi\Africa\Lagos
  Delete $INSTDIR\jre6\lib\zi\Africa\Libreville
  Delete $INSTDIR\jre6\lib\zi\Africa\Lome
  Delete $INSTDIR\jre6\lib\zi\Africa\Luanda
  Delete $INSTDIR\jre6\lib\zi\Africa\Lubumbashi
  Delete $INSTDIR\jre6\lib\zi\Africa\Lusaka
  Delete $INSTDIR\jre6\lib\zi\Africa\Malabo
  Delete $INSTDIR\jre6\lib\zi\Africa\Maputo
  Delete $INSTDIR\jre6\lib\zi\Africa\Maseru
  Delete $INSTDIR\jre6\lib\zi\Africa\Mbabane
  Delete $INSTDIR\jre6\lib\zi\Africa\Mogadishu
  Delete $INSTDIR\jre6\lib\zi\Africa\Monrovia
  Delete $INSTDIR\jre6\lib\zi\Africa\Nairobi
  Delete $INSTDIR\jre6\lib\zi\Africa\Ndjamena
  Delete $INSTDIR\jre6\lib\zi\Africa\Niamey
  Delete $INSTDIR\jre6\lib\zi\Africa\Nouakchott
  Delete $INSTDIR\jre6\lib\zi\Africa\Ouagadougou
  Delete $INSTDIR\jre6\lib\zi\Africa\Porto-Novo
  Delete $INSTDIR\jre6\lib\zi\Africa\Sao_Tome
  Delete $INSTDIR\jre6\lib\zi\Africa\Tripoli
  Delete $INSTDIR\jre6\lib\zi\Africa\Tunis
  Delete $INSTDIR\jre6\lib\zi\Africa\Windhoek
  Delete $INSTDIR\jre6\lib\zi\America\Adak
  Delete $INSTDIR\jre6\lib\zi\America\Anchorage
  Delete $INSTDIR\jre6\lib\zi\America\Anguilla
  Delete $INSTDIR\jre6\lib\zi\America\Antigua
  Delete $INSTDIR\jre6\lib\zi\America\Araguaina
  Delete $INSTDIR\jre6\lib\zi\America\Argentina\Buenos_Aires
  Delete $INSTDIR\jre6\lib\zi\America\Argentina\Catamarca
  Delete $INSTDIR\jre6\lib\zi\America\Argentina\Cordoba
  Delete $INSTDIR\jre6\lib\zi\America\Argentina\Jujuy
  Delete $INSTDIR\jre6\lib\zi\America\Argentina\La_Rioja
  Delete $INSTDIR\jre6\lib\zi\America\Argentina\Mendoza
  Delete $INSTDIR\jre6\lib\zi\America\Argentina\Rio_Gallegos
  Delete $INSTDIR\jre6\lib\zi\America\Argentina\Salta
  Delete $INSTDIR\jre6\lib\zi\America\Argentina\San_Juan
  Delete $INSTDIR\jre6\lib\zi\America\Argentina\San_Luis
  Delete $INSTDIR\jre6\lib\zi\America\Argentina\Tucuman
  Delete $INSTDIR\jre6\lib\zi\America\Argentina\Ushuaia
  Delete $INSTDIR\jre6\lib\zi\America\Aruba
  Delete $INSTDIR\jre6\lib\zi\America\Asuncion
  Delete $INSTDIR\jre6\lib\zi\America\Atikokan
  Delete $INSTDIR\jre6\lib\zi\America\Bahia
  Delete $INSTDIR\jre6\lib\zi\America\Barbados
  Delete $INSTDIR\jre6\lib\zi\America\Belem
  Delete $INSTDIR\jre6\lib\zi\America\Belize
  Delete $INSTDIR\jre6\lib\zi\America\Blanc-Sablon
  Delete $INSTDIR\jre6\lib\zi\America\Boa_Vista
  Delete $INSTDIR\jre6\lib\zi\America\Bogota
  Delete $INSTDIR\jre6\lib\zi\America\Boise
  Delete $INSTDIR\jre6\lib\zi\America\Cambridge_Bay
  Delete $INSTDIR\jre6\lib\zi\America\Campo_Grande
  Delete $INSTDIR\jre6\lib\zi\America\Cancun
  Delete $INSTDIR\jre6\lib\zi\America\Caracas
  Delete $INSTDIR\jre6\lib\zi\America\Cayenne
  Delete $INSTDIR\jre6\lib\zi\America\Cayman
  Delete $INSTDIR\jre6\lib\zi\America\Chicago
  Delete $INSTDIR\jre6\lib\zi\America\Chihuahua
  Delete $INSTDIR\jre6\lib\zi\America\Costa_Rica
  Delete $INSTDIR\jre6\lib\zi\America\Cuiaba
  Delete $INSTDIR\jre6\lib\zi\America\Curacao
  Delete $INSTDIR\jre6\lib\zi\America\Danmarkshavn
  Delete $INSTDIR\jre6\lib\zi\America\Dawson
  Delete $INSTDIR\jre6\lib\zi\America\Dawson_Creek
  Delete $INSTDIR\jre6\lib\zi\America\Denver
  Delete $INSTDIR\jre6\lib\zi\America\Detroit
  Delete $INSTDIR\jre6\lib\zi\America\Dominica
  Delete $INSTDIR\jre6\lib\zi\America\Edmonton
  Delete $INSTDIR\jre6\lib\zi\America\Eirunepe
  Delete $INSTDIR\jre6\lib\zi\America\El_Salvador
  Delete $INSTDIR\jre6\lib\zi\America\Fortaleza
  Delete $INSTDIR\jre6\lib\zi\America\Glace_Bay
  Delete $INSTDIR\jre6\lib\zi\America\Godthab
  Delete $INSTDIR\jre6\lib\zi\America\Goose_Bay
  Delete $INSTDIR\jre6\lib\zi\America\Grand_Turk
  Delete $INSTDIR\jre6\lib\zi\America\Grenada
  Delete $INSTDIR\jre6\lib\zi\America\Guadeloupe
  Delete $INSTDIR\jre6\lib\zi\America\Guatemala
  Delete $INSTDIR\jre6\lib\zi\America\Guayaquil
  Delete $INSTDIR\jre6\lib\zi\America\Guyana
  Delete $INSTDIR\jre6\lib\zi\America\Halifax
  Delete $INSTDIR\jre6\lib\zi\America\Havana
  Delete $INSTDIR\jre6\lib\zi\America\Hermosillo
  Delete $INSTDIR\jre6\lib\zi\America\Indiana\Indianapolis
  Delete $INSTDIR\jre6\lib\zi\America\Indiana\Knox
  Delete $INSTDIR\jre6\lib\zi\America\Indiana\Marengo
  Delete $INSTDIR\jre6\lib\zi\America\Indiana\Petersburg
  Delete $INSTDIR\jre6\lib\zi\America\Indiana\Tell_City
  Delete $INSTDIR\jre6\lib\zi\America\Indiana\Vevay
  Delete $INSTDIR\jre6\lib\zi\America\Indiana\Vincennes
  Delete $INSTDIR\jre6\lib\zi\America\Indiana\Winamac
  Delete $INSTDIR\jre6\lib\zi\America\Inuvik
  Delete $INSTDIR\jre6\lib\zi\America\Iqaluit
  Delete $INSTDIR\jre6\lib\zi\America\Jamaica
  Delete $INSTDIR\jre6\lib\zi\America\Juneau
  Delete $INSTDIR\jre6\lib\zi\America\Kentucky\Louisville
  Delete $INSTDIR\jre6\lib\zi\America\Kentucky\Monticello
  Delete $INSTDIR\jre6\lib\zi\America\La_Paz
  Delete $INSTDIR\jre6\lib\zi\America\Lima
  Delete $INSTDIR\jre6\lib\zi\America\Los_Angeles
  Delete $INSTDIR\jre6\lib\zi\America\Maceio
  Delete $INSTDIR\jre6\lib\zi\America\Managua
  Delete $INSTDIR\jre6\lib\zi\America\Manaus
  Delete $INSTDIR\jre6\lib\zi\America\Martinique
  Delete $INSTDIR\jre6\lib\zi\America\Matamoros
  Delete $INSTDIR\jre6\lib\zi\America\Mazatlan
  Delete $INSTDIR\jre6\lib\zi\America\Menominee
  Delete $INSTDIR\jre6\lib\zi\America\Merida
  Delete $INSTDIR\jre6\lib\zi\America\Mexico_City
  Delete $INSTDIR\jre6\lib\zi\America\Miquelon
  Delete $INSTDIR\jre6\lib\zi\America\Moncton
  Delete $INSTDIR\jre6\lib\zi\America\Monterrey
  Delete $INSTDIR\jre6\lib\zi\America\Montevideo
  Delete $INSTDIR\jre6\lib\zi\America\Montreal
  Delete $INSTDIR\jre6\lib\zi\America\Montserrat
  Delete $INSTDIR\jre6\lib\zi\America\Nassau
  Delete $INSTDIR\jre6\lib\zi\America\New_York
  Delete $INSTDIR\jre6\lib\zi\America\Nipigon
  Delete $INSTDIR\jre6\lib\zi\America\Nome
  Delete $INSTDIR\jre6\lib\zi\America\Noronha
  Delete $INSTDIR\jre6\lib\zi\America\North_Dakota\Center
  Delete $INSTDIR\jre6\lib\zi\America\North_Dakota\New_Salem
  Delete $INSTDIR\jre6\lib\zi\America\Ojinaga
  Delete $INSTDIR\jre6\lib\zi\America\Panama
  Delete $INSTDIR\jre6\lib\zi\America\Pangnirtung
  Delete $INSTDIR\jre6\lib\zi\America\Paramaribo
  Delete $INSTDIR\jre6\lib\zi\America\Phoenix
  Delete $INSTDIR\jre6\lib\zi\America\Port-au-Prince
  Delete $INSTDIR\jre6\lib\zi\America\Porto_Velho
  Delete $INSTDIR\jre6\lib\zi\America\Port_of_Spain
  Delete $INSTDIR\jre6\lib\zi\America\Puerto_Rico
  Delete $INSTDIR\jre6\lib\zi\America\Rainy_River
  Delete $INSTDIR\jre6\lib\zi\America\Rankin_Inlet
  Delete $INSTDIR\jre6\lib\zi\America\Recife
  Delete $INSTDIR\jre6\lib\zi\America\Regina
  Delete $INSTDIR\jre6\lib\zi\America\Resolute
  Delete $INSTDIR\jre6\lib\zi\America\Rio_Branco
  Delete $INSTDIR\jre6\lib\zi\America\Santarem
  Delete $INSTDIR\jre6\lib\zi\America\Santa_Isabel
  Delete $INSTDIR\jre6\lib\zi\America\Santiago
  Delete $INSTDIR\jre6\lib\zi\America\Santo_Domingo
  Delete $INSTDIR\jre6\lib\zi\America\Sao_Paulo
  Delete $INSTDIR\jre6\lib\zi\America\Scoresbysund
  Delete $INSTDIR\jre6\lib\zi\America\St_Johns
  Delete $INSTDIR\jre6\lib\zi\America\St_Kitts
  Delete $INSTDIR\jre6\lib\zi\America\St_Lucia
  Delete $INSTDIR\jre6\lib\zi\America\St_Thomas
  Delete $INSTDIR\jre6\lib\zi\America\St_Vincent
  Delete $INSTDIR\jre6\lib\zi\America\Swift_Current
  Delete $INSTDIR\jre6\lib\zi\America\Tegucigalpa
  Delete $INSTDIR\jre6\lib\zi\America\Thule
  Delete $INSTDIR\jre6\lib\zi\America\Thunder_Bay
  Delete $INSTDIR\jre6\lib\zi\America\Tijuana
  Delete $INSTDIR\jre6\lib\zi\America\Toronto
  Delete $INSTDIR\jre6\lib\zi\America\Tortola
  Delete $INSTDIR\jre6\lib\zi\America\Vancouver
  Delete $INSTDIR\jre6\lib\zi\America\Whitehorse
  Delete $INSTDIR\jre6\lib\zi\America\Winnipeg
  Delete $INSTDIR\jre6\lib\zi\America\Yakutat
  Delete $INSTDIR\jre6\lib\zi\America\Yellowknife
  Delete $INSTDIR\jre6\lib\zi\Antarctica\Casey
  Delete $INSTDIR\jre6\lib\zi\Antarctica\Davis
  Delete $INSTDIR\jre6\lib\zi\Antarctica\DumontDUrville
  Delete $INSTDIR\jre6\lib\zi\Antarctica\Mawson
  Delete $INSTDIR\jre6\lib\zi\Antarctica\McMurdo
  Delete $INSTDIR\jre6\lib\zi\Antarctica\Palmer
  Delete $INSTDIR\jre6\lib\zi\Antarctica\Rothera
  Delete $INSTDIR\jre6\lib\zi\Antarctica\Syowa
  Delete $INSTDIR\jre6\lib\zi\Antarctica\Vostok
  Delete $INSTDIR\jre6\lib\zi\Asia\Aden
  Delete $INSTDIR\jre6\lib\zi\Asia\Almaty
  Delete $INSTDIR\jre6\lib\zi\Asia\Amman
  Delete $INSTDIR\jre6\lib\zi\Asia\Anadyr
  Delete $INSTDIR\jre6\lib\zi\Asia\Aqtau
  Delete $INSTDIR\jre6\lib\zi\Asia\Aqtobe
  Delete $INSTDIR\jre6\lib\zi\Asia\Ashgabat
  Delete $INSTDIR\jre6\lib\zi\Asia\Baghdad
  Delete $INSTDIR\jre6\lib\zi\Asia\Bahrain
  Delete $INSTDIR\jre6\lib\zi\Asia\Baku
  Delete $INSTDIR\jre6\lib\zi\Asia\Bangkok
  Delete $INSTDIR\jre6\lib\zi\Asia\Beirut
  Delete $INSTDIR\jre6\lib\zi\Asia\Bishkek
  Delete $INSTDIR\jre6\lib\zi\Asia\Brunei
  Delete $INSTDIR\jre6\lib\zi\Asia\Choibalsan
  Delete $INSTDIR\jre6\lib\zi\Asia\Chongqing
  Delete $INSTDIR\jre6\lib\zi\Asia\Colombo
  Delete $INSTDIR\jre6\lib\zi\Asia\Damascus
  Delete $INSTDIR\jre6\lib\zi\Asia\Dhaka
  Delete $INSTDIR\jre6\lib\zi\Asia\Dili
  Delete $INSTDIR\jre6\lib\zi\Asia\Dubai
  Delete $INSTDIR\jre6\lib\zi\Asia\Dushanbe
  Delete $INSTDIR\jre6\lib\zi\Asia\Gaza
  Delete $INSTDIR\jre6\lib\zi\Asia\Harbin
  Delete $INSTDIR\jre6\lib\zi\Asia\Hong_Kong
  Delete $INSTDIR\jre6\lib\zi\Asia\Hovd
  Delete $INSTDIR\jre6\lib\zi\Asia\Ho_Chi_Minh
  Delete $INSTDIR\jre6\lib\zi\Asia\Irkutsk
  Delete $INSTDIR\jre6\lib\zi\Asia\Jakarta
  Delete $INSTDIR\jre6\lib\zi\Asia\Jayapura
  Delete $INSTDIR\jre6\lib\zi\Asia\Jerusalem
  Delete $INSTDIR\jre6\lib\zi\Asia\Kabul
  Delete $INSTDIR\jre6\lib\zi\Asia\Kamchatka
  Delete $INSTDIR\jre6\lib\zi\Asia\Karachi
  Delete $INSTDIR\jre6\lib\zi\Asia\Kashgar
  Delete $INSTDIR\jre6\lib\zi\Asia\Kathmandu
  Delete $INSTDIR\jre6\lib\zi\Asia\Kolkata
  Delete $INSTDIR\jre6\lib\zi\Asia\Krasnoyarsk
  Delete $INSTDIR\jre6\lib\zi\Asia\Kuala_Lumpur
  Delete $INSTDIR\jre6\lib\zi\Asia\Kuching
  Delete $INSTDIR\jre6\lib\zi\Asia\Kuwait
  Delete $INSTDIR\jre6\lib\zi\Asia\Macau
  Delete $INSTDIR\jre6\lib\zi\Asia\Magadan
  Delete $INSTDIR\jre6\lib\zi\Asia\Makassar
  Delete $INSTDIR\jre6\lib\zi\Asia\Manila
  Delete $INSTDIR\jre6\lib\zi\Asia\Muscat
  Delete $INSTDIR\jre6\lib\zi\Asia\Nicosia
  Delete $INSTDIR\jre6\lib\zi\Asia\Novokuznetsk
  Delete $INSTDIR\jre6\lib\zi\Asia\Novosibirsk
  Delete $INSTDIR\jre6\lib\zi\Asia\Omsk
  Delete $INSTDIR\jre6\lib\zi\Asia\Oral
  Delete $INSTDIR\jre6\lib\zi\Asia\Phnom_Penh
  Delete $INSTDIR\jre6\lib\zi\Asia\Pontianak
  Delete $INSTDIR\jre6\lib\zi\Asia\Pyongyang
  Delete $INSTDIR\jre6\lib\zi\Asia\Qatar
  Delete $INSTDIR\jre6\lib\zi\Asia\Qyzylorda
  Delete $INSTDIR\jre6\lib\zi\Asia\Rangoon
  Delete $INSTDIR\jre6\lib\zi\Asia\Riyadh
  Delete $INSTDIR\jre6\lib\zi\Asia\Riyadh87
  Delete $INSTDIR\jre6\lib\zi\Asia\Riyadh88
  Delete $INSTDIR\jre6\lib\zi\Asia\Riyadh89
  Delete $INSTDIR\jre6\lib\zi\Asia\Sakhalin
  Delete $INSTDIR\jre6\lib\zi\Asia\Samarkand
  Delete $INSTDIR\jre6\lib\zi\Asia\Seoul
  Delete $INSTDIR\jre6\lib\zi\Asia\Shanghai
  Delete $INSTDIR\jre6\lib\zi\Asia\Singapore
  Delete $INSTDIR\jre6\lib\zi\Asia\Taipei
  Delete $INSTDIR\jre6\lib\zi\Asia\Tashkent
  Delete $INSTDIR\jre6\lib\zi\Asia\Tbilisi
  Delete $INSTDIR\jre6\lib\zi\Asia\Tehran
  Delete $INSTDIR\jre6\lib\zi\Asia\Thimphu
  Delete $INSTDIR\jre6\lib\zi\Asia\Tokyo
  Delete $INSTDIR\jre6\lib\zi\Asia\Ulaanbaatar
  Delete $INSTDIR\jre6\lib\zi\Asia\Urumqi
  Delete $INSTDIR\jre6\lib\zi\Asia\Vientiane
  Delete $INSTDIR\jre6\lib\zi\Asia\Vladivostok
  Delete $INSTDIR\jre6\lib\zi\Asia\Yakutsk
  Delete $INSTDIR\jre6\lib\zi\Asia\Yekaterinburg
  Delete $INSTDIR\jre6\lib\zi\Asia\Yerevan
  Delete $INSTDIR\jre6\lib\zi\Atlantic\Azores
  Delete $INSTDIR\jre6\lib\zi\Atlantic\Bermuda
  Delete $INSTDIR\jre6\lib\zi\Atlantic\Canary
  Delete $INSTDIR\jre6\lib\zi\Atlantic\Cape_Verde
  Delete $INSTDIR\jre6\lib\zi\Atlantic\Faroe
  Delete $INSTDIR\jre6\lib\zi\Atlantic\Madeira
  Delete $INSTDIR\jre6\lib\zi\Atlantic\Reykjavik
  Delete $INSTDIR\jre6\lib\zi\Atlantic\South_Georgia
  Delete $INSTDIR\jre6\lib\zi\Atlantic\Stanley
  Delete $INSTDIR\jre6\lib\zi\Atlantic\St_Helena
  Delete $INSTDIR\jre6\lib\zi\Australia\Adelaide
  Delete $INSTDIR\jre6\lib\zi\Australia\Brisbane
  Delete $INSTDIR\jre6\lib\zi\Australia\Broken_Hill
  Delete $INSTDIR\jre6\lib\zi\Australia\Currie
  Delete $INSTDIR\jre6\lib\zi\Australia\Darwin
  Delete $INSTDIR\jre6\lib\zi\Australia\Eucla
  Delete $INSTDIR\jre6\lib\zi\Australia\Hobart
  Delete $INSTDIR\jre6\lib\zi\Australia\Lindeman
  Delete $INSTDIR\jre6\lib\zi\Australia\Lord_Howe
  Delete $INSTDIR\jre6\lib\zi\Australia\Melbourne
  Delete $INSTDIR\jre6\lib\zi\Australia\Perth
  Delete $INSTDIR\jre6\lib\zi\Australia\Sydney
  Delete $INSTDIR\jre6\lib\zi\CET
  Delete $INSTDIR\jre6\lib\zi\CST6CDT
  Delete $INSTDIR\jre6\lib\zi\EET
  Delete $INSTDIR\jre6\lib\zi\EST
  Delete $INSTDIR\jre6\lib\zi\EST5EDT
  Delete $INSTDIR\jre6\lib\zi\Etc\GMT
  Delete $INSTDIR\jre6\lib\zi\Etc\GMT+1
  Delete $INSTDIR\jre6\lib\zi\Etc\GMT+10
  Delete $INSTDIR\jre6\lib\zi\Etc\GMT+11
  Delete $INSTDIR\jre6\lib\zi\Etc\GMT+12
  Delete $INSTDIR\jre6\lib\zi\Etc\GMT+2
  Delete $INSTDIR\jre6\lib\zi\Etc\GMT+3
  Delete $INSTDIR\jre6\lib\zi\Etc\GMT+4
  Delete $INSTDIR\jre6\lib\zi\Etc\GMT+5
  Delete $INSTDIR\jre6\lib\zi\Etc\GMT+6
  Delete $INSTDIR\jre6\lib\zi\Etc\GMT+7
  Delete $INSTDIR\jre6\lib\zi\Etc\GMT+8
  Delete $INSTDIR\jre6\lib\zi\Etc\GMT+9
  Delete $INSTDIR\jre6\lib\zi\Etc\GMT-1
  Delete $INSTDIR\jre6\lib\zi\Etc\GMT-10
  Delete $INSTDIR\jre6\lib\zi\Etc\GMT-11
  Delete $INSTDIR\jre6\lib\zi\Etc\GMT-12
  Delete $INSTDIR\jre6\lib\zi\Etc\GMT-13
  Delete $INSTDIR\jre6\lib\zi\Etc\GMT-14
  Delete $INSTDIR\jre6\lib\zi\Etc\GMT-2
  Delete $INSTDIR\jre6\lib\zi\Etc\GMT-3
  Delete $INSTDIR\jre6\lib\zi\Etc\GMT-4
  Delete $INSTDIR\jre6\lib\zi\Etc\GMT-5
  Delete $INSTDIR\jre6\lib\zi\Etc\GMT-6
  Delete $INSTDIR\jre6\lib\zi\Etc\GMT-7
  Delete $INSTDIR\jre6\lib\zi\Etc\GMT-8
  Delete $INSTDIR\jre6\lib\zi\Etc\GMT-9
  Delete $INSTDIR\jre6\lib\zi\Etc\UCT
  Delete $INSTDIR\jre6\lib\zi\Etc\UTC
  Delete $INSTDIR\jre6\lib\zi\Europe\Amsterdam
  Delete $INSTDIR\jre6\lib\zi\Europe\Andorra
  Delete $INSTDIR\jre6\lib\zi\Europe\Athens
  Delete $INSTDIR\jre6\lib\zi\Europe\Belgrade
  Delete $INSTDIR\jre6\lib\zi\Europe\Berlin
  Delete $INSTDIR\jre6\lib\zi\Europe\Brussels
  Delete $INSTDIR\jre6\lib\zi\Europe\Bucharest
  Delete $INSTDIR\jre6\lib\zi\Europe\Budapest
  Delete $INSTDIR\jre6\lib\zi\Europe\Chisinau
  Delete $INSTDIR\jre6\lib\zi\Europe\Copenhagen
  Delete $INSTDIR\jre6\lib\zi\Europe\Dublin
  Delete $INSTDIR\jre6\lib\zi\Europe\Gibraltar
  Delete $INSTDIR\jre6\lib\zi\Europe\Helsinki
  Delete $INSTDIR\jre6\lib\zi\Europe\Istanbul
  Delete $INSTDIR\jre6\lib\zi\Europe\Kaliningrad
  Delete $INSTDIR\jre6\lib\zi\Europe\Kiev
  Delete $INSTDIR\jre6\lib\zi\Europe\Lisbon
  Delete $INSTDIR\jre6\lib\zi\Europe\London
  Delete $INSTDIR\jre6\lib\zi\Europe\Luxembourg
  Delete $INSTDIR\jre6\lib\zi\Europe\Madrid
  Delete $INSTDIR\jre6\lib\zi\Europe\Malta
  Delete $INSTDIR\jre6\lib\zi\Europe\Minsk
  Delete $INSTDIR\jre6\lib\zi\Europe\Monaco
  Delete $INSTDIR\jre6\lib\zi\Europe\Moscow
  Delete $INSTDIR\jre6\lib\zi\Europe\Oslo
  Delete $INSTDIR\jre6\lib\zi\Europe\Paris
  Delete $INSTDIR\jre6\lib\zi\Europe\Prague
  Delete $INSTDIR\jre6\lib\zi\Europe\Riga
  Delete $INSTDIR\jre6\lib\zi\Europe\Rome
  Delete $INSTDIR\jre6\lib\zi\Europe\Samara
  Delete $INSTDIR\jre6\lib\zi\Europe\Simferopol
  Delete $INSTDIR\jre6\lib\zi\Europe\Sofia
  Delete $INSTDIR\jre6\lib\zi\Europe\Stockholm
  Delete $INSTDIR\jre6\lib\zi\Europe\Tallinn
  Delete $INSTDIR\jre6\lib\zi\Europe\Tirane
  Delete $INSTDIR\jre6\lib\zi\Europe\Uzhgorod
  Delete $INSTDIR\jre6\lib\zi\Europe\Vaduz
  Delete $INSTDIR\jre6\lib\zi\Europe\Vienna
  Delete $INSTDIR\jre6\lib\zi\Europe\Vilnius
  Delete $INSTDIR\jre6\lib\zi\Europe\Volgograd
  Delete $INSTDIR\jre6\lib\zi\Europe\Warsaw
  Delete $INSTDIR\jre6\lib\zi\Europe\Zaporozhye
  Delete $INSTDIR\jre6\lib\zi\Europe\Zurich
  Delete $INSTDIR\jre6\lib\zi\GMT
  Delete $INSTDIR\jre6\lib\zi\HST
  Delete $INSTDIR\jre6\lib\zi\Indian\Antananarivo
  Delete $INSTDIR\jre6\lib\zi\Indian\Chagos
  Delete $INSTDIR\jre6\lib\zi\Indian\Christmas
  Delete $INSTDIR\jre6\lib\zi\Indian\Cocos
  Delete $INSTDIR\jre6\lib\zi\Indian\Comoro
  Delete $INSTDIR\jre6\lib\zi\Indian\Kerguelen
  Delete $INSTDIR\jre6\lib\zi\Indian\Mahe
  Delete $INSTDIR\jre6\lib\zi\Indian\Maldives
  Delete $INSTDIR\jre6\lib\zi\Indian\Mauritius
  Delete $INSTDIR\jre6\lib\zi\Indian\Mayotte
  Delete $INSTDIR\jre6\lib\zi\Indian\Reunion
  Delete $INSTDIR\jre6\lib\zi\MET
  Delete $INSTDIR\jre6\lib\zi\MST
  Delete $INSTDIR\jre6\lib\zi\MST7MDT
  Delete $INSTDIR\jre6\lib\zi\Pacific\Apia
  Delete $INSTDIR\jre6\lib\zi\Pacific\Auckland
  Delete $INSTDIR\jre6\lib\zi\Pacific\Chatham
  Delete $INSTDIR\jre6\lib\zi\Pacific\Easter
  Delete $INSTDIR\jre6\lib\zi\Pacific\Efate
  Delete $INSTDIR\jre6\lib\zi\Pacific\Enderbury
  Delete $INSTDIR\jre6\lib\zi\Pacific\Fakaofo
  Delete $INSTDIR\jre6\lib\zi\Pacific\Fiji
  Delete $INSTDIR\jre6\lib\zi\Pacific\Funafuti
  Delete $INSTDIR\jre6\lib\zi\Pacific\Galapagos
  Delete $INSTDIR\jre6\lib\zi\Pacific\Gambier
  Delete $INSTDIR\jre6\lib\zi\Pacific\Guadalcanal
  Delete $INSTDIR\jre6\lib\zi\Pacific\Guam
  Delete $INSTDIR\jre6\lib\zi\Pacific\Honolulu
  Delete $INSTDIR\jre6\lib\zi\Pacific\Johnston
  Delete $INSTDIR\jre6\lib\zi\Pacific\Kiritimati
  Delete $INSTDIR\jre6\lib\zi\Pacific\Kosrae
  Delete $INSTDIR\jre6\lib\zi\Pacific\Kwajalein
  Delete $INSTDIR\jre6\lib\zi\Pacific\Majuro
  Delete $INSTDIR\jre6\lib\zi\Pacific\Marquesas
  Delete $INSTDIR\jre6\lib\zi\Pacific\Midway
  Delete $INSTDIR\jre6\lib\zi\Pacific\Nauru
  Delete $INSTDIR\jre6\lib\zi\Pacific\Niue
  Delete $INSTDIR\jre6\lib\zi\Pacific\Norfolk
  Delete $INSTDIR\jre6\lib\zi\Pacific\Noumea
  Delete $INSTDIR\jre6\lib\zi\Pacific\Pago_Pago
  Delete $INSTDIR\jre6\lib\zi\Pacific\Palau
  Delete $INSTDIR\jre6\lib\zi\Pacific\Pitcairn
  Delete $INSTDIR\jre6\lib\zi\Pacific\Ponape
  Delete $INSTDIR\jre6\lib\zi\Pacific\Port_Moresby
  Delete $INSTDIR\jre6\lib\zi\Pacific\Rarotonga
  Delete $INSTDIR\jre6\lib\zi\Pacific\Saipan
  Delete $INSTDIR\jre6\lib\zi\Pacific\Tahiti
  Delete $INSTDIR\jre6\lib\zi\Pacific\Tarawa
  Delete $INSTDIR\jre6\lib\zi\Pacific\Tongatapu
  Delete $INSTDIR\jre6\lib\zi\Pacific\Truk
  Delete $INSTDIR\jre6\lib\zi\Pacific\Wake
  Delete $INSTDIR\jre6\lib\zi\Pacific\Wallis
  Delete $INSTDIR\jre6\lib\zi\PST8PDT
  Delete $INSTDIR\jre6\lib\zi\SystemV\AST4
  Delete $INSTDIR\jre6\lib\zi\SystemV\AST4ADT
  Delete $INSTDIR\jre6\lib\zi\SystemV\CST6
  Delete $INSTDIR\jre6\lib\zi\SystemV\CST6CDT
  Delete $INSTDIR\jre6\lib\zi\SystemV\EST5
  Delete $INSTDIR\jre6\lib\zi\SystemV\EST5EDT
  Delete $INSTDIR\jre6\lib\zi\SystemV\HST10
  Delete $INSTDIR\jre6\lib\zi\SystemV\MST7
  Delete $INSTDIR\jre6\lib\zi\SystemV\MST7MDT
  Delete $INSTDIR\jre6\lib\zi\SystemV\PST8
  Delete $INSTDIR\jre6\lib\zi\SystemV\PST8PDT
  Delete $INSTDIR\jre6\lib\zi\SystemV\YST9
  Delete $INSTDIR\jre6\lib\zi\SystemV\YST9YDT
  Delete $INSTDIR\jre6\lib\zi\WET
  Delete $INSTDIR\jre6\lib\zi\ZoneInfoMappings
  Delete $INSTDIR\jre6\LICENSE
  Delete $INSTDIR\jre6\README.txt
  Delete $INSTDIR\jre6\THIRDPARTYLICENSEREADME.txt  
  
  ; Remove log files, if any
  Delete $INSTDIR\*.log

  ; Remove shortcuts, if any
  Delete "$SMPROGRAMS\Monyrama\*.*"
  Delete "$DESKTOP\Monyrama.lnk"

  ;Remove directories used
  RMDir "$INSTDIR\jre6\bin\client"
  RMDir "$INSTDIR\jre6\bin\new_plugin"
  RMDir "$INSTDIR\jre6\lib\audio"
  RMDir "$INSTDIR\jre6\lib\cmm"
  RMDir "$INSTDIR\jre6\lib\deploy\jqs\ff\chrome\content"
  RMDir "$INSTDIR\jre6\lib\deploy\jqs\ff\chrome"
  RMDir "$INSTDIR\jre6\lib\deploy\jqs\ff"
  RMDir "$INSTDIR\jre6\lib\deploy\jqs\ie"
  RMDir "$INSTDIR\jre6\lib\deploy\jqs"
  RMDir "$INSTDIR\jre6\lib\deploy"
  RMDir "$INSTDIR\jre6\lib\ext"
  RMDir "$INSTDIR\jre6\lib\fonts"
  RMDir "$INSTDIR\jre6\lib\i386"
  RMDir "$INSTDIR\jre6\lib\im"
  RMDir "$INSTDIR\jre6\lib\images\cursors"
  RMDir "$INSTDIR\jre6\lib\images"
  RMDir "$INSTDIR\jre6\lib\management"
  RMDir "$INSTDIR\jre6\lib\security"
  RMDir "$INSTDIR\jre6\lib\servicetag"
  RMDir "$INSTDIR\jre6\lib\zi\America\Argentina"
  RMDir "$INSTDIR\jre6\lib\zi\America\Indiana"
  RMDir "$INSTDIR\jre6\lib\zi\America\Kentucky"
  RMDir "$INSTDIR\jre6\lib\zi\America\North_Dakota"
  RMDir "$INSTDIR\jre6\lib\zi\America"
  RMDir "$INSTDIR\jre6\lib\zi\Africa"
  RMDir "$INSTDIR\jre6\lib\zi\Antarctica"
  RMDir "$INSTDIR\jre6\lib\zi\Asia"
  RMDir "$INSTDIR\jre6\lib\zi\Atlantic"
  RMDir "$INSTDIR\jre6\lib\zi\Australia"  
  RMDir "$INSTDIR\jre6\lib\zi\Etc"
  RMDir "$INSTDIR\jre6\lib\zi\Europe"
  RMDir "$INSTDIR\jre6\lib\zi\Indian" 
  RMDir "$INSTDIR\jre6\lib\zi\Pacific"
  RMDir "$INSTDIR\jre6\lib\zi\SystemV" 
  RMDir "$INSTDIR\jre6\lib\zi"
  RMDir "$INSTDIR\jre6\lib"
  RMDir "$INSTDIR\jre6\bin"
  RMDir "$INSTDIR\jre6"
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