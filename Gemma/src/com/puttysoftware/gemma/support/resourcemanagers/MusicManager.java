/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.resourcemanagers;

import java.net.URL;
import java.nio.BufferUnderflowException;
import com.puttysoftware.media.Music;
import com.puttysoftware.gemma.support.Support;

public class MusicManager {
    private static final String DEFAULT_LOAD_PATH = "/com/puttysoftware/brainmaze/resources/music/";
    private static String LOAD_PATH = MusicManager.DEFAULT_LOAD_PATH;
    private static Class<?> LOAD_CLASS = MusicManager.class;
    private static Music CURRENT_MUSIC;
    private static MusicTask task;

    private static Music getMusic(final String filename) {
        try {
            final URL url = MusicManager.LOAD_CLASS.getResource(
                    MusicManager.LOAD_PATH + filename.toLowerCase() + ".ogg");
            return new Music(url);
        } catch (final NullPointerException np) {
            return null;
        }
    }

    public static void playMusic(final int musicID, final int offset) {
        MusicManager.CURRENT_MUSIC = MusicManager
                .getMusic(MusicConstants.getMusicNameForID(musicID, offset));
        if (MusicManager.CURRENT_MUSIC != null) {
            // Play the music
            MusicManager.task = new MusicTask(MusicManager.CURRENT_MUSIC);
            MusicManager.task.start();
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
        if (MusicManager.task != null) {
            if (MusicManager.task.isAlive()) {
                return true;
            }
        }
        return false;
    }
}