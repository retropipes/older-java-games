/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.resourcemanagers;

import studio.ignitionigloogames.images.BufferedImageIcon;

public class MonsterImageCache {
	// Fields
	private static CacheEntry[] cache;
	private static int CACHE_INCREMENT = 20;
	private static int CACHE_SIZE = 0;

	// Methods
	static BufferedImageIcon getCachedImage(final String name, final int transformColor) {
		if (!MonsterImageCache.isInCache(name)) {
			BufferedImageIcon bii = MonsterImageManager.getUncachedImage(name);
			bii = ImageTransformer.templateTransformImage(bii, transformColor, MonsterImageManager.MONSTER_IMAGE_SIZE);
			MonsterImageCache.addToCache(name, bii);
		}
		for (final CacheEntry element : MonsterImageCache.cache) {
			if (name.equals(element.getName())) {
				return element.getImage();
			}
		}
		return null;
	}

	private static void expandCache() {
		final CacheEntry[] tempCache = new CacheEntry[MonsterImageCache.cache.length
				+ MonsterImageCache.CACHE_INCREMENT];
		for (int x = 0; x < MonsterImageCache.CACHE_SIZE; x++) {
			tempCache[x] = MonsterImageCache.cache[x];
		}
		MonsterImageCache.cache = tempCache;
	}

	static synchronized void addToCache(final String name, final BufferedImageIcon bii) {
		if (MonsterImageCache.cache == null) {
			MonsterImageCache.cache = new CacheEntry[MonsterImageCache.CACHE_INCREMENT];
		}
		if (MonsterImageCache.CACHE_SIZE == MonsterImageCache.cache.length) {
			MonsterImageCache.expandCache();
		}
		MonsterImageCache.cache[MonsterImageCache.CACHE_SIZE] = new CacheEntry(bii, name);
		MonsterImageCache.CACHE_SIZE++;
	}

	static synchronized boolean isInCache(final String name) {
		if (MonsterImageCache.cache == null) {
			MonsterImageCache.cache = new CacheEntry[MonsterImageCache.CACHE_INCREMENT];
		}
		for (int x = 0; x < MonsterImageCache.CACHE_SIZE; x++) {
			if (name.equals(MonsterImageCache.cache[x].getName())) {
				return true;
			}
		}
		return false;
	}
}