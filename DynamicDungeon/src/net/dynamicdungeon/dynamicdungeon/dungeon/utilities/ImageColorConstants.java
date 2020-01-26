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
    private static final int[] LEVEL_COLORS = new int[] {
            ImageColorConstants.COLOR_00, ImageColorConstants.COLOR_01,
            ImageColorConstants.COLOR_02, ImageColorConstants.COLOR_03,
            ImageColorConstants.COLOR_04, ImageColorConstants.COLOR_05,
            ImageColorConstants.COLOR_06, ImageColorConstants.COLOR_07,
            ImageColorConstants.COLOR_08, ImageColorConstants.COLOR_09,
            ImageColorConstants.COLOR_10, ImageColorConstants.COLOR_11,
            ImageColorConstants.COLOR_12, ImageColorConstants.COLOR_13,
            ImageColorConstants.COLOR_14 };

    public static int getColorForLevel(final int level) {
        return ImageColorConstants.LEVEL_COLORS[level];
    }
}
