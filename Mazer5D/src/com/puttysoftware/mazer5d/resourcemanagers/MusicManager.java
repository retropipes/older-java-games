/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.resourcemanagers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.BufferUnderflowException;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.maze.Maze;
import com.puttysoftware.micromod.MicroMod;
import com.puttysoftware.xio.DirectoryUtilities;

public class MusicManager {
    private static final String DEFAULT_LOAD_PATH = "/com/puttysoftware/mazer5d/resources/music/";
    private static String LOAD_PATH = MusicManager.DEFAULT_LOAD_PATH;
    private static Class<?> LOAD_CLASS = MusicManager.class;
    private static MicroMod CURRENT_MUSIC;

    private static MicroMod getMusic(final String filename) {
        try {
            final File modFile = new File(
                    Maze.getMazeTempFolder() + File.separator + "MusicTemp"
                            + File.separator + filename + ".mod");
            if (!modFile.exists()) {
                final File modParent = modFile.getParentFile();
                if (!modParent.exists()) {
                    final boolean result = modParent.mkdirs();
                    if (!result) {
                        throw new IOException();
                    }
                }
                try (final InputStream is = MusicManager.LOAD_CLASS
                        .getResourceAsStream(
                                MusicManager.LOAD_PATH + filename + ".mod")) {
                    DirectoryUtilities.copyRAMFile(is, modFile);
                }
            }
            final MicroMod mm = new MicroMod();
            mm.loadModule(modFile);
            return mm;
        } catch (final NullPointerException | IOException e) {
            return null;
        }
    }

    public static void playMusic(final String musicName) {
        MusicManager.CURRENT_MUSIC = MusicManager.getMusic(musicName);
        if (MusicManager.CURRENT_MUSIC != null) {
            // Play the music
            MusicManager.CURRENT_MUSIC.playModule();
        }
    }

    public static void stopMusic() {
        if (MusicManager.CURRENT_MUSIC != null) {
            // Stop the music
            try {
                MusicManager.CURRENT_MUSIC.stopModule();
            } catch (final BufferUnderflowException bue) {
                // Ignore
            } catch (final NullPointerException np) {
                // Ignore
            } catch (final Throwable t) {
                Mazer5D.getErrorLogger().logError(t);
            }
        }
    }

    public static boolean isMusicPlaying() {
        if (MusicManager.CURRENT_MUSIC != null) {
            if (MusicManager.CURRENT_MUSIC.isPlayThreadAlive()) {
                return true;
            }
        }
        return false;
    }
}