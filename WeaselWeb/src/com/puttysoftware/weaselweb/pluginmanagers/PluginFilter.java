package com.puttysoftware.weaselweb.pluginmanagers;

import java.io.File;
import java.io.FilenameFilter;

import com.puttysoftware.weaselweb.maze.FileExtension;

public class PluginFilter implements FilenameFilter {
    @Override
    public boolean accept(final File dir, final String name) {
        final String ext = PluginFilter.getExtension(name);
        if (ext != null) {
            if (ext.equals(FileExtension.getPluginExtension())) {
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
