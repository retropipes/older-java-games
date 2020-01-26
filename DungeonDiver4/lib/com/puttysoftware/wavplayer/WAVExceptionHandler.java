package com.puttysoftware.wavplayer;

import java.lang.Thread.UncaughtExceptionHandler;

public class WAVExceptionHandler implements UncaughtExceptionHandler {
    @Override
    public void uncaughtException(final Thread thr, final Throwable exc) {
        if (thr instanceof WAVFactory) {
            try {
                final WAVFactory media = (WAVFactory) thr;
                WAVFactory.taskCompleted(media.getNumber());
            } catch (final Exception e) {
                // Ignore
            }
        }
    }
}
