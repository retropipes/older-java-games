/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.resourcemanagers;

import studio.ignitionigloogames.twistedtrek.import1.resourcemanagers.datamanagers.MonsterDataManager;

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
