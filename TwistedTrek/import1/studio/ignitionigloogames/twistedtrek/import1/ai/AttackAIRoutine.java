/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.ai;

import studio.ignitionigloogames.twistedtrek.import1.creatures.Creature;

public class AttackAIRoutine extends AIRoutine {
    @Override
    public int getNextAction(final Creature c) {
        return AIRoutine.ACTION_ATTACK;
    }
}
