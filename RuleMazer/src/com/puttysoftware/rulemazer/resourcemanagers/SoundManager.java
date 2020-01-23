/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.resourcemanagers;

import java.net.URL;
import java.nio.BufferUnderflowException;

import com.puttysoftware.audio.Sound;
import com.puttysoftware.rulemazer.Main;
import com.puttysoftware.rulemazer.prefs.PreferencesManager;

public class SoundManager {
    private static final String DEFAULT_LOAD_PATH = "/com/puttysoftware/rulemazer/resources/sounds/";
    private static String LOAD_PATH = SoundManager.DEFAULT_LOAD_PATH;
    private static Class<?> LOAD_CLASS = SoundManager.class;

    private static Sound getSound(final String filename) {
        // Get it from the cache
        return SoundCache.getCachedSound(filename);
    }

    static Sound getUncachedSound(final String filename) {
        try {
            final URL url = SoundManager.LOAD_CLASS
                    .getResource(SoundManager.LOAD_PATH
                            + filename.toLowerCase() + ".wav");
            final Sound snd = new Sound(url);
            return snd;
        } catch (final NullPointerException np) {
            return null;
        }
    }

    public static void playSound(final int soundCat, final int soundID) {
        if (PreferencesManager.getSoundEnabled(soundCat + 1)) {
            try {
                final String soundName = SoundConstants.SOUND_NAMES[soundID];
                final Sound snd = SoundManager.getSound(soundName);
                if (snd != null) {
                    // Play the sound
                    try {
                        snd.play();
                    } catch (final BufferUnderflowException bue) {
                        // Ignore
                    } catch (final NullPointerException np) {
                        // Ignore
                    } catch (final Throwable t) {
                        Main.getDebug().debug(t);
                    }
                }
            } catch (final ArrayIndexOutOfBoundsException aioob) {
                // Do nothing
            }
        }
    }
}