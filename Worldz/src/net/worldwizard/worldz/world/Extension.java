/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.world;

public class Extension {
    // Constants
    private static final String WORLD_EXTENSION = "wzw";
    private static final String SAVED_GAME_EXTENSION = "wzg";
    private static final String PREFERENCES_EXTENSION = "wzp";
    private static final String PLUGIN_EXTENSION = "wze";
    private static final String MUSIC_EXTENSION = "wzm";
    private static final String REGISTRY_EXTENSION = "wzr";
    private static final String RULE_SET_EXTENSION = "wzt";
    private static final String CHARACTER_EXTENSION = "wzc";

    // Methods
    public static String getWorldExtension() {
        return Extension.WORLD_EXTENSION;
    }

    public static String getWorldExtensionWithPeriod() {
        return "." + Extension.WORLD_EXTENSION;
    }

    public static String getGameExtension() {
        return Extension.SAVED_GAME_EXTENSION;
    }

    public static String getGameExtensionWithPeriod() {
        return "." + Extension.SAVED_GAME_EXTENSION;
    }

    public static String getPreferencesExtension() {
        return Extension.PREFERENCES_EXTENSION;
    }

    public static String getPluginExtension() {
        return Extension.PLUGIN_EXTENSION;
    }

    public static String getPluginExtensionWithPeriod() {
        return "." + Extension.PLUGIN_EXTENSION;
    }

    public static String getMusicExtension() {
        return Extension.MUSIC_EXTENSION;
    }

    public static String getMusicExtensionWithPeriod() {
        return "." + Extension.MUSIC_EXTENSION;
    }

    public static String getRegistryExtension() {
        return Extension.REGISTRY_EXTENSION;
    }

    public static String getRegistryExtensionWithPeriod() {
        return "." + Extension.REGISTRY_EXTENSION;
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
}
