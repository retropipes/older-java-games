/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.resourcemanagers;

import com.puttysoftware.images.BufferedImageIcon;

public class ObjectImageCache {
    // Fields
    private static CacheEntry[] cache;
    private static int CACHE_INCREMENT = 20;
    private static int CACHE_SIZE = 0;

    // Methods
    static BufferedImageIcon getCachedImage(final String name,
            final String baseName, final int transformColor,
            final String attrName, final int attrColor) {
        if (!ObjectImageCache.isInCache(name)) {
            if (attrName != null && !attrName.isEmpty()) {
                BufferedImageIcon bii1 = ObjectImageManager
                        .getUncachedImage(baseName);
                bii1 = ImageTransformer.templateTransformImage(bii1,
                        transformColor);
                BufferedImageIcon bii2 = ObjectImageManager
                        .getUncachedImage(attrName);
                bii2 = ImageTransformer.templateTransformImage(bii2, attrColor);
                final BufferedImageIcon bii3 = ImageTransformer
                        .getCompositeImage(bii1, bii2);
                ObjectImageCache.addToCache(name, bii3);
            } else {
                BufferedImageIcon bii = ObjectImageManager
                        .getUncachedImage(baseName);
                bii = ImageTransformer.templateTransformImage(bii,
                        transformColor);
                ObjectImageCache.addToCache(name, bii);
            }
        }
        for (final CacheEntry element : ObjectImageCache.cache) {
            if (name.equals(element.getName())) {
                return element.getImage();
            }
        }
        return null;
    }

    private static void expandCache() {
        final CacheEntry[] tempCache = new CacheEntry[ObjectImageCache.cache.length
                + ObjectImageCache.CACHE_INCREMENT];
        for (int x = 0; x < ObjectImageCache.CACHE_SIZE; x++) {
            tempCache[x] = ObjectImageCache.cache[x];
        }
        ObjectImageCache.cache = tempCache;
    }

    static void addToCache(final String name, final BufferedImageIcon bii) {
        if (ObjectImageCache.cache == null) {
            ObjectImageCache.cache = new CacheEntry[ObjectImageCache.CACHE_INCREMENT];
        }
        if (ObjectImageCache.CACHE_SIZE == ObjectImageCache.cache.length) {
            ObjectImageCache.expandCache();
        }
        ObjectImageCache.cache[ObjectImageCache.CACHE_SIZE] = new CacheEntry(
                bii, name);
        ObjectImageCache.CACHE_SIZE++;
    }

    static boolean isInCache(final String name) {
        if (ObjectImageCache.cache == null) {
            ObjectImageCache.cache = new CacheEntry[ObjectImageCache.CACHE_INCREMENT];
        }
        for (int x = 0; x < ObjectImageCache.CACHE_SIZE; x++) {
            if (name.equals(ObjectImageCache.cache[x].getName())) {
                return true;
            }
        }
        return false;
    }
}