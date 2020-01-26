package com.puttysoftware.media;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

class MusicResource extends Media {
    // Fields
    private final URL url;
    private AudioInputStream stream;
    private AudioInputStream decodedStream;
    private AudioFormat decodedFormat;
    private boolean stop;
    private int number;

    public MusicResource(final ThreadGroup group, final URL loc,
            final int taskNum) {
        super(group);
        this.url = loc;
        this.stop = false;
        this.number = taskNum;
    }

    @Override
    public void run() {
        while (!this.stop) {
            try {
                // Get AudioInputStream from given file.
                this.stream = AudioSystem.getAudioInputStream(this.url);
                this.decodedStream = null;
                if (this.stream != null) {
                    final AudioFormat format = this.stream.getFormat();
                    this.decodedFormat = new AudioFormat(
                            AudioFormat.Encoding.PCM_SIGNED,
                            format.getSampleRate(), 16, format.getChannels(),
                            format.getChannels() * 2, format.getSampleRate(),
                            false);
                    // Get AudioInputStream that will be decoded by underlying
                    // VorbisSPI
                    this.decodedStream = AudioSystem.getAudioInputStream(
                            this.decodedFormat, this.stream);
                }
            } catch (final Exception e) {
                // Do nothing
            }
            try (SourceDataLine line = MusicResource
                    .getLine(this.decodedFormat)) {
                if (line != null) {
                    try {
                        final byte[] data = new byte[Media.EXTERNAL_BUFFER_SIZE];
                        // Start
                        line.start();
                        int nBytesRead = this.decodedStream.read(data, 0,
                                data.length);
                        if (nBytesRead == -1) {
                            // No data
                            this.stop = true;
                        }
                        while (nBytesRead != -1) {
                            if (nBytesRead >= 0) {
                                line.write(data, 0, nBytesRead);
                            }
                            nBytesRead = this.decodedStream.read(data, 0,
                                    data.length);
                            if (this.stop) {
                                break;
                            }
                        }
                        // Stop
                        line.drain();
                        line.stop();
                        line.close();
                        this.decodedStream.close();
                        this.stream.close();
                    } catch (final IOException io) {
                        // Do nothing
                    }
                }
            } catch (final LineUnavailableException lue) {
                // Do nothing
            }
        }
        Media.taskCompleted(this.number);
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

    @Override
    public void stopLoop() {
        this.stop = true;
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
