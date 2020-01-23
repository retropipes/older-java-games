/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.manager.asset;

import studio.ignitionigloogames.common.images.BufferedImageIcon;

public class CrystalImageCache {
    // Fields
    private static CacheEntry[] cache;
    private static int CACHE_INCREMENT = 20;
    private static int CACHE_SIZE = 0;

    // Methods
    static BufferedImageIcon getCachedImage(final String name) {
        if (!CrystalImageCache.isInCache(name)) {
            final BufferedImageIcon bii = CrystalImageManager
                    .getUncachedImage(name);
            CrystalImageCache.addToCache(name, bii);
        }
        for (final CacheEntry element : CrystalImageCache.cache) {
            if (name.equals(element.getName())) {
                return element.getImage();
            }
        }
        return null;
    }

    private static void expandCache() {
        final CacheEntry[] tempCache = new CacheEntry[CrystalImageCache.cache.length
                + CrystalImageCache.CACHE_INCREMENT];
        for (int x = 0; x < CrystalImageCache.CACHE_SIZE; x++) {
            tempCache[x] = CrystalImageCache.cache[x];
        }
        CrystalImageCache.cache = tempCache;
    }

    static synchronized void addToCache(final String name,
            final BufferedImageIcon bii) {
        if (CrystalImageCache.cache == null) {
            CrystalImageCache.cache = new CacheEntry[CrystalImageCache.CACHE_INCREMENT];
        }
        if (CrystalImageCache.CACHE_SIZE == CrystalImageCache.cache.length) {
            CrystalImageCache.expandCache();
        }
        CrystalImageCache.cache[CrystalImageCache.CACHE_SIZE] = new CacheEntry(
                bii, name);
        CrystalImageCache.CACHE_SIZE++;
    }

    static synchronized boolean isInCache(final String name) {
        if (CrystalImageCache.cache == null) {
            CrystalImageCache.cache = new CacheEntry[CrystalImageCache.CACHE_INCREMENT];
        }
        for (int x = 0; x < CrystalImageCache.CACHE_SIZE; x++) {
            if (name.equals(CrystalImageCache.cache[x].getName())) {
                return true;
            }
        }
        return false;
    }
}