/*  DDRemix: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.ddremix.resourcemanagers;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.puttysoftware.ddremix.creatures.monsters.Element;
import com.puttysoftware.ddremix.prefs.PreferencesManager;
import com.puttysoftware.images.BufferedImageIcon;

public class MonsterImageManager {
    private static final String LO_LOAD_PATH = "/com/puttysoftware/ddremix/resources/graphics/monsters/";
    private static final String HI_LOAD_PATH = "/com/puttysoftware/ddremix/resources/higraphics/monsters/";
    private static String LOAD_PATH = null;
    private static Class<?> LOAD_CLASS = MonsterImageManager.class;
    static int MONSTER_IMAGE_SIZE = 32;

    public static BufferedImageIcon getImage(final String name, final int level,
            final Element e) {
        // Get it from the cache
        return MonsterImageCache.getCachedImage(name, level, e);
    }

    static BufferedImageIcon getUncachedImage(final String name,
            final int level) {
        try {
            if (MonsterImageManager.LOAD_PATH == null) {
                if (PreferencesManager.getHighDefEnabled()) {
                    MonsterImageManager.LOAD_PATH = MonsterImageManager.HI_LOAD_PATH;
                    MonsterImageManager.MONSTER_IMAGE_SIZE = 64;
                } else {
                    MonsterImageManager.LOAD_PATH = MonsterImageManager.LO_LOAD_PATH;
                    MonsterImageManager.MONSTER_IMAGE_SIZE = 32;
                }
            }
            final String normalName = ImageTransformer.normalizeName(name);
            final URL url = MonsterImageManager.LOAD_CLASS
                    .getResource(MonsterImageManager.LOAD_PATH + "level" + level
                            + "/" + normalName + ".png");
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
