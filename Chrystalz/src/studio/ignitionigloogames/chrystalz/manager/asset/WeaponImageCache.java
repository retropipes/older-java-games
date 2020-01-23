/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.manager.asset;

import studio.ignitionigloogames.common.images.BufferedImageIcon;

public class WeaponImageCache {
    // Fields
    private static CacheEntry[] cache;
    private static int CACHE_INCREMENT = 20;
    private static int CACHE_SIZE = 0;

    // Methods
    static BufferedImageIcon getCachedImage(final String name) {
        if (!WeaponImageCache.isInCache(name)) {
            final BufferedImageIcon bii = WeaponImageManager
                    .getUncachedImage(name);
            WeaponImageCache.addToCache(name, bii);
        }
        for (final CacheEntry element : WeaponImageCache.cache) {
            if (name.equals(element.getName())) {
                return element.getImage();
            }
        }
        return null;
    }

    private static void expandCache() {
        final CacheEntry[] tempCache = new CacheEntry[WeaponImageCache.cache.length
                + WeaponImageCache.CACHE_INCREMENT];
        for (int x = 0; x < WeaponImageCache.CACHE_SIZE; x++) {
            tempCache[x] = WeaponImageCache.cache[x];
        }
        WeaponImageCache.cache = tempCache;
    }

    static synchronized void addToCache(final String name,
            final BufferedImageIcon bii) {
        if (WeaponImageCache.cache == null) {
            WeaponImageCache.cache = new CacheEntry[WeaponImageCache.CACHE_INCREMENT];
        }
        if (WeaponImageCache.CACHE_SIZE == WeaponImageCache.cache.length) {
            WeaponImageCache.expandCache();
        }
        WeaponImageCache.cache[WeaponImageCache.CACHE_SIZE] = new CacheEntry(
                bii, name);
        WeaponImageCache.CACHE_SIZE++;
    }

    static synchronized boolean isInCache(final String name) {
        if (WeaponImageCache.cache == null) {
            WeaponImageCache.cache = new CacheEntry[WeaponImageCache.CACHE_INCREMENT];
        }
        for (int x = 0; x < WeaponImageCache.CACHE_SIZE; x++) {
            if (name.equals(WeaponImageCache.cache[x].getName())) {
                return true;
            }
        }
        return false;
    }
}