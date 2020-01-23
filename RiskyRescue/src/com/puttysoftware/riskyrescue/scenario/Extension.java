/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue.scenario;

public class Extension {
    // Constants
    private static final String PREFERENCES_EXTENSION = "xml";
    private static final String REGISTRY_EXTENSION = "gemreg";
    private static final String SCENARIO_EXTENSION = "gemadv";
    private static final String SAVED_GAME_EXTENSION = "gemsav";

    // Methods
    public static String getPreferencesExtension() {
        return Extension.PREFERENCES_EXTENSION;
    }

    public static String getRegistryExtensionWithPeriod() {
        return "." + Extension.REGISTRY_EXTENSION;
    }

    static String getScenarioExtensionWithPeriod() {
        return "." + Extension.SCENARIO_EXTENSION;
    }

    public static String getGameExtension() {
        return Extension.SAVED_GAME_EXTENSION;
    }

    public static String getGameExtensionWithPeriod() {
        return "." + Extension.SAVED_GAME_EXTENSION;
    }
}
