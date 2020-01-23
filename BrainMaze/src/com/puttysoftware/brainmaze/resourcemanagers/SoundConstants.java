package com.puttysoftware.brainmaze.resourcemanagers;

public class SoundConstants {
    // Public Category Constants
    public static final int SOUND_CATEGORY_USER_INTERFACE = 0;
    public static final int SOUND_CATEGORY_SOLVING_MAZE = 1;
    // Public Sound Constants
    public static final int SOUND_LOGO = 0;
    public static final int SOUND_ACTION_FAILED = 1;
    public static final int SOUND_BUTTON = 2;
    public static final int SOUND_CRACK = 3;
    public static final int SOUND_DOWN = 4;
    public static final int SOUND_FINISH = 5;
    public static final int SOUND_GRAB = 6;
    public static final int SOUND_IDENTIFY = 7;
    public static final int SOUND_FALL_INTO_PIT = 8;
    public static final int SOUND_LAVA = 9;
    public static final int SOUND_PUSH = 10;
    public static final int SOUND_PULL = 11;
    public static final int SOUND_SINK_BLOCK = 12;
    public static final int SOUND_SLIME = 13;
    public static final int SOUND_SPRINGBOARD = 14;
    public static final int SOUND_SUN_STONE = 15;
    public static final int SOUND_TELEPORT = 16;
    public static final int SOUND_UNLOCK = 17;
    public static final int SOUND_UP = 18;
    public static final int SOUND_WALK = 19;
    public static final int SOUND_WALK_FAILED = 20;
    public static final int SOUND_WALK_ON_ICE = 21;
    public static final int SOUND_WALK_ON_LAVA = 22;
    public static final int SOUND_WALK_ON_SLIME = 23;
    public static final int SOUND_WALK_ON_WATER = 24;
    public static final int SOUND_WATER = 25;
    // Package-Protected Constants
    static final String[] SOUND_NAMES = { "logo", "actionfailed", "button",
            "crack", "down", "finish", "grab", "identify", "intopit", "lava",
            "pushpull", "pushpull", "sinkblock", "slime", "spring", "sunstone",
            "teleport", "unlock", "up", "walk", "walkfailed", "walkice",
            "walklava", "walkslime", "walkwater", "water" };

    // Private constructor
    private SoundConstants() {
        // Do nothing
    }
}