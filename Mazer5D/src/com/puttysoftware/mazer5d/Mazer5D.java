/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d;

import java.awt.desktop.PreferencesEvent;
import java.awt.desktop.PreferencesHandler;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.errorlogger.ErrorLogger;
import com.puttysoftware.integration.NativeIntegration;
import com.puttysoftware.mazer5d.prefs.PreferencesManager;
import com.puttysoftware.mazer5d.resourcemanagers.LogoManager;

public class Mazer5D {
    // Constants
    private static Application application;
    private static final String PROGRAM_NAME = "Mazer5D";
    private static final String ERROR_MESSAGE = "Perhaps a bug is to blame for this error message.\n"
            + "Include the error log with your bug report.\n"
            + "Email bug reports to: products@puttysoftware.com\n"
            + "Subject: Mazer5D Bug Report";
    private static final String ERROR_TITLE = "Mazer5D Error";
    private static final ErrorLogger errorLogger = new ErrorLogger(
            Mazer5D.PROGRAM_NAME);

    // Methods
    public static Application getApplication() {
        return Mazer5D.application;
    }

    public static ErrorLogger getErrorLogger() {
        return Mazer5D.errorLogger;
    }

    public static void logError(Throwable e) {
        CommonDialogs.showErrorDialog(Mazer5D.ERROR_MESSAGE,
                Mazer5D.ERROR_TITLE);
        Mazer5D.errorLogger.logError(e);
    }

    public static void main(final String[] args) {
        try {
            // Integrate with host platform
            NativeIntegration ni = new NativeIntegration();
            ni.configureLookAndFeel();
            Mazer5D.application = new Application();
            Mazer5D.application.configureMenus(ni);
            ni.setAboutHandler(Mazer5D.application.getAboutDialog());
            ni.setOpenFileHandler(Mazer5D.application.getMazeManager());
            ni.setPreferencesHandler(new PreferencesLauncher());
            ni.setQuitHandler(Mazer5D.application.getGUIManager());
            // Set up Common Dialogs
            CommonDialogs.setDefaultTitle(Mazer5D.PROGRAM_NAME);
            CommonDialogs.setIcon(LogoManager.getMicroLogo());
            // Launch GUI
            Mazer5D.application.playLogoSound();
            Mazer5D.application.getGUIManager().showGUI();
        } catch (final Throwable e) {
            Mazer5D.logError(e);
        }
    }

    private static class PreferencesLauncher implements PreferencesHandler {
        @Override
        public void handlePreferences(PreferencesEvent inE) {
            PreferencesManager.showPrefs();
        }
    }
}
