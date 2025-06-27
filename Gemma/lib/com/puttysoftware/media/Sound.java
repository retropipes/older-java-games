package com.puttysoftware.media;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
    // Fields
    private final URL url;
    private AudioInputStream stream;
    private AudioFileFormat fileFormat;
    private AudioFormat format;
    private SourceDataLine line;
    private DataLine.Info info;
    private final int BUFFER_SIZE;
    private static final int SOUND_BUFFER_SIZE = 16384; // 16Kb

    public Sound(final URL loc) {
        this.url = loc;
        this.BUFFER_SIZE = Sound.SOUND_BUFFER_SIZE;
    }

    private void getData() {
        try {
            this.stream = AudioSystem.getAudioInputStream(this.url);
            this.fileFormat = AudioSystem.getAudioFileFormat(this.url);
            this.format = this.fileFormat.getFormat();
            this.info = new DataLine.Info(SourceDataLine.class, this.format);
            if (AudioSystem.isLineSupported(this.info)) {
                try {
                    this.line = (SourceDataLine) AudioSystem.getLine(this.info);
                    this.line.open(this.format, this.BUFFER_SIZE);
                } catch (final LineUnavailableException e) {
                    // Do nothing
                }
            }
        } catch (final UnsupportedAudioFileException e) {
            // Do nothing
        } catch (final IOException e) {
            // Do nothing
        }
    }

    public void play() {
        this.getData();
        if (this.line != null) {
            this.line.start();
            int nBytesRead = 0;
            final byte[] abData = new byte[this.BUFFER_SIZE];
            try {
                while (nBytesRead != -1) {
                    nBytesRead = this.stream.read(abData, 0, abData.length);
                    if (nBytesRead >= 0) {
                        this.line.write(abData, 0, nBytesRead);
                    }
                }
                this.stream.close();
            } catch (final IOException e) {
                return;
            } finally {
                this.line.drain();
                this.line.close();
            }
        }
    }
}
