/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.errorlogger.ErrorLogger;
import com.puttysoftware.mazerunner2.creatures.AbstractCreature;
import com.puttysoftware.mazerunner2.prefs.PreferencesManager;
import com.puttysoftware.platform.Platform;

public class MazeRunnerII {
    // Constants
    private static Application application;
    private static final String PROGRAM_NAME = "MazeRunnerII";
    private static final String ERROR_MESSAGE = "Perhaps a bug is to blame for this error message.\n"
            + "Include the error log with your bug report.\n"
            + "Email bug reports to: products@puttysoftware.com\n"
            + "Subject: MazeRunnerII Bug Report";
    private static final String ERROR_TITLE = "MazeRunnerII Error";
    private static final ErrorLogger elog = new ErrorLogger(
            MazeRunnerII.PROGRAM_NAME);
    private static final int BATTLE_MAP_SIZE = 16;
    private static final boolean DEBUG_MODE = false;

    // Methods
    public static Application getApplication() {
        return MazeRunnerII.application;
    }

    public static ErrorLogger getErrorLogger() {
        String suffix;
        if (MazeRunnerII.inDebugMode()) {
            suffix = " (DEBUG)";
        } else {
            suffix = "";
        }
        // Display error message
        CommonDialogs.showErrorDialog(MazeRunnerII.ERROR_MESSAGE,
                MazeRunnerII.ERROR_TITLE + suffix);
        return MazeRunnerII.elog;
    }

    public static boolean inDebugMode() {
        return MazeRunnerII.DEBUG_MODE;
    }

    public static int getBattleMazeSize() {
        return MazeRunnerII.BATTLE_MAP_SIZE;
    }

    public static void preInit() {
        // Compute action cap
        AbstractCreature.computeActionCap(MazeRunnerII.BATTLE_MAP_SIZE,
                MazeRunnerII.BATTLE_MAP_SIZE);
    }

    public static void main(final String[] args) {
        try {
            // Pre-Init
            MazeRunnerII.preInit();
            // Integrate with host platform
            Platform.hookLAF(MazeRunnerII.PROGRAM_NAME);
            MazeRunnerII.application = new Application();
            MazeRunnerII.application.postConstruct();
            MazeRunnerII.application.playLogoSound();
            MazeRunnerII.application.getGUIManager().showGUI();
            // Register platform hooks
            Platform.hookAbout(MazeRunnerII.application.getAboutDialog(),
                    MazeRunnerII.application.getAboutDialog().getClass()
                            .getDeclaredMethod("showAboutDialog"));
            String s;
            if (args.length == 0) {
                s = null;
            } else {
                s = args[0];
            }
            Platform.hookFileOpen(MazeRunnerII.application.getMazeManager(),
                    MazeRunnerII.application.getMazeManager().getClass()
                            .getDeclaredMethod("loadFromOSHandler",
                                    String.class),
                    s);
            Platform.hookPreferences(PreferencesManager.class,
                    PreferencesManager.class.getDeclaredMethod("showPrefs"));
            Platform.hookQuit(MazeRunnerII.application.getGUIManager(),
                    MazeRunnerII.application.getGUIManager().getClass()
                            .getDeclaredMethod("quitHandler"));
            // Set up Common Dialogs
            CommonDialogs.setDefaultTitle(MazeRunnerII.PROGRAM_NAME);
            CommonDialogs.setIcon(MazeRunnerII.application.getMicroLogo());
        } catch (final Throwable t) {
            MazeRunnerII.getErrorLogger().logError(t);
        }
    }
}
