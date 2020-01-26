/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.errorlogger.ErrorLogger;
import com.puttysoftware.fantastlex.creatures.AbstractCreature;
import com.puttysoftware.fantastlex.prefs.PreferencesManager;
import com.puttysoftware.fantastlex.resourcemanagers.LogoManager;
import com.puttysoftware.platform.Platform;

public class FantastleX {
    // Constants
    private static Application application;
    private static final String PROGRAM_NAME = "FantastleX";
    private static final String ERROR_MESSAGE = "Perhaps a bug is to blame for this error message.\n"
            + "Include the error log with your bug report.\n"
            + "Email bug reports to: products@puttysoftware.com\n"
            + "Subject: FantastleX Bug Report";
    private static final String ERROR_TITLE = "FantastleX Error";
    private static final ErrorLogger elog = new ErrorLogger(
            FantastleX.PROGRAM_NAME);
    private static final int BATTLE_MAP_SIZE = 16;
    private static final boolean DEBUG_MODE = false;

    // Methods
    public static Application getApplication() {
        return FantastleX.application;
    }

    public static ErrorLogger getErrorLogger() {
        String suffix;
        if (FantastleX.inDebugMode()) {
            suffix = " (DEBUG)";
        } else {
            suffix = "";
        }
        // Display error message
        CommonDialogs.showErrorDialog(FantastleX.ERROR_MESSAGE,
                FantastleX.ERROR_TITLE + suffix);
        return FantastleX.elog;
    }

    public static boolean inDebugMode() {
        return FantastleX.DEBUG_MODE;
    }

    public static int getBattleMazeSize() {
        return FantastleX.BATTLE_MAP_SIZE;
    }

    public static void preInit() {
        // Compute action cap
        AbstractCreature.computeActionCap(FantastleX.BATTLE_MAP_SIZE,
                FantastleX.BATTLE_MAP_SIZE);
    }

    public static void main(final String[] args) {
        try {
            // Pre-Init
            FantastleX.preInit();
            // Integrate with host platform
            Platform.hookLAF(FantastleX.PROGRAM_NAME);
            FantastleX.application = new Application();
            FantastleX.application.postConstruct();
            FantastleX.application.playLogoSound();
            FantastleX.application.getGUIManager().showGUI();
            // Register platform hooks
            Platform.hookAbout(FantastleX.application.getAboutDialog(),
                    FantastleX.application.getAboutDialog().getClass()
                            .getDeclaredMethod("showAboutDialog"));
            String s;
            if (args.length == 0) {
                s = null;
            } else {
                s = args[0];
            }
            Platform.hookFileOpen(FantastleX.application.getMazeManager(),
                    FantastleX.application.getMazeManager().getClass()
                            .getDeclaredMethod("loadFromOSHandler",
                                    String.class),
                    s);
            Platform.hookPreferences(PreferencesManager.class,
                    PreferencesManager.class.getDeclaredMethod("showPrefs"));
            Platform.hookQuit(FantastleX.application.getGUIManager(),
                    FantastleX.application.getGUIManager().getClass()
                            .getDeclaredMethod("quitHandler"));
            Platform.hookDockIcon(LogoManager.getLogo());
            // Set up Common Dialogs
            CommonDialogs.setDefaultTitle(FantastleX.PROGRAM_NAME);
            CommonDialogs.setIcon(FantastleX.application.getMicroLogo());
        } catch (final Throwable t) {
            FantastleX.getErrorLogger().logError(t);
        }
    }
}
