/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.resourcemanagers;

import java.awt.Color;

import net.worldwizard.images.BufferedImageIcon;
import net.worldwizard.support.map.generic.TemplateTransform;

public class ImageTransformer {
    private static final Color TRANSPARENT = new Color(200, 100, 100);
    private static Color REPLACE = new Color(223, 223, 223);

    static BufferedImageIcon getTemplateTransformedImage(
            final BufferedImageIcon icon, final TemplateTransform tt) {
        try {
            if (tt == null) {
                return icon;
            } else {
                final BufferedImageIcon result = new BufferedImageIcon(icon);
                if (icon != null) {
                    for (int x = 0; x < MapObjectImageManager
                            .getGraphicSize(); x++) {
                        for (int y = 0; y < MapObjectImageManager
                                .getGraphicSize(); y++) {
                            final int pixel = icon.getRGB(x, y);
                            final Color c = new Color(pixel);
                            if (!c.equals(ImageTransformer.TRANSPARENT)) {
                                result.setRGB(x, y,
                                        tt.applyTransform(c).getRGB());
                            }
                        }
                    }
                    return result;
                } else {
                    return null;
                }
            }
        } catch (final NullPointerException np) {
            return null;
        } catch (final IllegalArgumentException ia) {
            return null;
        }
    }

    public static BufferedImageIcon getTransformedImage(final String name,
            final String raw, final TemplateTransform tt) {
        try {
            final BufferedImageIcon icon = MapObjectImageCache
                    .getCachedImage(name, raw, tt);
            if (icon != null) {
                final BufferedImageIcon result = new BufferedImageIcon(icon);
                for (int x = 0; x < MapObjectImageManager
                        .getGraphicSize(); x++) {
                    for (int y = 0; y < MapObjectImageManager
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

    public static BufferedImageIcon getCompositeImage(final String name1,
            final String raw1, final TemplateTransform tt1, final String name2,
            final String raw2, final TemplateTransform tt2) {
        try {
            final BufferedImageIcon icon1 = MapObjectImageCache
                    .getCachedImage(name1, raw1, tt1);
            final BufferedImageIcon icon2 = MapObjectImageCache
                    .getCachedImage(name2, raw2, tt2);
            final BufferedImageIcon result = new BufferedImageIcon(icon2);
            if (icon1 != null && icon2 != null) {
                for (int x = 0; x < MapObjectImageManager
                        .getGraphicSize(); x++) {
                    for (int y = 0; y < MapObjectImageManager
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

    public static BufferedImageIcon getCompositeImage(
            final BufferedImageIcon icon1, final BufferedImageIcon icon2) {
        try {
            final BufferedImageIcon result = new BufferedImageIcon(icon2);
            if (icon1 != null && icon2 != null) {
                for (int x = 0; x < MapObjectImageManager
                        .getGraphicSize(); x++) {
                    for (int y = 0; y < MapObjectImageManager
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
}
