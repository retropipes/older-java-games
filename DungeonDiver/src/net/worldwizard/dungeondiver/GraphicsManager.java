package net.worldwizard.dungeondiver;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.ImageProducer;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;

public class GraphicsManager {
    // Methods
    public static ImageIcon getLogo() {
        try {
            final URL url = GraphicsManager.class
                    .getResource("/net/worldwizard/DungeonDiver/resources/graphics/logo/logo.png");
            final Image image = Toolkit.getDefaultToolkit().createImage(
                    (ImageProducer) url.getContent());
            final ImageIcon icon = new ImageIcon(image);
            return icon;
        } catch (final IOException ie) {
            return null;
        } catch (final NullPointerException np) {
            return null;
        }
    }
}
