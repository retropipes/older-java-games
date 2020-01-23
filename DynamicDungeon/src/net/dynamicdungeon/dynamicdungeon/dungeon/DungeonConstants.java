package net.dynamicdungeon.dynamicdungeon.dungeon;

public class DungeonConstants {
    public static final int LAYER_GROUND = 0;
    public static final int LAYER_OBJECT = 1;
    public static final int LAYER_COUNT = 2;
    public static final int VIRTUAL_LAYER_CHARACTER = 2;
    public static final int VISION_MODE_NONE = 0;
    public static final int VISION_MODE_EXPLORE = 1;
    public static final int VISION_MODE_LOS = 2;
    public static final int VISION_MODE_EXPLORE_AND_LOS = 3;
    public static final int MAZE_SIZE_BASE = 40;
    public static final int MAZE_SIZE_INCREMENT = 10;

    private DungeonConstants() {
	// Do nothing
    }
}
