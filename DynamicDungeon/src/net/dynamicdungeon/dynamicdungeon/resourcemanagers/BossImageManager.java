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

public class BossImageManager {
    private static final String LOAD_PATH = "/net/dynamicdungeon/dynamicdungeon/resources/graphics/boss/";
    private static final String HI_DEF_SUFFIX = "@2x";
    private static Class<?> LOAD_CLASS = BossImageManager.class;
    static final int BOSS_IMAGE_SIZE = 64;

    public static BufferedImageIcon getBossImage() {
        // Get it from the cache
        final BufferedImageIcon bii = BossImageCache.getCachedImage("boss");
        return ImageTransformer.getTransformedImage(bii,
                BossImageManager.BOSS_IMAGE_SIZE);
    }

    static BufferedImageIcon getUncachedImage(final String name) {
        try {
            if (PreferencesManager.getHighDefEnabled()) {
                final String normalName = ImageTransformer.normalizeName(name);
                final URL url = BossImageManager.LOAD_CLASS
                        .getResource(BossImageManager.LOAD_PATH + normalName
                                + BossImageManager.HI_DEF_SUFFIX + ".png");
                final BufferedImage image = ImageIO.read(url);
                return new BufferedRetinaImageIcon(image);
            } else {
                final String normalName = ImageTransformer.normalizeName(name);
                final URL url = BossImageManager.LOAD_CLASS.getResource(
                        BossImageManager.LOAD_PATH + normalName + ".png");
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
