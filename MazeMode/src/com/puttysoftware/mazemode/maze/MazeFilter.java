package com.puttysoftware.mazemode.maze;

import java.io.File;
import java.io.FilenameFilter;

public class MazeFilter implements FilenameFilter {
    @Override
    public boolean accept(final File dir, final String name) {
        final String ext = MazeFilter.getExtension(name);
        if (ext != null) {
            if (ext.equals(Extension.getMazeExtension())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
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
