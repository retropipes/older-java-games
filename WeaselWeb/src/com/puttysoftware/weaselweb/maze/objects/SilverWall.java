/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.objects;

import com.puttysoftware.weaselweb.maze.generic.GenericMultipleLock;

public class SilverWall extends GenericMultipleLock {
    // Constructors
    public SilverWall() {
        super(new SilverSquare());
    }

    @Override
    public String getName() {
        return "Silver Wall";
    }

    @Override
    public String getPluralName() {
        return "Silver Walls";
    }

    @Override
    public String getDescription() {
        return "Silver Walls are impassable without enough Silver Squares.";
    }
}