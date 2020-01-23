/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer;

import com.puttysoftware.ghosted.Ghosted;
import com.puttysoftware.rulemazer.platform.MazerPlatform;
import com.puttysoftware.rulemazer.prefs.PreferencesManager;

public class Main {
    // Constants
    private static Application application;
    private static final String PROGRAM_NAME = "RuleMazer";
    private static final String ERROR_MESSAGE = "Perhaps a bug is to blame for this error message.\n"
            + "Include the debug log with your bug report.\n"
            + "Email bug reports to: rulemazer@puttysoftware.com\n"
            + "Subject: RuleMazer Bug Report";
    private static final String ERROR_TITLE = "RuleMazer Error";
    private static final Ghosted debug = new Ghosted(Main.PROGRAM_NAME,
            Main.ERROR_MESSAGE, Main.ERROR_TITLE);

    // Methods
    public static Application getApplication() {
        return Main.application;
    }

    public static Ghosted getDebug() {
        return Main.debug;
    }

    public static void main(final String[] args) {
        try {
            // Integrate with host platform
            final MazerPlatform platform = new MazerPlatform();
            platform.hookLAF();
            Main.application = new Application();
            Main.application.postConstruct();
            Main.application.playLogoSound();
            Main.application.getGUIManager().showGUI();
            // Register platform hooks
            platform.hookAbout(Main.application.getAboutDialog(),
                    Main.application.getAboutDialog().getClass()
                            .getDeclaredMethod("showAboutDialog"));
            String s;
            if (args.length == 0) {
                s = null;
            } else {
                s = args[0];
            }
            platform.hookFileOpen(
                    Main.application.getMazeManager(),
                    Main.application
                            .getMazeManager()
                            .getClass()
                            .getDeclaredMethod("loadFromOSHandler",
                                    String.class), s);
            platform.hookPreferences(PreferencesManager.class,
                    PreferencesManager.class.getDeclaredMethod("showPrefs"));
            platform.hookQuit(Main.application.getGUIManager(),
                    Main.application.getGUIManager().getClass()
                            .getDeclaredMethod("quitHandler"));
        } catch (final Throwable t) {
            Main.getDebug().debug(t);
        }
    }
}
