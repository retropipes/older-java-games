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

public class StatImageManager {
    private static final String LO_LOAD_PATH = "/net/dynamicdungeon/dynamicdungeon/resources/graphics/stats/";
    private static final String HI_LOAD_PATH = "/net/dynamicdungeon/dynamicdungeon/resources/higraphics/stats/";
    private static String LOAD_PATH = null;
    private static Class<?> LOAD_CLASS = StatImageManager.class;

    public static BufferedImageIcon getImage(final int imageID) {
        // Get it from the cache
        final String name = StatImageConstants.getStatImageName(imageID);
        return StatImageCache.getCachedImage(name);
    }

    static BufferedImageIcon getUncachedImage(final String name) {
        try {
            if (StatImageManager.LOAD_PATH == null) {
                if (PreferencesManager.getHighDefEnabled()) {
                    StatImageManager.LOAD_PATH = StatImageManager.HI_LOAD_PATH;
                } else {
                    StatImageManager.LOAD_PATH = StatImageManager.LO_LOAD_PATH;
                }
            }
            final String normalName = ImageTransformer.normalizeName(name);
            final URL url = StatImageManager.LOAD_CLASS.getResource(
                    StatImageManager.LOAD_PATH + normalName + ".png");
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
