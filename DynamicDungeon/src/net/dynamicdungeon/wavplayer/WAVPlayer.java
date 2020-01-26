package net.dynamicdungeon.wavplayer;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class WAVPlayer {
    // Fields
    private final int BUFFER_SIZE = 128000;
    private AudioInputStream audioStream;
    private AudioFormat audioFormat;
    private SourceDataLine sourceLine;
    private final URL soundURL;

    // Constructor
    public WAVPlayer(final URL resource) {
        super();
        this.soundURL = resource;
    }

    // Methods
    public void play() {
        new Thread() {
            @Override
            public void run() {
                WAVPlayer.this.playSound();
            }
        }.start();
    }

    void playSound() {
        try {
            this.audioStream = AudioSystem.getAudioInputStream(this.soundURL);
            final AudioInputStream audioStream2 = this.audioStream;
            if (audioStream2 != null) {
                this.audioFormat = audioStream2.getFormat();
                final DataLine.Info info = new DataLine.Info(
                        SourceDataLine.class, this.audioFormat);
                this.sourceLine = (SourceDataLine) AudioSystem.getLine(info);
                final SourceDataLine sourceLine2 = this.sourceLine;
                if (sourceLine2 != null) {
                    sourceLine2.open(this.audioFormat);
                    sourceLine2.start();
                    int nBytesRead = 0;
                    final byte[] abData = new byte[this.BUFFER_SIZE];
                    while (nBytesRead != -1) {
                        try {
                            nBytesRead = audioStream2.read(abData, 0,
                                    abData.length);
                        } catch (final IOException ioe) {
                            return;
                        }
                        if (nBytesRead >= 0) {
                            sourceLine2.write(abData, 0, nBytesRead);
                        }
                    }
                }
            }
        } catch (UnsupportedAudioFileException | IOException
                | LineUnavailableException e) {
            // Ignore
        } finally {
            final SourceDataLine sourceLine2 = this.sourceLine;
            if (sourceLine2 != null) {
                sourceLine2.drain();
                sourceLine2.close();
            }
        }
    }
}
