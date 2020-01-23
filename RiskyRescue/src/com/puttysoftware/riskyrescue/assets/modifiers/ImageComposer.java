/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue.assets.modifiers;

import java.awt.Color;

import com.puttysoftware.images.BufferedImageIcon;

public class ImageComposer {
    private static final int TRANSPARENT = 0;

    public static BufferedImageIcon getCompositeImage(
            final BufferedImageIcon... icons) {
        try {
            if (icons.length == 2) {
                final BufferedImageIcon icon1 = icons[0];
                final BufferedImageIcon icon2 = icons[1];
                final BufferedImageIcon result = new BufferedImageIcon(icon2);
                if (icon1 != null && icon2 != null) {
                    if (icon1.getWidth() == icon2.getWidth()
                            && icon1.getHeight() == icon2.getHeight()) {
                        for (int x = 0; x < icon1.getWidth(); x++) {
                            for (int y = 0; y < icon1.getHeight(); y++) {
                                final int pixel = icon2.getRGB(x, y);
                                final Color c = new Color(pixel, true);
                                if (c.getAlpha() == ImageComposer.TRANSPARENT) {
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
                BufferedImageIcon result = ImageComposer
                        .getCompositeImage(icons[0], icons[1]);
                for (int x = 2; x < icons.length; x++) {
                    result = ImageComposer.getCompositeImage(result, icons[x]);
                }
                return result;
            }
        } catch (final IllegalArgumentException ia) {
            return null;
        }
    }
}
