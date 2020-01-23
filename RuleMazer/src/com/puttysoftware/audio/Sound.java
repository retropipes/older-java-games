/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.audio;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
    // Fields
    private final URL url;
    private AudioInputStream stream;
    private AudioFileFormat fileFormat;
    private AudioFormat format;
    private Clip clip;
    private DataLine.Info info;

    public Sound(final URL loc) {
        this.url = loc;
    }

    private void getData() {
        try {
            this.stream = AudioSystem.getAudioInputStream(this.url);
            this.fileFormat = AudioSystem.getAudioFileFormat(this.url);
            this.format = this.fileFormat.getFormat();
            this.info = new DataLine.Info(Clip.class, this.format);
            if (AudioSystem.isLineSupported(this.info)) {
                try {
                    this.clip = (Clip) AudioSystem.getLine(this.info);
                    this.clip.open(this.stream);
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
        if (this.clip != null) {
            this.clip.start();
        }
    }
}
