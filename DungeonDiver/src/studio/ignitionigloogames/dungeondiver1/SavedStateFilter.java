package studio.ignitionigloogames.dungeondiver1;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class SavedStateFilter extends FileFilter {
    @Override
    public boolean accept(final File f) {
        if (f.isDirectory()) {
            return true;
        }
        final String extension = SavedStateFilter.getExtension(f);
        if (extension != null) {
            if (extension.equals(SavedStateIdentifier.getIdentifier())) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public String getDescription() {
        return "Saved States";
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