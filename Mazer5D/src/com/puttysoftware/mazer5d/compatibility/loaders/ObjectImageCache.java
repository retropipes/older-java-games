/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.loaders;

import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.mazer5d.compatibility.abc.MazeObject;

public class ObjectImageCache {
    // Fields
    private static BufferedImageIcon[] cache;
    private static String[] nameCache;
    private static int CACHE_INCREMENT = 20;
    private static int CACHE_SIZE = 0;

    // Methods
    static BufferedImageIcon getCachedObjectImage(final MazeObject obj,
            final boolean game) {
        final String name = obj.getName();
        if (!ObjectImageCache.isInCache(name)) {
            final BufferedImageIcon bii = ObjectImageManager
                    .getUncachedObjectImage(obj, game);
            ObjectImageCache.addToCache(name, bii);
        }
        for (int x = 0; x < ObjectImageCache.nameCache.length; x++) {
            if (name.equals(ObjectImageCache.nameCache[x])) {
                return ObjectImageCache.cache[x];
            }
        }
        return null;
    }

    private static void expandCache() {
        final BufferedImageIcon[] tempCache = new BufferedImageIcon[ObjectImageCache.cache.length
                + ObjectImageCache.CACHE_INCREMENT];
        final String[] tempNameCache = new String[ObjectImageCache.cache.length
                + ObjectImageCache.CACHE_INCREMENT];
        for (int x = 0; x < ObjectImageCache.CACHE_SIZE; x++) {
            tempCache[x] = ObjectImageCache.cache[x];
            tempNameCache[x] = ObjectImageCache.nameCache[x];
        }
        ObjectImageCache.cache = tempCache;
        ObjectImageCache.nameCache = tempNameCache;
    }

    private static void addToCache(final String name,
            final BufferedImageIcon bii) {
        if (ObjectImageCache.cache == null
                || ObjectImageCache.nameCache == null) {
            ObjectImageCache.cache = new BufferedImageIcon[ObjectImageCache.CACHE_INCREMENT];
            ObjectImageCache.nameCache = new String[ObjectImageCache.CACHE_INCREMENT];
        }
        if (ObjectImageCache.CACHE_SIZE == ObjectImageCache.cache.length) {
            ObjectImageCache.expandCache();
        }
        ObjectImageCache.cache[ObjectImageCache.CACHE_SIZE] = bii;
        ObjectImageCache.nameCache[ObjectImageCache.CACHE_SIZE] = name;
        ObjectImageCache.CACHE_SIZE++;
    }

    private static boolean isInCache(final String name) {
        if (ObjectImageCache.cache == null
                || ObjectImageCache.nameCache == null) {
            ObjectImageCache.cache = new BufferedImageIcon[ObjectImageCache.CACHE_INCREMENT];
            ObjectImageCache.nameCache = new String[ObjectImageCache.CACHE_INCREMENT];
        }
        for (int x = 0; x < ObjectImageCache.CACHE_SIZE; x++) {
            if (name.equals(ObjectImageCache.nameCache[x])) {
                return true;
            }
        }
        return false;
    }
}