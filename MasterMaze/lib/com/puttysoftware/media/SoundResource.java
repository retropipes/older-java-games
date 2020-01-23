package com.puttysoftware.media;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

class SoundResource extends Media {
    private URL soundURL;
    private int number;

    public SoundResource(final ThreadGroup group, URL resURL, int taskNum) {
        super(group);
        this.soundURL = resURL;
        this.number = taskNum;
    }

    @Override
    public void run() {
        try (AudioInputStream audioInputStream = AudioSystem
                .getAudioInputStream(this.soundURL)) {
            AudioFormat format = audioInputStream.getFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            try (SourceDataLine auline = (SourceDataLine) AudioSystem
                    .getLine(info)) {
                auline.open(format);
                auline.start();
                int nBytesRead = 0;
                byte[] abData = new byte[Media.EXTERNAL_BUFFER_SIZE];
                try {
                    while (nBytesRead != -1) {
                        nBytesRead = audioInputStream.read(abData, 0,
                                abData.length);
                        if (nBytesRead >= 0)
                            auline.write(abData, 0, nBytesRead);
                    }
                } catch (IOException e) {
                    Media.taskCompleted(this.number);
                    return;
                } finally {
                    auline.drain();
                    auline.close();
                    try {
                        audioInputStream.close();
                    } catch (IOException e2) {
                        // Ignore
                    }
                }
                Media.taskCompleted(this.number);
            } catch (LineUnavailableException e) {
                try {
                    audioInputStream.close();
                } catch (IOException e2) {
                    // Ignore
                }
                Media.taskCompleted(this.number);
                return;
            }
        } catch (UnsupportedAudioFileException e1) {
            Media.taskCompleted(this.number);
            return;
        } catch (IOException e1) {
            Media.taskCompleted(this.number);
            return;
        }
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
    protected void updateNumber(int newNumber) {
        this.number = newNumber;
    }
}
