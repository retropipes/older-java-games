/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.resourcemanagers;

import com.puttysoftware.dungeondiver3.support.datamanagers.SoundDataManager;

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
