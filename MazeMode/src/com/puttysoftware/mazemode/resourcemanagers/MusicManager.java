/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.resourcemanagers;

import java.net.URL;
import java.nio.BufferUnderflowException;

import com.puttysoftware.mazemode.MazeMode;
import com.puttysoftware.sound.Sound;

public class MusicManager {
    private static final String DEFAULT_LOAD_PATH = "/com/puttysoftware/mazemode/resources/music/";
    private static String LOAD_PATH = MusicManager.DEFAULT_LOAD_PATH;
    private static Class<?> LOAD_CLASS = MusicManager.class;
    private static Sound CURRENT_MUSIC;
    private static MusicTask task;

    private static Sound getMusic(final String filename) {
        try {
            final URL url = MusicManager.LOAD_CLASS.getResource(
                    MusicManager.LOAD_PATH + filename.toLowerCase() + ".wav");
            final Sound snd = new Sound(url);
            return snd;
        } catch (final NullPointerException np) {
            return null;
        }
    }

    public static void playMusic(final int musicID) {
        MusicManager.CURRENT_MUSIC = MusicManager
                .getMusic(MusicConstants.MUSIC_NAMES[musicID]);
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
                MazeMode.getErrorLogger().logError(t);
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

    public static void useCustomLoadPath(final String customLoadPath,
            final Object plugin) {
        MusicManager.LOAD_PATH = customLoadPath;
        MusicManager.LOAD_CLASS = plugin.getClass();
    }

    public static void useDefaultLoadPath() {
        MusicManager.LOAD_PATH = MusicManager.DEFAULT_LOAD_PATH;
        MusicManager.LOAD_CLASS = MusicManager.class;
    }
}