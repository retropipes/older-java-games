package net.worldwizard.lasertank.assets;

import java.util.ArrayList;

import net.worldwizard.lasertank.loaders.ImageLoader;

public class GameImageCache {
    // Fields
    private static ArrayList<GameImage> cache;

    // Constructor
    private GameImageCache() {
	// Do nothing
    }

    // Methods
    public static GameImage get(final String name) {
	if (GameImageCache.cache == null) {
	    GameImageCache.cache = new ArrayList<>();
	}
	final GameImage test = new GameImage(name);
	if (GameImageCache.cache.contains(test)) {
	    final int index = GameImageCache.cache.indexOf(test);
	    return GameImageCache.cache.get(index);
	} else {
	    final GameImage gi = ImageLoader.loadObjectImage(name);
	    GameImageCache.cache.add(gi);
	    return gi;
	}
    }

    public static GameImage getComposite(final GameImage... gic) {
	if (GameImageCache.cache == null) {
	    GameImageCache.cache = new ArrayList<>();
	}
	final String cacheName = GameImage.generateCacheName(gic);
	final GameImage test = new GameImage(cacheName);
	if (GameImageCache.cache.contains(test)) {
	    final int index = GameImageCache.cache.indexOf(test);
	    return GameImageCache.cache.get(index);
	} else {
	    final GameImage gi = new GameImage(gic);
	    GameImageCache.cache.add(gi);
	    return gi;
	}
    }
}
