/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.items.combat.predefined;

import studio.ignitionigloogames.twistedtrek.import2.battle.BattleTarget;
import studio.ignitionigloogames.twistedtrek.import2.creatures.StatConstants;
import studio.ignitionigloogames.twistedtrek.import2.effects.Effect;
import studio.ignitionigloogames.twistedtrek.import2.items.combat.CombatItem;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.SoundConstants;

public class Fireball extends CombatItem {
    public Fireball() {
	super("Fireball", 500, BattleTarget.ENEMY);
    }

    @Override
    protected void defineFields() {
	this.sound = SoundConstants.SOUND_BOLT;
	this.e = new Effect("Fireball", 1);
	this.e.setEffect(Effect.EFFECT_ADD, StatConstants.STAT_CURRENT_HP, -3);
	this.e.setScaleStat(StatConstants.STAT_LEVEL);
	this.e.setScaleFactor(1.5);
	this.e.setMessage(Effect.MESSAGE_INITIAL, "You throw a fireball at the enemy!");
	this.e.setMessage(Effect.MESSAGE_SUBSEQUENT, "The fireball sears the enemy badly, dealing LOTS of damage!");
    }
}
