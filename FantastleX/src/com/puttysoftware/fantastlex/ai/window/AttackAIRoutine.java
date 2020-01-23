package com.puttysoftware.fantastlex.ai.window;

import com.puttysoftware.fantastlex.creatures.AbstractCreature;

public class AttackAIRoutine extends AbstractWindowAIRoutine {
    @Override
    public int getNextAction(final AbstractCreature c) {
        return AbstractWindowAIRoutine.ACTION_ATTACK;
    }
}
