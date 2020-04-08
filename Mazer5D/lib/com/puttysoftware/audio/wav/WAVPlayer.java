package com.puttysoftware.audio.wav;

import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.puttysoftware.errorlogger.ErrorLogger;

class WAVPlayer extends Thread {
    private final InputStream soundStream;
    private final ErrorLogger logger;

    public WAVPlayer(final InputStream stream, final ErrorLogger errorLogger) {
        super();
        this.soundStream = stream;
        this.logger = errorLogger;
    }

    @Override
    public void run() {
        try (AudioInputStream audioInputStream = AudioSystem
                .getAudioInputStream(this.soundStream)) {
            final AudioFormat format = audioInputStream.getFormat();
            final DataLine.Info info = new DataLine.Info(SourceDataLine.class,
                    format);
            try (Line rawLine = AudioSystem.getLine(info);
                    SourceDataLine auline = (SourceDataLine) rawLine) {
                auline.open(format);
                auline.start();
                int nBytesRead = 0;
                final byte[] abData = new byte[WAVFactory.EXTERNAL_BUFFER_SIZE];
                try {
                    while (nBytesRead != -1) {
                        nBytesRead = audioInputStream.read(abData, 0,
                                abData.length);
                        if (nBytesRead >= 0) {
                            auline.write(abData, 0, nBytesRead);
                        }
                    }
                } catch (final IOException e) {
                    this.logger.logError(e);
                } finally {
                    auline.drain();
                }
            } catch (final LineUnavailableException e) {
                this.logger.logError(e);
            }
        } catch (final UnsupportedAudioFileException e) {
            this.logger.logError(e);
        } catch (final IOException e) {
            this.logger.logError(e);
        }
    }
}
