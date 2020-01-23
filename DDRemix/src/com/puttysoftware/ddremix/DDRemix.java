/*  DDRemix: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.ddremix;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.ddremix.creatures.AbstractCreature;
import com.puttysoftware.ddremix.prefs.PreferencesManager;
import com.puttysoftware.ddremix.resourcemanagers.LogoManager;
import com.puttysoftware.errorlogger.ErrorLogger;
import com.puttysoftware.platform.Platform;

public class DDRemix {
    // Constants
    private static Application application;
    private static final String PROGRAM_NAME = "DDRemix";
    private static final String ERROR_MESSAGE = "Perhaps a bug is to blame for this error message.\n"
            + "Include the error log with your bug report.\n"
            + "Email bug reports to: products@puttysoftware.com\n"
            + "Subject: DDRemix Bug Report";
    private static final String ERROR_TITLE = "DDRemix Error";
    private static final ErrorLogger elog = new ErrorLogger(
            DDRemix.PROGRAM_NAME);
    private static final int BATTLE_MAZE_SIZE = 16;

    // Methods
    public static Application getApplication() {
        return DDRemix.application;
    }

    public static ErrorLogger getErrorLogger() {
        // Display error message
        CommonDialogs.showErrorDialog(DDRemix.ERROR_MESSAGE,
                DDRemix.ERROR_TITLE);
        return DDRemix.elog;
    }

    public static void preInit() {
        // Compute action cap
        AbstractCreature.computeActionCap(DDRemix.BATTLE_MAZE_SIZE,
                DDRemix.BATTLE_MAZE_SIZE);
    }

    public static void main(final String[] args) {
        try {
            // Pre-Init
            DDRemix.preInit();
            // Integrate with host platform
            Platform.hookLAF(DDRemix.PROGRAM_NAME);
            DDRemix.application = new Application();
            DDRemix.application.postConstruct();
            Application.playLogoSound();
            DDRemix.application.getGUIManager().showGUI();
            // Register platform hooks
            Platform.hookAbout(DDRemix.application.getAboutDialog(),
                    DDRemix.application.getAboutDialog().getClass()
                            .getDeclaredMethod("showAboutDialog"));
            Platform.hookPreferences(PreferencesManager.class,
                    PreferencesManager.class.getDeclaredMethod("showPrefs"));
            Platform.hookQuit(DDRemix.application.getGUIManager(),
                    DDRemix.application.getGUIManager().getClass()
                            .getDeclaredMethod("quitHandler"));
            Platform.hookDockIcon(LogoManager.getLogo());
            // Set up Common Dialogs
            CommonDialogs.setDefaultTitle(DDRemix.PROGRAM_NAME);
            CommonDialogs.setIcon(Application.getMicroLogo());
        } catch (final Throwable t) {
            DDRemix.getErrorLogger().logError(t);
        }
    }
}
