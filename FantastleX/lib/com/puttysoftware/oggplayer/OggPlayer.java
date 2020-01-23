package com.puttysoftware.oggplayer;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class OggPlayer extends Thread {
    // Fields
    private final URL url;
    private boolean stop;

    public OggPlayer(final URL loc) {
        super();
        this.url = loc;
        this.stop = false;
    }

    public void playLoop() {
        this.start();
    }

    @Override
    public void run() {
        AudioFormat format;
        AudioFormat decodedFormat;
        while (!this.stop) {
            try (AudioInputStream stream = AudioSystem
                    .getAudioInputStream(this.url)) {
                // Get AudioInputStream from given file.
                if (stream != null) {
                    format = stream.getFormat();
                    decodedFormat = new AudioFormat(
                            AudioFormat.Encoding.PCM_SIGNED,
                            format.getSampleRate(), 16, format.getChannels(),
                            format.getChannels() * 2, format.getSampleRate(),
                            false);
                    // Get AudioInputStream that will be decoded by underlying
                    // VorbisSPI
                    try (AudioInputStream decodedStream = AudioSystem
                            .getAudioInputStream(decodedFormat, stream)) {
                        try (SourceDataLine line = OggPlayer
                                .getLine(decodedFormat)) {
                            if (line != null) {
                                try {
                                    final byte[] data = new byte[4096];
                                    // Start
                                    line.start();
                                    int nBytesRead = 0;
                                    while (nBytesRead != -1) {
                                        nBytesRead = decodedStream.read(data,
                                                0, data.length);
                                        if (nBytesRead != -1) {
                                            line.write(data, 0, nBytesRead);
                                        }
                                        if (this.stop) {
                                            break;
                                        }
                                    }
                                    // Stop
                                    line.drain();
                                    line.stop();
                                    line.close();
                                } catch (final IOException io) {
                                    // Do nothing
                                }
                            }
                        } catch (final LineUnavailableException lue) {
                            // Do nothing
                        }
                    }
                }
            } catch (final Exception e) {
                // Do nothing
            }
        }
    }

    private static SourceDataLine getLine(final AudioFormat audioFormat)
            throws LineUnavailableException {
        SourceDataLine res = null;
        final DataLine.Info info = new DataLine.Info(SourceDataLine.class,
                audioFormat);
        res = (SourceDataLine) AudioSystem.getLine(info);
        res.open(audioFormat);
        return res;
    }

    public void stopLoop() {
        this.stop = true;
    }
}
