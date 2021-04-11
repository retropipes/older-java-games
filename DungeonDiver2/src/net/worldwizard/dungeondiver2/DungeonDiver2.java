/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.dungeondiver2;

import java.awt.desktop.PreferencesEvent;
import java.awt.desktop.PreferencesHandler;

import net.worldwizard.commondialogs.CommonDialogs;
import net.worldwizard.dungeondiver2.prefs.PreferencesManager;
import net.worldwizard.errorlogger.ErrorLogger;
import net.worldwizard.platform.Platform;
import net.worldwizard.support.Support;
import net.worldwizard.support.ai.AIRegistration;
import net.worldwizard.support.map.Map;
import net.worldwizard.support.variables.SystemVariablesLoader;

public class DungeonDiver2 {
    // Constants
    private static Application application;
    private static final String PROGRAM_NAME = "DungeonDiver2";

    // Methods
    public static Application getApplication() {
        return DungeonDiver2.application;
    }

    public static ErrorLogger getErrorLogger() {
        return Support.getErrorLogger();
    }

    public static ErrorLogger getNonFatalLogger() {
        return Support.getNonFatalLogger();
    }

    public static String getProgramName() {
        return DungeonDiver2.PROGRAM_NAME;
    }

    public static void main(final String[] args) {
        try {
            // Integrate with host platform
            final Platform platform = new Platform();
            platform.configureLookAndFeel();
            // Load system variables
            SystemVariablesLoader.loadSystemVariables();
            // Create local variables
            Support.createVariables();
            // Initialize application
            DungeonDiver2.application = new Application();
            DungeonDiver2.application.postConstruct();
            DungeonDiver2.application.playLogoSound();
            DungeonDiver2.application.getGUIManager().showGUI();
            // Initialize map
            DungeonDiver2.application.getVariablesManager().setMap(new Map());
            DungeonDiver2.application.getVariablesManager().getMap().addLevel(
                    Support.MAP_ROWS, Support.MAP_COLS, Support.MAP_FLOORS);
            // Register platform hooks
            platform.setAboutHandler(
                    DungeonDiver2.application.getAboutDialog());
            platform.setOpenFileHandler(
                    DungeonDiver2.application.getVariablesManager());
            platform.setPreferencesHandler(new PreferencesLauncher());
            platform.setQuitHandler(DungeonDiver2.application.getGUIManager());
            // Activate registered AIs
            AIRegistration.activateRegisteredAIs();
            // Set default title
            CommonDialogs.setDefaultTitle(DungeonDiver2.PROGRAM_NAME);
        } catch (final Throwable t) {
            DungeonDiver2.getErrorLogger().logError(t);
        }
    }

    private static class PreferencesLauncher implements PreferencesHandler {
        @Override
        public void handlePreferences(PreferencesEvent inE) {
            PreferencesManager.showPrefs();
        }
    }
}
