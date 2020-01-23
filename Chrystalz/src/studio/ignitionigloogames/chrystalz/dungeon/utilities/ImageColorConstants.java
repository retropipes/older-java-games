/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.dungeon.utilities;

import java.awt.Color;

public class ImageColorConstants {
    public static final int COLOR_NONE = -1;
    private static final int COLOR_00 = new Color(127, 127, 255).getRGB();
    private static final int COLOR_01 = new Color(127, 159, 255).getRGB();
    private static final int COLOR_02 = new Color(159, 127, 255).getRGB();
    private static final int COLOR_03 = new Color(159, 159, 255).getRGB();
    private static final int COLOR_04 = new Color(127, 191, 255).getRGB();
    private static final int COLOR_05 = new Color(159, 191, 255).getRGB();
    private static final int COLOR_06 = new Color(191, 191, 255).getRGB();
    private static final int COLOR_07 = new Color(127, 223, 255).getRGB();
    private static final int COLOR_08 = new Color(159, 223, 255).getRGB();
    private static final int COLOR_09 = new Color(191, 223, 255).getRGB();
    private static final int COLOR_10 = new Color(223, 191, 255).getRGB();
    private static final int COLOR_11 = new Color(223, 223, 255).getRGB();
    private static final int COLOR_12 = new Color(0, 255, 255).getRGB();
    private static final int COLOR_13 = new Color(127, 255, 255).getRGB();
    private static final int COLOR_14 = new Color(159, 255, 255).getRGB();
    private static final int COLOR_15 = new Color(191, 255, 255).getRGB();
    private static final int COLOR_16 = new Color(223, 255, 255).getRGB();
    private static final int COLOR_17 = new Color(0, 255, 0).getRGB();
    private static final int COLOR_18 = new Color(0, 255, 63).getRGB();
    private static final int COLOR_19 = new Color(0, 255, 127).getRGB();
    private static final int COLOR_20 = new Color(0, 255, 191).getRGB();
    private static final int COLOR_21 = new Color(63, 255, 0).getRGB();
    private static final int COLOR_22 = new Color(127, 255, 0).getRGB();
    private static final int COLOR_23 = new Color(191, 255, 0).getRGB();
    private static final int COLOR_24 = new Color(63, 255, 63).getRGB();
    private static final int COLOR_25 = new Color(63, 255, 127).getRGB();
    private static final int COLOR_26 = new Color(127, 255, 63).getRGB();
    private static final int COLOR_27 = new Color(127, 255, 127).getRGB();
    private static final int COLOR_28 = new Color(63, 255, 191).getRGB();
    private static final int COLOR_29 = new Color(191, 255, 63).getRGB();
    private static final int COLOR_30 = new Color(255, 255, 0).getRGB();
    private static final int COLOR_31 = new Color(255, 255, 63).getRGB();
    private static final int COLOR_32 = new Color(255, 255, 127).getRGB();
    private static final int COLOR_33 = new Color(255, 255, 191).getRGB();
    private static final int COLOR_34 = new Color(255, 0, 0).getRGB();
    private static final int COLOR_35 = new Color(255, 63, 0).getRGB();
    private static final int COLOR_36 = new Color(255, 127, 0).getRGB();
    private static final int COLOR_37 = new Color(255, 191, 0).getRGB();
    private static final int COLOR_38 = new Color(255, 0, 63).getRGB();
    private static final int COLOR_39 = new Color(255, 0, 127).getRGB();
    private static final int COLOR_40 = new Color(255, 0, 191).getRGB();
    private static final int COLOR_41 = new Color(255, 63, 63).getRGB();
    private static final int COLOR_42 = new Color(255, 63, 127).getRGB();
    private static final int COLOR_43 = new Color(255, 127, 63).getRGB();
    private static final int COLOR_44 = new Color(255, 127, 127).getRGB();
    private static final int COLOR_45 = new Color(255, 63, 191).getRGB();
    private static final int COLOR_46 = new Color(255, 127, 191).getRGB();
    private static final int COLOR_47 = new Color(255, 191, 63).getRGB();
    private static final int COLOR_48 = new Color(255, 191, 127).getRGB();
    private static final int COLOR_49 = new Color(255, 191, 191).getRGB();
    private static final int COLOR_50 = new Color(255, 0, 255).getRGB();
    private static final int COLOR_51 = new Color(255, 63, 255).getRGB();
    private static final int COLOR_52 = new Color(255, 127, 255).getRGB();
    private static final int COLOR_53 = new Color(255, 191, 255).getRGB();
    private static final int COLOR_54 = new Color(255, 255, 255).getRGB();
    private static final int[] LEVEL_COLORS = new int[] {
            ImageColorConstants.COLOR_00, ImageColorConstants.COLOR_01,
            ImageColorConstants.COLOR_02, ImageColorConstants.COLOR_03,
            ImageColorConstants.COLOR_04, ImageColorConstants.COLOR_05,
            ImageColorConstants.COLOR_06, ImageColorConstants.COLOR_07,
            ImageColorConstants.COLOR_08, ImageColorConstants.COLOR_09,
            ImageColorConstants.COLOR_10, ImageColorConstants.COLOR_11,
            ImageColorConstants.COLOR_12, ImageColorConstants.COLOR_13,
            ImageColorConstants.COLOR_14, ImageColorConstants.COLOR_15,
            ImageColorConstants.COLOR_16, ImageColorConstants.COLOR_17,
            ImageColorConstants.COLOR_18, ImageColorConstants.COLOR_19,
            ImageColorConstants.COLOR_20, ImageColorConstants.COLOR_21,
            ImageColorConstants.COLOR_22, ImageColorConstants.COLOR_23,
            ImageColorConstants.COLOR_24, ImageColorConstants.COLOR_25,
            ImageColorConstants.COLOR_26, ImageColorConstants.COLOR_27,
            ImageColorConstants.COLOR_28, ImageColorConstants.COLOR_29,
            ImageColorConstants.COLOR_30, ImageColorConstants.COLOR_31,
            ImageColorConstants.COLOR_32, ImageColorConstants.COLOR_33,
            ImageColorConstants.COLOR_34, ImageColorConstants.COLOR_35,
            ImageColorConstants.COLOR_36, ImageColorConstants.COLOR_37,
            ImageColorConstants.COLOR_38, ImageColorConstants.COLOR_39,
            ImageColorConstants.COLOR_40, ImageColorConstants.COLOR_41,
            ImageColorConstants.COLOR_42, ImageColorConstants.COLOR_43,
            ImageColorConstants.COLOR_44, ImageColorConstants.COLOR_45,
            ImageColorConstants.COLOR_46, ImageColorConstants.COLOR_47,
            ImageColorConstants.COLOR_48, ImageColorConstants.COLOR_49,
            ImageColorConstants.COLOR_50, ImageColorConstants.COLOR_51,
            ImageColorConstants.COLOR_52, ImageColorConstants.COLOR_53,
            ImageColorConstants.COLOR_54 };

    public static int getColorForLevel(final int level) {
        return ImageColorConstants.LEVEL_COLORS[level];
    }
}
