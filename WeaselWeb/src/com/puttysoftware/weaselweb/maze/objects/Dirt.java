/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.objects;

import com.puttysoftware.weaselweb.maze.generic.GenericGround;

public class Dirt extends GenericGround {
    // Constructors
    public Dirt() {
        super();
    }

    @Override
    public String getName() {
        return "Dirt";
    }

    @Override
    public String getPluralName() {
        return "Squares of Dirt";
    }

    @Override
    public String getDescription() {
        return "Dirt is one of the many types of ground.";
    }
}