/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.resourcemanagers;

import net.worldwizard.images.BufferedImageIcon;
import net.worldwizard.support.creatures.monsters.Element;

public class PlayerImageCache {
    // Fields
    private static BufferedImageIcon[] cache;
    private static String[] nameCache;
    private static int CACHE_INCREMENT = 20;
    private static int CACHE_SIZE = 0;

    // Methods
    static BufferedImageIcon getCachedPlayerImage(final String name,
            final Element element) {
        final String cacheName = element.getName() + " " + name;
        if (!PlayerImageCache.isInCache(cacheName)) {
            final BufferedImageIcon bii = PlayerImageManager
                    .getUncachedPlayerImage(name, element);
            PlayerImageCache.addToCache(cacheName, bii);
        }
        for (int x = 0; x < PlayerImageCache.nameCache.length; x++) {
            if (cacheName.equals(PlayerImageCache.nameCache[x])) {
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

    private static void addToCache(final String name,
            final BufferedImageIcon bii) {
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

    private static boolean isInCache(final String name) {
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
