package com.puttysoftware.ddremix.resourcemanagers;

import com.puttysoftware.ddremix.datamanagers.GraphicsDataManager;

public class ObjectImageConstants {
    public static final int OBJECT_IMAGE_NONE = -1;
    public static final int OBJECT_IMAGE_AMULET = 0;
    public static final int OBJECT_IMAGE_ARMOR_SHOP = 1;
    public static final int OBJECT_IMAGE_BANK = 3;
    public static final int OBJECT_IMAGE_BOMB = 4;
    public static final int OBJECT_IMAGE_BUTTON = 5;
    public static final int OBJECT_IMAGE_CCW_ROTATION_TRAP = 6;
    public static final int OBJECT_IMAGE_CLOSED_DOOR = 7;
    public static final int OBJECT_IMAGE_CONFUSION_TRAP = 8;
    public static final int OBJECT_IMAGE_CW_ROTATION_TRAP = 10;
    public static final int OBJECT_IMAGE_DARK_GEM = 11;
    public static final int OBJECT_IMAGE_DARKNESS = 12;
    public static final int OBJECT_IMAGE_DIZZINESS_TRAP = 13;
    public static final int OBJECT_IMAGE_DRUNK_TRAP = 14;
    public static final int OBJECT_IMAGE_EMPTY = 15;
    public static final int OBJECT_IMAGE_EXIT = 16;
    public static final int OBJECT_IMAGE_HAMMER = 17;
    public static final int OBJECT_IMAGE_HEAL_SHOP = 18;
    public static final int OBJECT_IMAGE_HEAL_TRAP = 19;
    public static final int OBJECT_IMAGE_HURT_TRAP = 20;
    public static final int OBJECT_IMAGE_ICE = 21;
    public static final int OBJECT_IMAGE_ITEM_SHOP = 22;
    public static final int OBJECT_IMAGE_KEY = 23;
    public static final int OBJECT_IMAGE_LIGHT_GEM = 24;
    public static final int OBJECT_IMAGE_LOCK = 25;
    public static final int OBJECT_IMAGE_MONSTER = 26;
    public static final int OBJECT_IMAGE_NOTE = 27;
    public static final int OBJECT_IMAGE_OPEN_DOOR = 28;
    public static final int OBJECT_IMAGE_PLAYER = 29;
    public static final int OBJECT_IMAGE_REGENERATOR = 30;
    public static final int OBJECT_IMAGE_SEALING_WALL = 31;
    public static final int OBJECT_IMAGE_SPELL_SHOP = 32;
    public static final int OBJECT_IMAGE_STAIRS_DOWN = 33;
    public static final int OBJECT_IMAGE_STAIRS_UP = 34;
    public static final int OBJECT_IMAGE_STONE = 35;
    public static final int OBJECT_IMAGE_TABLET = 36;
    public static final int OBJECT_IMAGE_TABLET_SLOT = 37;
    public static final int OBJECT_IMAGE_TILE = 38;
    public static final int OBJECT_IMAGE_U_TURN_TRAP = 40;
    public static final int OBJECT_IMAGE_VARIABLE_HEAL_TRAP = 41;
    public static final int OBJECT_IMAGE_VARIABLE_HURT_TRAP = 42;
    public static final int OBJECT_IMAGE_VOID = 43;
    public static final int OBJECT_IMAGE_WALL = 44;
    public static final int OBJECT_IMAGE_WALL_OFF = 45;
    public static final int OBJECT_IMAGE_WALL_ON = 46;
    public static final int OBJECT_IMAGE_WARP_TRAP = 47;
    public static final int OBJECT_IMAGE_WEAPONS_SHOP = 48;
    private static final String[] OBJECT_IMAGE_NAMES = GraphicsDataManager
            .getObjectGraphicsData();

    static String getObjectImageName(final int ID) {
        if (ID == ObjectImageConstants.OBJECT_IMAGE_NONE) {
            return "";
        } else {
            return ObjectImageConstants.OBJECT_IMAGE_NAMES[ID];
        }
    }
}
