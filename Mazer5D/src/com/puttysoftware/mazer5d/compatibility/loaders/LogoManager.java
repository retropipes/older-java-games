/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.loaders;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.puttysoftware.images.BufferedImageIcon;

public class LogoManager {
    private static final String DEFAULT_LOAD_PATH = "/assets/image/logo/";
    private static String LOAD_PATH = LogoManager.DEFAULT_LOAD_PATH;
    private static Class<?> LOAD_CLASS = LogoManager.class;

    static BufferedImageIcon getUncachedLogo(final String name) {
        try {
            final URL url = LogoManager.LOAD_CLASS
                    .getResource(LogoManager.LOAD_PATH + name + ".png");
            final BufferedImage image = ImageIO.read(url);
            final BufferedImageIcon icon = new BufferedImageIcon(image);
            return icon;
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
}
