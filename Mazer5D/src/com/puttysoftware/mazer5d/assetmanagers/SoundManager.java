/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.assetmanagers;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.Mazer5DException;
import com.puttysoftware.mazer5d.prefs.PreferencesManager;

public class SoundManager {
    private static final String DEFAULT_LOAD_PATH = "/assets/sounds/";
    private static String LOAD_PATH = SoundManager.DEFAULT_LOAD_PATH;
    private static Class<?> LOAD_CLASS = SoundManager.class;

    public static void playSound(final int soundCat, final int soundID) {
        if (PreferencesManager.getSoundEnabled(soundCat + 1)) {
            final String filename = SoundConstants.SOUND_NAMES[soundID];
            try {
                final URL url = SoundManager.LOAD_CLASS
                        .getResource(SoundManager.LOAD_PATH
                                + filename.toLowerCase() + ".wav");
                SoundManager.play(url);
            } catch (final ArrayIndexOutOfBoundsException aioob) {
                // Do nothing
            }
        }
    }

    private static final int BUFFER_SIZE = 4096; // 4Kb

    private static void play(final URL soundURL) {
        new Thread() {
            @Override
            public void run() {
                try (AudioInputStream audioInputStream = AudioSystem
                        .getAudioInputStream(soundURL)) {
                    final AudioFormat format = audioInputStream.getFormat();
                    final DataLine.Info info = new DataLine.Info(
                            SourceDataLine.class, format);
                    try (Line line = AudioSystem.getLine(info);
                            SourceDataLine auline = (SourceDataLine) line) {
                        auline.open(format);
                        auline.start();
                        int nBytesRead = 0;
                        final byte[] abData = new byte[SoundManager.BUFFER_SIZE];
                        try {
                            while (nBytesRead != -1) {
                                nBytesRead = audioInputStream.read(abData, 0,
                                        abData.length);
                                if (nBytesRead >= 0) {
                                    auline.write(abData, 0, nBytesRead);
                                }
                            }
                        } catch (final IOException e) {
                            Mazer5D.logError(Mazer5DException.from(e));
                        } finally {
                            auline.drain();
                        }
                    } catch (final LineUnavailableException e) {
                        Mazer5D.logError(Mazer5DException.from(e));
                    }
                } catch (final UnsupportedAudioFileException e) {
                    Mazer5D.logError(Mazer5DException.from(e));
                } catch (final IOException e) {
                    Mazer5D.logError(Mazer5DException.from(e));
                }
            }
        }.start();
    }
}