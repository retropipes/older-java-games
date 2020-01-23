/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.errorlogger.ErrorLogger;
import com.puttysoftware.loopchute.prefs.PreferencesManager;
import com.puttysoftware.platform.Platform;

public class LoopChute {
    // Constants
    private static Application application;
    private static final String PROGRAM_NAME = "loopchute";
    private static final String ERROR_MESSAGE = "Perhaps a bug is to blame for this error message.\n"
            + "Include the error log with your bug report.\n"
            + "Email bug reports to: products@puttysoftware.com\n"
            + "Subject: loopchute Bug Report";
    private static final String ERROR_TITLE = "loopchute Error";
    private static final ErrorLogger errorLogger = new ErrorLogger(
            LoopChute.PROGRAM_NAME);

    // Methods
    public static Application getApplication() {
        return LoopChute.application;
    }

    public static ErrorLogger getErrorLogger() {
        CommonDialogs.showErrorDialog(LoopChute.ERROR_MESSAGE,
                LoopChute.ERROR_TITLE);
        return LoopChute.errorLogger;
    }

    public static void main(final String[] args) {
        try {
            // Integrate with host platform
            Platform.hookLAF(LoopChute.PROGRAM_NAME);
            LoopChute.application = new Application();
            LoopChute.application.postConstruct();
            LoopChute.application.playLogoSound();
            LoopChute.application.getGUIManager().showGUI();
            // Register platform hooks
            Platform.hookAbout(LoopChute.application.getAboutDialog(),
                    LoopChute.application.getAboutDialog().getClass()
                            .getDeclaredMethod("showAboutDialog"));
            String s;
            if (args.length == 0) {
                s = null;
            } else {
                s = args[0];
            }
            Platform.hookFileOpen(
                    LoopChute.application.getMazeManager(),
                    LoopChute.application
                            .getMazeManager()
                            .getClass()
                            .getDeclaredMethod("loadFromOSHandler",
                                    String.class), s);
            Platform.hookPreferences(PreferencesManager.class,
                    PreferencesManager.class.getDeclaredMethod("showPrefs"));
            Platform.hookQuit(LoopChute.application.getGUIManager(),
                    LoopChute.application.getGUIManager().getClass()
                            .getDeclaredMethod("quitHandler"));
            // Set up Common Dialogs
            CommonDialogs.setDefaultTitle(LoopChute.PROGRAM_NAME);
            CommonDialogs.setIcon(LoopChute.application.getMicroLogo());
        } catch (final Throwable t) {
            LoopChute.getErrorLogger().logError(t);
        }
    }
}
