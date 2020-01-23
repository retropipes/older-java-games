package com.puttysoftware.mazerunner2.resourcemanagers;

import java.awt.Color;

import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;

public class ImageTransformer {
    public static final int MAX_WINDOW_SIZE = 700;
    private static final Color TRANSPARENT = new Color(200, 100, 100);
    private static Color REPLACE = new Color(223, 223, 223);

    public static Color generateEdgeColor(final int tc1, final int tc2) {
        Color c1 = new Color(tc1);
        Color c2 = new Color(tc2);
        int tr1 = c1.getRed();
        int tg1 = c1.getGreen();
        int tb1 = c1.getBlue();
        int tr2 = c2.getRed();
        int tg2 = c2.getGreen();
        int tb2 = c2.getBlue();
        int r = (((tr1 + 1) + (tr2 + 1)) / 2) - 1;
        int g = (((tg1 + 1) + (tg2 + 1)) / 2) - 1;
        int b = (((tb1 + 1) + (tb2 + 1)) / 2) - 1;
        return new Color(r, g, b);
    }

    static BufferedImageIcon templateTransformImage(BufferedImageIcon input,
            int transformColor) {
        if (transformColor == ColorConstants.COLOR_NONE) {
            return input;
        } else {
            try {
                BufferedImageIcon result = new BufferedImageIcon(input);
                for (int x = 0; x < ImageTransformer.getGraphicSize(); x++) {
                    for (int y = 0; y < ImageTransformer.getGraphicSize(); y++) {
                        int pixel = input.getRGB(x, y);
                        Color c = new Color(pixel);
                        int r = c.getRed();
                        int g = c.getGreen();
                        int b = c.getBlue();
                        if (r == g && r == b && g == b) {
                            Color tc = new Color(transformColor);
                            double tr = (tc.getRed() + 1) / 256.0;
                            double tg = (tc.getGreen() + 1) / 256.0;
                            double tb = (tc.getBlue() + 1) / 256.0;
                            int newR = (int) (r * tr);
                            int newG = (int) (g * tg);
                            int newB = (int) (b * tb);
                            Color nc = new Color(newR, newG, newB);
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
            BufferedImageIcon result = new BufferedImageIcon(icon);
            if (icon != null) {
                for (int x = 0; x < ImageTransformer.getGraphicSize(); x++) {
                    for (int y = 0; y < ImageTransformer.getGraphicSize(); y++) {
                        int pixel = icon.getRGB(x, y);
                        Color c = new Color(pixel);
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
            BufferedImageIcon result = new BufferedImageIcon(icon2);
            if (icon1 != null && icon2 != null) {
                for (int x = 0; x < ImageTransformer.getGraphicSize(); x++) {
                    for (int y = 0; y < ImageTransformer.getGraphicSize(); y++) {
                        int pixel = icon2.getRGB(x, y);
                        Color c = new Color(pixel);
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
            final BufferedImageIcon icon4 = ImageTransformer.getCompositeImage(
                    icon1, icon2);
            BufferedImageIcon result = new BufferedImageIcon(icon3);
            if (icon3 != null && icon4 != null) {
                for (int x = 0; x < ImageTransformer.getGraphicSize(); x++) {
                    for (int y = 0; y < ImageTransformer.getGraphicSize(); y++) {
                        int pixel = icon3.getRGB(x, y);
                        Color c = new Color(pixel);
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

    public static String normalizeName(String name) {
        StringBuilder sb = new StringBuilder(name);
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
