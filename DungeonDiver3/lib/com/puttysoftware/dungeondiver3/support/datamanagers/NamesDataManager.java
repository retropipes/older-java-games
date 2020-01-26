/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.datamanagers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.puttysoftware.dungeondiver3.support.Support;
import com.puttysoftware.dungeondiver3.support.names.NamesConstants;
import com.puttysoftware.fileutils.ResourceStreamReader;

public class NamesDataManager {
    private static final String MAC_PREFIX = "HOME";
    private static final String WIN_PREFIX = "APPDATA";
    private static final String UNIX_PREFIX = "HOME";
    private static final String MAC_DIR = "/Library/Application Support/Putty Software/DungeonDiver3/";
    private static final String WIN_DIR = "\\Putty Software\\DungeonDiver3\\";
    private static final String UNIX_DIR = "/.puttysoftware/dungeondiver3/";

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
                return NamesDataManager.getNamesOverrideData(overrideData);
            } else {
                // Load default
                return NamesDataManager.getNamesDefaultData();
            }
        } catch (final Exception e) {
            Support.getErrorLogger().logError(e);
            return null;
        }
    }

    private static String[] getNamesOverrideData(final File overrideData)
            throws IOException {
        ResourceStreamReader rsr = null;
        // Load global override
        try (FileInputStream fis = new FileInputStream(overrideData)) {
            rsr = new ResourceStreamReader(fis);
            final ArrayList<String> data = new ArrayList<>();
            // Ignore first line
            String raw = rsr.readString();
            while (raw != null) {
                raw = rsr.readString();
                data.add(raw);
            }
            rsr.close();
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
    }

    private static String[] getNamesDefaultData() throws IOException {
        ResourceStreamReader rsr = null;
        // Load default
        rsr = new ResourceStreamReader(
                NamesDataManager.class.getResourceAsStream(
                        "/com/puttysoftware/dungeondiver3/support/resources/data/names/names.txt"));
        final ArrayList<String> data = new ArrayList<>();
        // Ignore first line
        String raw = rsr.readString();
        while (raw != null) {
            raw = rsr.readString();
            data.add(raw);
        }
        rsr.close();
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
        ResourceStreamReader rsr = null;
        try (FileInputStream fis = new FileInputStream(f)) {
            rsr = new ResourceStreamReader(fis);
            final int version = rsr.readInt();
            return version == NamesConstants.NAMES_VERSION;
        } catch (final Exception e) {
            return false;
        } finally {
            if (rsr != null) {
                try {
                    rsr.close();
                } catch (final IOException ie) {
                    // Ignore
                }
            }
        }
    }
}
