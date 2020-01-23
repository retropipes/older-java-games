/*  DungeonDiver4: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.names;

import java.util.ArrayList;

import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.datamanagers.NamesDataManager;

public class NamesManager {
    private static boolean CACHE_CREATED = false;
    private static String[] RAW_CACHE;
    private static String[][] CACHE;

    public static String getName(String section, String type) {
        try {
            NamesManager.createCache();
        } catch (Exception e) {
            DungeonDiver4.getErrorLogger().logError(e);
        }
        String key = section + ":" + type;
        if (!NamesManager.containsKey(key)) {
            DungeonDiver4.getErrorLogger().logError(
                    new IllegalArgumentException("No such key " + key));
        }
        return NamesManager.getValue(key);
    }

    private static boolean containsKey(String key) {
        for (int x = 0; x < CACHE.length; x++) {
            if (CACHE[x][0].equals(key)) {
                return true;
            }
        }
        return false;
    }

    private static String getValue(String key) {
        for (int x = 0; x < CACHE.length; x++) {
            if (CACHE[x][0].equals(key)) {
                return CACHE[x][1];
            }
        }
        return null;
    }

    public static void invalidateNamesCache() {
        NamesManager.CACHE_CREATED = false;
    }

    public static String[][] getNamesCache() {
        try {
            NamesManager.createCache();
        } catch (Exception e) {
            DungeonDiver4.getErrorLogger().logError(e);
        }
        return NamesManager.CACHE;
    }

    public static String[] convertCacheToArray() {
        ArrayList<String> temp1 = new ArrayList<>();
        ArrayList<String> temp2 = new ArrayList<>();
        for (int x = 0; x < CACHE.length; x++) {
            temp1.add(CACHE[x][0]);
            temp2.add(CACHE[x][1]);
        }
        String[] temp3 = temp1.toArray(new String[temp1.size()]);
        String[] temp4 = temp2.toArray(new String[temp2.size()]);
        String[] results = new String[temp3.length];
        for (int x = 0; x < results.length; x++) {
            results[x] = temp3[x] + "=" + temp4[x];
        }
        return results;
    }

    private static void createCache() {
        if (!NamesManager.CACHE_CREATED) {
            // Create raw cache
            NamesManager.RAW_CACHE = NamesDataManager.getNamesData();
            if (NamesManager.RAW_CACHE != null) {
                NamesManager.CACHE = new String[NamesManager.RAW_CACHE.length][2];
                for (int x = 0; x < NamesManager.RAW_CACHE.length; x++) {
                    if (NamesManager.RAW_CACHE[x] != null
                            && !NamesManager.RAW_CACHE[x].isEmpty()) {
                        // Entry
                        String[] splitEntry = NamesManager.RAW_CACHE[x]
                                .split("=");
                        // Sanity check
                        if (splitEntry.length < 2) {
                            throw new IllegalArgumentException(
                                    "Invalid names file format: Entry format invalid!");
                        }
                        String key = splitEntry[0];
                        String value = splitEntry[1];
                        NamesManager.CACHE[x][0] = key;
                        NamesManager.CACHE[x][1] = value;
                    }
                }
                NamesManager.CACHE_CREATED = true;
            } else {
                throw new IllegalArgumentException("Names file not found!");
            }
        }
    }
}
