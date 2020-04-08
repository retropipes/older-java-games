/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.resourcemanagers;

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
import com.puttysoftware.mazer5d.prefs.PreferencesManager;

public class SoundManager {
    private static final String DEFAULT_LOAD_PATH = "/com/puttysoftware/mazer5d/resources/sounds/";
    private static String LOAD_PATH = SoundManager.DEFAULT_LOAD_PATH;
    private static Class<?> LOAD_CLASS = SoundManager.class;
    private static final int BUFFER_SIZE = 4096; // 4Kb

    public static void playSound(final int soundCat, final int soundID) {
        if (PreferencesManager.getSoundEnabled(soundCat + 1)) {
            try {
                final String soundName = SoundConstants.SOUND_NAMES[soundID];
                final URL url = SoundManager.LOAD_CLASS
                        .getResource(SoundManager.LOAD_PATH
                                + soundName.toLowerCase() + ".wav");
                SoundManager.play(url);
            } catch (final ArrayIndexOutOfBoundsException aioob) {
                // Do nothing
            }
        }
    }

    public static void play(final URL soundURL) {
        new Thread() {
            @Override
            public void run() {
                try (AudioInputStream audioInputStream = AudioSystem
                        .getAudioInputStream(soundURL)) {
                    final AudioFormat format = audioInputStream.getFormat();
                    final DataLine.Info info = new DataLine.Info(
                            SourceDataLine.class, format);
                    try (Line rawLine = AudioSystem.getLine(info);
                            SourceDataLine auline = (SourceDataLine) rawLine) {
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
                        } finally {
                            auline.drain();
                        }
                    }
                } catch (final LineUnavailableException
                        | UnsupportedAudioFileException | IOException e) {
                    Mazer5D.getErrorLogger().logError(e);
                }
            }
        }.start();
    }
}