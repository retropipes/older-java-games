/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.battle.damageengines;

import studio.ignitionigloogames.twistedtrek.import2.creatures.AbstractCreature;
import studio.ignitionigloogames.twistedtrek.import2.prefs.PreferencesManager;

public abstract class AbstractDamageEngine {
    // Methods
    public abstract int computeDamage(AbstractCreature enemy, AbstractCreature acting);

    public abstract boolean enemyDodged();

    public abstract boolean weaponMissed();

    public abstract boolean weaponCrit();

    public abstract boolean weaponPierce();

    public abstract boolean weaponFumble();

    public static AbstractDamageEngine getPlayerInstance() {
	final int difficulty = PreferencesManager.getGameDifficulty();
	if (difficulty == PreferencesManager.DIFFICULTY_VERY_EASY) {
	    return new VeryEasyDamageEngine();
	} else if (difficulty == PreferencesManager.DIFFICULTY_EASY) {
	    return new EasyDamageEngine();
	} else if (difficulty == PreferencesManager.DIFFICULTY_NORMAL) {
	    return new NormalDamageEngine();
	} else if (difficulty == PreferencesManager.DIFFICULTY_HARD) {
	    return new HardDamageEngine();
	} else if (difficulty == PreferencesManager.DIFFICULTY_VERY_HARD) {
	    return new VeryHardDamageEngine();
	} else {
	    return new NormalDamageEngine();
	}
    }

    public static AbstractDamageEngine getEnemyInstance() {
	final int difficulty = PreferencesManager.getGameDifficulty();
	if (difficulty == PreferencesManager.DIFFICULTY_VERY_EASY) {
	    return new VeryHardDamageEngine();
	} else if (difficulty == PreferencesManager.DIFFICULTY_EASY) {
	    return new HardDamageEngine();
	} else if (difficulty == PreferencesManager.DIFFICULTY_NORMAL) {
	    return new NormalDamageEngine();
	} else if (difficulty == PreferencesManager.DIFFICULTY_HARD) {
	    return new EasyDamageEngine();
	} else if (difficulty == PreferencesManager.DIFFICULTY_VERY_HARD) {
	    return new VeryEasyDamageEngine();
	} else {
	    return new NormalDamageEngine();
	}
    }
}
