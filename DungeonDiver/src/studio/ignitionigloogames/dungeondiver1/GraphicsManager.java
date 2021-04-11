package studio.ignitionigloogames.dungeondiver1;

import java.io.IOException;

import javax.imageio.ImageIO;

import studio.ignitionigloogames.dungeondiver1.utilities.BufferedImageIcon;

public class GraphicsManager {
    // Methods
    public static BufferedImageIcon getLogo() {
        try {
            return new BufferedImageIcon(
                    ImageIO.read(GraphicsManager.class.getResource(
                            "/studio/ignitionigloogames/dungeondiver1/resources/graphics/logo/logo.png")));
        } catch (final IOException ie) {
            DungeonDiver.debug(ie);
            return null;
        }
    }
}
