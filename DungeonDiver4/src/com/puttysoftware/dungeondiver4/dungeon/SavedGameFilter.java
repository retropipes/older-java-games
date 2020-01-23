/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class SavedGameFilter extends FileFilter {
    @Override
    public boolean accept(final File f) {
        if (f.isDirectory()) {
            return true;
        }
        final String extension = SavedGameFilter.getExtension(f);
        if (extension != null) {
            if (extension.equals(Extension.getSavedGameExtension())) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public String getDescription() {
        return "DungeonDiver4 Saved Games ("
                + Extension.getSavedGameExtensionWithPeriod() + ")";
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
