/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.resourcemanagers;

import java.net.URL;
import java.nio.BufferUnderflowException;

import com.puttysoftware.oggplayer.OggPlayer;
import com.puttysoftware.widgetwarren.WidgetWarren;
import com.puttysoftware.widgetwarren.prefs.PreferencesManager;

public class MusicManager {
    private static final String DEFAULT_LOAD_PATH = "/com/puttysoftware/widgetwarren/resources/music/";
    private static String LOAD_PATH = MusicManager.DEFAULT_LOAD_PATH;
    private static Class<?> LOAD_CLASS = MusicManager.class;
    private static OggPlayer CURRENT_MUSIC;

    private static OggPlayer getMusic(final String filename) {
        try {
            final URL url = MusicManager.LOAD_CLASS.getResource(
                    MusicManager.LOAD_PATH + filename.toLowerCase() + ".ogg");
            return new OggPlayer(url);
        } catch (final NullPointerException np) {
            return null;
        }
    }

    private static void playMusic(final String musicName) {
        MusicManager.CURRENT_MUSIC = MusicManager.getMusic(musicName);
        if (MusicManager.CURRENT_MUSIC != null) {
            // Play the music
            MusicManager.CURRENT_MUSIC.playLoop();
        }
    }

    public static void playExploringMusic() {
        if (PreferencesManager.getMusicEnabled()) {
            MusicManager.playMusic("exploring");
        }
    }

    public static void stopMusic() {
        if (MusicManager.isMusicPlaying()) {
            // Stop the music
            try {
                MusicManager.CURRENT_MUSIC.stopLoop();
            } catch (final BufferUnderflowException bue) {
                // Ignore
            } catch (final NullPointerException np) {
                // Ignore
            } catch (final Throwable t) {
                WidgetWarren.getErrorLogger().logError(t);
            }
        }
    }

    private static boolean isMusicPlaying() {
        if (MusicManager.CURRENT_MUSIC != null) {
            if (MusicManager.CURRENT_MUSIC.isPlaying()) {
                return true;
            }
        }
        return false;
    }
}