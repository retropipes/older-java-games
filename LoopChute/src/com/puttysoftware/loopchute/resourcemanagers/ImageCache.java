/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.resourcemanagers;

import com.puttysoftware.images.BufferedImageIcon;

public class ImageCache {
    // Fields
    private static BufferedImageIcon[] cache;
    private static String[] nameCache;
    private static int CACHE_INCREMENT = 20;
    private static int CACHE_SIZE = 0;

    // Methods
    static BufferedImageIcon getCachedImage(final String name,
            final String baseName, final int transformColor,
            final String attrName, final int attrColor) {
        if (!ImageCache.isInCache(name)) {
            if (attrName != null && !attrName.isEmpty()) {
                BufferedImageIcon bii1 = GraphicsManager
                        .getUncachedImage(baseName);
                bii1 = GraphicsManager.templateTransformImage(bii1,
                        transformColor);
                BufferedImageIcon bii2 = GraphicsManager
                        .getUncachedImage(attrName);
                bii2 = GraphicsManager.templateTransformImage(bii2, attrColor);
                final BufferedImageIcon bii3 = GraphicsManager
                        .getCompositeImage(bii1, bii2);
                ImageCache.addToCache(name, bii3);
            } else {
                BufferedImageIcon bii = GraphicsManager
                        .getUncachedImage(baseName);
                bii = GraphicsManager.templateTransformImage(bii,
                        transformColor);
                ImageCache.addToCache(name, bii);
            }
        }
        for (int x = 0; x < ImageCache.nameCache.length; x++) {
            if (name.equals(ImageCache.nameCache[x])) {
                return ImageCache.cache[x];
            }
        }
        return null;
    }

    private static void expandCache() {
        final BufferedImageIcon[] tempCache = new BufferedImageIcon[ImageCache.cache.length
                + ImageCache.CACHE_INCREMENT];
        final String[] tempNameCache = new String[ImageCache.cache.length
                + ImageCache.CACHE_INCREMENT];
        for (int x = 0; x < ImageCache.CACHE_SIZE; x++) {
            tempCache[x] = ImageCache.cache[x];
            tempNameCache[x] = ImageCache.nameCache[x];
        }
        ImageCache.cache = tempCache;
        ImageCache.nameCache = tempNameCache;
    }

    static void addToCache(final String name, final BufferedImageIcon bii) {
        if (ImageCache.cache == null || ImageCache.nameCache == null) {
            ImageCache.cache = new BufferedImageIcon[ImageCache.CACHE_INCREMENT];
            ImageCache.nameCache = new String[ImageCache.CACHE_INCREMENT];
        }
        if (ImageCache.CACHE_SIZE == ImageCache.cache.length) {
            ImageCache.expandCache();
        }
        ImageCache.cache[ImageCache.CACHE_SIZE] = bii;
        ImageCache.nameCache[ImageCache.CACHE_SIZE] = name;
        ImageCache.CACHE_SIZE++;
    }

    static boolean isInCache(final String name) {
        if (ImageCache.cache == null || ImageCache.nameCache == null) {
            ImageCache.cache = new BufferedImageIcon[ImageCache.CACHE_INCREMENT];
            ImageCache.nameCache = new String[ImageCache.CACHE_INCREMENT];
        }
        for (int x = 0; x < ImageCache.CACHE_SIZE; x++) {
            if (name.equals(ImageCache.nameCache[x])) {
                return true;
            }
        }
        return false;
    }
}