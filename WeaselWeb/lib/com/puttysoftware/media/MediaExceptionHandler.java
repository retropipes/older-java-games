package com.puttysoftware.media;

import java.lang.Thread.UncaughtExceptionHandler;

public class MediaExceptionHandler implements UncaughtExceptionHandler {
    @Override
    public void uncaughtException(final Thread thr, final Throwable exc) {
        if (thr instanceof Media) {
            final Media media = (Media) thr;
            Media.taskCompleted(media.getNumber());
        }
    }
}
