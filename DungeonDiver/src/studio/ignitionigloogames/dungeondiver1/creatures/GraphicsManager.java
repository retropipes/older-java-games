package studio.ignitionigloogames.dungeondiver1.creatures;

import java.awt.Color;
import java.io.IOException;

import javax.imageio.ImageIO;

import studio.ignitionigloogames.dungeondiver1.DungeonDiver;
import studio.ignitionigloogames.dungeondiver1.utilities.BufferedImageIcon;

public class GraphicsManager {
    // Methods
    public static BufferedImageIcon getBossImage() {
        try {
            return new BufferedImageIcon(
                    ImageIO.read(GraphicsManager.class.getResource(
                            "/studio/ignitionigloogames/dungeondiver1/resources/graphics/boss/boss.png")));
        } catch (final IOException ie) {
            DungeonDiver.debug(ie);
            return null;
        }
    }

    public static BufferedImageIcon getMonsterImage(final String name,
            final Element element) {
        final BufferedImageIcon template = GraphicsManager
                .getMonsterTemplate(name);
        final BufferedImageIcon templateOut = GraphicsManager
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
            return new BufferedImageIcon(templateOut);
        } else {
            return null;
        }
    }

    private static BufferedImageIcon getMonsterTemplate(final String name) {
        try {
            final String normalName = GraphicsManager.normalizeName(name);
            return new BufferedImageIcon(
                    ImageIO.read(GraphicsManager.class.getResource(
                            "/studio/ignitionigloogames/dungeondiver1/resources/graphics/monsters/"
                                    + normalName + ".png")));
        } catch (final IOException ie) {
            DungeonDiver.debug(ie);
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
