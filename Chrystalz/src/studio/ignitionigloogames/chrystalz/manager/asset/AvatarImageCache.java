/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.manager.asset;

import studio.ignitionigloogames.common.images.BufferedImageIcon;

public class AvatarImageCache {
    // Fields
    private static CacheEntry[] cache;
    private static int CACHE_INCREMENT = 20;
    private static int CACHE_SIZE = 0;

    // Methods
    static BufferedImageIcon getCachedImage(final String name) {
        if (!AvatarImageCache.isInCache(name)) {
            final BufferedImageIcon bii = AvatarImageManager
                    .getUncachedImage(name);
            AvatarImageCache.addToCache(name, bii);
        }
        for (final CacheEntry element : AvatarImageCache.cache) {
            if (name.equals(element.getName())) {
                return element.getImage();
            }
        }
        return null;
    }

    private static void expandCache() {
        final CacheEntry[] tempCache = new CacheEntry[AvatarImageCache.cache.length
                + AvatarImageCache.CACHE_INCREMENT];
        for (int x = 0; x < AvatarImageCache.CACHE_SIZE; x++) {
            tempCache[x] = AvatarImageCache.cache[x];
        }
        AvatarImageCache.cache = tempCache;
    }

    static synchronized void addToCache(final String name,
            final BufferedImageIcon bii) {
        if (AvatarImageCache.cache == null) {
            AvatarImageCache.cache = new CacheEntry[AvatarImageCache.CACHE_INCREMENT];
        }
        if (AvatarImageCache.CACHE_SIZE == AvatarImageCache.cache.length) {
            AvatarImageCache.expandCache();
        }
        AvatarImageCache.cache[AvatarImageCache.CACHE_SIZE] = new CacheEntry(
                bii, name);
        AvatarImageCache.CACHE_SIZE++;
    }

    static synchronized boolean isInCache(final String name) {
        if (AvatarImageCache.cache == null) {
            AvatarImageCache.cache = new CacheEntry[AvatarImageCache.CACHE_INCREMENT];
        }
        for (int x = 0; x < AvatarImageCache.CACHE_SIZE; x++) {
            if (name.equals(AvatarImageCache.cache[x].getName())) {
                return true;
            }
        }
        return false;
    }
}