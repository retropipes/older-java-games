/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.resourcemanagers;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.weaselweb.WeaselWeb;
import com.puttysoftware.weaselweb.maze.generic.TemplateTransform;

public class ImageManager {
    public static final int MAX_WINDOW_SIZE = 700;
    private static final Color TRANSPARENT = new Color(200, 100, 100);
    private static Color REPLACE = new Color(223, 223, 223);
    private static final String DEFAULT_LOAD_PATH = "/com/puttysoftware/weaselweb/resources/graphics/";
    private static String LOAD_PATH = ImageManager.DEFAULT_LOAD_PATH;
    private static Class<?> LOAD_CLASS = ImageManager.class;

    public static BufferedImageIcon getImage(final String name) {
        // Get it from the cache
        return ImageCache.getCachedImage(name);
    }

    static BufferedImageIcon getUncachedImage(final String name) {
        try {
            final String normalName = ImageManager.normalizeName(name);
            final URL url = ImageManager.LOAD_CLASS.getResource(
                    ImageManager.LOAD_PATH + "objects/" + normalName + ".png");
            final BufferedImage image = ImageIO.read(url);
            final BufferedImageIcon icon = new BufferedImageIcon(image);
            return icon;
        } catch (final IOException ie) {
            return null;
        } catch (final NullPointerException np) {
            return null;
        } catch (final IllegalArgumentException ia) {
            return null;
        }
    }

    public static BufferedImageIcon getTransformedImage(final String name) {
        try {
            final BufferedImageIcon icon = ImageCache.getCachedImage(name);
            final BufferedImageIcon result = new BufferedImageIcon(icon);
            if (icon != null) {
                for (int x = 0; x < ImageManager.getGraphicSize(); x++) {
                    for (int y = 0; y < ImageManager.getGraphicSize(); y++) {
                        final int pixel = icon.getRGB(x, y);
                        final Color c = new Color(pixel);
                        if (c.equals(ImageManager.TRANSPARENT)) {
                            result.setRGB(x, y, ImageManager.REPLACE.getRGB());
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

    public static BufferedImageIcon getCompositeImage(final String name1,
            final String name2) {
        try {
            final BufferedImageIcon icon1 = ImageCache.getCachedImage(name1);
            final BufferedImageIcon icon2 = ImageCache.getCachedImage(name2);
            final BufferedImageIcon result = new BufferedImageIcon(icon2);
            if (icon1 != null && icon2 != null) {
                for (int x = 0; x < ImageManager.getGraphicSize(); x++) {
                    for (int y = 0; y < ImageManager.getGraphicSize(); y++) {
                        final int pixel = icon2.getRGB(x, y);
                        final Color c = new Color(pixel);
                        if (c.equals(ImageManager.TRANSPARENT)) {
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

    public static BufferedImageIcon getCompositeImage(
            final BufferedImageIcon icon1, final BufferedImageIcon icon2) {
        try {
            final BufferedImageIcon result = new BufferedImageIcon(icon2);
            if (icon1 != null && icon2 != null) {
                for (int x = 0; x < ImageManager.getGraphicSize(); x++) {
                    for (int y = 0; y < ImageManager.getGraphicSize(); y++) {
                        final int pixel = icon2.getRGB(x, y);
                        final Color c = new Color(pixel);
                        if (c.equals(ImageManager.TRANSPARENT)) {
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

    private static BufferedImageIcon getTemplateTransformedImage(
            final BufferedImageIcon icon, final TemplateTransform tt) {
        try {
            final BufferedImageIcon result = new BufferedImageIcon(icon);
            if (icon != null) {
                for (int x = 0; x < ImageManager.getGraphicSize(); x++) {
                    for (int y = 0; y < ImageManager.getGraphicSize(); y++) {
                        final int pixel = icon.getRGB(x, y);
                        final Color c = new Color(pixel);
                        if (!c.equals(ImageManager.TRANSPARENT)) {
                            result.setRGB(x, y, tt.applyTransform(c).getRGB());
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

    public static BufferedImageIcon getTransformedCompositeImage(
            final BufferedImageIcon icon1, final TemplateTransform tt1,
            final BufferedImageIcon icon2, final TemplateTransform tt2,
            final boolean transformedIcon1) {
        try {
            final BufferedImageIcon tIcon1;
            if (transformedIcon1) {
                tIcon1 = icon1;
            } else {
                tIcon1 = ImageManager.getTemplateTransformedImage(icon1, tt1);
            }
            if (icon2 == null) {
                return tIcon1;
            }
            final BufferedImageIcon tIcon2 = ImageManager
                    .getTemplateTransformedImage(icon2, tt2);
            final BufferedImageIcon result = new BufferedImageIcon(tIcon2);
            if (tIcon1 != null && tIcon2 != null) {
                for (int x = 0; x < ImageManager.getGraphicSize(); x++) {
                    for (int y = 0; y < ImageManager.getGraphicSize(); y++) {
                        final int pixel = tIcon2.getRGB(x, y);
                        final Color c = new Color(pixel);
                        if (c.equals(ImageManager.TRANSPARENT)) {
                            result.setRGB(x, y, tIcon1.getRGB(x, y));
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
            final BufferedImageIcon icon4 = ImageManager
                    .getCompositeImage(icon1, icon2);
            final BufferedImageIcon result = new BufferedImageIcon(icon3);
            if (icon3 != null && icon4 != null) {
                for (int x = 0; x < ImageManager.getGraphicSize(); x++) {
                    for (int y = 0; y < ImageManager.getGraphicSize(); y++) {
                        final int pixel = icon3.getRGB(x, y);
                        final Color c = new Color(pixel);
                        if (c.equals(ImageManager.TRANSPARENT)) {
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
        final StringBuffer sb = new StringBuffer(name);
        for (int x = 0; x < sb.length(); x++) {
            if (!Character.isLetter(sb.charAt(x))
                    && !Character.isDigit(sb.charAt(x))) {
                sb.setCharAt(x, '_');
            }
        }
        return sb.toString().toLowerCase();
    }

    public static void useCustomLoadPath(final String customLoadPath,
            final Object plugin) {
        ImageManager.LOAD_PATH = customLoadPath;
        ImageManager.LOAD_CLASS = plugin.getClass();
        ImageCache.flushCache();
        StatImageCache.flushCache();
        WeaselWeb.getApplication().invalidateImageCaches();
    }

    public static void useDefaultLoadPath() {
        ImageManager.LOAD_PATH = ImageManager.DEFAULT_LOAD_PATH;
        ImageManager.LOAD_CLASS = ImageManager.class;
        ImageCache.flushCache();
        StatImageCache.flushCache();
        WeaselWeb.getApplication().invalidateImageCaches();
    }

    public static int getGraphicSize() {
        return 32;
    }
}
