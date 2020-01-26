/*  DynamicDungeon: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.datamanagers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import net.dynamicdungeon.dynamicdungeon.DynamicDungeon;
import net.dynamicdungeon.dynamicdungeon.names.NamesConstants;
import net.dynamicdungeon.dynamicdungeon.utilities.DynamicProperties;
import net.dynamicdungeon.fileutils.ResourceStreamReader;

public class NamesDataManager {
    private static final String NAMES_DIR = "/DDNet/DynamicDungeon/";

    public static String[] getNamesData() {
        final File overrideData = NamesDataManager.getNamesOverrideFile();
        if (overrideData.exists()) {
            return NamesDataManager.getNamesOverrideData();
        } else {
            return NamesDataManager.getNamesDefaultData();
        }
    }

    private static String[] getNamesDefaultData() {
        try (ResourceStreamReader rsr = new ResourceStreamReader(
                NamesDataManager.class.getResourceAsStream(
                        "/net/dynamicdungeon/dynamicdungeon/resources/data/names/names.txt"))) {
            // Load default
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
        } catch (final IOException e) {
            DynamicDungeon.getErrorLogger().logError(e);
            return null;
        }
    }

    private static String[] getNamesOverrideData() {
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
            try (FileInputStream fis = new FileInputStream(overrideData);
                    ResourceStreamReader rsr = new ResourceStreamReader(fis)) {
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
        } catch (final IOException e) {
            DynamicDungeon.getErrorLogger().logError(e);
            return null;
        }
    }

    private static String getNamesDirPrefix() {
        return DynamicProperties.getApplicationSupportDirectory();
    }

    private static String getNamesDirectory() {
        return NamesDataManager.NAMES_DIR;
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
        } catch (final IOException e) {
            return false;
        }
    }
}
