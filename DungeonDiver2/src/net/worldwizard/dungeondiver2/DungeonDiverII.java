/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.dungeondiver2;

import net.worldwizard.commondialogs.CommonDialogs;
import net.worldwizard.dungeondiver2.prefs.PreferencesManager;
import net.worldwizard.errorlogger.ErrorLogger;
import net.worldwizard.platform.Platform;
import net.worldwizard.support.Support;
import net.worldwizard.support.ai.AIRegistration;
import net.worldwizard.support.map.Map;
import net.worldwizard.support.variables.SystemVariablesLoader;

public class DungeonDiverII {
    // Constants
    private static Application application;
    private static final String PROGRAM_NAME = "DungeonDiverII";

    // Methods
    public static Application getApplication() {
        return DungeonDiverII.application;
    }

    public static ErrorLogger getErrorLogger() {
        return Support.getErrorLogger();
    }

    public static ErrorLogger getNonFatalLogger() {
        return Support.getNonFatalLogger();
    }

    public static String getProgramName() {
        return DungeonDiverII.PROGRAM_NAME;
    }

    public static void main(final String[] args) {
        try {
            // Integrate with host platform
            final Platform platform = new Platform();
            platform.hookLAF(DungeonDiverII.getProgramName());
            // Load system variables
            SystemVariablesLoader.loadSystemVariables();
            // Create local variables
            Support.createVariables();
            // Initialize application
            DungeonDiverII.application = new Application();
            DungeonDiverII.application.postConstruct();
            DungeonDiverII.application.playLogoSound();
            DungeonDiverII.application.getGUIManager().showGUI();
            // Initialize map
            DungeonDiverII.application.getVariablesManager().setMap(new Map());
            DungeonDiverII.application.getVariablesManager().getMap().addLevel(
                    Support.MAP_ROWS, Support.MAP_COLS, Support.MAP_FLOORS);
            // Register platform hooks
            platform.hookAbout(DungeonDiverII.application.getAboutDialog(),
                    DungeonDiverII.application.getAboutDialog().getClass()
                            .getDeclaredMethod("showAboutDialog"));
            String s;
            if (args.length == 0) {
                s = null;
            } else {
                s = args[0];
            }
            platform.hookFileOpen(
                    DungeonDiverII.application.getVariablesManager(),
                    DungeonDiverII.application.getVariablesManager().getClass()
                            .getDeclaredMethod("loadFromOSHandler",
                                    String.class),
                    s);
            platform.hookPreferences(PreferencesManager.class,
                    PreferencesManager.class.getDeclaredMethod("showPrefs"));
            platform.hookQuit(DungeonDiverII.application.getGUIManager(),
                    DungeonDiverII.application.getGUIManager().getClass()
                            .getDeclaredMethod("quitHandler"));
            // Activate registered AIs
            AIRegistration.activateRegisteredAIs();
            // Set default title
            CommonDialogs.setDefaultTitle(DungeonDiverII.PROGRAM_NAME);
        } catch (final Throwable t) {
            DungeonDiverII.getErrorLogger().logError(t);
        }
    }
}
