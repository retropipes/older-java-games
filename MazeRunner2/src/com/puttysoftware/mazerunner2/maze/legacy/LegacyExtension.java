/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.maze.legacy;

public class LegacyExtension {
    // Constants
    private static final String LEGACY_GAME_EXTENSION = "mr2game";
    private static final String LEGACY_MAZE_EXTENSION = "mr2maze";
    private static final String LEGACY_SAVED_GAME_EXTENSION = "mr2save";

    // Methods
    public static String getLegacyGameExtension() {
        return LegacyExtension.LEGACY_GAME_EXTENSION;
    }

    public static String getLegacyGameExtensionWithPeriod() {
        return "." + LegacyExtension.LEGACY_GAME_EXTENSION;
    }

    public static String getLegacyMazeExtension() {
        return LegacyExtension.LEGACY_MAZE_EXTENSION;
    }

    public static String getLegacyMazeExtensionWithPeriod() {
        return "." + LegacyExtension.LEGACY_MAZE_EXTENSION;
    }

    public static String getLegacySavedGameExtension() {
        return LegacyExtension.LEGACY_SAVED_GAME_EXTENSION;
    }

    public static String getLegacySavedGameExtensionWithPeriod() {
        return "." + LegacyExtension.LEGACY_SAVED_GAME_EXTENSION;
    }
}
