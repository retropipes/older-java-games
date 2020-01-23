/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.resourcemanagers;

import com.puttysoftware.gemma.support.datamanagers.MonsterDataManager;

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
