/*  DungeonDiver4: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.datamanagers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.names.NamesConstants;
import com.puttysoftware.xio.ResourceStreamReader;

public class NamesDataManager {
    private static final String MAC_PREFIX = "HOME";
    private static final String WIN_PREFIX = "APPDATA";
    private static final String UNIX_PREFIX = "HOME";
    private static final String MAC_DIR = "/Library/Application Support/Putty Software/DungeonDiver4/";
    private static final String WIN_DIR = "\\Putty Software\\DungeonDiver4\\";
    private static final String UNIX_DIR = "/.puttysoftware/dungeondiver4/";

    public static String[] getNamesData() {
        File overrideData = NamesDataManager.getNamesOverrideFile();
        if (overrideData.exists()) {
            return NamesDataManager.getNamesOverrideData();
        } else {
            return NamesDataManager.getNamesDefaultData();
        }
    }

    private static String[] getNamesDefaultData() {
        try (ResourceStreamReader rsr = new ResourceStreamReader(
                NamesDataManager.class
                        .getResourceAsStream("/com/puttysoftware/dungeondiver4/resources/data/names/names.txt"))) {
            // Load default
            final ArrayList<String> data = new ArrayList<>();
            // Ignore first line
            String raw = rsr.readString();
            while (raw != null) {
                raw = rsr.readString();
                data.add(raw);
            }
            Object[] arr = data.toArray();
            String[] tempres = new String[arr.length];
            int count = 0;
            for (int x = 0; x < arr.length; x++) {
                if (arr[x] != null) {
                    tempres[x] = arr[x].toString();
                    count++;
                }
            }
            String[] res = new String[count];
            count = 0;
            for (int x = 0; x < tempres.length; x++) {
                if (tempres[x] != null) {
                    res[count] = tempres[x];
                    count++;
                }
            }
            return res;
        } catch (final Exception e) {
            DungeonDiver4.getErrorLogger().logError(e);
            return null;
        }
    }

    private static String[] getNamesOverrideData() {
        try {
            File overrideData = NamesDataManager.getNamesOverrideFile();
            // Version check
            if (overrideData.exists()
                    && !NamesDataManager
                            .isNamesFileCorrectVersion(overrideData)) {
                boolean success = overrideData.delete();
                if (!success) {
                    throw new IOException("Deleting override failed!");
                }
            }
            try (FileInputStream fis = new FileInputStream(overrideData);
                    ResourceStreamReader rsr = new ResourceStreamReader(fis)) {
                final ArrayList<String> data = new ArrayList<>();
                // Ignore first line
                String raw = rsr.readString();
                while (raw != null) {
                    raw = rsr.readString();
                    data.add(raw);
                }
                Object[] arr = data.toArray();
                String[] tempres = new String[arr.length];
                int count = 0;
                for (int x = 0; x < arr.length; x++) {
                    if (arr[x] != null) {
                        tempres[x] = arr[x].toString();
                        count++;
                    }
                }
                String[] res = new String[count];
                count = 0;
                for (int x = 0; x < tempres.length; x++) {
                    if (tempres[x] != null) {
                        res[count] = tempres[x];
                        count++;
                    }
                }
                return res;
            }
        } catch (final Exception e) {
            DungeonDiver4.getErrorLogger().logError(e);
            return null;
        }
    }

    public static void resetNames() {
        try {
            File overrideData = NamesDataManager.getNamesOverrideFile();
            // Version check
            if (overrideData.exists()) {
                boolean success = overrideData.delete();
                if (!success) {
                    throw new IOException("Deleting override failed!");
                }
            }
        } catch (final Exception e) {
            DungeonDiver4.getErrorLogger().logError(e);
        }
    }

    private static String getNamesDirPrefix() {
        String osName = System.getProperty("os.name");
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
        String osName = System.getProperty("os.name");
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
        StringBuilder b = new StringBuilder();
        b.append(NamesDataManager.getNamesDirPrefix());
        b.append(NamesDataManager.getNamesDirectory());
        b.append("names.txt");
        return new File(b.toString());
    }

    private static boolean isNamesFileCorrectVersion(File f) {
        try (FileInputStream fis = new FileInputStream(f);
                ResourceStreamReader rsr = new ResourceStreamReader(fis)) {
            int version = rsr.readInt();
            return (version == NamesConstants.NAMES_VERSION);
        } catch (Exception e) {
            return false;
        }
    }
}
