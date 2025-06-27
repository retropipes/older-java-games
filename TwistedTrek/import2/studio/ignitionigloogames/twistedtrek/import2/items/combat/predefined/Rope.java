/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.items.combat.predefined;

import studio.ignitionigloogames.twistedtrek.import2.battle.BattleTarget;
import studio.ignitionigloogames.twistedtrek.import2.creatures.StatConstants;
import studio.ignitionigloogames.twistedtrek.import2.effects.Effect;
import studio.ignitionigloogames.twistedtrek.import2.items.combat.CombatItem;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.SoundConstants;

public class Rope extends CombatItem {
	public Rope() {
		super("Rope", 50, BattleTarget.ENEMY);
	}

	@Override
	protected void defineFields() {
		this.sound = SoundConstants.SOUND_BIND;
		this.e = new Effect("Roped", 4);
		this.e.setEffect(Effect.EFFECT_MULTIPLY, StatConstants.STAT_AGILITY, 0, Effect.DEFAULT_SCALE_FACTOR,
				StatConstants.STAT_NONE);
		this.e.setMessage(Effect.MESSAGE_INITIAL, "You wind a rope around the enemy!");
		this.e.setMessage(Effect.MESSAGE_SUBSEQUENT, "The enemy is tied up, and unable to act!");
		this.e.setMessage(Effect.MESSAGE_WEAR_OFF, "The rope falls off!");
	}
}
