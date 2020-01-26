/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode;

import com.puttysoftware.errorlogger.ErrorLogger;
import com.puttysoftware.mazemode.maze.MazeRegistration;
import com.puttysoftware.mazemode.platform.Platform;
import com.puttysoftware.mazemode.prefs.PreferencesManager;
import com.puttysoftware.mazemode.resourcemanagers.MusicManager;

public class MazeMode {
    // Constants
    private static Application application;
    private static final String PROGRAM_NAME = "MazeMode";
    private static final String ERROR_MESSAGE = "Perhaps a bug is to blame for this error message.\n"
            + "Include the debug log with your bug report.\n"
            + "Email bug reports to: products@puttysoftware.com\n"
            + "Subject: MazeMode Bug Report";
    private static final String ERROR_TITLE = "MazeMode Error";
    private static final ErrorLogger elog = new ErrorLogger(
            MazeMode.PROGRAM_NAME, MazeMode.ERROR_MESSAGE,
            MazeMode.ERROR_TITLE);
    private static boolean IN_MAZEMODE = true;

    // Methods
    public static Application getApplication() {
        return MazeMode.application;
    }

    public static boolean inMazeMode() {
        return MazeMode.IN_MAZEMODE;
    }

    public static void leaveMazeMode() {
        if (MusicManager.isMusicPlaying()) {
            MusicManager.stopMusic();
        }
        MazeMode.application.getOutputFrame().setVisible(false);
        PreferencesManager.writePrefs();
        MazeMode.IN_MAZEMODE = false;
    }

    public static ErrorLogger getErrorLogger() {
        return MazeMode.elog;
    }

    public static String getProgramName() {
        return MazeMode.PROGRAM_NAME;
    }

    public static void main(final String[] args) {
        try {
            // Integrate with host platform
            final Platform platform = new Platform();
            platform.hookLAF();
            // Auto-register all mazes
            MazeRegistration.autoRegisterAllMazes();
            MazeMode.application = new Application();
            MazeMode.application.postConstruct();
            MazeMode.application.playLogoSound();
            MazeMode.application.getGUIManager().showGUI();
            // Register platform hooks
            platform.hookAbout(MazeMode.application.getAboutDialog(),
                    MazeMode.application.getAboutDialog().getClass()
                            .getDeclaredMethod("showAboutDialog"));
            String s;
            if (args.length == 0) {
                s = null;
            } else {
                s = args[0];
            }
            platform.hookFileOpen(MazeMode.application.getMazeManager(),
                    MazeMode.application.getMazeManager().getClass()
                            .getDeclaredMethod("loadFromOSHandler",
                                    String.class),
                    s);
            platform.hookPreferences(PreferencesManager.class,
                    PreferencesManager.class.getDeclaredMethod("showPrefs"));
            platform.hookQuit(MazeMode.application.getGUIManager(),
                    MazeMode.application.getGUIManager().getClass()
                            .getDeclaredMethod("quitHandler"));
        } catch (final Throwable t) {
            MazeMode.getErrorLogger().logError(t);
        }
    }
}
