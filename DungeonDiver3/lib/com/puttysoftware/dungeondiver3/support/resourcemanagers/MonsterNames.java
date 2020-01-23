/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.resourcemanagers;

import com.puttysoftware.dungeondiver3.support.datamanagers.MonsterDataManager;

public class MonsterNames {
    // Fields
    private static String[] CACHE;
    private static boolean CACHE_CREATED = false;

    public static String[] getAllNames() {
        if (!MonsterNames.CACHE_CREATED) {
            MonsterNames.CACHE = MonsterDataManager.getMonsterData();
            MonsterNames.CACHE_CREATED = true;
        }
        return MonsterNames.CACHE;
    }
}
