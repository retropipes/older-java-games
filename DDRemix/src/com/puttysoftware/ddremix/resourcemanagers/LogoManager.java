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

public class LogoManager {
    private static final String LO_LOAD_PATH = "/com/puttysoftware/ddremix/resources/graphics/logo/";
    private static final String HI_LOAD_PATH = "/com/puttysoftware/ddremix/resources/graphics/logo/";
    private static String LOAD_PATH = null;
    private static Class<?> LOAD_CLASS = LogoManager.class;

    static BufferedImageIcon getUncachedLogo(final String name) {
        try {
            if (LogoManager.LOAD_PATH == null) {
                if (PreferencesManager.getHighDefEnabled()) {
                    LogoManager.LOAD_PATH = LogoManager.HI_LOAD_PATH;
                } else {
                    LogoManager.LOAD_PATH = LogoManager.LO_LOAD_PATH;
                }
            }
            final URL url = LogoManager.LOAD_CLASS
                    .getResource(LogoManager.LOAD_PATH + name + ".png");
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
