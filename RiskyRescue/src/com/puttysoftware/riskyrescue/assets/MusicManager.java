/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue.assets;

import java.io.File;
import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.util.Hashtable;

import com.puttysoftware.audio.mod.MODFactory;
import com.puttysoftware.riskyrescue.Support;

public class MusicManager {
    private static final String LOAD_PATH = "/assets/music/";
    private static Hashtable<String, MODFactory> cache = null;
    private static String TEMP = System.getProperty("java.io.tmpdir")
            + File.pathSeparator + "RiskyRescue";
    private static MODFactory CURRENT_MUSIC;

    private static MODFactory getMusic(final String filename) {
        if (MusicManager.cache == null) {
            MusicManager.cache = new Hashtable<>();
        }
        if (MusicManager.cache.containsKey(filename)) {
            return MusicManager.cache.get(filename);
        } else {
            try {
                final MODFactory newValue = new MODFactory(MusicManager.TEMP)
                        .loadResource(MusicManager.LOAD_PATH + filename);
                MusicManager.cache.put(filename, newValue);
                return newValue;
            } catch (final IOException ioe) {
                Support.getNonFatalLogger().logNonFatalError(ioe);
                return null;
            }
        }
    }

    public static void playMusic(final int musicID, final int offset) {
        MusicManager.CURRENT_MUSIC = MusicManager
                .getMusic(MusicConstants.getMusicNameForID(musicID, offset));
        if (MusicManager.CURRENT_MUSIC != null) {
            // Play the music
            MusicManager.CURRENT_MUSIC.play();
        }
    }

    public static void stopMusic() {
        if (MusicManager.CURRENT_MUSIC != null) {
            // Stop the music
            try {
                MusicManager.CURRENT_MUSIC.stopLoop();
            } catch (final BufferUnderflowException bue) {
                // Ignore
            } catch (final Throwable t) {
                Support.getErrorLogger().logError(t);
            }
        }
    }

    public static boolean isMusicPlaying() {
        if (MusicManager.CURRENT_MUSIC != null) {
            if (MusicManager.CURRENT_MUSIC.isPlaying()) {
                return true;
            }
        }
        return false;
    }
}