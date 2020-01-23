package net.worldwizard.worldz.resourcemanagers;

import net.worldwizard.worldz.resourcemanagers.datamanagers.MonsterDataManager;

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
