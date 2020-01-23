package net.worldwizard.support.creatures.characterfiles;

import java.io.File;
import java.io.FilenameFilter;

import net.worldwizard.support.variables.Extension;

public class CharacterFilter implements FilenameFilter {
    @Override
    public boolean accept(final File dir, final String name) {
        final String ext = CharacterFilter.getExtension(name);
        if (ext != null) {
            if (ext.equals(Extension.getCharacterExtension())) {
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
