/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.manager.asset;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import studio.ignitionigloogames.chrystalz.manager.string.GlobalFile;
import studio.ignitionigloogames.chrystalz.manager.string.StringManager;
import studio.ignitionigloogames.chrystalz.names.ZoneNames;
import studio.ignitionigloogames.common.images.BufferedImageIcon;

public class WeaponImageManager {
    private static final String DEFAULT_LOAD_PATH = "/assets/images/items/weapons/";
    private static String LOAD_PATH = WeaponImageManager.DEFAULT_LOAD_PATH;
    private static Class<?> LOAD_CLASS = WeaponImageManager.class;

    public static BufferedImageIcon getImage(final int typeID,
            final int zoneID) {
        // Get it from the cache
        final String name = StringManager
                .getGlobalString(GlobalFile.WEAPON_TYPES, typeID) + "/"
                + ZoneNames.getZoneNumber(zoneID);
        return WeaponImageCache.getCachedImage(name);
    }

    static BufferedImageIcon getUncachedImage(final String name) {
        try {
            final URL url = WeaponImageManager.LOAD_CLASS
                    .getResource(WeaponImageManager.LOAD_PATH + name + ".png");
            final BufferedImage image = ImageIO.read(url);
            return new BufferedImageIcon(image);
        } catch (final IOException ie) {
            return null;
        }
    }
}
