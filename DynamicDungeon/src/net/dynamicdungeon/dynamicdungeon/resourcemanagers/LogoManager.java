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

public class LogoManager {
    private static final String LOAD_PATH = "/net/dynamicdungeon/dynamicdungeon/resources/graphics/logo/";
    private static final String HI_DEF_SUFFIX = "@2x";
    private static Class<?> LOAD_CLASS = LogoManager.class;

    static BufferedImageIcon getUncachedLogo(final String name) {
        try {
            if (PreferencesManager.getHighDefEnabled()) {
                final URL url = LogoManager.LOAD_CLASS
                        .getResource(LogoManager.LOAD_PATH + name
                                + LogoManager.HI_DEF_SUFFIX + ".png");
                final BufferedImage image = ImageIO.read(url);
                return new BufferedRetinaImageIcon(image);
            } else {
                final URL url = LogoManager.LOAD_CLASS
                        .getResource(LogoManager.LOAD_PATH + name + ".png");
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

    public static BufferedImageIcon getLogo() {
        return LogoCache.getCachedLogo("logo");
    }

    public static BufferedImageIcon getMiniatureLogo() {
        return LogoCache.getCachedLogo("minilogo");
    }

    public static BufferedImageIcon getMicroLogo() {
        return LogoCache.getCachedLogo("micrologo");
    }

    public static BufferedImageIcon getIconLogo() {
        return LogoCache.getCachedLogo("logo");
    }
}
