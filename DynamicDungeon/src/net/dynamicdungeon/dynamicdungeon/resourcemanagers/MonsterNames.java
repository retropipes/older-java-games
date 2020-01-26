package net.dynamicdungeon.dynamicdungeon.resourcemanagers;

import net.dynamicdungeon.dynamicdungeon.datamanagers.MonsterDataManager;

public class MonsterNames {
    // Constants
    private static String[][] MONSTER_FILE_NAMES;
    private static String[][] MONSTER_DISPLAY_NAMES;
    private static final int MONSTER_LEVELS = 14;

    public static final String[] getAllFileNamesForLevel(final int level) {
        MonsterNames.initNames();
        return MonsterNames.MONSTER_FILE_NAMES[level];
    }

    public static final String[] getAllDisplayNamesForLevel(final int level) {
        MonsterNames.initNames();
        return MonsterNames.MONSTER_DISPLAY_NAMES[level];
    }

    private static void initNames() {
        if (MonsterNames.MONSTER_FILE_NAMES == null) {
            MonsterNames.MONSTER_FILE_NAMES = new String[MonsterNames.MONSTER_LEVELS][];
            for (int z = 0; z < MonsterNames.MONSTER_LEVELS; z++) {
                MonsterNames.MONSTER_FILE_NAMES[z] = MonsterDataManager
                        .getMonsterData(z + 1, false);
            }
        }
        if (MonsterNames.MONSTER_DISPLAY_NAMES == null) {
            MonsterNames.MONSTER_DISPLAY_NAMES = new String[MonsterNames.MONSTER_LEVELS][];
            for (int z = 0; z < MonsterNames.MONSTER_LEVELS; z++) {
                MonsterNames.MONSTER_DISPLAY_NAMES[z] = MonsterDataManager
                        .getMonsterData(z + 1, true);
            }
        }
    }

    // Private constructor
    private MonsterNames() {
        // Do nothing
    }
}