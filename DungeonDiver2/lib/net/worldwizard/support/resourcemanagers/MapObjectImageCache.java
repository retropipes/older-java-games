/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.resourcemanagers;

import net.worldwizard.images.BufferedImageIcon;
import net.worldwizard.support.map.generic.TemplateTransform;

public class MapObjectImageCache {
    // Fields
    private static BufferedImageIcon[] cache;
    private static String[] nameCache;
    private static int CACHE_INCREMENT = 20;
    private static int CACHE_SIZE = 0;

    // Methods
    static void flushCache() {
        MapObjectImageCache.cache = null;
        MapObjectImageCache.nameCache = null;
        MapObjectImageCache.CACHE_SIZE = 0;
    }

    static BufferedImageIcon getCachedImage(final String name,
            final String rawName, final TemplateTransform tt) {
        String cacheName;
        if (tt == null) {
            cacheName = rawName;
        } else {
            final String ttName = tt.getName();
            if (ttName == null || ttName.isEmpty()) {
                cacheName = rawName;
            } else {
                cacheName = tt.getName() + " " + rawName;
            }
        }
        if (!MapObjectImageCache.isInCache(cacheName)) {
            final BufferedImageIcon bii = MapObjectImageManager
                    .getUncachedImage(name, tt);
            MapObjectImageCache.addToCache(cacheName, bii);
        }
        for (int x = 0; x < MapObjectImageCache.nameCache.length; x++) {
            if (cacheName.equals(MapObjectImageCache.nameCache[x])) {
                return MapObjectImageCache.cache[x];
            }
        }
        return null;
    }

    private static void expandCache() {
        final BufferedImageIcon[] tempCache = new BufferedImageIcon[MapObjectImageCache.cache.length
                + MapObjectImageCache.CACHE_INCREMENT];
        final String[] tempNameCache = new String[MapObjectImageCache.cache.length
                + MapObjectImageCache.CACHE_INCREMENT];
        for (int x = 0; x < MapObjectImageCache.CACHE_SIZE; x++) {
            tempCache[x] = MapObjectImageCache.cache[x];
            tempNameCache[x] = MapObjectImageCache.nameCache[x];
        }
        MapObjectImageCache.cache = tempCache;
        MapObjectImageCache.nameCache = tempNameCache;
    }

    private static void addToCache(final String name,
            final BufferedImageIcon bii) {
        if (MapObjectImageCache.cache == null
                || MapObjectImageCache.nameCache == null) {
            MapObjectImageCache.cache = new BufferedImageIcon[MapObjectImageCache.CACHE_INCREMENT];
            MapObjectImageCache.nameCache = new String[MapObjectImageCache.CACHE_INCREMENT];
        }
        if (MapObjectImageCache.CACHE_SIZE == MapObjectImageCache.cache.length) {
            MapObjectImageCache.expandCache();
        }
        MapObjectImageCache.cache[MapObjectImageCache.CACHE_SIZE] = bii;
        MapObjectImageCache.nameCache[MapObjectImageCache.CACHE_SIZE] = name;
        MapObjectImageCache.CACHE_SIZE++;
    }

    private static boolean isInCache(final String name) {
        if (MapObjectImageCache.cache == null
                || MapObjectImageCache.nameCache == null) {
            MapObjectImageCache.cache = new BufferedImageIcon[MapObjectImageCache.CACHE_INCREMENT];
            MapObjectImageCache.nameCache = new String[MapObjectImageCache.CACHE_INCREMENT];
        }
        for (int x = 0; x < MapObjectImageCache.CACHE_SIZE; x++) {
            if (name.equals(MapObjectImageCache.nameCache[x])) {
                return true;
            }
        }
        return false;
    }
}
