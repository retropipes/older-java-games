/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.maze.xml;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class XMLMazeFilter extends FileFilter {
    @Override
    public boolean accept(final File f) {
        if (f.isDirectory()) {
            return true;
        }
        final String extension = XMLMazeFilter.getExtension(f);
        if (extension != null) {
            if (extension.equals(XMLExtension.getXMLMazeExtension())) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public String getDescription() {
        return "RuleMazer XML Mazes ("
                + XMLExtension.getXMLMazeExtensionWithPeriod() + ")";
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
