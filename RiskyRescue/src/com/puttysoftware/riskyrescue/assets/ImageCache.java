/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue.assets;

import java.util.ArrayList;

import com.puttysoftware.images.BufferedImageIcon;

class ImageCache {
    // Fields
    private static ArrayList<ImageCacheEntry> cache;

    // Methods
    static BufferedImageIcon getCachedImage(final String name,
            final String cat) {
        final String cacheName = name + cat;
        if (!ImageCache.isInCache(cacheName)) {
            final BufferedImageIcon bii = ImageManager.getUncachedImage(name,
                    cat);
            ImageCache.addToCache(cacheName, bii);
        }
        for (final ImageCacheEntry ice : ImageCache.cache) {
            if (ice.getName().equals(cacheName)) {
                return ice.getEntry();
            }
        }
        return null;
    }

    private static void addToCache(final String name,
            final BufferedImageIcon bii) {
        if (ImageCache.cache == null) {
            ImageCache.cache = new ArrayList<>();
        }
        ImageCache.cache.add(new ImageCacheEntry(bii, name));
    }

    private static boolean isInCache(final String name) {
        if (ImageCache.cache == null) {
            ImageCache.cache = new ArrayList<>();
        }
        for (final ImageCacheEntry ice : ImageCache.cache) {
            if (ice.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}
