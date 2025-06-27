/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.ai.window;

import studio.ignitionigloogames.twistedtrek.import2.prefs.PreferencesManager;

public final class WindowAIRoutinePicker {
	// Methods
	public static AbstractWindowAIRoutine getNextRoutine() {
		final int difficulty = PreferencesManager.getGameDifficulty();
		if (difficulty == PreferencesManager.DIFFICULTY_VERY_EASY) {
			return new VeryEasyWindowAIRoutine();
		} else if (difficulty == PreferencesManager.DIFFICULTY_EASY) {
			return new EasyWindowAIRoutine();
		} else if (difficulty == PreferencesManager.DIFFICULTY_NORMAL) {
			return new NormalWindowAIRoutine();
		} else if (difficulty == PreferencesManager.DIFFICULTY_HARD) {
			return new HardWindowAIRoutine();
		} else if (difficulty == PreferencesManager.DIFFICULTY_VERY_HARD) {
			return new VeryHardWindowAIRoutine();
		} else {
			return new NormalWindowAIRoutine();
		}
	}
}
