/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.dungeondiver4.creatures.AbstractCreature;
import com.puttysoftware.dungeondiver4.prefs.PreferencesManager;
import com.puttysoftware.dungeondiver4.resourcemanagers.LogoManager;
import com.puttysoftware.errorlogger.ErrorLogger;
import com.puttysoftware.platform.Platform;

public class DungeonDiver4 {
    // Constants
    private static Application application;
    private static final String PROGRAM_NAME = "DungeonDiver4";
    private static final String ERROR_MESSAGE = "Perhaps a bug is to blame for this error message.\n"
            + "Include the error log with your bug report.\n"
            + "Email bug reports to: products@puttysoftware.com\n"
            + "Subject: DungeonDiver4 Bug Report";
    private static final String ERROR_TITLE = "DungeonDiver4 Error";
    private static final ErrorLogger elog = new ErrorLogger(
            DungeonDiver4.PROGRAM_NAME);
    private static final int BATTLE_DUNGEON_SIZE = 16;
    private static final boolean DEBUG_MODE = false;

    // Methods
    public static Application getApplication() {
        return DungeonDiver4.application;
    }

    public static ErrorLogger getErrorLogger() {
        String suffix;
        if (DungeonDiver4.inDebugMode()) {
            suffix = " (DEBUG)";
        } else {
            suffix = "";
        }
        // Display error message
        CommonDialogs.showErrorDialog(DungeonDiver4.ERROR_MESSAGE,
                DungeonDiver4.ERROR_TITLE + suffix);
        return DungeonDiver4.elog;
    }

    public static boolean inDebugMode() {
        return DungeonDiver4.DEBUG_MODE;
    }

    public static int getBattleDungeonSize() {
        return DungeonDiver4.BATTLE_DUNGEON_SIZE;
    }

    public static void preInit() {
        // Compute action cap
        AbstractCreature.computeActionCap(DungeonDiver4.BATTLE_DUNGEON_SIZE,
                DungeonDiver4.BATTLE_DUNGEON_SIZE);
    }

    public static void main(final String[] args) {
        try {
            // Pre-Init
            DungeonDiver4.preInit();
            // Integrate with host platform
            Platform.hookLAF(DungeonDiver4.PROGRAM_NAME);
            DungeonDiver4.application = new Application();
            DungeonDiver4.application.postConstruct();
            DungeonDiver4.application.playLogoSound();
            DungeonDiver4.application.getGUIManager().showGUI();
            // Register platform hooks
            Platform.hookAbout(DungeonDiver4.application.getAboutDialog(),
                    DungeonDiver4.application.getAboutDialog().getClass()
                            .getDeclaredMethod("showAboutDialog"));
            String s;
            if (args.length == 0) {
                s = null;
            } else {
                s = args[0];
            }
            Platform.hookFileOpen(DungeonDiver4.application.getDungeonManager(),
                    DungeonDiver4.application.getDungeonManager().getClass()
                            .getDeclaredMethod("loadFromOSHandler",
                                    String.class),
                    s);
            Platform.hookPreferences(PreferencesManager.class,
                    PreferencesManager.class.getDeclaredMethod("showPrefs"));
            Platform.hookQuit(DungeonDiver4.application.getGUIManager(),
                    DungeonDiver4.application.getGUIManager().getClass()
                            .getDeclaredMethod("quitHandler"));
            // Set up Common Dialogs
            CommonDialogs.setDefaultTitle(DungeonDiver4.PROGRAM_NAME);
            CommonDialogs.setIcon(DungeonDiver4.application.getMicroLogo());
            Platform.hookDockIcon(LogoManager.getLogo());
        } catch (final Throwable t) {
            DungeonDiver4.getErrorLogger().logError(t);
        }
    }
}
