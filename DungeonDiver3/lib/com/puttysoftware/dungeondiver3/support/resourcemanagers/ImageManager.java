/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.resourcemanagers;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import com.puttysoftware.dungeondiver3.support.creatures.PartyMember;
import com.puttysoftware.dungeondiver3.support.creatures.monsters.Element;
import com.puttysoftware.dungeondiver3.support.creatures.races.RaceConstants;
import com.puttysoftware.dungeondiver3.support.map.generic.TemplateTransform;
import com.puttysoftware.dungeondiver3.support.resourcemodifiers.ImageTransformer;
import com.puttysoftware.images.BufferedImageIcon;

public class ImageManager {
    private static final String INTERNAL_LOAD_PATH = "/com/puttysoftware/dungeondiver3/support/resources/graphics/";
    private final static Class<?> LOAD_CLASS = ImageManager.class;

    private static BufferedImageIcon getImage(final String name,
            final String rawName, final TemplateTransform tt,
            final boolean horzflip, final boolean vertflip,
            final boolean helpImage) {
        return ImageCache.getCachedImage(name, rawName, tt, horzflip, vertflip,
                helpImage);
    }

    public static BufferedImageIcon getMapImage(final String name,
            final String rawName, final TemplateTransform tt) {
        return ImageManager.getImage(name, rawName, tt, false, false, false);
    }

    public static BufferedImageIcon getMapHelpImage(final String name,
            final String rawName, final TemplateTransform tt) {
        return ImageManager.getImage(name, rawName, tt, false, false, true);
    }

    public static BufferedImageIcon getMonsterImage(final String name,
            final Element element) {
        return ImageManager.getImage(name, name,
                new TemplateTransform(element), false, false, false);
    }

    public static BufferedImageIcon getPlayerImage(final PartyMember base) {
        final String[] raceNames = RaceConstants.getRaceNames();
        final String name = raceNames[base.getRace().getRaceID()];
        final Element element = base.getElement();
        return ImageManager.getImage(name, name,
                new TemplateTransform(element), false, false, false);
    }

    public static BufferedImageIcon getStatImage(final String name) {
        return ImageManager.getImage(name, name, null, false, false, true);
    }

    static BufferedImageIcon getUncachedImage(final String name,
            final TemplateTransform tt, final boolean horzflip,
            final boolean vertflip, final boolean helpImage) {
        String normalName = ImageManager.normalizeName(name);
        try {
            URL url = ImageManager.LOAD_CLASS
                    .getResource(ImageManager.INTERNAL_LOAD_PATH + normalName
                            + ".png");
            final BufferedImage image = ImageIO.read(url);
            final BufferedImageIcon icon = new BufferedImageIcon(image);
            BufferedImageIcon res = ImageTransformer
                    .getTemplateTransformedImage(icon, tt);
            if (horzflip) {
                res = ImageTransformer.getHorizontallyFlippedImage(res);
            }
            if (vertflip) {
                res = ImageTransformer.getVerticallyFlippedImage(res);
            }
            if (helpImage) {
                res = ImageTransformer.getTransformedImage(res);
            }
            return res;
        } catch (final Exception e) {
            return null;
        }
    }

    private static String normalizeName(String name) {
        StringBuilder sb = new StringBuilder(name);
        for (int x = 0; x < sb.length(); x++) {
            if (!Character.isLetter(sb.charAt(x))
                    && !Character.isDigit(sb.charAt(x))) {
                sb.setCharAt(x, '_');
            }
        }
        return sb.toString().toLowerCase();
    }

    public static int getGraphicSize() {
        return 32;
    }
}
