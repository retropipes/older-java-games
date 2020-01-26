/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.resourcemanagers;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import net.worldwizard.images.BufferedImageIcon;
import net.worldwizard.support.map.generic.TemplateTransform;

public class MapObjectImageManager {
    private static final String DEFAULT_LOAD_PATH = "/net/worldwizard/support/resources/graphics/objects/";
    private static String LOAD_PATH = MapObjectImageManager.DEFAULT_LOAD_PATH;
    private static Class<?> LOAD_CLASS = MapObjectImageManager.class;

    public static BufferedImageIcon getImage(final String name,
            final String rawName, final TemplateTransform tt) {
        return MapObjectImageCache.getCachedImage(name, rawName, tt);
    }

    static BufferedImageIcon getUncachedImage(final String name,
            final TemplateTransform tt) {
        final String normalName = MapObjectImageManager.normalizeName(name);
        try {
            final URL url = MapObjectImageManager.LOAD_CLASS.getResource(
                    MapObjectImageManager.LOAD_PATH + normalName + ".png");
            final BufferedImage image = ImageIO.read(url);
            final BufferedImageIcon icon = new BufferedImageIcon(image);
            final BufferedImageIcon res = ImageTransformer
                    .getTemplateTransformedImage(icon, tt);
            return res;
        } catch (final Exception e) {
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
