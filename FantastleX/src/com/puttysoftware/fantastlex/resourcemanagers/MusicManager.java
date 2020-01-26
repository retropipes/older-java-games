/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.resourcemanagers;

import java.net.URL;
import java.nio.BufferUnderflowException;

import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.oggplayer.OggPlayer;

public class MusicManager {
    private static final String DEFAULT_LOAD_PATH = "/com/puttysoftware/fantastlex/resources/music/";
    private static String LOAD_PATH = MusicManager.DEFAULT_LOAD_PATH;
    private static Class<?> LOAD_CLASS = MusicManager.class;
    private static OggPlayer CURRENT_MUSIC;

    private static OggPlayer getMusic(final String filename) {
        try {
            final URL u = MusicManager.LOAD_CLASS.getResource(
                    MusicManager.LOAD_PATH + filename.toLowerCase() + ".ogg");
            return new OggPlayer(u);
        } catch (final NullPointerException np) {
            return null;
        }
    }

    public static void playMusic(final String musicName) {
        MusicManager.CURRENT_MUSIC = MusicManager.getMusic(musicName);
        if (MusicManager.CURRENT_MUSIC != null) {
            // Play the music
            MusicManager.CURRENT_MUSIC.playLoop();
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
                FantastleX.getErrorLogger().logError(t);
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