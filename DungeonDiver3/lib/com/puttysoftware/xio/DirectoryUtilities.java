package com.puttysoftware.xio;

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

    public static void copyDirectory(File sourceLocation, File targetLocation)
            throws IOException {
        if (sourceLocation.isDirectory()) {
            if (!targetLocation.exists()) {
                targetLocation.mkdir();
            }
            String[] children = sourceLocation.list();
            for (int i = 0; i < children.length; i++) {
                copyDirectory(new File(sourceLocation, children[i]), new File(
                        targetLocation, children[i]));
            }
        } else {
            try (InputStream in = new FileInputStream(sourceLocation);
                    OutputStream out = new FileOutputStream(targetLocation)) {
                // Copy the bits from instream to outstream
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }
        }
    }

    public static void removeDirectory(File location) throws IOException {
        boolean success;
        if (location.isDirectory()) {
            String[] children = location.list();
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

    public static void copyFile(File sourceLocation, File targetLocation)
            throws IOException {
        try (InputStream in = new FileInputStream(sourceLocation);
                OutputStream out = new FileOutputStream(targetLocation)) {
            // Copy the bits from instream to outstream
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        }
    }

    public static boolean moveFile(File sourceLocation, File targetLocation)
            throws IOException {
        try (InputStream in = new FileInputStream(sourceLocation);
                OutputStream out = new FileOutputStream(targetLocation)) {
            // Copy the bits from instream to outstream
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        }
        return sourceLocation.delete();
    }

    public static void copyRAMFile(InputStream in, File targetLocation)
            throws IOException {
        try (OutputStream out = new FileOutputStream(targetLocation)) {
            // Copy the bits from instream to outstream
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
        }
    }
}
