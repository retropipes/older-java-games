/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.resourcemanagers;

import studio.ignitionigloogames.twistedtrek.import2.datamanagers.MonsterDataManager;

public class MonsterNames {
    // Package-Protected Constants
    private static final String[] MONSTER_NAMES = MonsterDataManager.getMonsterData();

    public static final String[] getAllNames() {
	return MonsterNames.MONSTER_NAMES;
    }

    // Private constructor
    private MonsterNames() {
	// Do nothing
    }
}