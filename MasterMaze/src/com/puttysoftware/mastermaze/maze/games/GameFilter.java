/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.games;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import com.puttysoftware.mastermaze.maze.Extension;

public class GameFilter extends FileFilter {
    @Override
    public boolean accept(final File f) {
        if (f.isDirectory()) {
            return true;
        }
        final String extension = GameFilter.getExtension(f);
        if (extension != null) {
            if (extension.equals(Extension.getGameExtension())) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public String getDescription() {
        return "MasterMaze Games (" + Extension.getGameExtensionWithPeriod()
                + ")";
    }

    private static String getExtension(final File f) {
        String ext = null;
        final String s = f.getName();
        final int i = s.lastIndexOf('.');
        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }
}
