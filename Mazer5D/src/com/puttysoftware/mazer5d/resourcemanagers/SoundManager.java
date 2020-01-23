/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.resourcemanagers;

import java.net.URL;

import com.puttysoftware.mazer5d.prefs.PreferencesManager;
import com.puttysoftware.wavplayer.WAVFactory;

public class SoundManager {
    private static final String DEFAULT_LOAD_PATH = "/com/puttysoftware/mazer5d/resources/sounds/";
    private static String LOAD_PATH = SoundManager.DEFAULT_LOAD_PATH;
    private static Class<?> LOAD_CLASS = SoundManager.class;

    private static WAVFactory getSound(final String filename) {
        try {
            final URL url = SoundManager.LOAD_CLASS
                    .getResource(SoundManager.LOAD_PATH
                            + filename.toLowerCase() + ".wav");
            final WAVFactory snd = WAVFactory.getNonLoopingResource(url);
            return snd;
        } catch (final NullPointerException np) {
            return null;
        }
    }

    public static void playSound(final int soundCat, final int soundID) {
        if (PreferencesManager.getSoundEnabled(soundCat + 1)) {
            try {
                final String soundName = SoundConstants.SOUND_NAMES[soundID];
                final WAVFactory snd = SoundManager.getSound(soundName);
                snd.start();
            } catch (final ArrayIndexOutOfBoundsException aioob) {
                // Do nothing
            }
        }
    }
}