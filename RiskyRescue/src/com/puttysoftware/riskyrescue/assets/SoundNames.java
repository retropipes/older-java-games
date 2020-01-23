/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue.assets;

import com.puttysoftware.riskyrescue.assets.data.SoundDataManager;

class SoundNames {
    // Fields
    private static String[] SOUND_NAMES = null;

    // Private constructor
    private SoundNames() {
        // Do nothing
    }

    // Methods
    static String[] getSoundNames() {
        if (SoundNames.SOUND_NAMES == null) {
            SoundNames.SOUND_NAMES = SoundDataManager.getSoundData();
        }
        return SoundNames.SOUND_NAMES;
    }
}
