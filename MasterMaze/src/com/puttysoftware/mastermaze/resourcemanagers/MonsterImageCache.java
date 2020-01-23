/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.resourcemanagers;

import com.puttysoftware.images.BufferedImageIcon;

public class MonsterImageCache {
    // Fields
    private static BufferedImageIcon[] cache;
    private static String[] nameCache;
    private static int CACHE_INCREMENT = 20;
    private static int CACHE_SIZE = 0;

    // Methods
    static BufferedImageIcon getCachedImage(final String name,
            final int transformColor) {
        if (!MonsterImageCache.isInCache(name)) {
            BufferedImageIcon bii = MonsterImageManager.getUncachedImage(name);
            bii = ImageTransformer.templateTransformImage(bii, transformColor);
            MonsterImageCache.addToCache(name, bii);
        }
        for (int x = 0; x < MonsterImageCache.nameCache.length; x++) {
            if (name.equals(MonsterImageCache.nameCache[x])) {
                return MonsterImageCache.cache[x];
            }
        }
        return null;
    }

    private static void expandCache() {
        final BufferedImageIcon[] tempCache = new BufferedImageIcon[MonsterImageCache.cache.length
                + MonsterImageCache.CACHE_INCREMENT];
        final String[] tempNameCache = new String[MonsterImageCache.cache.length
                + MonsterImageCache.CACHE_INCREMENT];
        for (int x = 0; x < MonsterImageCache.CACHE_SIZE; x++) {
            tempCache[x] = MonsterImageCache.cache[x];
            tempNameCache[x] = MonsterImageCache.nameCache[x];
        }
        MonsterImageCache.cache = tempCache;
        MonsterImageCache.nameCache = tempNameCache;
    }

    static void addToCache(final String name, final BufferedImageIcon bii) {
        if (MonsterImageCache.cache == null
                || MonsterImageCache.nameCache == null) {
            MonsterImageCache.cache = new BufferedImageIcon[MonsterImageCache.CACHE_INCREMENT];
            MonsterImageCache.nameCache = new String[MonsterImageCache.CACHE_INCREMENT];
        }
        if (MonsterImageCache.CACHE_SIZE == MonsterImageCache.cache.length) {
            MonsterImageCache.expandCache();
        }
        MonsterImageCache.cache[MonsterImageCache.CACHE_SIZE] = bii;
        MonsterImageCache.nameCache[MonsterImageCache.CACHE_SIZE] = name;
        MonsterImageCache.CACHE_SIZE++;
    }

    static boolean isInCache(final String name) {
        if (MonsterImageCache.cache == null
                || MonsterImageCache.nameCache == null) {
            MonsterImageCache.cache = new BufferedImageIcon[MonsterImageCache.CACHE_INCREMENT];
            MonsterImageCache.nameCache = new String[MonsterImageCache.CACHE_INCREMENT];
        }
        for (int x = 0; x < MonsterImageCache.CACHE_SIZE; x++) {
            if (name.equals(MonsterImageCache.nameCache[x])) {
                return true;
            }
        }
        return false;
    }
}