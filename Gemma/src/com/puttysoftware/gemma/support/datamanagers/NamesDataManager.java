/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell


 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.datamanagers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.puttysoftware.fileutils.ResourceStreamReader;
import com.puttysoftware.gemma.support.Support;
import com.puttysoftware.gemma.support.names.NamesConstants;

public class NamesDataManager {
    private static final String MAC_PREFIX = "HOME";
    private static final String WIN_PREFIX = "APPDATA";
    private static final String UNIX_PREFIX = "HOME";
    private static final String MAC_DIR = "/Library/Application Support/Putty Software/Gemma/";
    private static final String WIN_DIR = "\\Putty Software\\Gemma\\";
    private static final String UNIX_DIR = "/.puttysoftware/gemma/";

    public static String[] getNamesData() {
        try {
            final File overrideData = NamesDataManager.getNamesOverrideFile();
            // Version check
            if (overrideData.exists() && !NamesDataManager
                    .isNamesFileCorrectVersion(overrideData)) {
                final boolean success = overrideData.delete();
                if (!success) {
                    throw new IOException("Deleting override failed!");
                }
            }
            if (overrideData.exists()) {
                // Load global override
                try (FileInputStream fis = new FileInputStream(overrideData);
                        ResourceStreamReader rsr = new ResourceStreamReader(
                                fis)) {
                    return NamesDataManager.getDataInternal(rsr);
                }
            } else {
                // Load default
                try (ResourceStreamReader rsr = new ResourceStreamReader(
                        NamesDataManager.class.getResourceAsStream(
                                "/com/puttysoftware/gemma/support/resources/data/names/names.txt"))) {
                    return NamesDataManager.getDataInternal(rsr);
                }
            }
        } catch (final Exception e) {
            Support.getErrorLogger().logError(e);
            return null;
        }
    }

    private static String[] getDataInternal(final ResourceStreamReader rsr)
            throws IOException {
        final ArrayList<String> data = new ArrayList<>();
        // Ignore first line
        String raw = rsr.readString();
        while (raw != null) {
            raw = rsr.readString();
            data.add(raw);
        }
        final Object[] arr = data.toArray();
        final String[] tempres = new String[arr.length];
        int count = 0;
        for (int x = 0; x < arr.length; x++) {
            if (arr[x] != null) {
                tempres[x] = arr[x].toString();
                count++;
            }
        }
        final String[] res = new String[count];
        count = 0;
        for (final String tempre : tempres) {
            if (tempre != null) {
                res[count] = tempre;
                count++;
            }
        }
        return res;
    }

    public static void resetNames() {
        try {
            final File overrideData = NamesDataManager.getNamesOverrideFile();
            // Version check
            if (overrideData.exists()) {
                final boolean success = overrideData.delete();
                if (!success) {
                    throw new IOException("Deleting override failed!");
                }
            }
        } catch (final Exception e) {
            Support.getErrorLogger().logError(e);
        }
    }

    private static String getNamesDirPrefix() {
        final String osName = System.getProperty("os.name");
        if (osName.indexOf("Mac OS X") != -1) {
            // Mac OS X
            return System.getenv(NamesDataManager.MAC_PREFIX);
        } else if (osName.indexOf("Windows") != -1) {
            // Windows
            return System.getenv(NamesDataManager.WIN_PREFIX);
        } else {
            // Other - assume UNIX-like
            return System.getenv(NamesDataManager.UNIX_PREFIX);
        }
    }

    private static String getNamesDirectory() {
        final String osName = System.getProperty("os.name");
        if (osName.indexOf("Mac OS X") != -1) {
            // Mac OS X
            return NamesDataManager.MAC_DIR;
        } else if (osName.indexOf("Windows") != -1) {
            // Windows
            return NamesDataManager.WIN_DIR;
        } else {
            // Other - assume UNIX-like
            return NamesDataManager.UNIX_DIR;
        }
    }

    public static File getNamesOverrideFile() {
        final StringBuilder b = new StringBuilder();
        b.append(NamesDataManager.getNamesDirPrefix());
        b.append(NamesDataManager.getNamesDirectory());
        b.append("names.txt");
        return new File(b.toString());
    }

    private static boolean isNamesFileCorrectVersion(final File f) {
        try (FileInputStream fis = new FileInputStream(f);
                ResourceStreamReader rsr = new ResourceStreamReader(fis)) {
            final int version = rsr.readInt();
            return version == NamesConstants.NAMES_VERSION;
        } catch (final Exception e) {
            return false;
        }
    }
}
