/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.items.combat.predefined;

import studio.ignitionigloogames.twistedtrek.import2.battle.BattleTarget;
import studio.ignitionigloogames.twistedtrek.import2.creatures.StatConstants;
import studio.ignitionigloogames.twistedtrek.import2.effects.Effect;
import studio.ignitionigloogames.twistedtrek.import2.items.combat.CombatItem;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.SoundConstants;

public class Bolt extends CombatItem {
	public Bolt() {
		super("Bolt", 100, BattleTarget.ENEMY);
	}

	@Override
	protected void defineFields() {
		this.sound = SoundConstants.SOUND_BOLT;
		this.e = new Effect("Bolt", 1);
		this.e.setEffect(Effect.EFFECT_ADD, StatConstants.STAT_CURRENT_HP, -4);
		this.e.setScaleStat(StatConstants.STAT_LEVEL);
		this.e.setScaleFactor(1.0);
		this.e.setMessage(Effect.MESSAGE_INITIAL, "You throw a bolt at the enemy!");
		this.e.setMessage(Effect.MESSAGE_SUBSEQUENT, "The bolt ZAPS the enemy, dealing damage!");
	}
}
