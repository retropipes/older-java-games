/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.resourcemanagers;

import com.puttysoftware.images.BufferedImageIcon;

public class PlayerImageCache {
    // Fields
    private static BufferedImageIcon[] cache;
    private static String[] nameCache;
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
        for (int x = 0; x < PlayerImageCache.nameCache.length; x++) {
            if (name.equals(PlayerImageCache.nameCache[x])) {
                return PlayerImageCache.cache[x];
            }
        }
        return null;
    }

    private static void expandCache() {
        final BufferedImageIcon[] tempCache = new BufferedImageIcon[PlayerImageCache.cache.length
                + PlayerImageCache.CACHE_INCREMENT];
        final String[] tempNameCache = new String[PlayerImageCache.cache.length
                + PlayerImageCache.CACHE_INCREMENT];
        for (int x = 0; x < PlayerImageCache.CACHE_SIZE; x++) {
            tempCache[x] = PlayerImageCache.cache[x];
            tempNameCache[x] = PlayerImageCache.nameCache[x];
        }
        PlayerImageCache.cache = tempCache;
        PlayerImageCache.nameCache = tempNameCache;
    }

    static void addToCache(final String name, final BufferedImageIcon bii) {
        if (PlayerImageCache.cache == null
                || PlayerImageCache.nameCache == null) {
            PlayerImageCache.cache = new BufferedImageIcon[PlayerImageCache.CACHE_INCREMENT];
            PlayerImageCache.nameCache = new String[PlayerImageCache.CACHE_INCREMENT];
        }
        if (PlayerImageCache.CACHE_SIZE == PlayerImageCache.cache.length) {
            PlayerImageCache.expandCache();
        }
        PlayerImageCache.cache[PlayerImageCache.CACHE_SIZE] = bii;
        PlayerImageCache.nameCache[PlayerImageCache.CACHE_SIZE] = name;
        PlayerImageCache.CACHE_SIZE++;
    }

    static boolean isInCache(final String name) {
        if (PlayerImageCache.cache == null
                || PlayerImageCache.nameCache == null) {
            PlayerImageCache.cache = new BufferedImageIcon[PlayerImageCache.CACHE_INCREMENT];
            PlayerImageCache.nameCache = new String[PlayerImageCache.CACHE_INCREMENT];
        }
        for (int x = 0; x < PlayerImageCache.CACHE_SIZE; x++) {
            if (name.equals(PlayerImageCache.nameCache[x])) {
                return true;
            }
        }
        return false;
    }
}