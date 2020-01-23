/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue.battle;

import com.puttysoftware.riskyrescue.prefs.PreferencesManager;

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