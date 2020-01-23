/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.resourcemanagers;

import com.puttysoftware.images.BufferedImageIcon;

public class MonsterImageCache {
    // Fields
    private static CacheEntry[] cache;
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
        for (int x = 0; x < MonsterImageCache.cache.length; x++) {
            if (name.equals(MonsterImageCache.cache[x].getName())) {
                return MonsterImageCache.cache[x].getImage();
            }
        }
        return null;
    }

    private static void expandCache() {
        CacheEntry[] tempCache = new CacheEntry[MonsterImageCache.cache.length
                + MonsterImageCache.CACHE_INCREMENT];
        for (int x = 0; x < MonsterImageCache.CACHE_SIZE; x++) {
            tempCache[x] = MonsterImageCache.cache[x];
        }
        MonsterImageCache.cache = tempCache;
    }

    static void addToCache(final String name, final BufferedImageIcon bii) {
        if (MonsterImageCache.cache == null) {
            MonsterImageCache.cache = new CacheEntry[MonsterImageCache.CACHE_INCREMENT];
        }
        if (MonsterImageCache.CACHE_SIZE == MonsterImageCache.cache.length) {
            MonsterImageCache.expandCache();
        }
        MonsterImageCache.cache[MonsterImageCache.CACHE_SIZE] = new CacheEntry(
                bii, name);
        MonsterImageCache.CACHE_SIZE++;
    }

    static boolean isInCache(final String name) {
        if (MonsterImageCache.cache == null) {
            MonsterImageCache.cache = new CacheEntry[MonsterImageCache.CACHE_INCREMENT];
        }
        for (int x = 0; x < MonsterImageCache.CACHE_SIZE; x++) {
            if (name.equals(MonsterImageCache.cache[x].getName())) {
                return true;
            }
        }
        return false;
    }
}