/*  Fantastle: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

Any questions should be directed to the author via email at: fantastle@worldwizard.net
 */
package net.worldwizard.audio;

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
            if (!AudioSystem.isLineSupported(this.info)) {
                // Do nothing
            } else {
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

    public void syncPlay() {
        this.getData();
        if (this.clip != null) {
            this.clip.start();
            while (this.clip.isActive()) {
                try {
                    Thread.sleep(100);
                } catch (final InterruptedException ie) {
                    // Do nothing
                }
            }
        }
    }

    public void done() {
        if (this.clip != null) {
            this.clip.close();
        }
    }
}
