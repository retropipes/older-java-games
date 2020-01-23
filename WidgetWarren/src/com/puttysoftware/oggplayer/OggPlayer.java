package com.puttysoftware.oggplayer;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class OggPlayer {
    // Fields
    final URL url;
    AudioInputStream stream;
    AudioInputStream decodedStream;
    AudioFormat format;
    AudioFormat decodedFormat;
    boolean stop;
    private Thread player;

    public OggPlayer(final URL loc) {
        this.url = loc;
        this.stop = false;
        this.player = new Thread() {
            @Override
            public void run() {
                while (!OggPlayer.this.stop) {
                    try {
                        // Get AudioInputStream from given file.
                        OggPlayer.this.stream = AudioSystem
                                .getAudioInputStream(OggPlayer.this.url);
                        OggPlayer.this.decodedStream = null;
                        if (OggPlayer.this.stream != null) {
                            OggPlayer.this.format = OggPlayer.this.stream
                                    .getFormat();
                            OggPlayer.this.decodedFormat = new AudioFormat(
                                    AudioFormat.Encoding.PCM_SIGNED,
                                    OggPlayer.this.format.getSampleRate(), 16,
                                    OggPlayer.this.format.getChannels(),
                                    OggPlayer.this.format.getChannels() * 2,
                                    OggPlayer.this.format.getSampleRate(),
                                    false);
                            // Get AudioInputStream that will be decoded by
                            // underlying
                            // VorbisSPI
                            OggPlayer.this.decodedStream = AudioSystem
                                    .getAudioInputStream(
                                            OggPlayer.this.decodedFormat,
                                            OggPlayer.this.stream);
                        }
                        try (SourceDataLine line = OggPlayer
                                .getLine(OggPlayer.this.decodedFormat)) {
                            if (line != null) {
                                try {
                                    final byte[] data = new byte[4096];
                                    // Start
                                    line.start();
                                    int nBytesRead = 0;
                                    while (nBytesRead != -1) {
                                        nBytesRead = OggPlayer.this.decodedStream
                                                .read(data, 0, data.length);
                                        if (nBytesRead != -1) {
                                            line.write(data, 0, nBytesRead);
                                        }
                                        if (OggPlayer.this.stop) {
                                            break;
                                        }
                                    }
                                    // Stop
                                    line.drain();
                                    line.stop();
                                } catch (final IOException io) {
                                    // Do nothing
                                } finally {
                                    // Stop
                                    line.drain();
                                    line.stop();
                                }
                            }
                        } catch (final LineUnavailableException lue) {
                            // Do nothing
                        }
                    } catch (final Exception e) {
                        // Do nothing
                    }
                }
            }
        };
    }

    public void playLoop() {
        this.player.start();
    }

    static SourceDataLine getLine(final AudioFormat audioFormat)
            throws LineUnavailableException {
        SourceDataLine res = null;
        final DataLine.Info info = new DataLine.Info(SourceDataLine.class,
                audioFormat);
        res = (SourceDataLine) AudioSystem.getLine(info);
        res.open(audioFormat);
        return res;
    }

    public boolean isPlaying() {
        return this.player.isAlive();
    }

    public void stopLoop() {
        this.stop = true;
    }

    public boolean isStopped() {
        return this.stop;
    }
}
