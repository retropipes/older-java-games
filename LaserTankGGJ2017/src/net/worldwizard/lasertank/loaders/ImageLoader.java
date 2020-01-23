package net.worldwizard.lasertank.loaders;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import net.worldwizard.lasertank.assets.GameImage;

public class ImageLoader {
    // Private constructor
    private ImageLoader() {
	// Do nothing
    }

    public static GameImage loadObjectImage(final String imageName) {
	try (InputStream is = ImageLoader.class
		.getResourceAsStream("/net/worldwizard/lasertank/assets/images/objects/" + imageName + ".png")) {
	    final BufferedImage bi = ImageIO.read(is);
	    return new GameImage(imageName, bi);
	} catch (final IOException e) {
	    return new GameImage("null");
	}
    }

    public static GameImage loadUIImage(final String imageName) {
	try (InputStream is = ImageLoader.class
		.getResourceAsStream("/net/worldwizard/lasertank/assets/images/ui/" + imageName + ".png")) {
	    final BufferedImage bi = ImageIO.read(is);
	    return new GameImage(imageName, bi);
	} catch (final IOException e) {
	    return new GameImage("null");
	}
    }
}
