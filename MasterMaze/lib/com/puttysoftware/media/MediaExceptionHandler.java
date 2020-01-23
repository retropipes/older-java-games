package com.puttysoftware.media;

import java.lang.Thread.UncaughtExceptionHandler;

public class MediaExceptionHandler implements UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread thr, Throwable exc) {
        if (thr instanceof Media) {
            Media media = (Media) thr;
            Media.taskCompleted(media.getNumber());
        }
    }
}
