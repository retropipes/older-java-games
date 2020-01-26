/*  DynamicDungeon: An RPG
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: mazer5d@worldwizard.net
 */
package net.dynamicdungeon.dynamicdungeon.dungeon.effects;

import net.dynamicdungeon.randomrange.RandomRange;

public class Drunk extends DungeonEffect {
    // Constructor
    public Drunk(final int newRounds) {
        super("Drunk", newRounds);
    }

    @Override
    public int[] modifyMove2(final int... arg) {
        final RandomRange rx = new RandomRange(0, 1);
        final RandomRange ry = new RandomRange(0, 1);
        arg[0] += rx.generate();
        arg[1] += ry.generate();
        return arg;
    }
}