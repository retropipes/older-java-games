/*  DynamicDungeon: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.resourcemanagers;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import net.dynamicdungeon.dynamicdungeon.creatures.monsters.Element;
import net.dynamicdungeon.dynamicdungeon.prefs.PreferencesManager;
import net.dynamicdungeon.images.BufferedImageIcon;
import net.dynamicdungeon.images.BufferedRetinaImageIcon;

public class MonsterImageManager {
    private static final String LOAD_PATH = "/net/dynamicdungeon/dynamicdungeon/resources/graphics/monsters/";
    private static final String HI_DEF_SUFFIX = "@2x";
    private static Class<?> LOAD_CLASS = MonsterImageManager.class;
    static final int MONSTER_IMAGE_SIZE = 32;

    public static BufferedImageIcon getImage(final String name, final int level,
            final Element e) {
        // Get it from the cache
        return MonsterImageCache.getCachedImage(name, level, e);
    }

    static BufferedImageIcon getUncachedImage(final String name,
            final int level) {
        try {
            if (PreferencesManager.getHighDefEnabled()) {
                final String normalName = ImageTransformer.normalizeName(name);
                final URL url = MonsterImageManager.LOAD_CLASS
                        .getResource(MonsterImageManager.LOAD_PATH + "level"
                                + level + "/" + normalName
                                + MonsterImageManager.HI_DEF_SUFFIX + ".png");
                final BufferedImage image = ImageIO.read(url);
                return new BufferedRetinaImageIcon(image);
            } else {
                final String normalName = ImageTransformer.normalizeName(name);
                final URL url = MonsterImageManager.LOAD_CLASS
                        .getResource(MonsterImageManager.LOAD_PATH + "level"
                                + level + "/" + normalName + ".png");
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
