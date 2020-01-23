/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.items.combat;

import studio.ignitionigloogames.twistedtrek.import1.effects.DamageEffect;
import studio.ignitionigloogames.twistedtrek.import1.effects.Effect;

public class Bomb extends CombatUsableItem {
    public Bomb() {
	super("Bomb", 30, 'E');
    }

    @Override
    protected void defineFields() {
	this.sound = "bomb";
	this.e = new DamageEffect("Bomb", 5, 1, Effect.DEFAULT_SCALE_FACTOR, Effect.DEFAULT_SCALE_STAT,
		Effect.DEFAULT_DECAY_RATE);
	this.e.setMessage(Effect.MESSAGE_INITIAL, "You throw a bomb at the enemy!");
	this.e.setMessage(Effect.MESSAGE_SUBSEQUENT, "The bomb goes BOOM, inflicting a little damage!");
    }
}
