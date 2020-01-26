package com.puttysoftware.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public final class ZipUtilities {
    private ZipUtilities() {
        // Do nothing
    }

    public static void zipDirectory(final File directory, final File zip)
            throws IOException {
        try (final ZipOutputStream zos = new ZipOutputStream(
                new FileOutputStream(zip))) {
            ZipUtilities.zip(directory, directory, zos);
        }
    }

    private static void zip(final File directory, final File base,
            final ZipOutputStream zos) throws IOException {
        final File[] files = directory.listFiles();
        final byte[] buffer = new byte[8192];
        int read = 0;
        for (final File file : files) {
            if (file.isDirectory()) {
                ZipUtilities.zip(file, base, zos);
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

    public static void unzipDirectory(final File zip, final File extractTo)
            throws IOException {
        try (final ZipFile archive = new ZipFile(zip)) {
            final Enumeration<? extends ZipEntry> e = archive.entries();
            while (e.hasMoreElements()) {
                final ZipEntry entry = e.nextElement();
                final File file = new File(extractTo, entry.getName());
                if (entry.isDirectory() && !file.exists()) {
                    final boolean res = file.mkdirs();
                    if (!res) {
                        throw new IOException("Couldn't make folders!");
                    }
                } else {
                    if (!file.getParentFile().exists()) {
                        final boolean res = file.getParentFile().mkdirs();
                        if (!res) {
                            throw new IOException("Couldn't make folders!");
                        }
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
}
