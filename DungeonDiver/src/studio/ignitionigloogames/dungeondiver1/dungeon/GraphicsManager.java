package studio.ignitionigloogames.dungeondiver1.dungeon;

import java.awt.Color;
import java.io.IOException;

import javax.imageio.ImageIO;

import studio.ignitionigloogames.dungeondiver1.DungeonDiver;
import studio.ignitionigloogames.dungeondiver1.utilities.BufferedImageIcon;

public class GraphicsManager {
    // Methods
    public static BufferedImageIcon getDungeonImage(final String name) {
        final Scheme scheme = SchemeList.getActiveScheme();
        final BufferedImageIcon template = GraphicsManager
                .getDungeonTemplate(name);
        final BufferedImageIcon templateOut = GraphicsManager
                .getDungeonTemplate(name);
        if (template != null && templateOut != null) {
            final int w = template.getWidth();
            final int h = template.getHeight();
            int pixel;
            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    pixel = template.getRGB(x, y);
                    final Color old = new Color(pixel);
                    final Color transformed = scheme.applyTransform(old);
                    pixel = transformed.getRGB();
                    templateOut.setRGB(x, y, pixel);
                }
            }
            return new BufferedImageIcon(templateOut);
        } else {
            return null;
        }
    }

    private static BufferedImageIcon getDungeonTemplate(final String name) {
        try {
            return new BufferedImageIcon(
                    ImageIO.read(GraphicsManager.class.getResource(
                            "/studio/ignitionigloogames/dungeondiver1/resources/graphics/dungeon/"
                                    + name.toLowerCase() + ".png")));
        } catch (final IOException ie) {
            DungeonDiver.debug(ie);
            return null;
        }
    }
}
