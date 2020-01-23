/*  MasterMaze: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.battle.map;

import com.puttysoftware.mastermaze.prefs.PreferencesManager;

class MapBattleArrowSpeedConstants {
    // Constants
    private static int ARROW_SPEED_FACTOR = 6;

    // Constructor
    private MapBattleArrowSpeedConstants() {
        // Do nothing
    }

    // Method
    static int getArrowSpeed() {
        return PreferencesManager.getBattleSpeed()
                / MapBattleArrowSpeedConstants.ARROW_SPEED_FACTOR;
    }
}