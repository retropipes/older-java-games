/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.manager.file;

public class Extension {
    // Constants
    private static final String GAME_EXTENSION = "savzip";
    private static final String PREFERENCES_EXTENSION = "xml";
    private static final String CHARACTER_EXTENSION = "chrxml";
    private static final String REGISTRY_EXTENSION = "regtxt";
    private static final String INTERNAL_DATA_EXTENSION = "txt";
    private static final String MUSIC_EXTENSION = "ogg";
    private static final String SOUND_EXTENSION = "wav";
    private static final String STRING_EXTENSION = "properties";

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

    public static String getSoundExtensionWithPeriod() {
        return "." + Extension.SOUND_EXTENSION;
    }

    public static String getStringExtensionWithPeriod() {
        return "." + Extension.STRING_EXTENSION;
    }
}
