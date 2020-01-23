/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.objects;

import com.puttysoftware.mazemode.generic.GenericBarrier;

public class HorizontalBarrier extends GenericBarrier {
    // Constructors
    public HorizontalBarrier() {
        super();
    }

    @Override
    public String getName() {
        return "Horizontal Barrier";
    }

    @Override
    public String getPluralName() {
        return "Horizontal Barriers";
    }

    @Override
    public String getDescription() {
        return "Horizontal Barriers are impassable - you'll need to go around them.";
    }
}