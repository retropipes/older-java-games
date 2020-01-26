/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.resourcemanagers;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.puttysoftware.images.BufferedImageIcon;

public class ObjectImageManager {
    private static final String DEFAULT_LOAD_PATH = "/com/puttysoftware/mastermaze/resources/graphics/objects/";
    private static String LOAD_PATH = ObjectImageManager.DEFAULT_LOAD_PATH;
    private static Class<?> LOAD_CLASS = ObjectImageManager.class;

    public static BufferedImageIcon getImage(final String name,
            final int baseID, final int transformColor, final int attrID,
            final int attrColor) {
        // Get it from the cache
        final String baseName = ObjectImageConstants.getObjectImageName(baseID);
        final String attrName = ObjectImageConstants.getObjectImageName(attrID);
        return ObjectImageCache.getCachedImage(name, baseName, transformColor,
                attrName, attrColor);
    }

    public static void addImageToCache(final String name,
            final BufferedImageIcon img) {
        if (!ObjectImageCache.isInCache(name)) {
            ObjectImageCache.addToCache(name, img);
        }
    }

    static BufferedImageIcon getUncachedImage(final String name) {
        try {
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
