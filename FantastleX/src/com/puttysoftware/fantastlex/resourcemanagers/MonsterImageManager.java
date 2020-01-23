/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.resourcemanagers;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.puttysoftware.fantastlex.creatures.monsters.Element;
import com.puttysoftware.images.BufferedImageIcon;

public class MonsterImageManager {
    private static final String DEFAULT_LOAD_PATH = "/com/puttysoftware/fantastlex/resources/graphics/monsters/";
    private static String LOAD_PATH = MonsterImageManager.DEFAULT_LOAD_PATH;
    private static Class<?> LOAD_CLASS = MonsterImageManager.class;

    public static BufferedImageIcon getImage(final String name, final Element e) {
        // Get it from the cache
        return MonsterImageCache.getCachedImage(name, e.getFaith().getColor()
                .getRGB());
    }

    static BufferedImageIcon getUncachedImage(final String name) {
        try {
            final String normalName = ImageTransformer.normalizeName(name);
            final URL url = MonsterImageManager.LOAD_CLASS
                    .getResource(MonsterImageManager.LOAD_PATH + normalName
                            + ".png");
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
