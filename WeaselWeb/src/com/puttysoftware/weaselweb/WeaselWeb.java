/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb;

import java.util.Vector;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.errorlogger.ErrorLogger;
import com.puttysoftware.platform.Platform;
import com.puttysoftware.weaselweb.maze.TempDirCleanup;
import com.puttysoftware.weaselweb.pluginmanagers.PluginHooks;
import com.puttysoftware.weaselweb.pluginmanagers.PluginLoader;
import com.puttysoftware.weaselweb.prefs.PreferencesManager;
import com.puttysoftware.weaselweb.resourcemanagers.LogoManager;
import com.puttysoftware.weaselweb.resourcemanagers.MusicManager;

public class WeaselWeb {
    // Constants
    private static Application application;
    private static final String PROGRAM_NAME = "WeaselWeb";
    private static final String ERROR_MESSAGE = "Perhaps a bug is to blame for this error message.\n"
            + "Include the error log with your bug report.\n"
            + "Email bug reports to: products@puttysoftware.com\n"
            + "Subject: WeaselWeb Bug Report";
    private static final String ERROR_TITLE = "WeaselWeb Error";
    private static final ErrorLogger errorLogger = new ErrorLogger(
            WeaselWeb.PROGRAM_NAME);
    private static boolean IN_MAZER5D = true;

    // Methods
    public static Application getApplication() {
        return WeaselWeb.application;
    }

    public static boolean inWeaselWeb() {
        return WeaselWeb.IN_MAZER5D;
    }

    public static void leaveWeaselWeb() {
        if (MusicManager.isMusicPlaying()) {
            MusicManager.stopMusic();
        }
        WeaselWeb.application.getOutputFrame().setVisible(false);
        PreferencesManager.writePrefs();
        // Run cleanup task
        new TempDirCleanup().start();
        WeaselWeb.IN_MAZER5D = false;
    }

    public static ErrorLogger getErrorLogger() {
        CommonDialogs.showErrorDialog(WeaselWeb.ERROR_MESSAGE,
                WeaselWeb.ERROR_TITLE);
        return WeaselWeb.errorLogger;
    }

    public static void main(final String[] args) {
        try {
            // Integrate with host platform
            Platform.hookLAF(WeaselWeb.PROGRAM_NAME);
            // Load all registered plugins
            final Vector<Object> plugins = PluginLoader
                    .loadAllRegisteredPlugins();
            // Inject early hooks
            PluginHooks.injectEarlyHooks(plugins);
            WeaselWeb.application = new Application();
            WeaselWeb.application.postConstruct();
            WeaselWeb.application.playLogoSound();
            WeaselWeb.application.getGUIManager().showGUI();
            // Register platform hooks
            Platform.hookAbout(WeaselWeb.application.getAboutDialog(),
                    WeaselWeb.application.getAboutDialog().getClass()
                            .getDeclaredMethod("showAboutDialog"));
            String s;
            if (args.length == 0) {
                s = null;
            } else {
                s = args[0];
            }
            Platform.hookFileOpen(
                    WeaselWeb.application.getMazeManager(),
                    WeaselWeb.application
                            .getMazeManager()
                            .getClass()
                            .getDeclaredMethod("loadFromOSHandler",
                                    String.class), s);
            Platform.hookPreferences(PreferencesManager.class,
                    PreferencesManager.class.getDeclaredMethod("showPrefs"));
            Platform.hookQuit(WeaselWeb.application.getGUIManager(),
                    WeaselWeb.application.getGUIManager().getClass()
                            .getDeclaredMethod("quitHandler"));
            // Inject late hooks
            PluginHooks.injectLateHooks(plugins);
            // Set up Common Dialogs
            CommonDialogs.setDefaultTitle(WeaselWeb.PROGRAM_NAME);
            CommonDialogs.setIcon(LogoManager.getMicroLogo());
        } catch (final Throwable t) {
            WeaselWeb.getErrorLogger().logError(t);
        }
    }
}
