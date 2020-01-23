package net.worldwizard.dungeondiver.creatures.ai;

import net.worldwizard.dungeondiver.creatures.Creature;

public class AttackAIRoutine extends AIRoutine {
    @Override
    public int getNextAction(final Creature c) {
        return AIRoutine.ACTION_ATTACK;
    }
}
