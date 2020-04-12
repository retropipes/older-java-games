/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d;

import java.awt.desktop.PreferencesEvent;
import java.awt.desktop.PreferencesHandler;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.integration.NativeIntegration;
import com.puttysoftware.mazer5d.assetmanagers.LogoManager;
import com.puttysoftware.mazer5d.prefs.PreferencesManager;

public class Mazer5D {
    // Constants
    private static Application application;
    private static GameErrorHandler errhand;
    private static final String PROGRAM_NAME = "Mazer5D";

    // Methods
    public static Application getApplication() {
        return Mazer5D.application;
    }

    public static void logError(Mazer5DException e) {
        Mazer5D.errhand.uncaughtException(Thread.currentThread(), e.getCause());
    }

    @Deprecated
    public static void logError(RuntimeException re) {
        // Does nothing. Don't call this.
    }

    public static void main(final String[] args) {
        // Install error handler
        Mazer5D.errhand = new GameErrorHandler();
        Thread.setDefaultUncaughtExceptionHandler(errhand);
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
    }

    private static class PreferencesLauncher implements PreferencesHandler {
        @Override
        public void handlePreferences(PreferencesEvent inE) {
            PreferencesManager.showPrefs();
        }
    }
}
