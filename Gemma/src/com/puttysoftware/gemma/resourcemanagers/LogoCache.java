/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.resourcemanagers;

import com.puttysoftware.images.BufferedImageIcon;

class LogoCache {
    // Fields
    private static BufferedImageIcon[] cache;
    private static String[] nameCache;
    private final static int CACHE_INCREMENT = 5;
    private static int CACHE_SIZE = 0;

    // Methods
    static BufferedImageIcon getCachedLogo(final String name) {
        if (!LogoCache.isInCache(name)) {
            final BufferedImageIcon bii = LogoManager.getUncachedLogo(name);
            LogoCache.addToCache(name, bii);
        }
        for (int x = 0; x < LogoCache.nameCache.length; x++) {
            if (name.equals(LogoCache.nameCache[x])) {
                return LogoCache.cache[x];
            }
        }
        return null;
    }

    private static void expandCache() {
        final BufferedImageIcon[] tempCache = new BufferedImageIcon[LogoCache.cache.length
                + LogoCache.CACHE_INCREMENT];
        final String[] tempNameCache = new String[LogoCache.cache.length
                + LogoCache.CACHE_INCREMENT];
        for (int x = 0; x < LogoCache.CACHE_SIZE; x++) {
            tempCache[x] = LogoCache.cache[x];
            tempNameCache[x] = LogoCache.nameCache[x];
        }
        LogoCache.cache = tempCache;
        LogoCache.nameCache = tempNameCache;
    }

    private static void addToCache(final String name,
            final BufferedImageIcon bii) {
        if (LogoCache.cache == null || LogoCache.nameCache == null) {
            LogoCache.cache = new BufferedImageIcon[LogoCache.CACHE_INCREMENT];
            LogoCache.nameCache = new String[LogoCache.CACHE_INCREMENT];
        }
        if (LogoCache.CACHE_SIZE == LogoCache.cache.length) {
            LogoCache.expandCache();
        }
        LogoCache.cache[LogoCache.CACHE_SIZE] = bii;
        LogoCache.nameCache[LogoCache.CACHE_SIZE] = name;
        LogoCache.CACHE_SIZE++;
    }

    private static boolean isInCache(final String name) {
        if (LogoCache.cache == null || LogoCache.nameCache == null) {
            LogoCache.cache = new BufferedImageIcon[LogoCache.CACHE_INCREMENT];
            LogoCache.nameCache = new String[LogoCache.CACHE_INCREMENT];
        }
        for (int x = 0; x < LogoCache.CACHE_SIZE; x++) {
            if (name.equals(LogoCache.nameCache[x])) {
                return true;
            }
        }
        return false;
    }
}