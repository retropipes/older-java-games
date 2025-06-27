/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.resourcemanagers;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import studio.ignitionigloogames.help.GraphicalHelpViewer;
import studio.ignitionigloogames.images.BufferedImageIcon;
import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.generic.MazeObjectList;

public class ImageCache {
	// Fields
	private static BufferedImageIcon[] cache;
	private static String[] nameCache;
	private static boolean cacheCreated = false;

	// Methods
	public static BufferedImageIcon getCachedImage(final String name) {
		if (!ImageCache.cacheCreated) {
			ImageCache.createCache();
		}
		for (int x = 0; x < ImageCache.nameCache.length; x++) {
			if (name.equals(ImageCache.nameCache[x])) {
				return ImageCache.cache[x];
			}
		}
		return null;
	}

	private static void createCache() {
		if (!ImageCache.cacheCreated) {
			// Create the cache
			final MazeObjectList list = Import1.getApplication().getObjects();
			ImageCache.nameCache = list.getAllNamesForCache();
			ImageCache.cache = new BufferedImageIcon[ImageCache.nameCache.length];
			for (int x = 0; x < ImageCache.nameCache.length; x++) {
				ImageCache.cache[x] = GraphicsManager.getUncachedImage(ImageCache.nameCache[x]);
			}
			ImageCache.cacheCreated = true;
		}
	}

	public static void viewCache() {
		if (!ImageCache.cacheCreated) {
			ImageCache.createCache();
		}
		final GraphicalHelpViewer cv = new GraphicalHelpViewer(ImageCache.cache, ImageCache.nameCache);
		final JFrame viewFrame = new JFrame("Image Cache Viewer");
		viewFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		viewFrame.setLayout(new FlowLayout());
		viewFrame.add(cv.getHelp());
		cv.setHelpSize(GraphicsManager.MAX_DESKTOP_WINDOW_SIZE, GraphicsManager.MAX_DESKTOP_WINDOW_SIZE);
		viewFrame.pack();
		viewFrame.setResizable(false);
		viewFrame.setVisible(true);
	}

	public static void recreateCache() {
		ImageCache.cacheCreated = false;
		ImageCache.createCache();
	}
}