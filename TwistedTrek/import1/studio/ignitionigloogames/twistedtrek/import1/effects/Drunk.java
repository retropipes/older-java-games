/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.effects;

import studio.ignitionigloogames.randomrange.RandomRange;

public class Drunk extends Effect {
    // Constructor
    public Drunk(final int newRounds) {
	super("Drunk", newRounds);
    }

    @Override
    public int[] modifyMove2(final int[] arg) {
	final RandomRange rx = new RandomRange(0, 1);
	final RandomRange ry = new RandomRange(0, 1);
	arg[0] += rx.generate();
	arg[1] += ry.generate();
	return arg;
    }
}