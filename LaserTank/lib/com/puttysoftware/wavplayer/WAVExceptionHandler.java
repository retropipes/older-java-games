package com.puttysoftware.wavplayer;

import java.lang.Thread.UncaughtExceptionHandler;

public class WAVExceptionHandler implements UncaughtExceptionHandler {
    @Override
    public void uncaughtException(final Thread thr, final Throwable exc) {
        try {
            if (thr instanceof WAVFactory) {
                final WAVFactory media = (WAVFactory) thr;
                WAVFactory.taskCompleted(media.getNumber());
            }
        } catch (final Throwable t) {
            // Ignore
        }
    }
}
