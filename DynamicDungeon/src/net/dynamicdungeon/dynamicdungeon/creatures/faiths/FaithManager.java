/*  DynamicDungeon: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.creatures.faiths;

import net.dynamicdungeon.randomrange.RandomRange;

public class FaithManager {
    private static boolean CACHE_CREATED = false;
    private static Faith[] CACHE;

    public static Faith getFaith(final int faithID) {
        FaithManager.createCache();
        return FaithManager.CACHE[faithID];
    }

    public static Faith getRandomFaith() {
        FaithManager.createCache();
        final int faithID = new RandomRange(0, FaithManager.CACHE.length - 1)
                .generate();
        return FaithManager.CACHE[faithID];
    }

    private static void createCache() {
        if (!FaithManager.CACHE_CREATED) {
            // Create cache
            if (!FaithConstants.faithsReady()) {
                FaithConstants.initFaiths();
            }
            final int fc = FaithConstants.getFaithsCount();
            FaithManager.CACHE = new Faith[fc];
            for (int x = 0; x < fc; x++) {
                FaithManager.CACHE[x] = new Faith(x);
            }
            FaithManager.CACHE_CREATED = true;
        }
    }
}
