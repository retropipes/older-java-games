/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.battle;

import com.puttysoftware.dungeondiver3.prefs.PreferencesManager;

class ArrowSpeedConstants {
    // Constants
    private static int ARROW_SPEED_FACTOR = 6;

    // Constructor
    private ArrowSpeedConstants() {
        // Do nothing
    }

    // Method
    static int getArrowSpeed() {
        return PreferencesManager.getBattleSpeed()
                / ArrowSpeedConstants.ARROW_SPEED_FACTOR;
    }
}