/*  TallerTower: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.tallertower.resourcemanagers;

import java.net.URL;

import com.puttysoftware.randomrange.RandomRange;
import com.puttysoftware.tallertower.prefs.PreferencesManager;
import com.puttysoftware.wavplayer.WAVFactory;

public class SoundManager {
    private static final String DEFAULT_LOAD_PATH = "/com/puttysoftware/tallertower/resources/sounds/";
    private static String LOAD_PATH = SoundManager.DEFAULT_LOAD_PATH;
    private static Class<?> LOAD_CLASS = SoundManager.class;

    private static WAVFactory getSound(final String filename) {
        try {
            final URL url = SoundManager.LOAD_CLASS.getResource(
                    SoundManager.LOAD_PATH + filename.toLowerCase() + ".wav");
            return WAVFactory.getNonLoopingResource(url);
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
                final String soundName = SoundConstants
                        .getSoundName(soundID + offset);
                final WAVFactory snd = SoundManager.getSound(soundName);
                snd.start();
            }
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            // Do nothing
        }
    }
}