package net.dynamicdungeon.dynamicdungeon.resourcemanagers;

import net.dynamicdungeon.dynamicdungeon.datamanagers.MonsterDataManager;

public class MonsterNames {
    // Constants
    private static String[][] MONSTER_FILE_NAMES;
    private static String[][] MONSTER_DISPLAY_NAMES;
    private static final int MONSTER_LEVELS = 14;

    public static final String[] getAllFileNamesForLevel(final int level) {
	initNames();
	return MONSTER_FILE_NAMES[level];
    }

    public static final String[] getAllDisplayNamesForLevel(final int level) {
	initNames();
	return MONSTER_DISPLAY_NAMES[level];
    }

    private static void initNames() {
	if (MONSTER_FILE_NAMES == null) {
	    MONSTER_FILE_NAMES = new String[MONSTER_LEVELS][];
	    for (int z = 0; z < MONSTER_LEVELS; z++) {
		MONSTER_FILE_NAMES[z] = MonsterDataManager.getMonsterData(
			z + 1, false);
	    }
	}
	if (MONSTER_DISPLAY_NAMES == null) {
	    MONSTER_DISPLAY_NAMES = new String[MONSTER_LEVELS][];
	    for (int z = 0; z < MONSTER_LEVELS; z++) {
		MONSTER_DISPLAY_NAMES[z] = MonsterDataManager.getMonsterData(
			z + 1, true);
	    }
	}
    }

    // Private constructor
    private MonsterNames() {
	// Do nothing
    }
}