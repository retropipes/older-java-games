package com.puttysoftware.mazerunner2.ai.window;

import com.puttysoftware.mazerunner2.creatures.AbstractCreature;

public class AttackAIRoutine extends AbstractWindowAIRoutine {
    @Override
    public int getNextAction(AbstractCreature c) {
        return AbstractWindowAIRoutine.ACTION_ATTACK;
    }
}
