/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.manager.asset;

import studio.ignitionigloogames.common.images.BufferedImageIcon;

public class ArmorImageCache {
    // Fields
    private static CacheEntry[] cache;
    private static int CACHE_INCREMENT = 20;
    private static int CACHE_SIZE = 0;

    // Methods
    static BufferedImageIcon getCachedImage(final String name) {
        if (!ArmorImageCache.isInCache(name)) {
            final BufferedImageIcon bii = ArmorImageManager
                    .getUncachedImage(name);
            ArmorImageCache.addToCache(name, bii);
        }
        for (final CacheEntry element : ArmorImageCache.cache) {
            if (name.equals(element.getName())) {
                return element.getImage();
            }
        }
        return null;
    }

    private static void expandCache() {
        final CacheEntry[] tempCache = new CacheEntry[ArmorImageCache.cache.length
                + ArmorImageCache.CACHE_INCREMENT];
        for (int x = 0; x < ArmorImageCache.CACHE_SIZE; x++) {
            tempCache[x] = ArmorImageCache.cache[x];
        }
        ArmorImageCache.cache = tempCache;
    }

    static synchronized void addToCache(final String name,
            final BufferedImageIcon bii) {
        if (ArmorImageCache.cache == null) {
            ArmorImageCache.cache = new CacheEntry[ArmorImageCache.CACHE_INCREMENT];
        }
        if (ArmorImageCache.CACHE_SIZE == ArmorImageCache.cache.length) {
            ArmorImageCache.expandCache();
        }
        ArmorImageCache.cache[ArmorImageCache.CACHE_SIZE] = new CacheEntry(bii,
                name);
        ArmorImageCache.CACHE_SIZE++;
    }

    static synchronized boolean isInCache(final String name) {
        if (ArmorImageCache.cache == null) {
            ArmorImageCache.cache = new CacheEntry[ArmorImageCache.CACHE_INCREMENT];
        }
        for (int x = 0; x < ArmorImageCache.CACHE_SIZE; x++) {
            if (name.equals(ArmorImageCache.cache[x].getName())) {
                return true;
            }
        }
        return false;
    }
}