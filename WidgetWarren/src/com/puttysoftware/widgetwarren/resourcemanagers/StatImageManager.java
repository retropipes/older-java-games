/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.resourcemanagers;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.puttysoftware.images.BufferedImageIcon;

public class StatImageManager {
    private static final String DEFAULT_LOAD_PATH = "/com/puttysoftware/widgetwarren/resources/images/stats/";
    private static String LOAD_PATH = StatImageManager.DEFAULT_LOAD_PATH;
    private static Class<?> LOAD_CLASS = StatImageManager.class;

    public static BufferedImageIcon getStatImage(final String name) {
        // Get it from the cache
        return StatImageCache.getCachedStatImage(name);
    }

    static BufferedImageIcon getUncachedStatImage(final String name) {
        try {
            // Fetch the icon
            final String normalName = StatImageManager.normalizeName(name);
            final URL url = StatImageManager.LOAD_CLASS.getResource(
                    StatImageManager.LOAD_PATH + normalName + ".png");
            final BufferedImage image = ImageIO.read(url);
            final BufferedImageIcon icon = new BufferedImageIcon(image);
            return ImageScaleManager.getScaledImage(icon);
        } catch (final IOException ie) {
            return null;
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
}
