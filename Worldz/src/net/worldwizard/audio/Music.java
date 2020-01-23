/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.audio;

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

public class Music {
    // Fields
    private final URL url;
    private AudioInputStream stream;
    private AudioFileFormat fileFormat;
    private AudioFormat format;
    private SourceDataLine line;
    private DataLine.Info info;
    private boolean stop;

    public Music(final URL loc) {
        this.url = loc;
        this.stop = false;
    }

    private void getData() {
        try {
            this.stream = AudioSystem.getAudioInputStream(this.url);
            this.fileFormat = AudioSystem.getAudioFileFormat(this.url);
            this.format = this.fileFormat.getFormat();
            this.info = new DataLine.Info(SourceDataLine.class, this.format);
            if (!AudioSystem.isLineSupported(this.info)) {
                System.out.println("Unsupported");
                // Do nothing
            } else {
                try {
                    this.line = (SourceDataLine) AudioSystem.getLine(this.info);
                    this.line.open(this.format);
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
            int numRead = 0;
            final int chunkSize = 1024;
            final byte[] buffer = new byte[chunkSize];
            this.line.start();
            this.stream.mark(Integer.MAX_VALUE);
            try {
                while (!this.stop) {
                    numRead = this.stream.read(buffer, 0, chunkSize);
                    if (numRead > 0) {
                        this.line.write(buffer, 0, numRead);
                    }
                    if (numRead < chunkSize) {
                        // Loop
                        this.stream.reset();
                    }
                }
            } catch (final IOException io) {
                // Do nothing
            }
        }
    }

    public void done() {
        this.stop = true;
        this.line.close();
        try {
            this.stream.close();
        } catch (final IOException io) {
            // Ignore
        }
    }
}
