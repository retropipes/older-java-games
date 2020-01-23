package com.puttysoftware.fileutils;

import java.io.File;
import java.io.IOException;

public final class DirectoryUtilities {
    private DirectoryUtilities() {
        // Do nothing
    }

    public static void removeDirectory(final File location) throws IOException {
        if (location == null) {
            // Abort
            return;
        }
        boolean success;
        if (location.isDirectory()) {
            final String[] children = location.list();
            for (final String element : children) {
                if (element != null) {
                    DirectoryUtilities
                            .removeDirectory(new File(location, element));
                }
            }
            success = location.delete();
            if (!success) {
                throw new IOException("Directory deletion failed!"); //$NON-NLS-1$
            }
        } else {
            success = location.delete();
            if (!success) {
                throw new IOException("Directory deletion failed!"); //$NON-NLS-1$
            }
        }
    }
}
