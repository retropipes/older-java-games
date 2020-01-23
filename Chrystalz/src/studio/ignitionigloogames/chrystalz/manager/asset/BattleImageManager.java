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

public class BattleImageManager {
    private static final String DEFAULT_LOAD_PATH = "/assets/images/objects/";
    private static String LOAD_PATH = BattleImageManager.DEFAULT_LOAD_PATH;
    private static Class<?> LOAD_CLASS = BattleImageManager.class;

    /**
     *
     * @param name
     * @param baseID
     * @param transformColor
     * @return
     */
    public static BufferedImageIcon getImage(final String name,
            final int baseID) {
        // Get it from the cache
        final String baseName = ObjectImageConstants.getObjectImageName(baseID);
        return BattleImageCache.getCachedImage(name, baseName);
    }

    static BufferedImageIcon getUncachedImage(final String name) {
        try {
            final URL url = BattleImageManager.LOAD_CLASS
                    .getResource(BattleImageManager.LOAD_PATH + name + ".png");
            final BufferedImage image = ImageIO.read(url);
            return new BufferedImageIcon(image);
        } catch (final IOException ie) {
            return null;
        }
    }

    public static int getGraphicSize() {
        return 64;
    }
}
