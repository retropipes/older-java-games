/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.ai;

import studio.ignitionigloogames.chrystalz.prefs.PreferencesManager;

public final class MapAIRoutinePicker {
    // Constructors
    private MapAIRoutinePicker() {
        // Do nothing
    }

    // Methods
    public static AbstractMapAIRoutine getNextRoutine() {
        final int difficulty = PreferencesManager.getGameDifficulty();
        if (difficulty == PreferencesManager.DIFFICULTY_VERY_EASY) {
            return new VeryEasyMapAIRoutine();
        } else if (difficulty == PreferencesManager.DIFFICULTY_EASY) {
            return new EasyMapAIRoutine();
        } else if (difficulty == PreferencesManager.DIFFICULTY_NORMAL) {
            return new NormalMapAIRoutine();
        } else if (difficulty == PreferencesManager.DIFFICULTY_HARD) {
            return new HardMapAIRoutine();
        } else if (difficulty == PreferencesManager.DIFFICULTY_VERY_HARD) {
            return new VeryHardMapAIRoutine();
        } else {
            return new NormalMapAIRoutine();
        }
    }
}
