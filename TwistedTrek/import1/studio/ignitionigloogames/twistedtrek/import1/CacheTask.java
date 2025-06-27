/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1;

import studio.ignitionigloogames.twistedtrek.import1.resourcemanagers.ImageCache;
import studio.ignitionigloogames.twistedtrek.import1.resourcemanagers.MonsterImageCache;

class CacheTask extends Thread {
	// Constructors
	public CacheTask() {
		// Do nothing
	}

	@Override
	public void run() {
		Import1.getApplication().getPrefsManager().updateWaitProgress(0);
		// Enter Wait Mode
		Import1.getApplication().getPrefsManager().enterWaitMode();
		// Update Micro Logo
		Import1.getApplication().updateMicroLogo();
		Import1.getApplication().getPrefsManager().updateWaitProgress(20);
		// Update GUI Logo
		Import1.getApplication().getGUIManager().updateLogo();
		Import1.getApplication().getPrefsManager().updateWaitProgress(40);
		// Recreate image cache
		ImageCache.recreateCache();
		Import1.getApplication().getPrefsManager().updateWaitProgress(60);
		// Recreate monster image cache
		MonsterImageCache.recreateMonsterCache();
		Import1.getApplication().getPrefsManager().updateWaitProgress(80);
		// Update stat image cache
		Import1.getApplication().getGameManager().getStatGUI().updateGUI();
		Import1.getApplication().getPrefsManager().updateWaitProgress(100);
		// Update Help
		Import1.getApplication().getObjectHelpManager().updateHelpSize();
		Import1.getApplication().getGeneralHelpManager().updateHelpSize();
		// Exit Wait Mode
		Import1.getApplication().getPrefsManager().exitWaitMode();
		Import1.getApplication().getPrefsManager().hidePrefs();
	}
}
