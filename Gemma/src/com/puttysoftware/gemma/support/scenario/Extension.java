/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.scenario;

public class Extension {
    // Constants
    private static final String PREFERENCES_EXTENSION = "xml";
    private static final String REGISTRY_EXTENSION = "gemreg";
    private static final String SCENARIO_EXTENSION = "gemadv";
    private static final String SAVED_GAME_EXTENSION = "gemsav";
    private static final String CHARACTER_EXTENSION = "gemchr";
    private static final String INTERNAL_DATA_EXTENSION = "dat";

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

    public static String getCharacterExtension() {
        return Extension.CHARACTER_EXTENSION;
    }

    public static String getCharacterExtensionWithPeriod() {
        return "." + Extension.CHARACTER_EXTENSION;
    }

    public static String getRaceExtensionWithPeriod() {
        return "." + Extension.INTERNAL_DATA_EXTENSION;
    }

    public static String getPersonalityExtensionWithPeriod() {
        return "." + Extension.INTERNAL_DATA_EXTENSION;
    }

    public static String getFaithExtensionWithPeriod() {
        return "." + Extension.INTERNAL_DATA_EXTENSION;
    }

    public static String getDescriptionExtensionWithPeriod() {
        return "." + Extension.INTERNAL_DATA_EXTENSION;
    }
}
