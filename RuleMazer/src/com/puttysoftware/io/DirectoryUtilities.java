package com.puttysoftware.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class DirectoryUtilities {
    private DirectoryUtilities() {
        // Do nothing
    }

    public static final void removeDirectory(final File location)
            throws IOException {
        boolean success;
        if (location.isDirectory()) {
            final String[] children = location.list();
            for (final String element : children) {
                DirectoryUtilities.removeDirectory(new File(location, element));
            }
            success = location.delete();
            if (!success) {
                throw new IOException("Directory deletion failed!");
            }
        } else {
            success = location.delete();
            if (!success) {
                throw new IOException("Directory deletion failed!");
            }
        }
    }

    public static final boolean moveFile(final File sourceLocation,
            final File targetLocation) throws IOException {
        try (final InputStream in = new FileInputStream(sourceLocation);
                final OutputStream out = new FileOutputStream(targetLocation)) {
            // Copy the bits from instream to outstream
            final byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        }
        return sourceLocation.delete();
    }
}
