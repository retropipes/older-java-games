/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.resourcemanagers;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.UIManager;

import net.worldwizard.images.BufferedImageIcon;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.creatures.Element;

public class GraphicsManager {
    public static final int MAX_WINDOW_SIZE = 700;
    private static final Color TRANSPARENT = new Color(200, 100, 100);
    private static Color REPLACE = null;
    private static Color REPLACE_STAT = UIManager.getColor("control");
    private static final String DEFAULT_LOAD_PATH = "/net/worldwizard/worldz/resources/graphics/";
    private static String LOAD_PATH = GraphicsManager.DEFAULT_LOAD_PATH;
    private static Class<?> LOAD_CLASS = GraphicsManager.class;

    public static BufferedImageIcon getImage(final String name) {
        // Get it from the cache
        return ImageCache.getCachedImage(name);
    }

    static BufferedImageIcon getUncachedImage(final String name) {
        try {
            final String normalName = GraphicsManager.normalizeName(name);
            final URL url = GraphicsManager.LOAD_CLASS
                    .getResource(GraphicsManager.LOAD_PATH + "objects/"
                            + normalName + ".png");
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

    public static BufferedImageIcon getStatImage(final String name) {
        try {
            // Fetch the icon
            final String normalName = GraphicsManager.normalizeName(name);
            final URL url = GraphicsManager.LOAD_CLASS
                    .getResource(GraphicsManager.LOAD_PATH + "stats/"
                            + normalName + ".png");
            final BufferedImage image = ImageIO.read(url);
            final BufferedImageIcon icon = new BufferedImageIcon(image);
            // Transform the icon
            final BufferedImageIcon result = new BufferedImageIcon(icon);
            for (int x = 0; x < GraphicsManager.getGraphicSize(); x++) {
                for (int y = 0; y < GraphicsManager.getGraphicSize(); y++) {
                    final int pixel = icon.getRGB(x, y);
                    final Color c = new Color(pixel);
                    if (c.equals(GraphicsManager.TRANSPARENT)) {
                        result.setRGB(x, y,
                                GraphicsManager.REPLACE_STAT.getRGB());
                    }
                }
            }
            return result;
        } catch (final IOException ie) {
            return null;
        } catch (final NullPointerException np) {
            return null;
        } catch (final IllegalArgumentException ia) {
            return null;
        }
    }

    public static BufferedImageIcon getTransformedImage(final String name) {
        if (GraphicsManager.REPLACE == null) {
            GraphicsManager.defineReplacementColor();
        }
        try {
            final BufferedImageIcon icon = ImageCache.getCachedImage(name);
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

    public static BufferedImageIcon getCompositeImage(final String name1,
            final String name2) {
        try {
            final BufferedImageIcon icon1 = ImageCache.getCachedImage(name1);
            final BufferedImageIcon icon2 = ImageCache.getCachedImage(name2);
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
            final String name1, final String name2, final String name3) {
        try {
            final BufferedImageIcon icon3 = ImageCache.getCachedImage(name3);
            final BufferedImageIcon icon2 = GraphicsManager.getCompositeImage(
                    name1, name2);
            final BufferedImageIcon result = new BufferedImageIcon(icon3);
            if (icon3 != null && icon2 != null) {
                for (int x = 0; x < GraphicsManager.getGraphicSize(); x++) {
                    for (int y = 0; y < GraphicsManager.getGraphicSize(); y++) {
                        final int pixel = icon3.getRGB(x, y);
                        final Color c = new Color(pixel);
                        if (c.equals(GraphicsManager.TRANSPARENT)) {
                            result.setRGB(x, y, icon2.getRGB(x, y));
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

    public static BufferedImageIcon getLogo() {
        try {
            final URL url = GraphicsManager.LOAD_CLASS
                    .getResource(GraphicsManager.LOAD_PATH + "logo/logo.png");
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

    public static BufferedImageIcon getMiniatureLogo() {
        try {
            final URL url = GraphicsManager.LOAD_CLASS
                    .getResource(GraphicsManager.LOAD_PATH
                            + "logo/minilogo.png");
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

    public static BufferedImageIcon getMicroLogo() {
        try {
            final URL url = GraphicsManager.LOAD_CLASS
                    .getResource(GraphicsManager.LOAD_PATH
                            + "logo/micrologo.png");
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

    public static Image getIconLogo() {
        try {
            final URL url = GraphicsManager.LOAD_CLASS
                    .getResource(GraphicsManager.LOAD_PATH
                            + "logo/iconlogo.png");
            final BufferedImage image = ImageIO.read(url);
            return image;
        } catch (final IOException ie) {
            return null;
        } catch (final NullPointerException np) {
            return null;
        } catch (final IllegalArgumentException ia) {
            return null;
        }
    }

    public static BufferedImageIcon getMonsterImage(final String name,
            final Element element) {
        final BufferedImageIcon template = MonsterImageCache
                .getCachedMonsterImage(name);
        if (template != null) {
            final BufferedImageIcon templateOut = new BufferedImageIcon(
                    template);
            final int w = template.getWidth();
            final int h = template.getHeight();
            int pixel;
            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    pixel = template.getRGB(x, y);
                    final Color old = new Color(pixel);
                    final Color transformed = element.applyTransform(old);
                    pixel = transformed.getRGB();
                    templateOut.setRGB(x, y, pixel);
                }
            }
            return templateOut;
        } else {
            return null;
        }
    }

    static BufferedImageIcon getUncachedMonsterImage(final String name) {
        final BufferedImage template = GraphicsManager.getMonsterTemplate(name);
        if (template != null) {
            final BufferedImageIcon templateOut = new BufferedImageIcon(
                    GraphicsManager.getGraphicSize(),
                    GraphicsManager.getGraphicSize());
            // Recolor the templateOut
            for (int x = 0; x < GraphicsManager.getGraphicSize(); x++) {
                for (int y = 0; y < GraphicsManager.getGraphicSize(); y++) {
                    templateOut.setRGB(x, y,
                            GraphicsManager.TRANSPARENT.getRGB());
                }
            }
            // Draw the monster with an offset into the templateOut
            final int offset = (GraphicsManager.getGraphicSize() - template
                    .getWidth()) / 2;
            for (int x = 0; x < template.getWidth(); x++) {
                for (int y = 0; y < template.getHeight(); y++) {
                    templateOut.setRGB(x + offset, y + offset,
                            template.getRGB(x, y));
                }
            }
            return templateOut;
        } else {
            return null;
        }
    }

    private static BufferedImage getMonsterTemplate(final String name) {
        try {
            final String normalName = GraphicsManager.normalizeName(name);
            final URL url = GraphicsManager.LOAD_CLASS
                    .getResource(GraphicsManager.LOAD_PATH + "monsters/"
                            + normalName + ".png");
            final BufferedImage image = ImageIO.read(url);
            if (image != null) {
                return image;
            } else {
                return null;
            }
        } catch (final IOException ie) {
            return null;
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

    private static void defineReplacementColor() {
        if (System.getProperty("os.name").startsWith("Mac OS X")) {
            GraphicsManager.REPLACE = UIManager.getColor("text");
        } else {
            GraphicsManager.REPLACE = UIManager.getColor("control");
        }
    }

    public static void useCustomLoadPath(final String customLoadPath,
            final Object plugin) {
        GraphicsManager.LOAD_PATH = customLoadPath;
        GraphicsManager.LOAD_CLASS = plugin.getClass();
        ImageCache.flushCache();
        MonsterImageCache.flushCache();
        Worldz.getApplication().invalidateImageCaches();
    }

    public static int getGraphicSize() {
        return 48;
    }
}
