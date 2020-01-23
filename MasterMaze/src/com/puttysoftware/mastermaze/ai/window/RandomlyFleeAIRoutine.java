package com.puttysoftware.mastermaze.ai.window;

import com.puttysoftware.mastermaze.creatures.Creature;
import com.puttysoftware.randomrange.RandomRange;

public class RandomlyFleeAIRoutine extends WindowAIRoutine {
    @Override
    public int getNextAction(final Creature c) {
        final RandomRange chance = new RandomRange(1, 100);
        final RandomRange flee = new RandomRange(1, 100);
        if (chance.generate() <= flee.generate()) {
            return WindowAIRoutine.ACTION_FLEE;
        } else {
            return WindowAIRoutine.ACTION_ATTACK;
        }
    }
}
