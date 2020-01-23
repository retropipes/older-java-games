/*  LaserTank: An Arena-Solving Game
 Copyright (C) 2008-2013 Eric Ahnell

 Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.lasertank;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.errorlogger.ErrorLogger;
import com.puttysoftware.lasertank.prefs.PreferencesManager;
import com.puttysoftware.lasertank.resourcemanagers.LogoManager;
import com.puttysoftware.lasertank.stringmanagers.StringConstants;
import com.puttysoftware.lasertank.stringmanagers.StringLoader;
import com.puttysoftware.platform.Platform;

public class LaserTank {
    private LaserTank() {
        // Do nothing
    }

    // Constants
    private static Application application;
    private static String PROGRAM_NAME = "LaserTank";
    private static String ERROR_MESSAGE = null;
    private static String ERROR_TITLE = null;
    private static ErrorLogger errorLogger;

    // Methods
    public static Application getApplication() {
        return LaserTank.application;
    }

    public static ErrorLogger getErrorLogger() {
        CommonDialogs.showErrorDialog(LaserTank.ERROR_MESSAGE,
                LaserTank.ERROR_TITLE);
        return LaserTank.errorLogger;
    }

    public static ErrorLogger getErrorLoggerDirectly() {
        return LaserTank.errorLogger;
    }

    public static void main(final String[] args) {
        try {
            // Integrate with host platform
            Platform.hookLAF(LaserTank.PROGRAM_NAME);
            try {
                // Initialize strings
                LaserTank.initStrings();
                // Initialize error logger
                LaserTank.errorLogger = new ErrorLogger(LaserTank.PROGRAM_NAME);
            } catch (final RuntimeException re) {
                // Something has gone horribly wrong
                CommonDialogs.showErrorDialog(
                        "Something has gone horribly wrong trying to load the string data!",
                        "FATAL ERROR");
                System.exit(1);
            }
            // Create and initialize application
            LaserTank.application = new Application();
            LaserTank.application.postConstruct();
            // Set Up Common Dialogs
            CommonDialogs.setDefaultTitle(LaserTank.PROGRAM_NAME);
            CommonDialogs.setIcon(LogoManager.getMicroLogo());
            // Initialize preferences
            PreferencesManager.readPrefs();
            StringLoader
                    .activeLanguageChanged(PreferencesManager.getLanguageID());
            // Register platform hooks
            Platform.hookAbout(LaserTank.application.getAboutDialog(),
                    LaserTank.application.getAboutDialog().getClass()
                            .getDeclaredMethod(StringLoader.loadString(
                                    StringConstants.NOTL_STRINGS_FILE,
                                    StringConstants.NOTL_STRING_SHOW_ABOUT_DIALOG_METHOD)));
            Platform.hookPreferences(PreferencesManager.class,
                    PreferencesManager.class.getDeclaredMethod(StringLoader
                            .loadString(StringConstants.NOTL_STRINGS_FILE,
                                    StringConstants.NOTL_STRING_SHOW_PREFERENCES_METHOD)));
            Platform.hookQuit(LaserTank.application.getGUIManager(),
                    LaserTank.application.getGUIManager().getClass()
                            .getDeclaredMethod(StringLoader.loadString(
                                    StringConstants.NOTL_STRINGS_FILE,
                                    StringConstants.NOTL_STRING_QUIT_HANDLER_METHOD)));
            Platform.hookDockIcon(LogoManager.getMiniatureLogo());
            // Display GUI
            LaserTank.application.getGUIManager().showGUI();
        } catch (final Throwable t) {
            LaserTank.getErrorLogger().logError(t);
        }
    }

    private static void initStrings() {
        StringLoader.setDefaultLanguage();
        LaserTank.ERROR_TITLE = StringLoader.loadString(
                StringConstants.ERROR_STRINGS_FILE,
                StringConstants.ERROR_STRING_ERROR_TITLE);
        LaserTank.ERROR_MESSAGE = StringLoader.loadString(
                StringConstants.ERROR_STRINGS_FILE,
                StringConstants.ERROR_STRING_ERROR_MESSAGE);
    }
}
