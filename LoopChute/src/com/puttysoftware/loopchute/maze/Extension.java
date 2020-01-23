/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.maze;

public class Extension {
    // Constants
    private static final String GAME_EXTENSION = "loopgame";
    private static final String PREFERENCES_EXTENSION = "xml";
    private static final String MAZE_EXTENSION = "loopchute";
    private static final String SAVED_GAME_EXTENSION = "loopsave";
    private static final String RULE_SET_EXTENSION = "looprules";

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

    public static String getMazeExtension() {
        return Extension.MAZE_EXTENSION;
    }

    public static String getMazeExtensionWithPeriod() {
        return "." + Extension.MAZE_EXTENSION;
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
}
