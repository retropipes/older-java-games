package net.worldwizard.dungeondiver.dungeon;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class GraphicsManager {
    // Methods
    public static ImageIcon getDungeonImage(final String name) {
        final Scheme scheme = SchemeList.getActiveScheme();
        final BufferedImage template = GraphicsManager.getDungeonTemplate(name);
        final BufferedImage templateOut = GraphicsManager
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
            return new ImageIcon(templateOut);
        } else {
            return null;
        }
    }

    private static BufferedImage getDungeonTemplate(final String name) {
        try {
            final URL url = GraphicsManager.class.getResource(
                    "/net/worldwizard/DungeonDiver/resources/graphics/dungeon/"
                            + name.toLowerCase() + ".png");
            final BufferedImage image = ImageIO.read(url);
            if (image != null) {
                return image;
            } else {
                return null;
            }
        } catch (final IOException ie) {
            return null;
        } catch (final NullPointerException np) {
            return null;
        } catch (final IllegalArgumentException ia) {
            return null;
        }
    }
}
