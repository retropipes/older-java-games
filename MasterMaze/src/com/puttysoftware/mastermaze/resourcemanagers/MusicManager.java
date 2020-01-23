/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.resourcemanagers;

import java.net.URL;
import java.nio.BufferUnderflowException;

import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.media.Media;

public class MusicManager {
    private static final String DEFAULT_LOAD_PATH = "/com/puttysoftware/mastermaze/resources/music/";
    private static String LOAD_PATH = MusicManager.DEFAULT_LOAD_PATH;
    private static Class<?> LOAD_CLASS = MusicManager.class;
    private static Media CURRENT_MUSIC;

    private static Media getMusic(final String filename) {
        try {
            final URL url = MusicManager.LOAD_CLASS
                    .getResource(MusicManager.LOAD_PATH
                            + filename.toLowerCase() + ".ogg");
            return Media.getLoopingResource(url);
        } catch (final NullPointerException np) {
            return null;
        }
    }

    public static void playMusic(final String musicName) {
        MusicManager.CURRENT_MUSIC = MusicManager.getMusic(musicName);
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
                MasterMaze.getErrorLogger().logError(t);
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