package com.puttysoftware.wavplayer;

import java.net.URL;

public abstract class WAVFactory extends Thread {
    // Constants
    protected static final int EXTERNAL_BUFFER_SIZE = 4096; // 4Kb
    private static int ACTIVE_MEDIA_COUNT = 0;
    private static int MAX_MEDIA_ACTIVE = 5;
    private static WAVFactory[] ACTIVE_MEDIA = new WAVFactory[WAVFactory.MAX_MEDIA_ACTIVE];
    private static ThreadGroup MEDIA_GROUP = new ThreadGroup("Media Players");
    private static WAVExceptionHandler meh = new WAVExceptionHandler();

    // Constructor
    protected WAVFactory(final ThreadGroup group) {
        super(group, "Media Player " + WAVFactory.ACTIVE_MEDIA_COUNT);
    }

    // Methods
    public abstract void stopLoop();

    protected abstract void updateNumber(int newNumber);

    abstract int getNumber();

    // Factories
    public static WAVFactory getNonLoopingFile(final String file) {
        return WAVFactory.provisionMedia(new WAVFile(WAVFactory.MEDIA_GROUP,
                file, WAVFactory.ACTIVE_MEDIA_COUNT));
    }

    public static WAVFactory getNonLoopingResource(final URL resource) {
        return WAVFactory.provisionMedia(new WAVResource(WAVFactory.MEDIA_GROUP,
                resource, WAVFactory.ACTIVE_MEDIA_COUNT));
    }

    private static WAVFactory provisionMedia(final WAVFactory src) {
        if (WAVFactory.ACTIVE_MEDIA_COUNT >= WAVFactory.MAX_MEDIA_ACTIVE) {
            WAVFactory.killAllMediaPlayers();
        }
        try {
            if (src != null) {
                src.setUncaughtExceptionHandler(WAVFactory.meh);
                WAVFactory.ACTIVE_MEDIA[WAVFactory.ACTIVE_MEDIA_COUNT] = src;
                WAVFactory.ACTIVE_MEDIA_COUNT++;
            }
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            // Do nothing
        }
        return src;
    }

    private static void killAllMediaPlayers() {
        WAVFactory.MEDIA_GROUP.interrupt();
    }

    static synchronized void taskCompleted(final int taskNum) {
        WAVFactory.ACTIVE_MEDIA[taskNum] = null;
        for (int z = taskNum + 1; z < WAVFactory.ACTIVE_MEDIA.length; z++) {
            if (WAVFactory.ACTIVE_MEDIA[z] != null) {
                WAVFactory.ACTIVE_MEDIA[z - 1] = WAVFactory.ACTIVE_MEDIA[z];
                if (WAVFactory.ACTIVE_MEDIA[z - 1].isAlive()) {
                    WAVFactory.ACTIVE_MEDIA[z - 1].updateNumber(z - 1);
                }
            }
        }
        WAVFactory.ACTIVE_MEDIA_COUNT--;
    }
}
