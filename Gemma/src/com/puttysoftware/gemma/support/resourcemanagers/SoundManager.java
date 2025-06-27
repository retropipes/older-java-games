/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.resourcemanagers;

import java.net.URL;
import com.puttysoftware.media.Sound;
import com.puttysoftware.gemma.support.prefs.LocalPreferencesManager;
import com.puttysoftware.randomrange.RandomRange;

public class SoundManager {
	private static final String DEFAULT_LOAD_PATH = "/com/puttysoftware/brainmaze/resources/sounds/";
	private static String LOAD_PATH = SoundManager.DEFAULT_LOAD_PATH;
	private static Class<?> LOAD_CLASS = SoundManager.class;

	private static Sound getSound(final String filename) {
		try {
			final URL url = SoundManager.LOAD_CLASS.getResource(
					SoundManager.LOAD_PATH + filename.toLowerCase() + ".wav");
			return new Sound(url);
		} catch (final NullPointerException np) {
			return null;
		}
	}

	public static void playSound(final int soundID) {
		if (LocalPreferencesManager.getSoundsEnabled()) {
			try {
				int offset;
				RandomRange rr;
				switch (soundID) {
					case GameSoundConstants.SOUND_STEP:
						rr = new RandomRange(0, 1);
						offset = rr.generate();
						break;
					default:
						offset = 0;
						break;
				}
				final String soundName = SoundNames.getSoundNames()[soundID + offset];
				final Sound snd = SoundManager.getSound(soundName);
				if (snd != null) {
					snd.play();
				}
			} catch (final ArrayIndexOutOfBoundsException aioob) {
				// Do nothing
			}
		}
	}
}