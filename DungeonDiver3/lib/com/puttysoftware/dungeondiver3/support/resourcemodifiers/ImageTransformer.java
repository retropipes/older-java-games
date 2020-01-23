/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.resourcemodifiers;

import java.awt.Color;

import javax.swing.UIManager;

import com.puttysoftware.dungeondiver3.support.map.generic.TemplateTransform;
import com.puttysoftware.images.BufferedImageIcon;

public class ImageTransformer {
    private static final Color TRANSPARENT = new Color(200, 100, 100);
    private static final Color REPLACE = UIManager.getColor("control");

    public static Color getReplacementColor() {
        return REPLACE;
    }

    public static BufferedImageIcon getTemplateTransformedImage(
            final BufferedImageIcon icon, final TemplateTransform tt) {
        try {
            if (tt == null) {
                return icon;
            } else {
                BufferedImageIcon result = new BufferedImageIcon(icon);
                if (icon != null) {
                    for (int x = 0; x < icon.getWidth(); x++) {
                        for (int y = 0; y < icon.getHeight(); y++) {
                            int pixel = icon.getRGB(x, y);
                            Color c = new Color(pixel);
                            if (!c.equals(ImageTransformer.TRANSPARENT)) {
                                result.setRGB(x, y, tt.applyTransform(c)
                                        .getRGB());
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

    public static BufferedImageIcon getTransformedImage(
            final BufferedImageIcon icon) {
        try {
            BufferedImageIcon result = new BufferedImageIcon(icon);
            if (icon != null) {
                for (int x = 0; x < icon.getWidth(); x++) {
                    for (int y = 0; y < icon.getHeight(); y++) {
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

    public static BufferedImageIcon getHorizontallyFlippedImage(
            final BufferedImageIcon icon) {
        try {
            BufferedImageIcon result = new BufferedImageIcon(icon);
            for (int x = 0; x < icon.getWidth(); x++) {
                for (int y = 0; y < icon.getHeight(); y++) {
                    int pixel = icon.getRGB(x, y);
                    result.setRGB(icon.getWidth() - x, y, pixel);
                }
            }
            return result;
        } catch (final NullPointerException np) {
            return null;
        } catch (final IllegalArgumentException ia) {
            return null;
        }
    }

    public static BufferedImageIcon getVerticallyFlippedImage(
            final BufferedImageIcon icon) {
        try {
            BufferedImageIcon result = new BufferedImageIcon(icon);
            for (int x = 0; x < icon.getWidth(); x++) {
                for (int y = 0; y < icon.getHeight(); y++) {
                    int pixel = icon.getRGB(x, y);
                    result.setRGB(x, icon.getHeight() - y, pixel);
                }
            }
            return result;
        } catch (final NullPointerException np) {
            return null;
        } catch (final IllegalArgumentException ia) {
            return null;
        }
    }

    public static BufferedImageIcon getCompositeImage(
            final BufferedImageIcon... icons) {
        try {
            if (icons.length == 2) {
                BufferedImageIcon icon1 = icons[0];
                BufferedImageIcon icon2 = icons[1];
                BufferedImageIcon result = new BufferedImageIcon(icon2);
                if (icon1 != null && icon2 != null) {
                    if (icon1.getWidth() == icon2.getWidth()
                            && icon1.getHeight() == icon2.getHeight()) {
                        for (int x = 0; x < icon1.getWidth(); x++) {
                            for (int y = 0; y < icon1.getHeight(); y++) {
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
                } else {
                    return null;
                }
            } else {
                BufferedImageIcon result = ImageTransformer.getCompositeImage(
                        icons[0], icons[1]);
                if (result != null) {
                    for (int x = 2; x < icons.length; x++) {
                        result = ImageTransformer.getCompositeImage(result,
                                icons[x]);
                        if (result == null) {
                            return null;
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
}
