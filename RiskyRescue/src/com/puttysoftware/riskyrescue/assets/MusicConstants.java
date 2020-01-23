/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue.assets;

public class MusicConstants {
    // Public Music Constants
    public static final int BATTLE = 0;
    public static final int DUNGEON = 1;

    // Private constructor
    private MusicConstants() {
        // Do nothing
    }

    static String getMusicNameForID(final int musicID, final int offset) {
        String musicStr = "";
        if (musicID == MusicConstants.BATTLE) {
            musicStr = "battle" + Integer.toString(offset) + ".mod";
        }
        if (musicID == MusicConstants.DUNGEON) {
            musicStr = "dungeon" + Integer.toString(offset) + ".mod";
        }
        return musicStr;
    }
}
