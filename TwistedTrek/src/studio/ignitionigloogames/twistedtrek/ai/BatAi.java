/* Twisted Trek: A Dual-World Action RPG */
package studio.ignitionigloogames.twistedtrek.ai;

import studio.ignitionigloogames.twistedtrek.Creature;

public class BatAi extends CreatureAi {
    public BatAi(final Creature newCreature) {
        super(newCreature);
    }

    @Override
    public void onUpdate() {
        this.wander();
        this.wander();
    }
}
