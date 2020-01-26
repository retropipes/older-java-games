/*  BrainMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.brainmaze.resourcemanagers;

import java.net.URL;

import com.puttysoftware.brainmaze.prefs.PreferencesManager;
import com.puttysoftware.media.Sound;

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

    public static void playSound(final int soundCat, final int soundID) {
        if (PreferencesManager.getSoundEnabled(soundCat + 1)) {
            try {
                final String soundName = SoundConstants.SOUND_NAMES[soundID];
                final Sound snd = SoundManager.getSound(soundName);
                new SoundTask(snd).start();
            } catch (final ArrayIndexOutOfBoundsException aioob) {
                // Do nothing
            }
        }
    }
}