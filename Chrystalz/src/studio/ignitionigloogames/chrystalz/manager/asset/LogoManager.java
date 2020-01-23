/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.manager.asset;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import studio.ignitionigloogames.common.images.BufferedImageIcon;

public class LogoManager {
    private static final String DEFAULT_LOAD_PATH = "/assets/images/logo/";
    private static String LOAD_PATH = LogoManager.DEFAULT_LOAD_PATH;
    private static Class<?> LOAD_CLASS = LogoManager.class;

    static BufferedImageIcon getUncachedLogo(final String name) {
        try {
            final URL url = LogoManager.LOAD_CLASS
                    .getResource(LogoManager.LOAD_PATH + name + ".png");
            final BufferedImage image = ImageIO.read(url);
            return new BufferedImageIcon(image);
        } catch (final IOException ie) {
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
        return LogoCache.getCachedLogo("iconlogo");
    }
}
