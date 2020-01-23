/*  TallerTower: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.tallertower.battle.map.time;

import com.puttysoftware.tallertower.prefs.PreferencesManager;

class MapTimeBattleSpeed {
    // Constants
    private static int SPEED_FACTOR = 20;

    // Constructor
    private MapTimeBattleSpeed() {
        // Do nothing
    }

    // Method
    static int getSpeed() {
        return PreferencesManager.getBattleSpeed()
                / MapTimeBattleSpeed.SPEED_FACTOR;
    }
}