package net.worldwizard.dungeondiver.creatures;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ImageProducer;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class GraphicsManager {
    // Methods
    public static ImageIcon getBossImage() {
        try {
            final URL url = GraphicsManager.class.getResource(
                    "/net/worldwizard/DungeonDiver/resources/graphics/boss/boss.png");
            final Image image = Toolkit.getDefaultToolkit()
                    .createImage((ImageProducer) url.getContent());
            final ImageIcon icon = new ImageIcon(image);
            return icon;
        } catch (final IOException ie) {
            return null;
        } catch (final NullPointerException np) {
            return null;
        }
    }

    public static ImageIcon getMonsterImage(final String name,
            final Element element) {
        final BufferedImage template = GraphicsManager.getMonsterTemplate(name);
        final BufferedImage templateOut = GraphicsManager
                .getMonsterTemplate(name);
        if (template != null && templateOut != null) {
            final int w = template.getWidth();
            final int h = template.getHeight();
            int pixel;
            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    pixel = template.getRGB(x, y);
                    final Color old = new Color(pixel);
                    final Color transformed = element.applyTransform(old);
                    pixel = transformed.getRGB();
                    templateOut.setRGB(x, y, pixel);
                }
            }
            return new ImageIcon(templateOut);
        } else {
            return null;
        }
    }

    private static BufferedImage getMonsterTemplate(final String name) {
        try {
            final String normalName = GraphicsManager.normalizeName(name);
            final URL url = GraphicsManager.class.getResource(
                    "/net/worldwizard/DungeonDiver/resources/graphics/monsters/"
                            + normalName + ".png");
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

    private static String normalizeName(final String name) {
        final StringBuilder sb = new StringBuilder();
        for (int x = 0; x < name.length(); x++) {
            final char curr = name.charAt(x);
            if (!Character.isWhitespace(curr)) {
                sb.append(curr);
            }
        }
        return sb.toString().toLowerCase();
    }
}
