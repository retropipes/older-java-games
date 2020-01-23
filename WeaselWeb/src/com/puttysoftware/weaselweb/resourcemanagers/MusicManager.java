/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.resourcemanagers;

import java.net.URL;
import java.nio.BufferUnderflowException;

import com.puttysoftware.media.Media;
import com.puttysoftware.weaselweb.WeaselWeb;

public class MusicManager {
    private static final String DEFAULT_LOAD_PATH = "/com/puttysoftware/weaselweb/resources/music/";
    private static String LOAD_PATH = MusicManager.DEFAULT_LOAD_PATH;
    private static Class<?> LOAD_CLASS = MusicManager.class;
    private static Media CURRENT_MUSIC;

    private static Media getMusic(final String filename) {
        try {
            final URL url = MusicManager.LOAD_CLASS
                    .getResource(MusicManager.LOAD_PATH
                            + filename.toLowerCase() + ".ogg");
            final Media mus = Media.getLoopingResource(url);
            return mus;
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
                WeaselWeb.getErrorLogger().logError(t);
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