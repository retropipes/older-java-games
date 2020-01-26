/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.resourcemanagers;

import java.nio.BufferUnderflowException;

import com.puttysoftware.audio.ogg.OggFactory;
import com.puttysoftware.gemma.support.Support;

public class MusicManager {
    private static final String INTERNAL_LOAD_PATH = "/com/puttysoftware/gemma/support/resources/music/";
    private final static Class<?> LOAD_CLASS = MusicManager.class;
    private static OggFactory CURRENT_MUSIC;

    private static OggFactory getMusic(final String filename) {
        return OggFactory.loadResource(MusicManager.LOAD_CLASS
                .getResource(MusicManager.INTERNAL_LOAD_PATH + filename));
    }

    public static void playMusic(final int musicID, final int offset) {
        MusicManager.CURRENT_MUSIC = MusicManager
                .getMusic(MusicConstants.getMusicNameForID(musicID, offset));
        if (MusicManager.CURRENT_MUSIC != null) {
            // Play the music
            MusicManager.CURRENT_MUSIC.start();
        }
    }

    public static void stopMusic() {
        if (MusicManager.CURRENT_MUSIC != null) {
            // Stop the music
            try {
                MusicManager.CURRENT_MUSIC.stopLoop();
            } catch (final BufferUnderflowException bue) {
                // Ignore
            } catch (final NullPointerException np) {
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