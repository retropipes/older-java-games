/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.resourcemanagers;

import java.util.ArrayList;

import com.puttysoftware.gemma.support.map.generic.TemplateTransform;
import com.puttysoftware.images.BufferedImageIcon;

class ImageCache {
    // Fields
    private static ArrayList<ImageCacheEntry> cache;

    // Methods
    static BufferedImageIcon getCachedImage(final String name,
            final String rawName, final String cat, final TemplateTransform tt,
            final boolean horzflip, final boolean vertflip,
            final boolean helpImage) {
        String cacheName;
        String orientation = "_";
        String help = "";
        if (helpImage) {
            help = "_H";
        }
        if (horzflip) {
            orientation += "R";
        } else {
            orientation += "L";
        }
        if (vertflip) {
            orientation += "D";
        } else {
            orientation += "U";
        }
        cacheName = rawName + orientation + help;
        if (!ImageCache.isInCache(cacheName)) {
            BufferedImageIcon bii = ImageManager.getUncachedImage(name, cat, tt,
                    horzflip, vertflip, helpImage);
            ImageCache.addToCache(cacheName, bii);
        }
        for (ImageCacheEntry ice : ImageCache.cache) {
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
        for (ImageCacheEntry ice : ImageCache.cache) {
            if (ice.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}
