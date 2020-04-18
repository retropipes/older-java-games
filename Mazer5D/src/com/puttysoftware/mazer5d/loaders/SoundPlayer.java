package com.puttysoftware.mazer5d.loaders;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.assets.SoundGroup;
import com.puttysoftware.mazer5d.assets.SoundIndex;
import com.puttysoftware.mazer5d.prefs.Prefs;

public class SoundPlayer {
    private SoundPlayer() {
        // Do nothing
    }

    private static String[] allFilenames;
    private static Properties fileExtensions;
    private static final int BUFFER_SIZE = 4096; // 4Kb

    private static String getSoundFilename(final SoundIndex sound) {
        if (SoundPlayer.allFilenames == null
                && SoundPlayer.fileExtensions == null) {
            SoundPlayer.allFilenames = DataLoader.loadSoundData();
            try (final InputStream stream = SoundPlayer.class
                    .getResourceAsStream(
                            "/assets/data/extension/extension.properties")) {
                SoundPlayer.fileExtensions = new Properties();
                SoundPlayer.fileExtensions.load(stream);
            } catch (final IOException e) {
                Mazer5D.logError(e);
            }
        }
        final String soundExt = SoundPlayer.fileExtensions.getProperty(
                "sounds");
        return SoundPlayer.allFilenames[sound.ordinal()] + soundExt;
    }

    public static void playSound(final SoundIndex sound,
            final SoundGroup group) {
        if (Prefs.isSoundGroupEnabled(group)) {
            if (sound != null && sound != SoundIndex._NONE) {
                final String filename = SoundPlayer.getSoundFilename(sound);
                SoundPlayer.play(SoundPlayer.class.getResource("/assets/sound/"
                        + filename));
            }
        }
    }

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
                        final byte[] abData = new byte[SoundPlayer.BUFFER_SIZE];
                        try {
                            while (nBytesRead != -1) {
                                nBytesRead = audioInputStream.read(abData, 0,
                                        abData.length);
                                if (nBytesRead >= 0) {
                                    auline.write(abData, 0, nBytesRead);
                                }
                            }
                        } catch (final IOException e) {
                            Mazer5D.logError(e);
                        } finally {
                            auline.drain();
                        }
                    } catch (final LineUnavailableException e) {
                        Mazer5D.logError(e);
                    }
                } catch (final UnsupportedAudioFileException e) {
                    Mazer5D.logError(e);
                } catch (final IOException e) {
                    Mazer5D.logError(e);
                }
            }
        }.start();
    }
}