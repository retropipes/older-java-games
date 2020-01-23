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

public class StatImageManager {
    private static final String DEFAULT_LOAD_PATH = "/assets/images/stats/";
    private static String LOAD_PATH = StatImageManager.DEFAULT_LOAD_PATH;
    private static Class<?> LOAD_CLASS = StatImageManager.class;

    public static BufferedImageIcon getImage(final int imageID) {
        // Get it from the cache
        final String name = StatImageConstants.getStatImageName(imageID);
        return StatImageCache.getCachedImage(name);
    }

    static BufferedImageIcon getUncachedImage(final String name) {
        try {
            final URL url = StatImageManager.LOAD_CLASS
                    .getResource(StatImageManager.LOAD_PATH + name + ".png");
            final BufferedImage image = ImageIO.read(url);
            return new BufferedImageIcon(image);
        } catch (final IOException ie) {
            return null;
        }
    }
}
