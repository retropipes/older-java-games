package com.puttysoftware.mastermaze.ai.window;

import com.puttysoftware.mastermaze.creatures.Creature;

public class AttackAIRoutine extends WindowAIRoutine {
    @Override
    public int getNextAction(final Creature c) {
        return WindowAIRoutine.ACTION_ATTACK;
    }
}
