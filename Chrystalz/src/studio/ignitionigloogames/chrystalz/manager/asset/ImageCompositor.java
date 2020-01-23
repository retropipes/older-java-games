/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.manager.asset;

import java.awt.Color;

import studio.ignitionigloogames.common.images.BufferedImageIcon;

public class ImageCompositor {
    public static final int MAX_WINDOW_SIZE = 700;
    private static final int TRANSPARENT = 0;

    public static BufferedImageIcon getCompositeImage(
            final BufferedImageIcon icon1, final BufferedImageIcon icon2,
            final int imageSize) {
        final BufferedImageIcon result = new BufferedImageIcon(icon2);
        if (icon1 != null && icon2 != null) {
            for (int x = 0; x < imageSize; x++) {
                for (int y = 0; y < imageSize; y++) {
                    final int pixel = icon2.getRGB(x, y);
                    final Color c = new Color(pixel, true);
                    if (c.getAlpha() == ImageCompositor.TRANSPARENT) {
                        result.setRGB(x, y, icon1.getRGB(x, y));
                    }
                }
            }
            return result;
        } else {
            return null;
        }
    }

    public static BufferedImageIcon getVirtualCompositeImage(
            final BufferedImageIcon icon1, final BufferedImageIcon icon2,
            final BufferedImageIcon icon3, final int imageSize) {
        final BufferedImageIcon icon4 = ImageCompositor.getCompositeImage(icon1,
                icon2, imageSize);
        final BufferedImageIcon result = new BufferedImageIcon(icon3);
        if (icon3 != null && icon4 != null) {
            for (int x = 0; x < imageSize; x++) {
                for (int y = 0; y < imageSize; y++) {
                    final int pixel = icon3.getRGB(x, y);
                    final Color c = new Color(pixel, true);
                    if (c.getAlpha() == ImageCompositor.TRANSPARENT) {
                        result.setRGB(x, y, icon4.getRGB(x, y));
                    }
                }
            }
            return result;
        } else {
            return null;
        }
    }

    public static String normalizeName(final String name) {
        final StringBuilder sb = new StringBuilder(name);
        for (int x = 0; x < sb.length(); x++) {
            if (!Character.isLetter(sb.charAt(x))
                    && !Character.isDigit(sb.charAt(x))) {
                sb.setCharAt(x, '_');
            }
        }
        return sb.toString().toLowerCase();
    }

    public static int getGraphicSize() {
        return 64;
    }
}
