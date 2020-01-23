package com.puttysoftware.dungeondiver4.ai.window;

import com.puttysoftware.dungeondiver4.creatures.AbstractCreature;

public class AttackAIRoutine extends AbstractWindowAIRoutine {
    @Override
    public int getNextAction(AbstractCreature c) {
        return AbstractWindowAIRoutine.ACTION_ATTACK;
    }
}
