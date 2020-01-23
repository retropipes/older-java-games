/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.resourcemanagers;

import java.net.URL;

import studio.ignitionigloogames.audio.wav.WavFactory;
import studio.ignitionigloogames.randomrange.RandomRange;
import studio.ignitionigloogames.twistedtrek.import2.prefs.PreferencesManager;

public class SoundManager {
    private static final String DEFAULT_LOAD_PATH = "/com/puttysoftware/tallertower/resources/sounds/";
    private static String LOAD_PATH = SoundManager.DEFAULT_LOAD_PATH;
    private static Class<?> LOAD_CLASS = SoundManager.class;

    private static WavFactory getSound(final String filename) {
	try {
	    final URL url = SoundManager.LOAD_CLASS
		    .getResource(SoundManager.LOAD_PATH + filename.toLowerCase() + ".wav");
	    return WavFactory.loadResource(url);
	} catch (final NullPointerException np) {
	    return null;
	}
    }

    public static void playSound(final int soundID) {
	try {
	    if (PreferencesManager.getSoundsEnabled()) {
		int offset = 0;
		if (soundID == SoundConstants.SOUND_WALK) {
		    final RandomRange rSound = new RandomRange(0, 2);
		    offset = rSound.generate();
		}
		final String soundName = SoundConstants.getSoundName(soundID + offset);
		final WavFactory snd = SoundManager.getSound(soundName);
		snd.start();
	    }
	} catch (final ArrayIndexOutOfBoundsException aioob) {
	    // Do nothing
	}
    }
}