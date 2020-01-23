/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue.assets;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.riskyrescue.utilities.PCImage;

public class ImageManager {
    private static final String INTERNAL_LOAD_PATH = "/assets/images/";
    private final static Class<?> LOAD_CLASS = ImageManager.class;

    private static BufferedImageIcon getImage(final String name,
            final String cat) {
        return ImageCache.getCachedImage(name, cat);
    }

    public static BufferedImageIcon getObjectImage(final int dungeonIndex,
            final ObjectImage oi) {
        final String name = ObjectImageNames.getName(oi);
        return ImageManager.getImage(name, "objects/dungeon" + dungeonIndex);
    }

    public static BufferedImageIcon getMonsterImage(final int dungeonIndex,
            final int nameIndex) {
        final String name = MonsterNames.getImageName(dungeonIndex, nameIndex);
        return ImageManager.getImage(name, "monsters/dungeon" + dungeonIndex);
    }

    public static BufferedImageIcon getPlayerImage(final PCImage pci) {
        final String name = pci.getImageName();
        return ImageManager.getImage(name, "players");
    }

    public static BufferedImageIcon getPCPickerImage(final String name) { // NO_UCD
                                                                          // (actually
                                                                          // used)
        return ImageManager.getImage(name, "players");
    }

    public static BufferedImageIcon getStatImage(final StatImage si) {
        final String name = StatImageNames.getName(si);
        return ImageManager.getImage(name, "stats");
    }

    static BufferedImageIcon getUncachedImage(final String name,
            final String cat) {
        try {
            final URL url = ImageManager.LOAD_CLASS
                    .getResource(ImageManager.INTERNAL_LOAD_PATH + cat + "/"
                            + name + ".png");
            final BufferedImage image = ImageIO.read(url);
            return new BufferedImageIcon(image);
        } catch (final IOException e) {
            return null;
        }
    }

    public static int getImageSize() {
        return 64;
    }
}
