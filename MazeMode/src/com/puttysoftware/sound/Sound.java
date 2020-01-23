/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.sound;

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
    private boolean stop;
    private static final int EXTERNAL_BUFFER_SIZE = 4096; // 4Kb

    public Sound(final URL loc) {
        this.url = loc;
        this.stop = false;
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
                    this.line.open(this.format, Sound.EXTERNAL_BUFFER_SIZE);
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
            final byte[] abData = new byte[Sound.EXTERNAL_BUFFER_SIZE];
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

    public void playLoop() {
        this.getData();
        if (this.line != null) {
            this.line.start();
            this.stream.mark(Integer.MAX_VALUE);
            int nBytesRead = 0;
            final byte[] abData = new byte[Sound.EXTERNAL_BUFFER_SIZE];
            try {
                while (!this.stop) {
                    while (nBytesRead != -1) {
                        nBytesRead = this.stream.read(abData, 0, abData.length);
                        if (nBytesRead >= 0) {
                            this.line.write(abData, 0, nBytesRead);
                        }
                        if (this.stop) {
                            break;
                        }
                        if (nBytesRead < Sound.EXTERNAL_BUFFER_SIZE) {
                            // Loop
                            this.stream.reset();
                        }
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

    public void stopLoop() {
        this.stop = true;
    }
}