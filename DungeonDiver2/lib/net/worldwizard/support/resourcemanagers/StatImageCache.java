/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.resourcemanagers;

import net.worldwizard.images.BufferedImageIcon;

public class StatImageCache {
    // Fields
    private static BufferedImageIcon[] cache;
    private static String[] nameCache;
    private static int CACHE_INCREMENT = 20;
    private static int CACHE_SIZE = 0;

    // Methods
    static BufferedImageIcon getCachedStatImage(final String name) {
        if (!StatImageCache.isInCache(name)) {
            final BufferedImageIcon bii = StatImageManager
                    .getUncachedStatImage(name);
            StatImageCache.addToCache(name, bii);
        }
        for (int x = 0; x < StatImageCache.nameCache.length; x++) {
            if (name.equals(StatImageCache.nameCache[x])) {
                return StatImageCache.cache[x];
            }
        }
        return null;
    }

    private static void expandCache() {
        final BufferedImageIcon[] tempCache = new BufferedImageIcon[StatImageCache.cache.length
                + StatImageCache.CACHE_INCREMENT];
        final String[] tempNameCache = new String[StatImageCache.cache.length
                + StatImageCache.CACHE_INCREMENT];
        for (int x = 0; x < StatImageCache.CACHE_SIZE; x++) {
            tempCache[x] = StatImageCache.cache[x];
            tempNameCache[x] = StatImageCache.nameCache[x];
        }
        StatImageCache.cache = tempCache;
        StatImageCache.nameCache = tempNameCache;
    }

    private static void addToCache(final String name,
            final BufferedImageIcon bii) {
        if (StatImageCache.cache == null || StatImageCache.nameCache == null) {
            StatImageCache.cache = new BufferedImageIcon[StatImageCache.CACHE_INCREMENT];
            StatImageCache.nameCache = new String[StatImageCache.CACHE_INCREMENT];
        }
        if (StatImageCache.CACHE_SIZE == StatImageCache.cache.length) {
            StatImageCache.expandCache();
        }
        StatImageCache.cache[StatImageCache.CACHE_SIZE] = bii;
        StatImageCache.nameCache[StatImageCache.CACHE_SIZE] = name;
        StatImageCache.CACHE_SIZE++;
    }

    private static boolean isInCache(final String name) {
        if (StatImageCache.cache == null || StatImageCache.nameCache == null) {
            StatImageCache.cache = new BufferedImageIcon[StatImageCache.CACHE_INCREMENT];
            StatImageCache.nameCache = new String[StatImageCache.CACHE_INCREMENT];
        }
        for (int x = 0; x < StatImageCache.CACHE_SIZE; x++) {
            if (name.equals(StatImageCache.nameCache[x])) {
                return true;
            }
        }
        return false;
    }
}
