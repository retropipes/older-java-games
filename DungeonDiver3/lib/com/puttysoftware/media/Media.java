package com.puttysoftware.media;

import java.net.URL;

public abstract class Media extends Thread {
    // Constants
    protected static final int EXTERNAL_BUFFER_SIZE = 4096; // 4Kb
    private static int ACTIVE_MEDIA_COUNT = 0;
    private static int MAX_MEDIA_ACTIVE = 5;
    private static Media[] ACTIVE_MEDIA = new Media[MAX_MEDIA_ACTIVE];
    private static ThreadGroup MEDIA_GROUP = new ThreadGroup("Media Players");
    private static MediaExceptionHandler meh = new MediaExceptionHandler();

    // Constructor
    protected Media(ThreadGroup group) {
        super(group, "Media Player " + Media.ACTIVE_MEDIA_COUNT);
    }

    // Methods
    public abstract void stopLoop();

    protected abstract void updateNumber(int newNumber);

    abstract int getNumber();

    // Factories
    public static Media getLoopingFile(String file) {
        return Media.provisionMedia(new MusicFile(Media.MEDIA_GROUP, file,
                Media.ACTIVE_MEDIA_COUNT));
    }

    public static Media getLoopingResource(URL resource) {
        return Media.provisionMedia(new MusicResource(Media.MEDIA_GROUP,
                resource, Media.ACTIVE_MEDIA_COUNT));
    }

    public static Media getNonLoopingFile(String file) {
        return Media.provisionMedia(new SoundFile(Media.MEDIA_GROUP, file,
                Media.ACTIVE_MEDIA_COUNT));
    }

    public static Media getNonLoopingResource(URL resource) {
        return Media.provisionMedia(new SoundResource(Media.MEDIA_GROUP,
                resource, Media.ACTIVE_MEDIA_COUNT));
    }

    private static Media provisionMedia(Media src) {
        if (Media.ACTIVE_MEDIA_COUNT >= Media.MAX_MEDIA_ACTIVE) {
            Media.killAllMediaPlayers();
        }
        try {
            if (src != null) {
                src.setUncaughtExceptionHandler(meh);
                Media.ACTIVE_MEDIA[Media.ACTIVE_MEDIA_COUNT] = src;
                Media.ACTIVE_MEDIA_COUNT++;
            }
        } catch (ArrayIndexOutOfBoundsException aioob) {
            // Do nothing
        }
        return src;
    }

    private static void killAllMediaPlayers() {
        Media.MEDIA_GROUP.interrupt();
    }

    static synchronized void taskCompleted(int taskNum) {
        Media.ACTIVE_MEDIA[taskNum] = null;
        for (int z = taskNum + 1; z < Media.ACTIVE_MEDIA.length; z++) {
            if (Media.ACTIVE_MEDIA[z] != null) {
                Media.ACTIVE_MEDIA[z - 1] = Media.ACTIVE_MEDIA[z];
                if (Media.ACTIVE_MEDIA[z - 1].isAlive()) {
                    Media.ACTIVE_MEDIA[z - 1].updateNumber(z - 1);
                }
            }
        }
        Media.ACTIVE_MEDIA_COUNT--;
    }
}
