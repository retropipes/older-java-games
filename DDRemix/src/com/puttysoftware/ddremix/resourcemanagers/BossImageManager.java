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

public class BossImageManager {
    private static final String LO_LOAD_PATH = "/com/puttysoftware/ddremix/resources/graphics/boss/";
    private static final String HI_LOAD_PATH = "/com/puttysoftware/ddremix/resources/higraphics/boss/";
    private static String LOAD_PATH = null;
    private static Class<?> LOAD_CLASS = BossImageManager.class;
    static int BOSS_IMAGE_SIZE = 64;

    public static BufferedImageIcon getBossImage() {
        // Get it from the cache
        final BufferedImageIcon bii = BossImageCache.getCachedImage("boss");
        return ImageTransformer.getTransformedImage(bii,
                BossImageManager.BOSS_IMAGE_SIZE);
    }

    static BufferedImageIcon getUncachedImage(final String name) {
        try {
            if (BossImageManager.LOAD_PATH == null) {
                if (PreferencesManager.getHighDefEnabled()) {
                    BossImageManager.LOAD_PATH = BossImageManager.HI_LOAD_PATH;
                    BossImageManager.BOSS_IMAGE_SIZE = 128;
                } else {
                    BossImageManager.LOAD_PATH = BossImageManager.LO_LOAD_PATH;
                    BossImageManager.BOSS_IMAGE_SIZE = 64;
                }
            }
            final String normalName = ImageTransformer.normalizeName(name);
            final URL url = BossImageManager.LOAD_CLASS.getResource(
                    BossImageManager.LOAD_PATH + normalName + ".png");
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
