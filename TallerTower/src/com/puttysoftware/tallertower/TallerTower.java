/*  TallerTower: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.tallertower;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.errorlogger.ErrorLogger;
import com.puttysoftware.platform.Platform;
import com.puttysoftware.tallertower.creatures.AbstractCreature;
import com.puttysoftware.tallertower.prefs.PreferencesManager;
import com.puttysoftware.tallertower.resourcemanagers.LogoManager;

public class TallerTower {
    // Constants
    private static Application application;
    private static final String PROGRAM_NAME = "TallerTower";
    private static final String ERROR_MESSAGE = "Perhaps a bug is to blame for this error message.\n"
            + "Include the error log with your bug report.\n"
            + "Email bug reports to: products@puttysoftware.com\n"
            + "Subject: TallerTower Bug Report";
    private static final String ERROR_TITLE = "TallerTower Error";
    private static final ErrorLogger elog = new ErrorLogger(
            TallerTower.PROGRAM_NAME);
    private static final int BATTLE_MAZE_SIZE = 16;

    // Methods
    public static Application getApplication() {
        return TallerTower.application;
    }

    public static int getBattleMazeSize() {
        return TallerTower.BATTLE_MAZE_SIZE;
    }

    public static ErrorLogger getErrorLogger() {
        // Display error message
        CommonDialogs.showErrorDialog(TallerTower.ERROR_MESSAGE,
                TallerTower.ERROR_TITLE);
        return TallerTower.elog;
    }

    public static void preInit() {
        // Compute action cap
        AbstractCreature.computeActionCap(TallerTower.BATTLE_MAZE_SIZE,
                TallerTower.BATTLE_MAZE_SIZE);
    }

    public static void main(final String[] args) {
        try {
            // Pre-Init
            TallerTower.preInit();
            // Integrate with host platform
            Platform.hookLAF(TallerTower.PROGRAM_NAME);
            TallerTower.application = new Application();
            TallerTower.application.postConstruct();
            Application.playLogoSound();
            TallerTower.application.getGUIManager().showGUI();
            // Register platform hooks
            Platform.hookAbout(TallerTower.application.getAboutDialog(),
                    TallerTower.application.getAboutDialog().getClass()
                            .getDeclaredMethod("showAboutDialog"));
            Platform.hookPreferences(PreferencesManager.class,
                    PreferencesManager.class.getDeclaredMethod("showPrefs"));
            Platform.hookQuit(TallerTower.application.getGUIManager(),
                    TallerTower.application.getGUIManager().getClass()
                            .getDeclaredMethod("quitHandler"));
            Platform.hookDockIcon(LogoManager.getLogo());
            // Set up Common Dialogs
            CommonDialogs.setDefaultTitle(TallerTower.PROGRAM_NAME);
            CommonDialogs.setIcon(Application.getMicroLogo());
        } catch (final Throwable t) {
            TallerTower.getErrorLogger().logError(t);
        }
    }
}
