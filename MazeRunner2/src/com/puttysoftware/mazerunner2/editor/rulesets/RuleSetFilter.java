/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.editor.rulesets;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import com.puttysoftware.mazerunner2.maze.Extension;

public class RuleSetFilter extends FileFilter {
    @Override
    public boolean accept(final File f) {
        if (f.isDirectory()) {
            return true;
        }
        final String extension = RuleSetFilter.getExtension(f);
        if (extension != null) {
            if (extension.equals(Extension.getRuleSetExtension())) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public String getDescription() {
        return "MazeRunnerII Rule Sets ("
                + Extension.getRuleSetExtensionWithPeriod() + ")";
    }

    private static String getExtension(final File f) {
        String ext = null;
        final String s = f.getName();
        final int i = s.lastIndexOf('.');
        if ((i > 0) && (i < s.length() - 1)) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }
}
