/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.files;

public class Extension {
    // Constants
    private static final String LOCKED_MAZE_EXTENSION = "5dl";
    private static final String PREFERENCES_EXTENSION = "5dp";

    // Methods
    public static String getPreferencesExtension() {
        return Extension.PREFERENCES_EXTENSION;
    }

    public static String getLockedMazeExtension() {
        return Extension.LOCKED_MAZE_EXTENSION;
    }

    public static String getLockedMazeExtensionWithPeriod() {
        return "." + Extension.LOCKED_MAZE_EXTENSION;
    }
}
