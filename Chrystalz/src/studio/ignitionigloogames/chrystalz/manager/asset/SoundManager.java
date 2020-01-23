/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.manager.asset;

import java.net.URL;

import studio.ignitionigloogames.chrystalz.manager.file.Extension;
import studio.ignitionigloogames.chrystalz.prefs.PreferencesManager;
import studio.ignitionigloogames.common.wavplayer.WAVFactory;

public class SoundManager {
    private static final String DEFAULT_LOAD_PATH = "/assets/sounds/";
    private static String LOAD_PATH = SoundManager.DEFAULT_LOAD_PATH;
    private static Class<?> LOAD_CLASS = SoundManager.class;

    private static WAVFactory getSound(final String filename) {
        final URL url = SoundManager.LOAD_CLASS
                .getResource(SoundManager.LOAD_PATH + filename.toLowerCase()
                        + Extension.getSoundExtensionWithPeriod());
        return WAVFactory.getNonLoopingResource(url);
    }

    public static void playSound(final int soundID) {
        try {
            if (PreferencesManager.getSoundsEnabled()) {
                final String soundName = SoundConstants.getSoundName(soundID);
                final WAVFactory snd = SoundManager.getSound(soundName);
                snd.start();
            }
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            // Do nothing
        }
    }
}