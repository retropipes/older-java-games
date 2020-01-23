/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.maze;

public class Extension {
    // Constants
    private static final String GAME_EXTENSION = "mr2game2";
    private static final String PREFERENCES_EXTENSION = "xml";
    private static final String MAZE_EXTENSION = "mr2maze2";
    private static final String SAVED_GAME_EXTENSION = "mr2save2";
    private static final String RULE_SET_EXTENSION = "mr2rule";
    private static final String CHARACTER_EXTENSION = "mr2char";
    private static final String REGISTRY_EXTENSION = "mr2regi";
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
