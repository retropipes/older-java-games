/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz;

import java.util.Vector;

import net.worldwizard.ghosted.Ghosted;
import net.worldwizard.worldz.platform.WorldPlatform;
import net.worldwizard.worldz.resourcemanagers.MusicManager;

public class Worldz {
    // Constants
    private static Application application;
    private static final String PROGRAM_NAME = "Worldz";
    private static final String ERROR_MESSAGE = "Perhaps a bug is to blame for this error message.\n"
            + "Include the debug log with your bug report.\n"
            + "Email bug reports to: worldz@worldwizard.net\n"
            + "Subject: Worldz Bug Report";
    private static final String NON_FATAL_ERROR_MESSAGE = "An error has occurred while running a script."
            + "\nThis error is non-fatal, and has been logged."
            + "\nPlease notify the creator of this world of the buggy script.";
    private static final String ERROR_TITLE = "Worldz Error";
    private static final Ghosted debug = new Ghosted(Worldz.PROGRAM_NAME,
            Worldz.ERROR_MESSAGE, Worldz.ERROR_TITLE,
            Worldz.NON_FATAL_ERROR_MESSAGE);
    private static boolean IN_WORLDZ = true;

    // Methods
    public static Application getApplication() {
        return Worldz.application;
    }

    public static boolean inWorldz() {
        return Worldz.IN_WORLDZ;
    }

    public static void leaveWorldz() {
        if (MusicManager.isMusicPlaying()) {
            MusicManager.stopMusic();
        }
        Worldz.application.getOutputFrame().setVisible(false);
        Worldz.IN_WORLDZ = false;
    }

    public static Ghosted getDebug() {
        return Worldz.debug;
    }

    public static void main(final String[] args) {
        try {
            // Integrate with host platform
            final WorldPlatform platform = new WorldPlatform();
            platform.hookLAF();
            // Load all registered plugins
            final Vector<Object> plugins = PluginLoader
                    .loadAllRegisteredPlugins();
            // Inject early hooks
            PluginHooks.injectEarlyHooks(plugins);
            Worldz.application = new Application();
            Worldz.application.postConstruct();
            Worldz.application.playLogoSound();
            Worldz.application.getGUIManager().showGUI();
            // Register platform hooks
            platform.hookAbout(Worldz.application.getAboutDialog(),
                    Worldz.application.getAboutDialog().getClass()
                            .getDeclaredMethod("showAboutDialog"));
            String s;
            if (args.length == 0) {
                s = null;
            } else {
                s = args[0];
            }
            platform.hookFileOpen(
                    Worldz.application.getWorldManager(),
                    Worldz.application
                            .getWorldManager()
                            .getClass()
                            .getDeclaredMethod("loadFromOSHandler",
                                    String.class), s);
            platform.hookPreferences(Worldz.application.getPrefsManager(),
                    Worldz.application.getPrefsManager().getClass()
                            .getDeclaredMethod("showPrefs"));
            platform.hookQuit(Worldz.application.getWorldManager(),
                    Worldz.application.getWorldManager().getClass()
                            .getDeclaredMethod("quitHandler"));
            // Inject late hooks
            PluginHooks.injectLateHooks(plugins);
        } catch (final Throwable t) {
            Worldz.getDebug().debug(t);
        }
    }
}
