package com.puttysoftware.ddremix.resourcemanagers;

import com.puttysoftware.ddremix.datamanagers.MusicDataManager;

public class MusicConstants {
    // Public Music Constants
    public static final int MUSIC_BATTLE = 0;
    public static final int MUSIC_EXPLORING = 1;
    private static final String[] MUSIC_NAMES = MusicDataManager.getMusicData();

    // Private constructor
    private MusicConstants() {
        // Do nothing
    }

    static String getMusicName(final int ID) {
        return MusicConstants.MUSIC_NAMES[ID];
    }
}