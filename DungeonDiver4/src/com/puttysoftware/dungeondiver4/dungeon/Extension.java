/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon;

public class Extension {
    // Constants
    private static final String GAME_EXTENSION = "dd4game";
    private static final String PREFERENCES_EXTENSION = "xml";
    private static final String DUNGEON_EXTENSION = "dd4dungeon";
    private static final String SAVED_GAME_EXTENSION = "dd4save";
    private static final String RULE_SET_EXTENSION = "dd4ruleset";
    private static final String CHARACTER_EXTENSION = "dd4character";
    private static final String REGISTRY_EXTENSION = "dd4registry";
    private static final String INTERNAL_DATA_EXTENSION = "dat";

    // Methods
    public static String getPreferencesExtension() {
        return Extension.PREFERENCES_EXTENSION;
    }

    public static String getGameExtension() {
        return Extension.GAME_EXTENSION;
    }

    public static String getGameExtensionWithPeriod() {
        return "." + Extension.GAME_EXTENSION;
    }

    public static String getDungeonExtension() {
        return Extension.DUNGEON_EXTENSION;
    }

    public static String getDungeonExtensionWithPeriod() {
        return "." + Extension.DUNGEON_EXTENSION;
    }

    public static String getSavedGameExtension() {
        return Extension.SAVED_GAME_EXTENSION;
    }

    public static String getSavedGameExtensionWithPeriod() {
        return "." + Extension.SAVED_GAME_EXTENSION;
    }

    public static String getRuleSetExtension() {
        return Extension.RULE_SET_EXTENSION;
    }

    public static String getRuleSetExtensionWithPeriod() {
        return "." + Extension.RULE_SET_EXTENSION;
    }

    public static String getCharacterExtension() {
        return Extension.CHARACTER_EXTENSION;
    }

    public static String getCharacterExtensionWithPeriod() {
        return "." + Extension.CHARACTER_EXTENSION;
    }

    public static String getRegistryExtensionWithPeriod() {
        return "." + Extension.REGISTRY_EXTENSION;
    }

    public static String getInternalDataExtensionWithPeriod() {
        return "." + Extension.INTERNAL_DATA_EXTENSION;
    }
}
