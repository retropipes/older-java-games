/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.resourcemanagers;

public class MusicConstants {
    // Public Music Constants
    public static final int MUSIC_BATTLE = 0;
    public static final int MUSIC_BOSS = 1;
    public static final int MUSIC_CREATE = 2;
    public static final int MUSIC_EXPLORING = 3;
    // Private Music Constants
    private static final String[] MUSIC_NAMES = { "battle", "boss", "create",
            "dungeon" };

    // Private constructor
    private MusicConstants() {
        // Do nothing
    }

    static String getMusicNameForID(final int musicID, final int offset) {
        String offsetStr = "";
        if (musicID == MusicConstants.MUSIC_EXPLORING) {
            offsetStr = Integer.toString(offset);
            if (offset < 10) {
                offsetStr = "0" + offsetStr;
            }
        }
        return MusicConstants.MUSIC_NAMES[musicID] + offsetStr + ".ogg";
    }
}
