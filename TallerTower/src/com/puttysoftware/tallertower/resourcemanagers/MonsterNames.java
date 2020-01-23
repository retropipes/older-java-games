package com.puttysoftware.tallertower.resourcemanagers;

import com.puttysoftware.tallertower.datamanagers.MonsterDataManager;

public class MonsterNames {
    // Package-Protected Constants
    private static final String[] MONSTER_NAMES = MonsterDataManager
            .getMonsterData();

    public static final String[] getAllNames() {
        return MONSTER_NAMES;
    }

    // Private constructor
    private MonsterNames() {
        // Do nothing
    }
}