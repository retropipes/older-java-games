/*  BrainMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.brainmaze.generic;

import java.awt.Color;

public interface ColorConstants {
    public static final int COLOR_NONE = -1;
    public static final int COLOR_BLUE = new Color(0, 0, 255).getRGB();
    public static final int COLOR_CYAN = new Color(0, 255, 255).getRGB();
    public static final int COLOR_GREEN = new Color(0, 255, 0).getRGB();
    public static final int COLOR_MAGENTA = new Color(255, 0, 255).getRGB();
    public static final int COLOR_ORANGE = new Color(255, 127, 0).getRGB();
    public static final int COLOR_PURPLE = new Color(127, 0, 255).getRGB();
    public static final int COLOR_RED = new Color(255, 0, 0).getRGB();
    public static final int COLOR_ROSE = new Color(255, 0, 127).getRGB();
    public static final int COLOR_SEAWEED = new Color(127, 255, 0).getRGB();
    public static final int COLOR_SKY = new Color(0, 127, 255).getRGB();
    public static final int COLOR_WHITE = new Color(255, 255, 255).getRGB();
    public static final int COLOR_YELLOW = new Color(255, 255, 0).getRGB();
    public static final int COLOR_WATER = new Color(127, 127, 255).getRGB();
    public static final int COLOR_BROWN = new Color(127, 63, 31).getRGB();
    public static final int COLOR_TUNDRA = new Color(223, 191, 255).getRGB();
    public static final int COLOR_GRASS = new Color(0, 191, 0).getRGB();
    public static final int COLOR_SAND = new Color(255, 191, 127).getRGB();
    public static final int COLOR_BLACK = new Color(0, 0, 0).getRGB();
    public static final int COLOR_GRAY = new Color(127, 127, 127).getRGB();
    public static final int COLOR_INVISIBLE = new Color(191, 191, 191).getRGB();
    public static final int COLOR_INVISIBLE_TELEPORT_ATTRIBUTE = new Color(127,
            127, 0).getRGB();
    public static final int COLOR_BLOCK = new Color(191, 0, 0).getRGB();
    public static final int COLOR_INVISIBLE_BLOCK_TELEPORT_ATTRIBUTE = new Color(
            63, 0, 127).getRGB();
    public static final int COLOR_FAKE = new Color(159, 79, 49).getRGB();
    public static final int COLOR_DOOR = new Color(191, 95, 47).getRGB();
    public static final int COLOR_SUN_DOOR = new Color(255, 255, 127).getRGB();
    public static final int COLOR_MOON_DOOR = new Color(223, 223, 223).getRGB();
}
