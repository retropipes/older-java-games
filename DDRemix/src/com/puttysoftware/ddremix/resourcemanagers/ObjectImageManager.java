/*  DDRemix: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.ddremix.resourcemanagers;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.puttysoftware.ddremix.prefs.PreferencesManager;
import com.puttysoftware.images.BufferedImageIcon;

public class ObjectImageManager {
    private static final String LO_LOAD_PATH = "/com/puttysoftware/ddremix/resources/graphics/objects/";
    private static final String HI_LOAD_PATH = "/com/puttysoftware/ddremix/resources/higraphics/objects/";
    private static String LOAD_PATH = null;
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
            if (ObjectImageManager.LOAD_PATH == null) {
                if (PreferencesManager.getHighDefEnabled()) {
                    ObjectImageManager.LOAD_PATH = ObjectImageManager.HI_LOAD_PATH;
                } else {
                    ObjectImageManager.LOAD_PATH = ObjectImageManager.LO_LOAD_PATH;
                }
            }
            final String normalName = ImageTransformer.normalizeName(name);
            final URL url = ObjectImageManager.LOAD_CLASS.getResource(
                    ObjectImageManager.LOAD_PATH + normalName + ".png");
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
}
