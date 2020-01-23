/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.resourcemanagers;

import java.net.URL;
import java.nio.BufferUnderflowException;

import net.worldwizard.audio.Sound;
import net.worldwizard.worldz.Worldz;

public class SoundManager {
    private static final String DEFAULT_LOAD_PATH = "/net/worldwizard/worldz/resources/sounds/";
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

    public static void playSound(final String soundName) {
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
                Worldz.getDebug().debug(t);
            }
        }
    }

    public static void useCustomLoadPath(final String customLoadPath,
            final Object plugin) {
        SoundManager.LOAD_PATH = customLoadPath;
        SoundManager.LOAD_CLASS = plugin.getClass();
        SoundCache.flushCache();
    }
}