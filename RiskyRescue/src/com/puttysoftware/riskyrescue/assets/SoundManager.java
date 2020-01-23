/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue.assets;

import com.puttysoftware.audio.wav.WAVFactory;
import com.puttysoftware.randomrange.RandomRange;
import com.puttysoftware.riskyrescue.prefs.LocalPreferencesManager;

public class SoundManager {
    private static final String INTERNAL_LOAD_PATH = "/assets/sounds/";
    private final static Class<?> LOAD_CLASS = SoundManager.class;

    private static WAVFactory getSound(final String filename) {
        return WAVFactory.loadResource(SoundManager.LOAD_CLASS
                .getResource(SoundManager.INTERNAL_LOAD_PATH
                        + filename.toLowerCase() + ".wav"));
    }

    public static void playSound(final int soundID) {
        if (LocalPreferencesManager.getSoundsEnabled()) {
            try {
                int offset;
                RandomRange rr;
                switch (soundID) {
                case SoundConstants.STEP:
                    rr = new RandomRange(0, 1);
                    offset = rr.generate();
                    break;
                default:
                    offset = 0;
                    break;
                }
                final String soundName = SoundNames.getSoundNames()[soundID
                        + offset];
                final WAVFactory snd = SoundManager.getSound(soundName);
                if (snd != null) {
                    snd.start();
                }
            } catch (final ArrayIndexOutOfBoundsException aioob) {
                // Do nothing
            }
        }
    }
}