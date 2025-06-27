/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.resourcemanagers;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import studio.ignitionigloogames.help.GraphicalHelpViewer;
import studio.ignitionigloogames.images.BufferedImageIcon;

public class MonsterImageCache {
	// Fields
	private static BufferedImageIcon[] cache;
	private static String[] nameCache;
	private static boolean cacheCreated = false;

	// Methods
	public static BufferedImageIcon getCachedMonsterImage(final String name) {
		if (!MonsterImageCache.cacheCreated) {
			MonsterImageCache.createMonsterCache();
		}
		for (int x = 0; x < MonsterImageCache.nameCache.length; x++) {
			if (name.equals(MonsterImageCache.nameCache[x])) {
				return MonsterImageCache.cache[x];
			}
		}
		return null;
	}

	private static void createMonsterCache() {
		if (!MonsterImageCache.cacheCreated) {
			// Create the cache
			MonsterImageCache.nameCache = MonsterNames.getAllNames();
			MonsterImageCache.cache = new BufferedImageIcon[MonsterImageCache.nameCache.length];
			for (int x = 0; x < MonsterImageCache.nameCache.length; x++) {
				MonsterImageCache.cache[x] = GraphicsManager.getUncachedMonsterImage(MonsterImageCache.nameCache[x]);
			}
			MonsterImageCache.cacheCreated = true;
		}
	}

	public static void viewMonsterCache() {
		if (!MonsterImageCache.cacheCreated) {
			MonsterImageCache.createMonsterCache();
		}
		final GraphicalHelpViewer cv = new GraphicalHelpViewer(MonsterImageCache.cache, MonsterImageCache.nameCache);
		final JFrame viewFrame = new JFrame("Monster Cache Viewer");
		viewFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		viewFrame.setLayout(new FlowLayout());
		viewFrame.add(cv.getHelp());
		cv.setHelpSize(GraphicsManager.MAX_DESKTOP_WINDOW_SIZE, GraphicsManager.MAX_DESKTOP_WINDOW_SIZE);
		viewFrame.pack();
		viewFrame.setResizable(false);
		viewFrame.setVisible(true);
	}

	public static void recreateMonsterCache() {
		MonsterImageCache.cacheCreated = false;
		MonsterImageCache.createMonsterCache();
	}
}