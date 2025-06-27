/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.resourcemanagers;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import studio.ignitionigloogames.images.BufferedImageIcon;
import studio.ignitionigloogames.twistedtrek.import2.creatures.monsters.Element;

public class MonsterImageManager {
	private static final String DEFAULT_LOAD_PATH = "/com/puttysoftware/tallertower/resources/graphics/monsters/";
	private static String LOAD_PATH = MonsterImageManager.DEFAULT_LOAD_PATH;
	private static Class<?> LOAD_CLASS = MonsterImageManager.class;
	static int MONSTER_IMAGE_SIZE = 64;

	public static BufferedImageIcon getImage(final String name, final Element e) {
		// Get it from the cache
		final BufferedImageIcon bii = MonsterImageCache.getCachedImage(name, e.getFaith().getColor().getRGB());
		return ImageTransformer.getTransformedImage(bii, MonsterImageManager.MONSTER_IMAGE_SIZE);
	}

	static BufferedImageIcon getUncachedImage(final String name) {
		try {
			final String normalName = ImageTransformer.normalizeName(name);
			final URL url = MonsterImageManager.LOAD_CLASS
					.getResource(MonsterImageManager.LOAD_PATH + normalName + ".png");
			final BufferedImage image = ImageIO.read(url);
			return new BufferedImageIcon(image);
		} catch (final IOException ie) {
			return null;
		} catch (final NullPointerException np) {
			return null;
		} catch (final IllegalArgumentException ia) {
			return null;
		}
	}
}
