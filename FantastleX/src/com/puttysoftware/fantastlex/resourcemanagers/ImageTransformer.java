package com.puttysoftware.fantastlex.resourcemanagers;

import java.awt.Color;

import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;
import com.puttysoftware.images.BufferedImageIcon;

public class ImageTransformer {
    public static final int MAX_WINDOW_SIZE = 700;
    private static final Color TRANSPARENT = new Color(200, 100, 100);
    private static Color REPLACE = new Color(223, 223, 223);

    public static Color generateEdgeColor(final int tc1, final int tc2) {
        final Color c1 = new Color(tc1);
        final Color c2 = new Color(tc2);
        final int tr1 = c1.getRed();
        final int tg1 = c1.getGreen();
        final int tb1 = c1.getBlue();
        final int tr2 = c2.getRed();
        final int tg2 = c2.getGreen();
        final int tb2 = c2.getBlue();
        final int r = (tr1 + 1 + tr2 + 1) / 2 - 1;
        final int g = (tg1 + 1 + tg2 + 1) / 2 - 1;
        final int b = (tb1 + 1 + tb2 + 1) / 2 - 1;
        return new Color(r, g, b);
    }

    static BufferedImageIcon templateTransformImage(
            final BufferedImageIcon input, final int transformColor) {
        if (transformColor == ColorConstants.COLOR_NONE) {
            return input;
        } else {
            try {
                final BufferedImageIcon result = new BufferedImageIcon(input);
                for (int x = 0; x < ImageTransformer.getGraphicSize(); x++) {
                    for (int y = 0; y < ImageTransformer
                            .getGraphicSize(); y++) {
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

    public static BufferedImageIcon getTransformedImage(
            final BufferedImageIcon icon) {
        try {
            final BufferedImageIcon result = new BufferedImageIcon(icon);
            if (icon != null) {
                for (int x = 0; x < ImageTransformer.getGraphicSize(); x++) {
                    for (int y = 0; y < ImageTransformer
                            .getGraphicSize(); y++) {
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
            final BufferedImageIcon icon1, final BufferedImageIcon icon2) {
        try {
            final BufferedImageIcon result = new BufferedImageIcon(icon2);
            if (icon1 != null && icon2 != null) {
                for (int x = 0; x < ImageTransformer.getGraphicSize(); x++) {
                    for (int y = 0; y < ImageTransformer
                            .getGraphicSize(); y++) {
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
            final BufferedImageIcon icon3) {
        try {
            final BufferedImageIcon icon4 = ImageTransformer
                    .getCompositeImage(icon1, icon2);
            final BufferedImageIcon result = new BufferedImageIcon(icon3);
            if (icon3 != null && icon4 != null) {
                for (int x = 0; x < ImageTransformer.getGraphicSize(); x++) {
                    for (int y = 0; y < ImageTransformer
                            .getGraphicSize(); y++) {
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
