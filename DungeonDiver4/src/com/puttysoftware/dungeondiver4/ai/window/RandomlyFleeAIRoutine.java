package com.puttysoftware.dungeondiver4.ai.window;

import com.puttysoftware.dungeondiver4.creatures.AbstractCreature;
import com.puttysoftware.randomrange.RandomRange;

public class RandomlyFleeAIRoutine extends AbstractWindowAIRoutine {
    @Override
    public int getNextAction(final AbstractCreature c) {
        final RandomRange chance = new RandomRange(1, 100);
        final RandomRange flee = new RandomRange(1, 100);
        if (chance.generate() <= flee.generate()) {
            return AbstractWindowAIRoutine.ACTION_FLEE;
        } else {
            return AbstractWindowAIRoutine.ACTION_ATTACK;
        }
    }
}
