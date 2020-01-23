/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.maze;

public class Extension {
    // Constants
    private static final String MAZE_2_EXTENSION = "ftlmz";
    private static final String MAZE_3_EXTENSION = "ft3mz";
    private static final String MAZE_4_EXTENSION = "ft4mz";
    private static final String MAZE_5_EXTENSION = "ft5mz";
    private static final String SAVED_GAME_EXTENSION = "ft5sg";
    private static final String SCORES_EXTENSION = "ft5sc";
    private static final String PREFERENCES_EXTENSION = "ft5prf";

    // Methods
    public static String getMaze2Extension() {
	return Extension.MAZE_2_EXTENSION;
    }

    public static String getMaze2ExtensionWithPeriod() {
	return "." + Extension.MAZE_2_EXTENSION;
    }

    public static String getMaze3Extension() {
	return Extension.MAZE_3_EXTENSION;
    }

    public static String getMaze3ExtensionWithPeriod() {
	return "." + Extension.MAZE_3_EXTENSION;
    }

    public static String getMaze4Extension() {
	return Extension.MAZE_4_EXTENSION;
    }

    public static String getMaze4ExtensionWithPeriod() {
	return "." + Extension.MAZE_4_EXTENSION;
    }

    public static String getMaze5Extension() {
	return Extension.MAZE_5_EXTENSION;
    }

    public static String getMaze5ExtensionWithPeriod() {
	return "." + Extension.MAZE_5_EXTENSION;
    }

    public static String getGameExtension() {
	return Extension.SAVED_GAME_EXTENSION;
    }

    public static String getGameExtensionWithPeriod() {
	return "." + Extension.SAVED_GAME_EXTENSION;
    }

    public static String getScoresExtension() {
	return Extension.SCORES_EXTENSION;
    }

    public static String getScoresExtensionWithPeriod() {
	return "." + Extension.SCORES_EXTENSION;
    }

    public static String getPreferencesExtension() {
	return Extension.PREFERENCES_EXTENSION;
    }

    public static String getPreferencesExtensionWithPeriod() {
	return "." + Extension.PREFERENCES_EXTENSION;
    }
}
