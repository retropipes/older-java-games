/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.resourcemanagers;

import java.io.File;
import java.nio.BufferUnderflowException;

import net.worldwizard.audio.Music;
import net.worldwizard.worldz.PluginRegistration;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.world.Extension;

public class MusicManager {
    private static Music CURRENT_MUSIC;
    private static MusicTask task;

    private static Music getMusic(final String filename) {
        try {
            final String basePath = PluginRegistration.getBasePath();
            final File file = new File(basePath + "/Music/"
                    + filename.toLowerCase()
                    + Extension.getMusicExtensionWithPeriod());
            final Music mus = new Music(file.toURI().toURL());
            return mus;
        } catch (final Exception e) {
            return null;
        }
    }

    public static void playMusic(final String musicName) {
        MusicManager.CURRENT_MUSIC = MusicManager.getMusic(musicName);
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
                MusicManager.CURRENT_MUSIC.done();
            } catch (final BufferUnderflowException bue) {
                // Ignore
            } catch (final NullPointerException np) {
                // Ignore
            } catch (final Throwable t) {
                Worldz.getDebug().debug(t);
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