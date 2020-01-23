/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.battle.map.time;

import studio.ignitionigloogames.twistedtrek.import2.prefs.PreferencesManager;

class MapTimeBattleSpeed {
    // Constants
    private static int SPEED_FACTOR = 20;

    // Constructor
    private MapTimeBattleSpeed() {
	// Do nothing
    }

    // Method
    static int getSpeed() {
	return PreferencesManager.getBattleSpeed() / MapTimeBattleSpeed.SPEED_FACTOR;
    }
}