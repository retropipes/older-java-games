/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.dungeondiver2.resourcemanagers;

import java.net.URL;
import java.nio.BufferUnderflowException;

import net.worldwizard.dungeondiver2.DungeonDiverII;
import net.worldwizard.music.Music;

public class MusicManager {
    private static final String DEFAULT_LOAD_PATH = "/net/worldwizard/dungeondiver2/resources/music/";
    private static String LOAD_PATH = MusicManager.DEFAULT_LOAD_PATH;
    private static Class<?> LOAD_CLASS = MusicManager.class;
    private static Music CURRENT_MUSIC;
    private static MusicTask task;

    private static Music getMusic(final String filename) {
        try {
            final URL url = MusicManager.LOAD_CLASS
                    .getResource(MusicManager.LOAD_PATH
                            + filename.toLowerCase() + ".ogg");
            final Music music = new Music(url);
            return music;
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
                DungeonDiverII.getErrorLogger().logError(t);
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