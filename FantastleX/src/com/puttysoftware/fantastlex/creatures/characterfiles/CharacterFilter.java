/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.creatures.characterfiles;

import java.io.File;
import java.io.FilenameFilter;

import com.puttysoftware.fantastlex.maze.Extension;

class CharacterFilter implements FilenameFilter {
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
