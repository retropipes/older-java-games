/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.items.combat.predefined;

import studio.ignitionigloogames.twistedtrek.import2.battle.BattleTarget;
import studio.ignitionigloogames.twistedtrek.import2.creatures.StatConstants;
import studio.ignitionigloogames.twistedtrek.import2.effects.Effect;
import studio.ignitionigloogames.twistedtrek.import2.items.combat.CombatItem;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.SoundConstants;

public class Potion extends CombatItem {
    public Potion() {
	super("Potion", 250, BattleTarget.SELF);
    }

    @Override
    protected void defineFields() {
	this.sound = SoundConstants.SOUND_HEAL;
	this.e = new Effect("Potion", 1);
	this.e.setEffect(Effect.EFFECT_ADD, StatConstants.STAT_CURRENT_HP, 5);
	this.e.setScaleStat(StatConstants.STAT_LEVEL);
	this.e.setScaleFactor(1.25);
	this.e.setMessage(Effect.MESSAGE_INITIAL, "You drink a healing potion!");
	this.e.setMessage(Effect.MESSAGE_SUBSEQUENT, "You feel better!");
    }
}
