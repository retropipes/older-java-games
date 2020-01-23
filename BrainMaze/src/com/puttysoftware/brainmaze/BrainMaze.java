/*  BrainMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.brainmaze;

import com.puttysoftware.brainmaze.prefs.PreferencesManager;
import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.errorlogger.ErrorLogger;
import com.puttysoftware.platform.Platform;

public class BrainMaze {
    // Constants
    private static Application application;
    private static final String PROGRAM_NAME = "BrainMaze";
    private static final String ERROR_MESSAGE = "Perhaps a bug is to blame for this error message.\n"
            + "Include the error log with your bug report.\n"
            + "Email bug reports to: products@puttysoftware.com\n"
            + "Subject: BrainMaze Bug Report";
    private static final String ERROR_TITLE = "BrainMaze Error";
    private static final ErrorLogger errorLogger = new ErrorLogger(
            BrainMaze.PROGRAM_NAME);

    // Methods
    public static Application getApplication() {
        return BrainMaze.application;
    }

    public static ErrorLogger getErrorLogger() {
        CommonDialogs.showErrorDialog(BrainMaze.ERROR_MESSAGE,
                BrainMaze.ERROR_TITLE);
        return BrainMaze.errorLogger;
    }

    public static void main(final String[] args) {
        try {
            // Integrate with host platform
            Platform.hookLAF(BrainMaze.PROGRAM_NAME);
            BrainMaze.application = new Application();
            BrainMaze.application.postConstruct();
            BrainMaze.application.playLogoSound();
            BrainMaze.application.getGUIManager().showGUI();
            // Register platform hooks
            Platform.hookAbout(BrainMaze.application.getAboutDialog(),
                    BrainMaze.application.getAboutDialog().getClass()
                            .getDeclaredMethod("showAboutDialog"));
            String s;
            if (args.length == 0) {
                s = null;
            } else {
                s = args[0];
            }
            Platform.hookFileOpen(
                    BrainMaze.application.getMazeManager(),
                    BrainMaze.application
                            .getMazeManager()
                            .getClass()
                            .getDeclaredMethod("loadFromOSHandler",
                                    String.class), s);
            Platform.hookPreferences(PreferencesManager.class,
                    PreferencesManager.class.getDeclaredMethod("showPrefs"));
            Platform.hookQuit(BrainMaze.application.getGUIManager(),
                    BrainMaze.application.getGUIManager().getClass()
                            .getDeclaredMethod("quitHandler"));
            // Set up Common Dialogs
            CommonDialogs.setDefaultTitle(BrainMaze.PROGRAM_NAME);
            CommonDialogs.setIcon(BrainMaze.application.getMicroLogo());
        } catch (final Throwable t) {
            BrainMaze.getErrorLogger().logError(t);
        }
    }
}
