package com.puttysoftware.fileutils;

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

    public static void copyDirectory(final File sourceLocation,
            final File targetLocation) throws IOException {
        if (sourceLocation.isDirectory()) {
            if (!targetLocation.exists()) {
                targetLocation.mkdir();
            }
            final String[] children = sourceLocation.list();
            for (int i = 0; i < children.length; i++) {
                copyDirectory(new File(sourceLocation, children[i]), new File(
                        targetLocation, children[i]));
            }
        } else {
            try (InputStream in = new FileInputStream(sourceLocation);
                    OutputStream out = new FileOutputStream(targetLocation)) {
                // Copy the bits from instream to outstream
                final byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            } catch (final IOException ioe) {
                throw ioe;
            }
        }
    }

    public static void removeDirectory(final File location) throws IOException {
        boolean success;
        if (location.isDirectory()) {
            final String[] children = location.list();
            for (int i = 0; i < children.length; i++) {
                removeDirectory(new File(location, children[i]));
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
}
