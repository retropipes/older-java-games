package net.worldwizard.dungeondiver;

import java.io.File;
import java.io.FilenameFilter;

public class SavedStateFilterMacOSX implements FilenameFilter {
    @Override
    public boolean accept(final File dir, final String name) {
        if (dir.isDirectory()) {
            return true;
        }
        final String extension = SavedStateFilterMacOSX.getExtension(name);
        if (extension != null) {
            if (extension.equals(SavedStateIdentifier.getIdentifier())) {
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