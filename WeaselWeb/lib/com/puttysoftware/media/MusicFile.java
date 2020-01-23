package com.puttysoftware.media;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

class MusicFile extends Media {
    // Fields
    private File file;
    private AudioInputStream stream;
    private AudioInputStream decodedStream;
    private AudioFormat decodedFormat;
    private boolean stop;
    private int number;

    public MusicFile(final ThreadGroup group, final String loc,
            final int taskNum) {
        super(group);
        this.file = new File(loc);
        this.stop = false;
        this.number = taskNum;
    }

    @Override
    public void run() {
        while (!this.stop) {
            try {
                // Get AudioInputStream from given file.
                this.stream = AudioSystem.getAudioInputStream(this.file);
                this.decodedStream = null;
                if (this.stream != null) {
                    AudioFormat format = this.stream.getFormat();
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
            } catch (Exception e) {
                // Do nothing
            }
            try (SourceDataLine line = MusicFile.getLine(this.decodedFormat)) {
                if (line != null) {
                    try {
                        byte[] data = new byte[Media.EXTERNAL_BUFFER_SIZE];
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
                    } catch (IOException io) {
                        // Do nothing
                    }
                }
            } catch (LineUnavailableException lue) {
                // Do nothing
            }
        }
        Media.taskCompleted(this.number);
    }

    private static SourceDataLine getLine(AudioFormat audioFormat)
            throws LineUnavailableException {
        SourceDataLine res = null;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class,
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
    protected void updateNumber(int newNumber) {
        this.number = newNumber;
    }
}
