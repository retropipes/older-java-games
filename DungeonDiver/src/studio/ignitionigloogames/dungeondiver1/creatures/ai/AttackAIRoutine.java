package studio.ignitionigloogames.dungeondiver1.creatures.ai;

import studio.ignitionigloogames.dungeondiver1.creatures.Creature;

public class AttackAIRoutine extends AIRoutine {
    @Override
    public int getNextAction(final Creature c) {
        return AIRoutine.ACTION_ATTACK;
    }
}
