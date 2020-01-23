/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.ai;

import studio.ignitionigloogames.twistedtrek.import1.creatures.Creature;
import studio.ignitionigloogames.twistedtrek.import1.spells.Spell;

public abstract class AIRoutine {
    // Fields
    protected Spell spell;
    public static final int ACTION_ATTACK = 0;
    public static final int ACTION_FLEE = 1;
    public static final int ACTION_CAST_SPELL = 2;
    public static final int ACTION_STEAL = 3;
    public static final int ACTION_DRAIN = 4;
    public static final int ACTION_USE_ITEM = 5;

    // Methods
    public abstract int getNextAction(Creature c);

    public final Spell getSpellToCast() {
	return this.spell;
    }
}
