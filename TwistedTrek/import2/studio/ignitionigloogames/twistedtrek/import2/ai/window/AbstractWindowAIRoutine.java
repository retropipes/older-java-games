/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.ai.window;

import studio.ignitionigloogames.twistedtrek.import2.creatures.AbstractCreature;
import studio.ignitionigloogames.twistedtrek.import2.spells.Spell;

public abstract class AbstractWindowAIRoutine {
    // Fields
    protected Spell spell;
    public static final int ACTION_ATTACK = 0;
    public static final int ACTION_FLEE = 1;
    public static final int ACTION_CAST_SPELL = 2;
    public static final int ACTION_STEAL = 3;
    public static final int ACTION_DRAIN = 4;
    public static final int ACTION_USE_ITEM = 5;

    // Methods
    public abstract int getNextAction(AbstractCreature c);

    public void newRoundHook() {
        // Do nothing
    }

    public final Spell getSpellToCast() {
        return this.spell;
    }
}
