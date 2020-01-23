/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.manager.asset;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import studio.ignitionigloogames.chrystalz.names.MonsterNames;
import studio.ignitionigloogames.common.images.BufferedImageIcon;

public class BossImageManager {
    private static final String DEFAULT_LOAD_PATH = "/assets/images/bosses/";
    private static String LOAD_PATH = BossImageManager.DEFAULT_LOAD_PATH;
    private static Class<?> LOAD_CLASS = BossImageManager.class;

    public static BufferedImageIcon getBossImage(final int zoneID) {
        // Get it from the cache
        return BossImageCache.getCachedImage(MonsterNames.getName(zoneID));
    }

    public static BufferedImageIcon getFinalBossImage() {
        // Get it from the cache
        return BossImageCache.getCachedImage("chrys");
    }

    static BufferedImageIcon getUncachedImage(final String name) {
        try {
            final URL url = BossImageManager.LOAD_CLASS
                    .getResource(BossImageManager.LOAD_PATH + name + ".png");
            final BufferedImage image = ImageIO.read(url);
            return new BufferedImageIcon(image);
        } catch (final IOException ie) {
            return null;
        }
    }
}
