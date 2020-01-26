package com.puttysoftware.widgetwarren.resourcemanagers;

import java.awt.Color;

import com.puttysoftware.images.BufferedImageIcon;

public class ImageModifier {
    private static final Color TRANSPARENT = new Color(200, 100, 100);

    public static BufferedImageIcon getCompositeImage(
            final BufferedImageIcon icon1,
            final BufferedImageIcon... otherIcons) {
        try {
            final int scaleSize = ImageConstants.getScaledImageSize();
            final BufferedImageIcon result = new BufferedImageIcon(icon1);
            for (final BufferedImageIcon icon2 : otherIcons) {
                if (icon2 != null) {
                    for (int x = 0; x < scaleSize; x++) {
                        for (int y = 0; y < scaleSize; y++) {
                            final int pixel = icon2.getRGB(x, y);
                            final Color c = new Color(pixel);
                            if (!c.equals(ImageModifier.TRANSPARENT)) {
                                result.setRGB(x, y, pixel);
                            }
                        }
                    }
                    // final Graphics2D g2d = result.createGraphics();
                    // g2d.drawImage(icon2, 0, 0, null);
                    // g2d.dispose();
                }
            }
            return result;
        } catch (final NullPointerException np) {
            return null;
        } catch (final IllegalArgumentException ia) {
            return null;
        }
    }
}
