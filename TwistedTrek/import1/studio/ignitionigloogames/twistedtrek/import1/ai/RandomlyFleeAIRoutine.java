/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.ai;

import studio.ignitionigloogames.randomrange.RandomRange;
import studio.ignitionigloogames.twistedtrek.import1.creatures.Creature;

public class RandomlyFleeAIRoutine extends AIRoutine {
    @Override
    public int getNextAction(final Creature c) {
	final RandomRange chance = new RandomRange(1, 100);
	final RandomRange flee = new RandomRange(1, 100);
	if (chance.generate() <= flee.generate()) {
	    return AIRoutine.ACTION_FLEE;
	} else {
	    return AIRoutine.ACTION_ATTACK;
	}
    }
}
