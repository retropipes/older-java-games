/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.errorlogger.ErrorLogger;
import com.puttysoftware.mastermaze.creatures.Creature;
import com.puttysoftware.mastermaze.prefs.PreferencesManager;
import com.puttysoftware.platform.Platform;

public class MasterMaze {
    // Constants
    private static Application application;
    private static final String PROGRAM_NAME = "MasterMaze";
    private static final String ERROR_MESSAGE = "Perhaps a bug is to blame for this error message.\n"
            + "Include the error log with your bug report.\n"
            + "Email bug reports to: products@puttysoftware.com\n"
            + "Subject: MasterMaze Bug Report";
    private static final String ERROR_TITLE = "MasterMaze Error";
    private static final ErrorLogger elog = new ErrorLogger(
            MasterMaze.PROGRAM_NAME);
    private static final int BATTLE_MAP_SIZE = 16;
    private static final boolean debugMode = false;

    // Methods
    public static Application getApplication() {
        return MasterMaze.application;
    }

    public static ErrorLogger getErrorLogger() {
        String suffix;
        if (MasterMaze.inDebugMode()) {
            suffix = " (DEBUG)";
        } else {
            suffix = "";
        }
        // Display error message
        CommonDialogs.showErrorDialog(MasterMaze.ERROR_MESSAGE,
                MasterMaze.ERROR_TITLE + suffix);
        return MasterMaze.elog;
    }

    public static boolean inDebugMode() {
        return MasterMaze.debugMode;
    }

    public static int getBattleMazeSize() {
        return MasterMaze.BATTLE_MAP_SIZE;
    }

    public static void preInit() {
        // Compute action cap
        Creature.computeActionCap(MasterMaze.BATTLE_MAP_SIZE,
                MasterMaze.BATTLE_MAP_SIZE);
    }

    public static void main(final String[] args) {
        try {
            // Pre-Init
            MasterMaze.preInit();
            // Integrate with host platform
            Platform.hookLAF(MasterMaze.PROGRAM_NAME);
            MasterMaze.application = new Application();
            MasterMaze.application.postConstruct();
            MasterMaze.application.playLogoSound();
            MasterMaze.application.getGUIManager().showGUI();
            // Register platform hooks
            Platform.hookAbout(MasterMaze.application.getAboutDialog(),
                    MasterMaze.application.getAboutDialog().getClass()
                            .getDeclaredMethod("showAboutDialog"));
            String s;
            if (args.length == 0) {
                s = null;
            } else {
                s = args[0];
            }
            Platform.hookFileOpen(
                    MasterMaze.application.getMazeManager(),
                    MasterMaze.application
                            .getMazeManager()
                            .getClass()
                            .getDeclaredMethod("loadFromOSHandler",
                                    String.class), s);
            Platform.hookPreferences(PreferencesManager.class,
                    PreferencesManager.class.getDeclaredMethod("showPrefs"));
            Platform.hookQuit(MasterMaze.application.getGUIManager(),
                    MasterMaze.application.getGUIManager().getClass()
                            .getDeclaredMethod("quitHandler"));
            // Set up Common Dialogs
            CommonDialogs.setDefaultTitle(MasterMaze.PROGRAM_NAME);
            CommonDialogs.setIcon(MasterMaze.application.getMicroLogo());
        } catch (final Throwable t) {
            MasterMaze.getErrorLogger().logError(t);
        }
    }
}
