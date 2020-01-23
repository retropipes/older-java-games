/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.creatures.characterfiles;

import java.io.File;
import java.io.FilenameFilter;

import com.puttysoftware.gemma.support.scenario.Extension;

class CharacterFilter implements FilenameFilter {
    @Override
    public boolean accept(File dir, String name) {
        String ext = CharacterFilter.getExtension(name);
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
        if ((i > 0) && (i < s.length() - 1)) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }
}
