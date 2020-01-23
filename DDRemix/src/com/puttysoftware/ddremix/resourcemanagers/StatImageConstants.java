package com.puttysoftware.ddremix.resourcemanagers;

import com.puttysoftware.ddremix.datamanagers.GraphicsDataManager;

public class StatImageConstants {
    public static final int STAT_IMAGE_ATTACK = 0;
    public static final int STAT_IMAGE_DEFENSE = 1;
    public static final int STAT_IMAGE_GOLD = 2;
    public static final int STAT_IMAGE_HEALTH = 3;
    public static final int STAT_IMAGE_LEVEL = 4;
    public static final int STAT_IMAGE_MAGIC = 5;
    public static final int STAT_IMAGE_POWER = 6;
    public static final int STAT_IMAGE_XP = 7;
    private static final String[] STAT_IMAGE_NAMES = GraphicsDataManager
            .getStatGraphicsData();

    static String getStatImageName(final int ID) {
        return StatImageConstants.STAT_IMAGE_NAMES[ID];
    }
}
