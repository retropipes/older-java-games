package com.puttysoftware.media;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

class SoundFile extends Media {
    private final String filename;
    private int number;

    public SoundFile(final ThreadGroup group, final String wavfile,
            final int taskNum) {
        super(group);
        this.filename = wavfile;
        this.number = taskNum;
    }

    @Override
    public void run() {
        if (this.filename != null) {
            final File soundFile = new File(this.filename);
            if (!soundFile.exists()) {
                Media.taskCompleted(this.number);
                return;
            }
            try (AudioInputStream audioInputStream = AudioSystem
                    .getAudioInputStream(soundFile)) {
                final AudioFormat format = audioInputStream.getFormat();
                final DataLine.Info info = new DataLine.Info(
                        SourceDataLine.class, format);
                try (SourceDataLine auline = (SourceDataLine) AudioSystem
                        .getLine(info)) {
                    auline.open(format);
                    auline.start();
                    int nBytesRead = 0;
                    final byte[] abData = new byte[Media.EXTERNAL_BUFFER_SIZE];
                    try {
                        while (nBytesRead != -1) {
                            nBytesRead = audioInputStream.read(abData, 0,
                                    abData.length);
                            if (nBytesRead >= 0) {
                                auline.write(abData, 0, nBytesRead);
                            }
                        }
                    } catch (final IOException e) {
                        Media.taskCompleted(this.number);
                        return;
                    } finally {
                        auline.drain();
                        auline.close();
                        try {
                            audioInputStream.close();
                        } catch (final IOException e2) {
                            // Ignore
                        }
                    }
                } catch (final LineUnavailableException e) {
                    try {
                        audioInputStream.close();
                    } catch (final IOException e2) {
                        // Ignore
                    }
                    Media.taskCompleted(this.number);
                    return;
                }
            } catch (final UnsupportedAudioFileException e1) {
                Media.taskCompleted(this.number);
                return;
            } catch (final IOException e1) {
                Media.taskCompleted(this.number);
                return;
            }
        }
        Media.taskCompleted(this.number);
    }

    @Override
    public void stopLoop() {
        // Do nothing
    }

    @Override
    int getNumber() {
        return this.number;
    }

    @Override
    protected void updateNumber(final int newNumber) {
        this.number = newNumber;
    }
}
