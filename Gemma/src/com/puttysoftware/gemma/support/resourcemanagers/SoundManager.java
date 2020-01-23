/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.resourcemanagers;

import com.puttysoftware.audio.wav.WAVFactory;
import com.puttysoftware.gemma.support.prefs.LocalPreferencesManager;
import com.puttysoftware.randomrange.RandomRange;

public class SoundManager {
	private static final String INTERNAL_LOAD_PATH = "/com/puttysoftware/gemma/support/resources/sounds/";
	private final static Class<?> LOAD_CLASS = SoundManager.class;

	private static WAVFactory getSound(final String filename) {
		try {
			return WAVFactory.loadResource(SoundManager.LOAD_CLASS
					.getResource(SoundManager.INTERNAL_LOAD_PATH + filename.toLowerCase() + ".wav"));
		} catch (final NullPointerException np) {
			return null;
		}
	}

	public static void playSound(int soundID) {
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
				final WAVFactory snd = SoundManager.getSound(soundName);
				if (snd != null) {
					snd.start();
				}
			} catch (ArrayIndexOutOfBoundsException aioob) {
				// Do nothing
			}
		}
	}
}