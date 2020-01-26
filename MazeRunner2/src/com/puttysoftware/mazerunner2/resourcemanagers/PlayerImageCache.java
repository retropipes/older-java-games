/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.resourcemanagers;

import com.puttysoftware.images.BufferedImageIcon;

public class PlayerImageCache {
    // Fields
    private static CacheEntry[] cache;
    private static int CACHE_INCREMENT = 20;
    private static int CACHE_SIZE = 0;

    // Methods
    static BufferedImageIcon getCachedImage(final String name,
            final int transformColor) {
        if (!PlayerImageCache.isInCache(name)) {
            BufferedImageIcon bii = PlayerImageManager.getUncachedImage(name);
            bii = ImageTransformer.templateTransformImage(bii, transformColor);
            PlayerImageCache.addToCache(name, bii);
        }
        for (final CacheEntry element : PlayerImageCache.cache) {
            if (name.equals(element.getName())) {
                return element.getImage();
            }
        }
        return null;
    }

    private static void expandCache() {
        final CacheEntry[] tempCache = new CacheEntry[PlayerImageCache.cache.length
                + PlayerImageCache.CACHE_INCREMENT];
        for (int x = 0; x < PlayerImageCache.CACHE_SIZE; x++) {
            tempCache[x] = PlayerImageCache.cache[x];
        }
        PlayerImageCache.cache = tempCache;
    }

    static void addToCache(final String name, final BufferedImageIcon bii) {
        if (PlayerImageCache.cache == null) {
            PlayerImageCache.cache = new CacheEntry[PlayerImageCache.CACHE_INCREMENT];
        }
        if (PlayerImageCache.CACHE_SIZE == PlayerImageCache.cache.length) {
            PlayerImageCache.expandCache();
        }
        PlayerImageCache.cache[PlayerImageCache.CACHE_SIZE] = new CacheEntry(
                bii, name);
        PlayerImageCache.CACHE_SIZE++;
    }

    static boolean isInCache(final String name) {
        if (PlayerImageCache.cache == null) {
            PlayerImageCache.cache = new CacheEntry[PlayerImageCache.CACHE_INCREMENT];
        }
        for (int x = 0; x < PlayerImageCache.CACHE_SIZE; x++) {
            if (name.equals(PlayerImageCache.cache[x].getName())) {
                return true;
            }
        }
        return false;
    }
}