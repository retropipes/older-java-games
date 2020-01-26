/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.maze.legacy.games;

import java.io.File;
import java.io.FilenameFilter;

import com.puttysoftware.mazerunner2.maze.legacy.LegacyExtension;

public class LegacyGameFinder implements FilenameFilter {
    @Override
    public boolean accept(final File f, final String s) {
        final String extension = LegacyGameFinder.getExtension(s);
        if (extension != null) {
            if (extension.equals(LegacyExtension.getLegacyGameExtension())) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    private static String getExtension(final String s) {
        String ext = null;
        final int i = s.lastIndexOf('.');
        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }
}
