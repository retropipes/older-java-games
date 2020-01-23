/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.resourcemanagers;

import com.puttysoftware.audio.Sound;

public class SoundCache {
    // Fields
    private static Sound[] cache;
    private static String[] nameCache;
    private static int CACHE_INCREMENT = 20;
    private static int CACHE_SIZE = 0;

    // Methods
    static Sound getCachedSound(final String name) {
        if (!SoundCache.isInCache(name)) {
            final Sound snd = SoundManager.getUncachedSound(name);
            SoundCache.addToCache(name, snd);
        }
        for (int x = 0; x < SoundCache.nameCache.length; x++) {
            if (name.equals(SoundCache.nameCache[x])) {
                return SoundCache.cache[x];
            }
        }
        return null;
    }

    private static void expandCache() {
        final Sound[] tempCache = new Sound[SoundCache.cache.length
                + SoundCache.CACHE_INCREMENT];
        final String[] tempNameCache = new String[SoundCache.cache.length
                + SoundCache.CACHE_INCREMENT];
        for (int x = 0; x < SoundCache.CACHE_SIZE; x++) {
            tempCache[x] = SoundCache.cache[x];
            tempNameCache[x] = SoundCache.nameCache[x];
        }
        SoundCache.cache = tempCache;
        SoundCache.nameCache = tempNameCache;
    }

    private static void addToCache(final String name, final Sound snd) {
        if (SoundCache.cache == null || SoundCache.nameCache == null) {
            SoundCache.cache = new Sound[SoundCache.CACHE_INCREMENT];
            SoundCache.nameCache = new String[SoundCache.CACHE_INCREMENT];
        }
        if (SoundCache.CACHE_SIZE == SoundCache.cache.length) {
            SoundCache.expandCache();
        }
        SoundCache.cache[SoundCache.CACHE_SIZE] = snd;
        SoundCache.nameCache[SoundCache.CACHE_SIZE] = name;
        SoundCache.CACHE_SIZE++;
    }

    private static boolean isInCache(final String name) {
        if (SoundCache.cache == null || SoundCache.nameCache == null) {
            SoundCache.cache = new Sound[SoundCache.CACHE_INCREMENT];
            SoundCache.nameCache = new String[SoundCache.CACHE_INCREMENT];
        }
        for (int x = 0; x < SoundCache.CACHE_SIZE; x++) {
            if (name.equals(SoundCache.nameCache[x])) {
                return true;
            }
        }
        return false;
    }
}