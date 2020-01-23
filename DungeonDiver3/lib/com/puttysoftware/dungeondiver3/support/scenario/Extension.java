/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.scenario;

public class Extension {
    // Constants
    private static final String PREFERENCES_EXTENSION = "dd3prefs";
    private static final String REGISTRY_EXTENSION = "dd3registry";
    private static final String SCENARIO_EXTENSION = "dd3scenario";
    private static final String SAVED_GAME_EXTENSION = "dd3game";
    private static final String CHARACTER_EXTENSION = "dd3character";
    private static final String RACE_EXTENSION = "dd3race";
    private static final String PERSONALITY_EXTENSION = "dd3personality";
    private static final String FAITH_EXTENSION = "dd3faith";
    private static final String DESCRIPTION_EXTENSION = "dd3description";

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
        return "." + Extension.RACE_EXTENSION;
    }

    public static String getPersonalityExtensionWithPeriod() {
        return "." + Extension.PERSONALITY_EXTENSION;
    }

    public static String getFaithExtensionWithPeriod() {
        return "." + Extension.FAITH_EXTENSION;
    }

    public static String getDescriptionExtensionWithPeriod() {
        return "." + Extension.DESCRIPTION_EXTENSION;
    }
}
