/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.battle.map;

import studio.ignitionigloogames.twistedtrek.import2.prefs.PreferencesManager;

public class MapBattleArrowSpeedConstants {
    // Constants
    private static int ARROW_SPEED_FACTOR = 8;

    // Constructor
    private MapBattleArrowSpeedConstants() {
        // Do nothing
    }

    // Method
    public static int getArrowSpeed() {
        return PreferencesManager.getBattleSpeed() / MapBattleArrowSpeedConstants.ARROW_SPEED_FACTOR;
    }
}