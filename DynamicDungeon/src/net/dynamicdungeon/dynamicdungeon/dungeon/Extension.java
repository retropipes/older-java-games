/*  DynamicDungeon: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.dungeon;

public class Extension {
    // Constants
    private static final String GAME_EXTENSION = "ddgam";
    private static final String CHARACTER_EXTENSION = "ddchr";
    private static final String PREFERENCES_EXTENSION = "xml";
    private static final String REGISTRY_EXTENSION = "txt";
    private static final String INTERNAL_DATA_EXTENSION = "dat";
    private static final String MUSIC_EXTENSION = "mod";

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

    public static String getMusicExtensionWithPeriod() {
	return "." + Extension.MUSIC_EXTENSION;
    }
}
