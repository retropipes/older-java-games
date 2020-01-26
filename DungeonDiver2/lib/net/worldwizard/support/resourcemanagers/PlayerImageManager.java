/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.resourcemanagers;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import net.worldwizard.images.BufferedImageIcon;
import net.worldwizard.support.creatures.PartyMember;
import net.worldwizard.support.creatures.monsters.Element;
import net.worldwizard.support.creatures.races.RaceConstants;

public class PlayerImageManager {
    private static final String DEFAULT_LOAD_PATH = "/net/worldwizard/support/resources/graphics/players/";
    private static String LOAD_PATH = PlayerImageManager.DEFAULT_LOAD_PATH;
    private static Class<?> LOAD_CLASS = PlayerImageManager.class;

    public static BufferedImageIcon getPlayerImage(final PartyMember base) {
        final String[] raceNames = RaceConstants.RACE_NAMES;
        final String name = raceNames[base.getRace().getRaceID()];
        final Element element = base.getElement();
        return PlayerImageCache.getCachedPlayerImage(name, element);
    }

    static BufferedImageIcon getUncachedPlayerImage(final String name,
            final Element element) {
        try {
            final String normalName = PlayerImageManager.normalizeName(name);
            final URL url = PlayerImageManager.LOAD_CLASS.getResource(
                    PlayerImageManager.LOAD_PATH + normalName + ".png");
            final BufferedImage image = ImageIO.read(url);
            final BufferedImageIcon template = new BufferedImageIcon(image);
            final BufferedImageIcon templateOut = new BufferedImageIcon(
                    template);
            final int w = template.getWidth();
            final int h = template.getHeight();
            int pixel;
            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    pixel = template.getRGB(x, y);
                    final Color old = new Color(pixel);
                    final Color transformed = element.applyTransform(old);
                    pixel = transformed.getRGB();
                    templateOut.setRGB(x, y, pixel);
                }
            }
            return templateOut;
        } catch (final IOException ie) {
            return null;
        } catch (final NullPointerException np) {
            return null;
        } catch (final IllegalArgumentException ia) {
            return null;
        }
    }

    private static String normalizeName(final String name) {
        final StringBuilder sb = new StringBuilder(name);
        for (int x = 0; x < sb.length(); x++) {
            if (!Character.isLetter(sb.charAt(x))
                    && !Character.isDigit(sb.charAt(x))) {
                sb.setCharAt(x, '_');
            }
        }
        return sb.toString().toLowerCase();
    }
}
