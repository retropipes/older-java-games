/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.effects;

import net.worldwizard.randomnumbers.RandomRange;

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