/*  DynamicDungeon: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.dungeon.utilities;

import java.awt.Color;

public class ImageColorConstants {
    public static final int COLOR_NONE = -1;
    private static final int COLOR_00 = new Color(127, 127, 255).getRGB();
    private static final int COLOR_01 = new Color(127, 0, 255).getRGB();
    private static final int COLOR_02 = new Color(0, 127, 255).getRGB();
    private static final int COLOR_03 = new Color(0, 255, 255).getRGB();
    private static final int COLOR_04 = new Color(127, 255, 255).getRGB();
    private static final int COLOR_05 = new Color(0, 255, 127).getRGB();
    private static final int COLOR_06 = new Color(127, 255, 127).getRGB();
    private static final int COLOR_07 = new Color(0, 255, 0).getRGB();
    private static final int COLOR_08 = new Color(127, 255, 0).getRGB();
    private static final int COLOR_09 = new Color(255, 127, 0).getRGB();
    private static final int COLOR_10 = new Color(255, 0, 127).getRGB();
    private static final int COLOR_11 = new Color(255, 0, 0).getRGB();
    private static final int COLOR_12 = new Color(255, 225, 0).getRGB();
    private static final int COLOR_13 = new Color(255, 255, 127).getRGB();
    private static final int COLOR_14 = new Color(255, 255, 255).getRGB();
    private static final int[] LEVEL_COLORS = new int[] { COLOR_00, COLOR_01,
	    COLOR_02, COLOR_03, COLOR_04, COLOR_05, COLOR_06, COLOR_07,
	    COLOR_08, COLOR_09, COLOR_10, COLOR_11, COLOR_12, COLOR_13,
	    COLOR_14 };

    public static int getColorForLevel(final int level) {
	return LEVEL_COLORS[level];
    }
}
