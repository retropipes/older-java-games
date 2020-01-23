/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue.assets;

import com.puttysoftware.riskyrescue.assets.data.ImageDataManager;
import com.puttysoftware.riskyrescue.assets.data.MonsterDataManager;

public class MonsterNames {
    // Fields
    private static final int CACHE_ROWS = 5;
    private static boolean CACHES_CREATED = false;
    private static String[][] NAME_CACHE;
    private static String[][] IMAGE_CACHE;

    private static synchronized void createCaches() {
        MonsterNames.NAME_CACHE = new String[MonsterNames.CACHE_ROWS][];
        MonsterNames.IMAGE_CACHE = new String[MonsterNames.CACHE_ROWS][];
        for (int index = 0; index < MonsterNames.CACHE_ROWS; index++) {
            MonsterNames.NAME_CACHE[index] = MonsterDataManager
                    .getMonsterData(index);
            MonsterNames.IMAGE_CACHE[index] = ImageDataManager
                    .getMonsterImageData(index);
        }
        MonsterNames.CACHES_CREATED = true;
    }

    public static synchronized String[] getAllNames(final int index) {
        if (!MonsterNames.CACHES_CREATED) {
            MonsterNames.createCaches();
        }
        return MonsterNames.NAME_CACHE[index];
    }

    public static synchronized String getImageName(final int dungeonIndex,
            final int nameIndex) {
        if (!MonsterNames.CACHES_CREATED) {
            MonsterNames.createCaches();
        }
        return MonsterNames.IMAGE_CACHE[dungeonIndex][nameIndex];
    }
}
