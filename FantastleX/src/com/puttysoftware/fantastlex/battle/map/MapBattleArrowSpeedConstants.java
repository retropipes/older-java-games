/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.battle.map;

import com.puttysoftware.fantastlex.prefs.PreferencesManager;

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