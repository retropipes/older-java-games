/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.dungeondiver3.prefs.PreferencesManager;
import com.puttysoftware.dungeondiver3.resourcemanagers.LogoManager;
import com.puttysoftware.dungeondiver3.support.Support;
import com.puttysoftware.dungeondiver3.support.creatures.PartyManager;
import com.puttysoftware.errorlogger.ErrorLogger;
import com.puttysoftware.platform.Platform;

public class DungeonDiver3 {
    // Constants
    private static Application application;
    private static final String PROGRAM_NAME = "DungeonDiver3";
    public static final int GENERATOR_RANDOMNESS_MAX = 6;

    // Methods
    public static Application getApplication() {
        return DungeonDiver3.application;
    }

    public static ErrorLogger getErrorLogger() {
        return Support.getErrorLogger();
    }

    public static ErrorLogger getNonFatalLogger() {
        return Support.getNonFatalLogger();
    }

    public static String getProgramName() {
        return DungeonDiver3.PROGRAM_NAME;
    }

    public static void newScenario() {
        if (Support.getScenario() != null) {
            // Check character writeback
            if (PreferencesManager.areCharacterChangesPermanent()) {
                PartyManager.writebackCharacters();
            }
            Support.deleteScenario();
            DungeonDiver3.application.getScenarioManager().setMap(null);
        }
        Support.createScenario();
        PartyManager.revivePartyFully();
    }

    public static void main(final String[] args) {
        try {
            String suffix;
            if (Support.inDebugMode()) {
                suffix = " (DEBUG)";
            } else {
                suffix = "";
            }
            // Integrate with host platform
            Platform.hookLAF(DungeonDiver3.getProgramName());
            // Set defaults
            CommonDialogs.setDefaultTitle(DungeonDiver3.PROGRAM_NAME + suffix);
            CommonDialogs.setIcon(LogoManager.getMicroLogo());
            // Initialization
            Support.preInit();
            DungeonDiver3.application = new Application();
            DungeonDiver3.application.postConstruct();
            Application.playLogoSound();
            DungeonDiver3.application.getGUIManager().showGUI();
            // Register platform hooks
            Platform.hookAbout(DungeonDiver3.application.getAboutDialog(),
                    DungeonDiver3.application.getAboutDialog().getClass()
                            .getDeclaredMethod("showAboutDialog"));
            String s;
            if (args.length == 0) {
                s = null;
            } else {
                s = args[0];
            }
            Platform.hookFileOpen(
                    DungeonDiver3.application.getScenarioManager(),
                    DungeonDiver3.application.getScenarioManager().getClass()
                            .getDeclaredMethod("loadFromOSHandler",
                                    String.class),
                    s);
            Platform.hookPreferences(PreferencesManager.class,
                    PreferencesManager.class.getDeclaredMethod("showPrefs"));
            Platform.hookQuit(DungeonDiver3.application.getGUIManager(),
                    DungeonDiver3.application.getGUIManager().getClass()
                            .getDeclaredMethod("quitHandler"));
            Platform.hookDockIcon(LogoManager.getLogo());
        } catch (final Throwable t) {
            DungeonDiver3.getErrorLogger().logError(t);
        }
    }
}
