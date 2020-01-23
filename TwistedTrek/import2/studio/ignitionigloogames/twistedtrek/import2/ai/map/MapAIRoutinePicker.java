/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.ai.map;

import studio.ignitionigloogames.twistedtrek.import2.prefs.PreferencesManager;

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
