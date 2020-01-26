/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.resourcemanagers;

import java.net.URL;
import java.nio.BufferUnderflowException;

import com.puttysoftware.mazemode.MazeMode;
import com.puttysoftware.mazemode.prefs.PreferencesManager;
import com.puttysoftware.sound.Sound;

public class SoundManager {
    private static final String DEFAULT_LOAD_PATH = "/com/puttysoftware/mazemode/resources/sounds/";
    private static String LOAD_PATH = SoundManager.DEFAULT_LOAD_PATH;
    private static Class<?> LOAD_CLASS = SoundManager.class;

    private static Sound getSound(final String filename) {
        try {
            final URL url = SoundManager.LOAD_CLASS.getResource(
                    SoundManager.LOAD_PATH + filename.toLowerCase() + ".wav");
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
                    new SoundTask(snd).start();
                }
            } catch (final ArrayIndexOutOfBoundsException aioob) {
                // Do nothing
            }
        }
    }

    public static void syncPlaySound(final int soundCat, final int soundID) {
        if (PreferencesManager.getSoundEnabled(soundCat + 1)) {
            try {
                final String soundName = SoundConstants.SOUND_NAMES[soundID];
                final Sound snd = SoundManager.getSound(soundName);
                if (snd != null) {
                    try {
                        snd.play();
                    } catch (final BufferUnderflowException bue) {
                        // Ignore
                    } catch (final NullPointerException np) {
                        // Ignore
                    } catch (final Throwable t) {
                        MazeMode.getErrorLogger().logError(t);
                    }
                }
            } catch (final ArrayIndexOutOfBoundsException aioob) {
                // Do nothing
            }
        }
    }

    public static void useCustomLoadPath(final String customLoadPath,
            final Object plugin) {
        SoundManager.LOAD_PATH = customLoadPath;
        SoundManager.LOAD_CLASS = plugin.getClass();
    }

    public static void useDefaultLoadPath() {
        SoundManager.LOAD_PATH = SoundManager.DEFAULT_LOAD_PATH;
        SoundManager.LOAD_CLASS = SoundManager.class;
    }
}