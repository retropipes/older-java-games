package com.puttysoftware.audio.wav;

import java.net.URL;

import com.puttysoftware.errorlogger.ErrorLogger;

public abstract class WAVFactory {
    // Constants
    protected static final int EXTERNAL_BUFFER_SIZE = 4096; // 4Kb

    // Constructor
    protected WAVFactory() {
        super();
    }

    // Methods
    public abstract void play(final ErrorLogger logger);

    // Factories
    public static WAVFactory loadFile(final String file) {
        return new WAVFile(file);
    }

    public static WAVFactory loadResource(final URL resource) {
        return new WAVResource(resource);
    }

    public static void playFile(final String file, final ErrorLogger logger) {
        new WAVFile(file).play(logger);
    }

    public static void playResource(final URL resource,
            final ErrorLogger logger) {
        new WAVResource(resource).play(logger);
    }
}
