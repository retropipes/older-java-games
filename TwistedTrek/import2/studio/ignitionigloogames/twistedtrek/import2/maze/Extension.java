/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.maze;

public class Extension {
    // Constants
    private static final String GAME_EXTENSION = "ttgame";
    private static final String PREFERENCES_EXTENSION = "ttpref";
    private static final String CHARACTER_EXTENSION = "ttchar";
    private static final String REGISTRY_EXTENSION = "ttregi";
    private static final String INTERNAL_DATA_EXTENSION = "dat";
    private static final String MUSIC_EXTENSION = "tttune";

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
