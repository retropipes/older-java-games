/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.battle.window.time;

import studio.ignitionigloogames.twistedtrek.import2.prefs.PreferencesManager;

class WindowTimeBattleSpeed {
    // Constants
    private static int SPEED_FACTOR = 10;

    // Constructor
    private WindowTimeBattleSpeed() {
	// Do nothing
    }

    // Method
    static int getSpeed() {
	return PreferencesManager.getBattleSpeed() / WindowTimeBattleSpeed.SPEED_FACTOR;
    }
}