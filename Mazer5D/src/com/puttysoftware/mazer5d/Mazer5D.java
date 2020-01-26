/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.errorlogger.ErrorLogger;
import com.puttysoftware.mazer5d.prefs.PreferencesManager;
import com.puttysoftware.mazer5d.resourcemanagers.LogoManager;
import com.puttysoftware.platform.Platform;

public class Mazer5D {
    // Constants
    private static Application application;
    private static final String PROGRAM_NAME = "Mazer5D";
    private static final String ERROR_MESSAGE = "Perhaps a bug is to blame for this error message.\n"
            + "Include the error log with your bug report.\n"
            + "Email bug reports to: products@puttysoftware.com\n"
            + "Subject: Mazer5D Bug Report";
    private static final String ERROR_TITLE = "Mazer5D Error";
    private static final ErrorLogger errorLogger = new ErrorLogger(
            Mazer5D.PROGRAM_NAME);

    // Methods
    public static Application getApplication() {
        return Mazer5D.application;
    }

    public static ErrorLogger getErrorLogger() {
        CommonDialogs.showErrorDialog(Mazer5D.ERROR_MESSAGE,
                Mazer5D.ERROR_TITLE);
        return Mazer5D.errorLogger;
    }

    public static void main(final String[] args) {
        try {
            // Integrate with host platform
            Platform.hookLAF(Mazer5D.PROGRAM_NAME);
            Mazer5D.application = new Application();
            Mazer5D.application.postConstruct();
            Mazer5D.application.playLogoSound();
            Mazer5D.application.getGUIManager().showGUI();
            // Register platform hooks
            Platform.hookAbout(Mazer5D.application.getAboutDialog(),
                    Mazer5D.application.getAboutDialog().getClass()
                            .getDeclaredMethod("showAboutDialog"));
            String s;
            if (args.length == 0) {
                s = null;
            } else {
                s = args[0];
            }
            Platform.hookFileOpen(Mazer5D.application.getMazeManager(),
                    Mazer5D.application.getMazeManager().getClass()
                            .getDeclaredMethod("loadFromOSHandler",
                                    String.class),
                    s);
            Platform.hookPreferences(PreferencesManager.class,
                    PreferencesManager.class.getDeclaredMethod("showPrefs"));
            Platform.hookQuit(Mazer5D.application.getGUIManager(),
                    Mazer5D.application.getGUIManager().getClass()
                            .getDeclaredMethod("quitHandler"));
            Platform.hookDockIcon(LogoManager.getLogo());
            // Set up Common Dialogs
            CommonDialogs.setDefaultTitle(Mazer5D.PROGRAM_NAME);
            CommonDialogs.setIcon(LogoManager.getMicroLogo());
        } catch (final Throwable t) {
            Mazer5D.getErrorLogger().logError(t);
        }
    }
}
