/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.resourcemanagers;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.UIManager;

import com.puttysoftware.images.BufferedImageIcon;

public class GraphicsManager {
    public static final int MAX_WINDOW_SIZE = 700;
    private static final Color TRANSPARENT = new Color(200, 100, 100);
    private static Color REPLACE = new Color(223, 223, 223);
    private static Color REPLACE_STAT = UIManager.getColor("control");
    private static final String DEFAULT_LOAD_PATH = "/com/puttysoftware/rulemazer/resources/graphics/";
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
        // Get it from the cache
        return StatImageCache.getCachedStatImage(name);
    }

    static BufferedImageIcon getUncachedStatImage(final String name) {
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

    static BufferedImageIcon getUncachedLogo(final String name) {
        try {
            final URL url = GraphicsManager.LOAD_CLASS
                    .getResource(GraphicsManager.LOAD_PATH + "logo/" + name
                            + ".png");
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

    public static BufferedImageIcon getLogo() {
        return LogoCache.getCachedLogo("logo");
    }

    public static BufferedImageIcon getMiniatureLogo() {
        return LogoCache.getCachedLogo("minilogo");
    }

    public static BufferedImageIcon getMicroLogo() {
        return LogoCache.getCachedLogo("micrologo");
    }

    public static Image getIconLogo() {
        return LogoCache.getCachedLogo("iconlogo");
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

    public static int getGraphicSize() {
        return 48;
    }
}
