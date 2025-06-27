/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.items.combat.predefined;

import studio.ignitionigloogames.twistedtrek.import2.battle.BattleTarget;
import studio.ignitionigloogames.twistedtrek.import2.creatures.StatConstants;
import studio.ignitionigloogames.twistedtrek.import2.effects.Effect;
import studio.ignitionigloogames.twistedtrek.import2.items.combat.CombatItem;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.SoundConstants;

public class Bomb extends CombatItem {
	public Bomb() {
		super("Bomb", 30, BattleTarget.ENEMY);
	}

	@Override
	protected void defineFields() {
		this.sound = SoundConstants.SOUND_EXPLODE;
		this.e = new Effect("Bomb", 1);
		this.e.setEffect(Effect.EFFECT_ADD, StatConstants.STAT_CURRENT_HP, -5);
		this.e.setScaleStat(StatConstants.STAT_LEVEL);
		this.e.setScaleFactor(0.75);
		this.e.setMessage(Effect.MESSAGE_INITIAL, "You throw a bomb at the enemy!");
		this.e.setMessage(Effect.MESSAGE_SUBSEQUENT, "The bomb goes BOOM, inflicting a little damage!");
	}
}
