/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.maze;

public class Extension {
    // Constants
    private static final String PREFERENCES_EXTENSION = "xml";
    private static final String MODE_EXTENSION = "mode";
    private static final String REGISTRY_EXTENSION = "regi";
    private static final String MAZE_EXTENSION = "maze";
    private static final String SAVED_GAME_EXTENSION = "game";
    private static final String SCORES_EXTENSION = "scor";
    private static final String RULE_SET_EXTENSION = "rule";

    // Methods
    public static String getPreferencesExtension() {
        return Extension.PREFERENCES_EXTENSION;
    }

    public static String getModeExtension() {
        return Extension.MODE_EXTENSION;
    }

    public static String getModeExtensionWithPeriod() {
        return "." + Extension.MODE_EXTENSION;
    }

    public static String getRegistryExtensionWithPeriod() {
        return "." + Extension.REGISTRY_EXTENSION;
    }

    public static String getMazeExtension() {
        return Extension.MAZE_EXTENSION;
    }

    public static String getMazeExtensionWithPeriod() {
        return "." + Extension.MAZE_EXTENSION;
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

    public static String getRuleSetExtension() {
        return Extension.RULE_SET_EXTENSION;
    }

    public static String getRuleSetExtensionWithPeriod() {
        return "." + Extension.RULE_SET_EXTENSION;
    }
}
