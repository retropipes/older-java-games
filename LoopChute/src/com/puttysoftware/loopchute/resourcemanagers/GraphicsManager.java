/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.resourcemanagers;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.loopchute.generic.ColorConstants;

public class GraphicsManager {
    public static final int MAX_WINDOW_SIZE = 700;
    private static final Color TRANSPARENT = new Color(200, 100, 100);
    private static Color REPLACE = new Color(223, 223, 223);
    private static final String DEFAULT_LOAD_PATH = "/com/puttysoftware/loopchute/resources/graphics/objects/";
    private static String LOAD_PATH = GraphicsManager.DEFAULT_LOAD_PATH;
    private static Class<?> LOAD_CLASS = GraphicsManager.class;

    public static BufferedImageIcon getImage(final String name,
            final String baseName, final int transformColor,
            final String attrName, final int attrColor) {
        // Get it from the cache
        return ImageCache.getCachedImage(name, baseName, transformColor,
                attrName, attrColor);
    }

    public static void addImageToCache(final String name,
            final BufferedImageIcon img) {
        if (!ImageCache.isInCache(name)) {
            ImageCache.addToCache(name, img);
        }
    }

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

    static BufferedImageIcon getUncachedImage(final String name) {
        try {
            final String normalName = GraphicsManager.normalizeName(name);
            final URL url = GraphicsManager.LOAD_CLASS.getResource(
                    GraphicsManager.LOAD_PATH + normalName + ".png");
            final BufferedImage image = ImageIO.read(url);
            return new BufferedImageIcon(image);
        } catch (final IOException ie) {
            return null;
        } catch (final NullPointerException np) {
            return null;
        } catch (final IllegalArgumentException ia) {
            return null;
        }
    }

    static BufferedImageIcon templateTransformImage(
            final BufferedImageIcon input, final int transformColor) {
        if (transformColor == ColorConstants.COLOR_NONE) {
            return input;
        } else {
            try {
                final BufferedImageIcon result = new BufferedImageIcon(input);
                for (int x = 0; x < GraphicsManager.getGraphicSize(); x++) {
                    for (int y = 0; y < GraphicsManager.getGraphicSize(); y++) {
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

    public static BufferedImageIcon getTransformedImage(final String name,
            final String baseName, final int transformColor,
            final String attrName, final int attrColor) {
        try {
            final BufferedImageIcon icon = GraphicsManager.getImage(name,
                    baseName, transformColor, attrName, attrColor);
            final BufferedImageIcon result = new BufferedImageIcon(icon);
            if (icon != null) {
                for (int x = 0; x < GraphicsManager.getGraphicSize(); x++) {
                    for (int y = 0; y < GraphicsManager.getGraphicSize(); y++) {
                        final int pixel = icon.getRGB(x, y);
                        final Color c = new Color(pixel);
                        if (c.equals(GraphicsManager.TRANSPARENT)) {
                            result.setRGB(x, y,
                                    GraphicsManager.REPLACE.getRGB());
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
                for (int x = 0; x < GraphicsManager.getGraphicSize(); x++) {
                    for (int y = 0; y < GraphicsManager.getGraphicSize(); y++) {
                        final int pixel = icon2.getRGB(x, y);
                        final Color c = new Color(pixel);
                        if (c.equals(GraphicsManager.TRANSPARENT)) {
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
            final BufferedImageIcon icon4 = GraphicsManager
                    .getCompositeImage(icon1, icon2);
            final BufferedImageIcon result = new BufferedImageIcon(icon3);
            if (icon3 != null && icon4 != null) {
                for (int x = 0; x < GraphicsManager.getGraphicSize(); x++) {
                    for (int y = 0; y < GraphicsManager.getGraphicSize(); y++) {
                        final int pixel = icon3.getRGB(x, y);
                        final Color c = new Color(pixel);
                        if (c.equals(GraphicsManager.TRANSPARENT)) {
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

    private static String normalizeName(final String name) {
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
