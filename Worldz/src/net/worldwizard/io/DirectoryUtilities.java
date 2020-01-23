package net.worldwizard.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public final class DirectoryUtilities {
    private DirectoryUtilities() {
        // Do nothing
    }

    public static final void zipDirectory(final File directory, final File zip)
            throws IOException {
        try (final ZipOutputStream zos = new ZipOutputStream(
                new FileOutputStream(zip))) {
            DirectoryUtilities.zip(directory, directory, zos);
        }
    }

    private static final void zip(final File directory, final File base,
            final ZipOutputStream zos) throws IOException {
        final File[] files = directory.listFiles();
        final byte[] buffer = new byte[8192];
        int read = 0;
        for (final File file : files) {
            if (file.isDirectory()) {
                DirectoryUtilities.zip(file, base, zos);
            } else {
                try (final FileInputStream in = new FileInputStream(file)) {
                    final ZipEntry entry = new ZipEntry(file.getPath()
                            .substring(base.getPath().length() + 1));
                    zos.putNextEntry(entry);
                    while (-1 != (read = in.read(buffer))) {
                        zos.write(buffer, 0, read);
                    }
                }
            }
        }
    }

    public static final void unzipDirectory(final File zip, final File extractTo)
            throws IOException {
        try (final ZipFile archive = new ZipFile(zip)) {
            final Enumeration<? extends ZipEntry> e = archive.entries();
            while (e.hasMoreElements()) {
                final ZipEntry entry = e.nextElement();
                final File file = new File(extractTo, entry.getName());
                if (entry.isDirectory() && !file.exists()) {
                    file.mkdirs();
                } else {
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    try (final InputStream in = archive.getInputStream(entry);
                            final BufferedOutputStream out = new BufferedOutputStream(
                                    new FileOutputStream(file))) {
                        final byte[] buffer = new byte[8192];
                        int read;
                        while (-1 != (read = in.read(buffer))) {
                            out.write(buffer, 0, read);
                        }
                    }
                }
            }
        }
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

    public static final void copyDirectory(final File sourceLocation,
            final File targetLocation) throws IOException {
        if (sourceLocation.isDirectory()) {
            if (!targetLocation.exists()) {
                final boolean success = targetLocation.mkdir();
                if (!success) {
                    throw new IOException("Could not make directory!");
                }
            }
            final String[] children = sourceLocation.list();
            for (final String element : children) {
                DirectoryUtilities.copyDirectory(new File(sourceLocation,
                        element), new File(targetLocation, element));
            }
        } else {
            try (final InputStream in = new FileInputStream(sourceLocation);
                    final OutputStream out = new FileOutputStream(
                            targetLocation)) {
                // Copy the bits from instream to outstream
                final byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }
        }
    }

    public static final void moveFile(final File sourceLocation,
            final File targetLocation) throws IOException {
        try (final InputStream in = new FileInputStream(sourceLocation);
                final OutputStream out = new FileOutputStream(targetLocation)) {
            // Copy the bits from instream to outstream
            final byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            sourceLocation.delete();
        }
    }
}
