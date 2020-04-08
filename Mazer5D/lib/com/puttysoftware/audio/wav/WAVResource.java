package com.puttysoftware.audio.wav;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.puttysoftware.errorlogger.ErrorLogger;

class WAVResource extends WAVFactory {
    private final URL soundURL;

    public WAVResource(final URL resURL) {
        super();
        this.soundURL = resURL;
    }

    @Override
    public void play(final ErrorLogger logger) {
        if (this.soundURL != null) {
            try (InputStream inputStream = this.soundURL.openStream()) {
                new WAVPlayer(inputStream, logger).start();
            } catch (final IOException e) {
                logger.logError(e);
            }
        }
    }
}
