package net.dynamicdungeon.dynamicdungeon.resourcemanagers;

import java.awt.Color;

import net.dynamicdungeon.dynamicdungeon.creatures.monsters.Element;
import net.dynamicdungeon.dynamicdungeon.dungeon.utilities.ImageColorConstants;
import net.dynamicdungeon.dynamicdungeon.prefs.PreferencesManager;
import net.dynamicdungeon.images.BufferedImageIcon;
import net.dynamicdungeon.images.BufferedRetinaImageIcon;

public class ImageTransformer {
    public static final int MAX_WINDOW_SIZE = 700;
    private static final Color TRANSPARENT = new Color(200, 100, 100);
    private static Color REPLACE = new Color(200, 100, 100, 0);
    private static final Color MONSTER_FIRST = new Color(200, 100, 100);
    private static final Color MONSTER_SECOND = new Color(100, 200, 100);
    private static final Color MONSTER_OUTLINE = new Color(100, 100, 200);
    private static final Color MONSTER_THIRD = new Color(200, 200, 100);
    private static final Color MONSTER_FOURTH = new Color(200, 100, 200);
    private static final Color MONSTER_FIFTH = new Color(100, 200, 200);
    private static final Color MONSTER_EYE = new Color(100, 100, 100);
    private static final int[] MONSTER_COLORS = new int[] {
            ImageTransformer.MONSTER_FIRST.getRGB(),
            ImageTransformer.MONSTER_SECOND.getRGB(),
            ImageTransformer.MONSTER_OUTLINE.getRGB(),
            ImageTransformer.MONSTER_THIRD.getRGB(),
            ImageTransformer.MONSTER_FOURTH.getRGB(),
            ImageTransformer.MONSTER_FIFTH.getRGB(),
            ImageTransformer.MONSTER_EYE.getRGB() };

    static BufferedImageIcon templateTransformImage(
            final BufferedImageIcon input, final int transformColor,
            final int imageSize) {
        if (transformColor == ImageColorConstants.COLOR_NONE) {
            return input;
        } else {
            try {
                BufferedImageIcon result;
                if (PreferencesManager.getHighDefEnabled()) {
                    result = new BufferedRetinaImageIcon(input);
                } else {
                    result = new BufferedImageIcon(input);
                }
                for (int x = 0; x < imageSize; x++) {
                    for (int y = 0; y < imageSize; y++) {
                        final int pixel = input.getRGB(x, y);
                        final Color c = new Color(pixel);
                        final int r = c.getRed();
                        final int g = c.getGreen();
                        final int b = c.getBlue();
                        if (r == g && r == b && g == b) {
                            final Color tc = new Color(transformColor);
                            final double tr = (tc.getRed() + 1) / 256.0;
                            final double tg = (tc.getGreen() + 1) / 256.0;
                            final double tb = (tc.getBlue() + 1) / 256.0;
                            final int newR = (int) (r * tr);
                            final int newG = (int) (g * tg);
                            final int newB = (int) (b * tb);
                            final Color nc = new Color(newR, newG, newB);
                            result.setRGB(x, y, nc.getRGB());
                        }
                    }
                }
                return result;
            } catch (final NullPointerException np) {
                return input;
            }
        }
    }

    static BufferedImageIcon templateTransformMonsterImage(
            final BufferedImageIcon input, final Element e,
            final int imageSize) {
        if (input != null && e != null) {
            final Color[] tcs = e.getTransformColors();
            BufferedImageIcon result;
            if (PreferencesManager.getHighDefEnabled()) {
                result = new BufferedRetinaImageIcon(input);
            } else {
                result = new BufferedImageIcon(input);
            }
            for (int x = 0; x < imageSize; x++) {
                for (int y = 0; y < imageSize; y++) {
                    final int pixel = input.getRGB(x, y);
                    final Color c = new Color(pixel, true);
                    final int a = c.getAlpha();
                    if (a != 0) {
                        for (int z = 0; z < ImageTransformer.MONSTER_COLORS.length; z++) {
                            if (pixel == ImageTransformer.MONSTER_COLORS[z]) {
                                final int nc = tcs[z].getRGB();
                                result.setRGB(x, y, nc);
                            }
                        }
                    }
                }
            }
            return result;
        } else {
            return null;
        }
    }

    public static BufferedImageIcon getTransformedImage(
            final BufferedImageIcon icon, final int imageSize) {
        try {
            BufferedImageIcon result;
            if (PreferencesManager.getHighDefEnabled()) {
                result = new BufferedRetinaImageIcon(icon);
            } else {
                result = new BufferedImageIcon(icon);
            }
            if (icon != null) {
                for (int x = 0; x < imageSize; x++) {
                    for (int y = 0; y < imageSize; y++) {
                        final int pixel = icon.getRGB(x, y);
                        final Color c = new Color(pixel);
                        if (c.equals(ImageTransformer.TRANSPARENT)) {
                            result.setRGB(x, y,
                                    ImageTransformer.REPLACE.getRGB());
                        }
                    }
                }
                return result;
            } else {
                return null;
            }
        } catch (final NullPointerException np) {
            return null;
        } catch (final IllegalArgumentException ia) {
            return null;
        }
    }

    public static BufferedImageIcon getCompositeImage(
            final BufferedImageIcon icon1, final BufferedImageIcon icon2,
            final int imageSize) {
        try {
            BufferedImageIcon result;
            if (PreferencesManager.getHighDefEnabled()) {
                result = new BufferedRetinaImageIcon(icon2);
            } else {
                result = new BufferedImageIcon(icon2);
            }
            if (icon1 != null && icon2 != null) {
                for (int x = 0; x < imageSize; x++) {
                    for (int y = 0; y < imageSize; y++) {
                        final int pixel = icon2.getRGB(x, y);
                        final Color c = new Color(pixel);
                        if (c.equals(ImageTransformer.TRANSPARENT)) {
                            result.setRGB(x, y, icon1.getRGB(x, y));
                        }
                    }
                }
                return result;
            } else {
                return null;
            }
        } catch (final NullPointerException np) {
            return null;
        } catch (final IllegalArgumentException ia) {
            return null;
        }
    }

    public static BufferedImageIcon getVirtualCompositeImage(
            final BufferedImageIcon icon1, final BufferedImageIcon icon2,
            final BufferedImageIcon icon3, final int imageSize) {
        try {
            final BufferedImageIcon icon4 = ImageTransformer
                    .getCompositeImage(icon1, icon2, imageSize);
            BufferedImageIcon result;
            if (PreferencesManager.getHighDefEnabled()) {
                result = new BufferedRetinaImageIcon(icon3);
            } else {
                result = new BufferedImageIcon(icon3);
            }
            if (icon3 != null && icon4 != null) {
                for (int x = 0; x < imageSize; x++) {
                    for (int y = 0; y < imageSize; y++) {
                        final int pixel = icon3.getRGB(x, y);
                        final Color c = new Color(pixel);
                        if (c.equals(ImageTransformer.TRANSPARENT)) {
                            result.setRGB(x, y, icon4.getRGB(x, y));
                        }
                    }
                }
                return result;
            } else {
                return null;
            }
        } catch (final NullPointerException np) {
            return null;
        } catch (final IllegalArgumentException ia) {
            return null;
        }
    }

    public static String normalizeName(final String name) {
        final StringBuilder sb = new StringBuilder(name);
        for (int x = 0; x < sb.length(); x++) {
            if (!Character.isLetter(sb.charAt(x))
                    && !Character.isDigit(sb.charAt(x))) {
                sb.setCharAt(x, '_');
            }
        }
        return sb.toString().toLowerCase();
    }

    public static int getGraphicSize() {
        return 32;
    }
}
