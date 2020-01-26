/*  DynamicDungeon: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.resourcemanagers;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import net.dynamicdungeon.dynamicdungeon.prefs.PreferencesManager;
import net.dynamicdungeon.images.BufferedImageIcon;
import net.dynamicdungeon.images.BufferedRetinaImageIcon;

public class ObjectImageManager {
    private static final String LOAD_PATH = "/net/dynamicdungeon/dynamicdungeon/resources/graphics/objects/";
    private static final String HI_DEF_SUFFIX = "@2x";
    private static Class<?> LOAD_CLASS = ObjectImageManager.class;

    /**
     *
     * @param name
     * @param baseID
     * @param transformColor
     * @return
     */
    public static BufferedImageIcon getImage(final String name,
            final int baseID, final int transformColor) {
        // Get it from the cache
        final String baseName = ObjectImageConstants.getObjectImageName(baseID);
        final BufferedImageIcon bii = ObjectImageCache.getCachedImage(name,
                baseName);
        return ImageTransformer.templateTransformImage(bii, transformColor,
                ImageTransformer.getGraphicSize());
    }

    static BufferedImageIcon getUncachedImage(final String name) {
        try {
            if (PreferencesManager.getHighDefEnabled()) {
                final String normalName = ImageTransformer.normalizeName(name);
                final URL url = ObjectImageManager.LOAD_CLASS
                        .getResource(ObjectImageManager.LOAD_PATH + normalName
                                + ObjectImageManager.HI_DEF_SUFFIX + ".png");
                final BufferedImage image = ImageIO.read(url);
                return new BufferedRetinaImageIcon(image);
            } else {
                final String normalName = ImageTransformer.normalizeName(name);
                final URL url = ObjectImageManager.LOAD_CLASS.getResource(
                        ObjectImageManager.LOAD_PATH + normalName + ".png");
                final BufferedImage image = ImageIO.read(url);
                return new BufferedImageIcon(image);
            }
        } catch (final IOException ie) {
            return null;
        } catch (final NullPointerException np) {
            return null;
        } catch (final IllegalArgumentException ia) {
            return null;
        }
    }
}
