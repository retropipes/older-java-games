/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.maze.effects;

import com.puttysoftware.randomnumbers.RandomRange;

public class Drunk extends MazeEffect {
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