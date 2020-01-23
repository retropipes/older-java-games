/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze;

public class FileExtension {
    // Constants
    private static final String LOCKED_MAZE_EXTENSION = "wwl";
    private static final String PREFERENCES_EXTENSION = "xml";
    private static final String PLUGIN_EXTENSION = "wwp";
    private static final String REGISTRY_EXTENSION = "wwr";
    private static final String MAZE_EXTENSION = "wwm";
    private static final String SAVED_GAME_EXTENSION = "wwg";
    private static final String SCORES_EXTENSION = "wws";
    private static final String RULE_SET_EXTENSION = "wwt";

    // Methods
    public static String getPreferencesExtension() {
        return FileExtension.PREFERENCES_EXTENSION;
    }

    public static String getLockedMazeExtension() {
        return FileExtension.LOCKED_MAZE_EXTENSION;
    }

    public static String getLockedMazeExtensionWithPeriod() {
        return "." + FileExtension.LOCKED_MAZE_EXTENSION;
    }

    public static String getPluginExtension() {
        return FileExtension.PLUGIN_EXTENSION;
    }

    public static String getPluginExtensionWithPeriod() {
        return "." + FileExtension.PLUGIN_EXTENSION;
    }

    public static String getRegistryExtensionWithPeriod() {
        return "." + FileExtension.REGISTRY_EXTENSION;
    }

    public static String getMazeExtension() {
        return FileExtension.MAZE_EXTENSION;
    }

    public static String getMazeExtensionWithPeriod() {
        return "." + FileExtension.MAZE_EXTENSION;
    }

    public static String getGameExtension() {
        return FileExtension.SAVED_GAME_EXTENSION;
    }

    public static String getGameExtensionWithPeriod() {
        return "." + FileExtension.SAVED_GAME_EXTENSION;
    }

    public static String getScoresExtension() {
        return FileExtension.SCORES_EXTENSION;
    }

    public static String getScoresExtensionWithPeriod() {
        return "." + FileExtension.SCORES_EXTENSION;
    }

    public static String getRuleSetExtension() {
        return FileExtension.RULE_SET_EXTENSION;
    }

    public static String getRuleSetExtensionWithPeriod() {
        return "." + FileExtension.RULE_SET_EXTENSION;
    }
}
