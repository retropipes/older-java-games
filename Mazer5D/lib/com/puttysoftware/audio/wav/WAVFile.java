package com.puttysoftware.audio.wav;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.puttysoftware.errorlogger.ErrorLogger;

class WAVFile extends WAVFactory {
    private final String filename;

    public WAVFile(final String wavfile) {
        super();
        this.filename = wavfile;
    }

    @Override
    public void play(final ErrorLogger logger) {
        if (this.filename != null) {
            final File soundFile = new File(this.filename);
            if (soundFile.exists()) {
                try (FileInputStream inputStream = new FileInputStream(
                        soundFile)) {
                    new WAVPlayer(inputStream, logger).start();
                } catch (final IOException e) {
                    logger.logError(e);
                }
            }
        }
    }
}
