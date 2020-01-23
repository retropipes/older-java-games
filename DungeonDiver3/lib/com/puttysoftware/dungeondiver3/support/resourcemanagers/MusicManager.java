/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.resourcemanagers;

import java.net.URL;
import java.nio.BufferUnderflowException;

import com.puttysoftware.dungeondiver3.support.Support;
import com.puttysoftware.media.Media;

public class MusicManager {
    private static final String INTERNAL_LOAD_PATH = "/com/puttysoftware/dungeondiver3/support/resources/music/";
    private final static Class<?> LOAD_CLASS = MusicManager.class;
    private static Media CURRENT_MUSIC;

    private static Media getMusic(final String filename) {
        try {
            final URL url = MusicManager.LOAD_CLASS
                    .getResource(MusicManager.INTERNAL_LOAD_PATH
                            + filename.toLowerCase() + ".ogg");
            return Media.getLoopingResource(url);
        } catch (final NullPointerException np) {
            return null;
        }
    }

    public static void playMusic(int musicID) {
        MusicManager.CURRENT_MUSIC = MusicManager
                .getMusic(MusicConstants.MUSIC_NAMES[musicID]);
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
            } catch (BufferUnderflowException bue) {
                // Ignore
            } catch (NullPointerException np) {
                // Ignore
            } catch (Throwable t) {
                Support.getErrorLogger().logError(t);
            }
        }
    }

    public static boolean isMusicPlaying() {
        if (MusicManager.CURRENT_MUSIC != null) {
            if (MusicManager.CURRENT_MUSIC.isAlive()) {
                return true;
            }
        }
        return false;
    }
}