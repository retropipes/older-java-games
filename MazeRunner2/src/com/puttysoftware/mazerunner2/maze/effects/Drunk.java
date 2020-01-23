/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: mazer5d@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.effects;

import com.puttysoftware.randomrange.RandomRange;

public class Drunk extends MazeEffect {
    // Constructor
    public Drunk(final int newRounds) {
        super("Drunk", newRounds);
    }

    @Override
    public int[] modifyMove2(int[] arg) {
        RandomRange rx = new RandomRange(0, 1);
        RandomRange ry = new RandomRange(0, 1);
        arg[0] += rx.generate();
        arg[1] += ry.generate();
        return arg;
    }
}